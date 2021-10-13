package com.orange.trip.service.impl;

import com.orange.trip.dao.TripInventoryDao;
import com.orange.trip.dao.TripInventoryElementDao;
import com.orange.trip.domain.TripInventory;
import com.orange.trip.domain.TripInventoryElement;
import com.orange.trip.info.TripInventoryInfo;
import com.orange.trip.service.TripInventoryService;
import com.orange.trip.vo.TripInventoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripInventoryServiceImpl implements TripInventoryService {
    @Autowired
    TripInventoryDao tripInventoryDao;
    @Autowired
    TripInventoryElementDao expensesElementDao;
    @Override
    public List<TripInventoryVo> addTripExpenses(List<TripInventoryInfo> tripInventoryInfos) {
        List<TripInventoryVo> tripInventoryVos = new ArrayList<>();
        for (TripInventoryInfo inventoryInfo :tripInventoryInfos){
            TripInventory tripInventory = new TripInventory();
            TripInventoryVo tripInventoryVo = new TripInventoryVo();
            BeanUtils.copyProperties(inventoryInfo,tripInventory);
            tripInventoryDao.saveAndFlush(tripInventory);
            BeanUtils.copyProperties(tripInventory,tripInventoryVo);
            List<TripInventoryElement > tripInventoryElements = new ArrayList<>();
            for (TripInventoryElement element :inventoryInfo.getInventoryElements()){
                element.setInventoryId(tripInventory.getId());
                expensesElementDao.saveAndFlush(element);
                tripInventoryElements.add(element);
            }
            tripInventoryVo.setInventoryElements(tripInventoryElements);
            tripInventoryVos.add(tripInventoryVo);
        }

        return tripInventoryVos;
    }

    @Override
    public List<TripInventory> getTripExpenses(Long tripId) {

        return tripInventoryDao.findAllByTripId(tripId);
    }

    @Override
    public List<TripInventoryVo> getAllTripInventoryVo(Long tripId) {
        List<TripInventory> inventories=tripInventoryDao.findAllByTripId(tripId);
        List<TripInventoryVo> vos = new ArrayList<>();
        for (TripInventory inventory: inventories){
            TripInventoryVo inventoryVo = new TripInventoryVo();
            BeanUtils.copyProperties(inventory,inventoryVo);
            List<TripInventoryElement> elements= expensesElementDao.findAllByInventoryId(inventory.getId());

            inventoryVo.setInventoryElements(elements);
            vos.add(inventoryVo);
        }
        return vos;
    }

    @Override
    public void delTripInventory(Integer id) {
        tripInventoryDao.delete(id);
    }
}
