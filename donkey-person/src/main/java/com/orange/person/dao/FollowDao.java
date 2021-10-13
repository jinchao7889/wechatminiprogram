package com.orange.person.dao;

import com.orange.person.domain.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowDao extends JpaRepository<Follow,Integer> {
    int deleteByBeConcernedAndConcern(String beConcerned,String concern);

    Follow findByBeConcernedAndConcern(String beConcerned,String concern);

    Page<Follow> findAllByConcern(String concern, Pageable pageable);
}
