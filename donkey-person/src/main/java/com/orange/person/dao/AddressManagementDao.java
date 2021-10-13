package com.orange.person.dao;

import com.orange.person.domain.AddressManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author yusuwei
 * @date 2018-08-14 13:36
 **/
public interface AddressManagementDao extends JpaRepository<AddressManagement,Long> {

    @Query("select  a from AddressManagement a where a.userId=:u  order by a.createTime desc ")
    List<AddressManagement> queryAllByUserIdAndAddressType(@Param("u") String userId);

    @Query("select  a from AddressManagement a where a.userId=:u  and a.approximatelyAddress=:aa and  a.detailedAddress=:da and a.phone=:p and a.contacts=:c" )
    AddressManagement queryMsg(@Param("u") String userId, @Param("aa") String approximatelyAddress, @Param("da") String detailedAddress, @Param("p") String phone, @Param("c") String contacts);

    @Query("select  a from AddressManagement a where a.userId=:u  and a.addressDefault=:ad")
    List<AddressManagement> queryAllByUserIdAndAddressTypeAAndAddressDefault(@Param("u") String userId, @Param("ad") Boolean addressDefault);

    @Modifying
    @Query("update AddressManagement as  a set a.addressDefault=:ad where a.id=:id")
    void updatDefaultAddressById(@Param("id") Long id, @Param("ad") Boolean defaultAddress);
}
