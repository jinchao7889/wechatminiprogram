package com.orange.person.controller;

import com.orange.person.domain.AddressManagement;
import com.orange.person.service.AddressManagementService;
import com.orange.share.response.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/address")
public class AddressManagementController {

    @Autowired
    AddressManagementService addressManagementService;
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseWrapper addAddress(@Validated @RequestBody AddressManagement addressManagement){

        return  ResponseWrapper.markCustom(true, HttpStatus.OK.toString(),"成功", addressManagementService.addAddress(addressManagement));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseWrapper getAddress(){

        return  ResponseWrapper.markCustom(true, HttpStatus.OK.toString(),"成功", addressManagementService.getAddressByType());
    }

    @RequestMapping(value = "/del/{addressId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseWrapper delAddress(@PathVariable("addressId") Long addressId){

        return  ResponseWrapper.markCustom(true, HttpStatus.OK.toString(),"成功", addressManagementService.delAddress(addressId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseWrapper updateAddress( @RequestBody AddressManagement addressManagement){

        return  ResponseWrapper.markCustom(true, HttpStatus.OK.toString(),"成功", addressManagementService.upAddress(addressManagement));
    }

    @RequestMapping(value = "/get/defaultAddress", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseWrapper getDefaultAddress(){
        return  ResponseWrapper.markCustom(true, HttpStatus.OK.toString(),"成功", addressManagementService.getDefaultAddress());
    }
}

