package com.orange.person.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "tb_user_base")
public class UserBase implements SocialUserDetails,Serializable {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    String id;

    @Column(name = "username",length = 30)
    String username;
    @Column(name = "password",length = 30)
    String password;
    @Column(name = "nickname",length = 30)
    String nickname;
    @Column(name = "autograph",length=20)
    String autograph;
    /**
     * 电话号码
     */
    @Column(name = "user_phone",length = 20)
    String userPhone;

    /**
     * 头像地址
     */
    @Column(name = "portrait_url")
    String portraitUrl;
    @Column(name = "create_time")
    Long createTime;
    @Column(name = "account_non_Expired")
    boolean accountNonExpired=false;
    @Column(name = "account_non_locked")
    boolean accountNonLocked=false;
    @Column(name = "credentials_non_expired")
    boolean credentialsNonExpired=false;
    boolean enabled=false;

    @Column(name = "open_id",length = 50)
    String openId;

    @Column(name = "gender")
    Integer gender;

    @Column(name = "last_login_time")
    Long lastLoginTime;

    @Column(name = "union_id")
    String unionId;

    String country;

    String province;

    String city;

    /**
     * 访问量
     */
    @Column(name = "traffic_volume")
    Integer trafficVolume=0;

    /**
     * 用户等级
     */
    @Column(name = "user_grade")
    Integer userGrade=0;

    /**
     * 关注量
     */
    Integer followVolume=0;

    @Column(name = "back_image_url")
    Integer backImageUrl;

    @Column(name = "authentication_grade")
    Integer authenticationGrade=1;

    @Override
    public String getUserId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
