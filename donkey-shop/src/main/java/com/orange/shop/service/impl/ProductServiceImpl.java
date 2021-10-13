package com.orange.shop.service.impl;

import com.orange.share.page.SortUtil;
import com.orange.share.util.JsonUtil;
import com.orange.share.vo.PageVo;
import com.orange.shop.dao.*;
import com.orange.shop.domain.*;
import com.orange.shop.dto.ProductDto;
import com.orange.shop.info.ProductMgPageInfo;
import com.orange.shop.info.ProductPageInfo;
import com.orange.shop.info.ProductSellWellPageInfo;
import com.orange.shop.info.ProductUpPageInfo;
import com.orange.shop.service.ProductService;
import com.orange.shop.vo.ProductDetailVo;
import com.orange.shop.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Autowired
    ProductCarouselImgDao carouselImgDao;
    @Autowired
    ProductIntroduceContentDao contentDao;
    @Autowired
    ProductAdditionalChargesDao additionalChargesDao;
    @Autowired
    ProductSpecsDao productSpecsDao;
    @Autowired
    ProductTypeDao productTypeDao;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductDetailVo addProduct(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);
        productDao.saveAndFlush(product);
        log.info(JsonUtil.objectToString(productDto));


        List<ProductCarouselImg> carouselImgs = carouselImgDao.findAllByProductId(product.getId());

        for (ProductCarouselImg carouselImg:productDto.getCarouselImg()){
            carouselImg.setProductId(product.getId());
            carouselImgDao.saveAndFlush(carouselImg);
            carouselImgs.add(carouselImg);
        }
        for (ProductAdditionalCharges additionalCharges: productDto.getAdditionalCharges()){
            additionalCharges.setProductId(product.getId());
            additionalChargesDao.saveAndFlush(additionalCharges);
        }
        List<ProductSpecs> productSpecs =  productSpecsDao.findAllByProductIdAndEnable(product.getId(),true);
        boolean flag = true;
        for (ProductSpecs ps:productDto.getProductSpecs()){
            if (ps.getId()==null){
                ps.setProductId(product.getId());
                productSpecsDao.saveAndFlush(ps);
            }else {
                for (ProductSpecs specs :productSpecs){
                    if (ps.getId().longValue()==specs.getId().longValue()){
                        productSpecsDao.saveAndFlush(ps);
                        break;
                    }
                }
            }
        }
        for(ProductSpecs specs :productSpecs){
            for (ProductSpecs ps:productDto.getProductSpecs()){
                if(ps.getId().longValue()==specs.getId().longValue()){
                    flag=false;
                    break;
                }
            }
            if (!flag){
                flag=true;
            }else {
                specs.setEnable(false);
                productSpecsDao.saveAndFlush(specs);
            }
        }

        ProductIntroduceContent content = new ProductIntroduceContent();
        content.setId(product.getId());
        content.setContent(productDto.getDetailContent());
        contentDao.save(content);
        ProductDetailVo detailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product,detailVo);
        detailVo.setCarouselImg(carouselImgs);
        return detailVo;
    }

    @Override
    public ProductDetailVo getProductDetail(Long productId) {
        Product product = productDao.findOne(productId);
        ProductDetailVo detailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product,detailVo);
        List<ProductCarouselImg> carouselImgs =carouselImgDao.findAllByProductId(productId);
        detailVo.setCarouselImg(carouselImgs);
        ProductIntroduceContent content= contentDao.findOne(productId);
        if (content!=null){
            detailVo.setDetailContent(content.getContent());
        }
        List<ProductAdditionalCharges> charges= additionalChargesDao.findAllByProductId(productId);
        detailVo.setAdditionalCharges(charges);

        detailVo.setProductSpecs(productSpecsDao.findAllByProductIdAndEnable(product.getId(),true));

        return detailVo;
    }

    @Override
    public PageVo getPageProduct(ProductPageInfo pageInfo) {
        Page<Product> page = productDao.findAll(ProductSpec.method1(pageInfo.getShopId(),pageInfo.getProductTypeId(),true), buildDESC(pageInfo.getPage(),pageInfo.getSize()));

        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverProductVo(page.getContent()));
    }

    @Override
    public PageVo getAllPageProduct(ProductUpPageInfo pageInfo) {
        Page<Product> page = productDao.findAll(ProductSpec.method3(pageInfo.getShopId(),pageInfo.getProductTypeId(),pageInfo.getUpperShelf()), buildDESC(pageInfo.getPage(),pageInfo.getSize()));

        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverProductVo(page.getContent()));
    }

    private PageRequest buildDESC(int page, int size) {
        Sort sort = new Sort(Sort.Direction.ASC, "serialNumber");//.and(new Sort(Sort.Direction.DESC, "createTime"));
        return new PageRequest(page, size, sort);
    }

    @Override
    public PageVo getSellWellProduct(ProductSellWellPageInfo pageInfo) {
        Page<Product> page = productDao.findAll(ProductSpec.method2(pageInfo.getShopId()),buildDESC(pageInfo.getPage(),pageInfo.getSize()));

        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverProductVo(page.getContent()));
    }

    private List<ProductVo> coverProductVo(List<Product> products){
        List<ProductVo> vos = new ArrayList<>();

        for (Product product:products){
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product,productVo);
            ProductType type = productTypeDao.findOne(product.getProductTypeId());
            if (type==null){
                productVo.setProductType("未分配");

            }else {
                productVo.setProductType(type.getTypeName());

            }
            vos.add(productVo);
        }
        return vos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void upUpperShelf(Long id, Boolean isUpperShelf) {
        productDao.updateUpperShelf(id,isUpperShelf);
    }

    @Override
    public void upProductStock(Long id, Integer stock) {
        productDao.updateProductStock(id,stock);
    }

    /**
     * 获得所有商品
     * @param pageInfo
     * @return
     */
    @Override
    public PageVo getPageMgProduct(ProductMgPageInfo pageInfo) {
        Page<Product> page = productDao.findAll(ProductSpec.method1(pageInfo.getShopId(),pageInfo.getProductTypeId(),pageInfo.getUpperShelf()), buildDESC(pageInfo.getPage(),pageInfo.getSize()));
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverProductVo(page.getContent()));
    }

    public static class ProductSpec {
        public static Specification<Product> method1(Integer shopId,Integer productTypeId,Boolean upperShelf) {
            return new Specification<Product>() {
                @Override
                public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("isEnable");
                    Path<Integer> si = root.get("shopId");
                    Path<Integer> pti = root.get("productTypeId");
                    Path<Boolean> isUpperShelf = root.get("isUpperShelf");
                    Predicate predicate;

                    if (upperShelf==null){
                        if(shopId==0){
                            if (productTypeId==0){
                                predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true));
                            }else {
                                predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(pti, productTypeId));
                            }
                        }else {
                            if (productTypeId==0){
                                predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId));
                            }else {
                                predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId),criteriaBuilder.equal(pti, productTypeId));
                            }
                        }
                    }else {
                        if(shopId==0){
                            if (productTypeId==0){
                                predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(isUpperShelf,upperShelf));
                            }else {
                                predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(pti, productTypeId),criteriaBuilder.equal(isUpperShelf,upperShelf));
                            }
                        }else {
                            if (productTypeId==0){
                                predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId),criteriaBuilder.equal(isUpperShelf,upperShelf));
                            }else {
                                predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId),criteriaBuilder.equal(pti, productTypeId),criteriaBuilder.equal(isUpperShelf,upperShelf));
                            }
                        }
                    }

                    return predicate;
                }
            };
        }

        public static Specification<Product> method2(Integer shopId ) {
            return new Specification<Product>() {
                @Override
                public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("isEnable");
                    Path<Integer> si = root.get("shopId");
                    Path<Integer> isw = root.get("isSellWell");
                    Path<Boolean> isUpperShelf = root.get("isUpperShelf");
                    Predicate predicate;
                    predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId),criteriaBuilder.equal(isw, true),criteriaBuilder.equal(isUpperShelf,true));
                    return predicate;
                }
            };
        }

        public static Specification<Product> method3(Integer shopId,Integer productTypeId,Boolean isUp ) {
            return new Specification<Product>() {
                @Override
                public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("isEnable");
                    Path<Integer> si = root.get("shopId");
                    Path<Integer> pti = root.get("productTypeId");
                    Path<Boolean> isUpperShelf = root.get("isUpperShelf");

                    Predicate predicate;
                    if (isUp!=null){
                        if (productTypeId==0){
                            predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId),criteriaBuilder.equal(isUpperShelf,isUp));
                        }else {
                            predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId),criteriaBuilder.equal(pti, productTypeId),criteriaBuilder.equal(isUpperShelf,isUp));
                        }
                    }else {
                        if (productTypeId==0){
                            predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId));
                        }else {
                            predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(si, shopId),criteriaBuilder.equal(pti, productTypeId));
                        }
                    }

                    return predicate;
                }
            };
        }
    }
}
