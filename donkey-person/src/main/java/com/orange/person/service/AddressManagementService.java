package com.orange.person.service;


import com.orange.person.domain.AddressManagement;

import java.util.List;


public interface AddressManagementService {
    Long addAddress(AddressManagement addressManagement);
    String delAddress(Long id);
    List<AddressManagement> getAddressByType();
    String upAddress(AddressManagement addressManagement);
    AddressManagement getDefaultAddress();
}
