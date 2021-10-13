package com.orange.trip.service.impl;

import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.trip.dao.TripInventoryElementDao;
import com.orange.trip.domain.TripInventoryElement;
import com.orange.trip.info.ElementPageInfo;
import com.orange.trip.info.TripInventoryElementInfo;
import com.orange.trip.service.TripInventoryElementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;

@Service
public class TripInventoryElementServiceImpl implements TripInventoryElementService {
    @Autowired
    TripInventoryElementDao inventoryElementDao;

    @Override
    public TripInventoryElement addElement(TripInventoryElementInfo info) {
        TripInventoryElement expensesElement = new TripInventoryElement();
        BeanUtils.copyProperties(info,expensesElement);
        inventoryElementDao.save(expensesElement);
        return expensesElement;
    }

    @Override
    public List<TripInventoryElement> getElements(Integer inventoryId) {

        return inventoryElementDao.findAllByInventoryId(inventoryId);
    }

    @Transactional
    @Override
    public void delElement(Integer id) {
        inventoryElementDao.delete(id);
    }

    @Transactional
    @Override
    public void upElementCheck(Integer id, Boolean isCheck) {
        inventoryElementDao.upElementCheck(id,isCheck);
    }

    @Override
    public PageVo getTripElement(ElementPageInfo pageInfo) {
     Page<TripInventoryElement> page= inventoryElementDao.findAll(ElementSpec.method1(pageInfo.getTripInventoryId()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"serialNumber"));
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),page.getContent());
    }

    public static class ElementSpec {
        public static Specification<TripInventoryElement> method1(Integer inventoryId) {
            return new Specification<TripInventoryElement>() {
                @Override
                public Predicate toPredicate(Root<TripInventoryElement> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Integer> ii = root.get("inventoryId");
                    Predicate predicate;
                    predicate = criteriaBuilder.and(criteriaBuilder.equal(ii,inventoryId));
                    return predicate;
                }
            };
        }
    }
}
