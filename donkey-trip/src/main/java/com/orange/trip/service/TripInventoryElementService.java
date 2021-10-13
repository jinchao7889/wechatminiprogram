package com.orange.trip.service;

import com.orange.share.vo.PageVo;
import com.orange.trip.domain.TripInventoryElement;
import com.orange.trip.info.ElementPageInfo;
import com.orange.trip.info.TripInventoryElementInfo;

import java.util.List;

public interface TripInventoryElementService {
    TripInventoryElement addElement(TripInventoryElementInfo info);
    List<TripInventoryElement> getElements(Integer inventoryId);
    void delElement(Integer id);
    void upElementCheck(Integer id,Boolean isCheck);

    PageVo getTripElement(ElementPageInfo pageInfo);

}
