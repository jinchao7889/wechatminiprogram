package com.orange.shop.service;

import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;
import com.orange.shop.domain.Product;
import com.orange.shop.dto.ProductDto;
import com.orange.shop.info.ProductMgPageInfo;
import com.orange.shop.info.ProductPageInfo;
import com.orange.shop.info.ProductSellWellPageInfo;
import com.orange.shop.info.ProductUpPageInfo;
import com.orange.shop.vo.ProductDetailVo;

public interface ProductService {
    /**
     * 新增商品
     * @param product
     * @return
     */
    ProductDetailVo addProduct(ProductDto product);

    /**
     * 获得详细的产品
     * @param productId
     * @return
     */
    ProductDetailVo getProductDetail(Long productId);

    /**
     * 分页获得
     * @param pageInfo
     * @return
     */
    PageVo getPageProduct(ProductPageInfo pageInfo);

    PageVo getAllPageProduct(ProductUpPageInfo pageInfo);

    PageVo getSellWellProduct(ProductSellWellPageInfo pageInfo);

    /**
     * 是否上架
     * @param id
     * @param isUpperShelf
     */
    void upUpperShelf(Long id,Boolean isUpperShelf);

    /**
     * 修改库存
     * @param id
     * @param stock
     */
    void upProductStock(Long id,Integer stock);

    PageVo getPageMgProduct(ProductMgPageInfo pageInfo);
}
