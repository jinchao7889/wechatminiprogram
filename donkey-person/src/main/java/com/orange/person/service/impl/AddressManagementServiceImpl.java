package com.orange.person.service.impl;


import com.orange.person.dao.AddressManagementDao;
import com.orange.person.domain.AddressManagement;
import com.orange.person.service.AddressManagementService;
import com.orange.share.constant.ReturnCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class AddressManagementServiceImpl implements AddressManagementService {
    @Autowired
    AddressManagementDao addressManagementDao;
    @Override
    public Long addAddress(AddressManagement addressManagement) {
        String userId=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        addressManagement.setUserId(userId);
        addressManagement.setCreateTime((new Date()).getTime());
        if (addressManagement.isAddressDefault()){
            List<AddressManagement> addressManagements= addressManagementDao.queryAllByUserIdAndAddressTypeAAndAddressDefault(userId,true);
            if (addressManagements !=null &&addressManagements.size()>0){
                for (AddressManagement a : addressManagements){
                    addressManagementDao.updatDefaultAddressById(a.getId(),false);
                }
            }
        }
        addressManagementDao.save(addressManagement);
        return addressManagement.getId();
    }

    @Override
    public String delAddress(Long id) {
         addressManagementDao.delete(id);
        return "sucess";
    }

    @Override
    public List<AddressManagement> getAddressByType() {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return addressManagementDao.queryAllByUserIdAndAddressType(userId);
    }

    @Override
    public String upAddress(AddressManagement addressManagement) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AddressManagement a= addressManagementDao.getOne(addressManagement.getId())  ;
        if (StringUtils.isNotBlank(addressManagement.getPhone())){
            a.setPhone(addressManagement.getPhone());
        }
        if (StringUtils.isNotBlank(addressManagement.getApproximatelyAddress())){
            a.setApproximatelyAddress(addressManagement.getApproximatelyAddress());
        }
        if (StringUtils.isNotBlank(addressManagement.getContacts())){
            a.setContacts(addressManagement.getContacts());
        }
        if (StringUtils.isNotBlank(addressManagement.getDetailedAddress())){
            a.setDetailedAddress(addressManagement.getDetailedAddress());
        }
        if (addressManagement.isAddressDefault()){
            List<AddressManagement> addressManagements= addressManagementDao.queryAllByUserIdAndAddressTypeAAndAddressDefault(userId,true);
            if (addressManagements !=null &&addressManagements.size()>0){
                for (AddressManagement ad : addressManagements){
                    addressManagementDao.updatDefaultAddressById(ad.getId(),false);
                }
            }
        }
        a.setAddressDefault(addressManagement.isAddressDefault());
        a.setUpdateTime(System.currentTimeMillis()/1000);
        addressManagementDao.saveAndFlush(a);
        return ReturnCode.UPDATE_SUCCESS.toString();
    }

    @Override
    public AddressManagement getDefaultAddress() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AddressManagement> addressManagements= addressManagementDao.queryAllByUserIdAndAddressTypeAAndAddressDefault(userId,true);
        if (addressManagements !=null &&addressManagements.size()>0) {
             return addressManagements.get(0);
        }
        return null;
    }
}
