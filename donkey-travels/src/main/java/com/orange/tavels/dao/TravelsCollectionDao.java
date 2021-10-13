package com.orange.tavels.dao;


import com.orange.tavels.domain.TravelsCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TravelsCollectionDao extends JpaRepository<TravelsCollection,Integer> {
    void deleteByTravelsIdAndUserId(String travelsId,String userId);
    TravelsCollection findTravelsCollectionByUserIdAndTravelsId(String userId,String travelsId);
    TravelsCollection findTravelsCollectionByUserIdAndTravelsIdAndEnable(String userId,String travelsId,Boolean enable);

    @Query("select count(*) from TravelsCollection t where t.travelsId=:ti and t.enable=true ")
    Long findCountByTravelsId(@Param("ti") String travelsId);
}
