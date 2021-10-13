package com.orange.activity.dao;

import com.orange.activity.domain.ActivityCarouselImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityCarouselImgDao extends JpaRepository<ActivityCarouselImg,Integer> {

    List<ActivityCarouselImg> findAllByActivityId(String activityId);
}
