package com.orange.activity.service.impl;

import com.orange.activity.constants.ActivityStatus;
import com.orange.activity.constants.EnterStatus;
import com.orange.activity.dao.ActivityCarouselImgDao;
import com.orange.activity.dao.ActivityDao;
import com.orange.activity.dao.ActivityDeatilContentDao;
import com.orange.activity.dao.ActivityEnterDao;
import com.orange.activity.domain.Activity;
import com.orange.activity.domain.ActivityCarouselImg;
import com.orange.activity.domain.ActivityDeatilContent;
import com.orange.activity.domain.ActivityEnter;
import com.orange.activity.dto.ActivityDto;
import com.orange.activity.info.ActivityPageInfo;
import com.orange.activity.service.ActivityService;
import com.orange.activity.vo.ActivityQueryVo;
import com.orange.activity.vo.ActivityVo;
import com.orange.order.constant.OrderStatus;
import com.orange.order.service.OrderService;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl  implements ActivityService {
    @Autowired
    ActivityDao activityDao;
    @Autowired
    ActivityDeatilContentDao deatilContentDao;
    @Autowired
    ActivityCarouselImgDao activityCarouselImgDao;
    @Autowired
    ActivityEnterDao activityEnterDao;
    @Autowired
    OrderService orderService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ActivityDto addActivity(ActivityDto activityDto) {
        Activity activity = new Activity();
        if(activityDto.getActivityStatus()==ActivityStatus.PEOPLE_FULL.getCode()||activityDto.getActivityStatus()==ActivityStatus.TIME_END.getCode()){
            activityDto.setActivityStatus(ActivityStatus.ONGOING.getCode());
        }
        BeanUtils.copyProperties(activityDto,activity);
        activity.setCreateTime(System.currentTimeMillis() / 1000);

        activityDao.saveAndFlush(activity);

        ActivityDeatilContent deatilContent = new ActivityDeatilContent();
        for (ActivityCarouselImg carouselImg :activityDto.getCarouselImgs()){
            carouselImg.setActivityId(activity.getId());
            activityCarouselImgDao.saveAndFlush(carouselImg);
        }

        deatilContent.setId(activity.getId());
        deatilContent.setContent(activityDto.getContent());
        deatilContentDao.save(deatilContent);

        return activityDto;
    }

    @Override
    public PageVo getPageActivity(ActivityPageInfo pageInfo) {
        Page<Activity> page= activityDao.findAll(ActivitySpec.method1(), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<ActivityVo> list = new ArrayList<>();
        for (Activity activity : page.getContent()){
            ActivityVo activityVo = new ActivityVo();
            BeanUtils.copyProperties(activity,activityVo);
            if (activity.getEnrolmentEndTime()<System.currentTimeMillis()/1000||activity.getActivityStatus()==ActivityStatus.TIME_END.getCode()){
                activityVo.setActivityStatus(ActivityStatus.TIME_END.getCode());
            }
            if (activity.getMaxPeopleNumber()<= activity.getEnrolmentPeopleNumber()||activity.getActivityStatus()==ActivityStatus.PEOPLE_FULL.getCode()){
                activityVo.setActivityStatus(ActivityStatus.PEOPLE_FULL.getCode());
            }
            list.add(activityVo);
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),list);
    }

    @Override
    public PageVo getMgPageActivity(ActivityPageInfo pageInfo) {
        Page<Activity> page= activityDao.findAll(ActivitySpec.method2(pageInfo.getActivityStatus()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),page.getContent());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateActivity(String activityId,Integer status) {

        if (ActivityStatus.ALL.getCode()<status&&status<=ActivityStatus.COMPLETED.getCode()){
            if (status==ActivityStatus.COMPLETED.getCode()){
                Activity activity = activityDao.findOne(activityId);
                if (activity==null){
                    return;
                }
                List<ActivityEnter> enters =  activityEnterDao.findAllByActivityId(activityId);
                for (ActivityEnter enter : enters){
                    enter.setPaymentStatus(EnterStatus.PEOPLE_FULL.getCode());

                    activityEnterDao.saveAndFlush(enter);
                    if (StringUtils.isBlank(enter.getOrderId())){
                        orderService.upOrder(enter.getOrderId(), OrderStatus.CLOSE.getCode());
                    }
                }


            }
            activityDao.updateStatus(activityId,status);

        }
    }

    @Override
    public ActivityDto getActivity(String id) {
        Activity activity = activityDao.findOne(id);
        ActivityDto activityDto = new ActivityDto();
        BeanUtils.copyProperties(activity,activityDto);
        ActivityDeatilContent deatilContent = deatilContentDao.findOne(id);
        activityDto.setContent(deatilContent.getContent());
        activityDto.setCarouselImgs(activityCarouselImgDao.findAllByActivityId(id));
        if (activity.getEnrolmentEndTime()<System.currentTimeMillis()/1000){
            activityDto.setActivityStatus(ActivityStatus.TIME_END.getCode());
        }
        if (activity.getMaxPeopleNumber()<= activity.getEnrolmentPeopleNumber()){
            activityDto.setActivityStatus(ActivityStatus.PEOPLE_FULL.getCode());
        }
        return activityDto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delImg(Integer id) {
        activityCarouselImgDao.delete(id);
    }

    @Override
    public List<ActivityQueryVo> getActivityQuery(String act) {
        List<Activity> activities =  activityDao.findAllByTitleLike("%"+act+"%");
        List<ActivityQueryVo> list = activities.stream().map(u-> new ActivityQueryVo(u.getId(),u.getTitle())).collect(Collectors.toList());

        return list;
    }


    public static class ActivitySpec {
        public static Specification<Activity> method1() {
            return new Specification<Activity>() {
                @Override
                public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> activityStatus = root.get("activityStatus");
                    Predicate predicate;
                    predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.notEqual(activityStatus, ActivityStatus.COMPLETED.getCode()));

                    return predicate;
                }
            };
        }

        public static Specification<Activity> method2(Integer status) {
            return new Specification<Activity>() {
                @Override
                public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> activityStatus = root.get("activityStatus");
                    Predicate predicate;
                    if (status==0) {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true));
                    }else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(activityStatus, status));
                    }
                    return predicate;
                }
            };
        }
    }
}
