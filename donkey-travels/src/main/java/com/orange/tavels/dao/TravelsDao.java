package com.orange.tavels.dao;

import com.orange.tavels.domain.Travels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TravelsDao extends JpaRepository<Travels,String> , JpaSpecificationExecutor<Travels> {

    @Modifying
    @Query("update Travels t set t.browseVolume=(t.browseVolume + 1) where t.id=:id")
    int updateBrowseVolumePush(@Param("id") String id);

    @Modifying
    @Query("update Travels t set t.commentVolume=(t.commentVolume+1) where t.id=:id")
    int updateCommentVolumePush(@Param("id") String id);

    @Modifying
    @Query("update Travels t set t.commentVolume=(t.commentVolume-1) where t.id=:id")
    int updateCommentVolumeReduce(@Param("id") String id);

    @Modifying
    @Query("update Travels t set t.collectionVolume=(t.collectionVolume+1) where t.id=:id")
    int updateCollectionVolumePush(@Param("id") String id);

    @Modifying
    @Query("update Travels t set t.collectionVolume=(t.collectionVolume-1) where t.id=:id")
    int updateCollectionVolumeReduce(@Param("id") String id);

    @Modifying
    @Query("update Travels t set t.enable=false where t.id=:id")
    int delEnable(@Param("id") String id);

    @Modifying
    @Query("update Travels t set t.travelsStatus=:ts where t.id=:id")
    int updateStatus(@Param("id") String id,@Param("ts") Integer travelsStatus);
}
