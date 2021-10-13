/**
 * 
 */
package com.orange.security.rbac.authentication;


import com.orange.person.dao.UserBaseDao;
import com.orange.person.dao.UserConnectionDao;
import com.orange.person.domain.UserBase;
import com.orange.person.domain.UserConnection;
import com.orange.person.service.UserBaseService;
import com.orange.person.service.UserConnectionService;
import com.orange.security.app.authentication.openid.OpenIdAuthenticationToken;
import com.orange.security.app.authentication.openid.SocialUserDetailsExportService;
import com.orange.share.util.AesCbcUtil;
import com.orange.share.util.JsonUtil;
import com.orange.share.util.WxInfoDecrypt;
import com.orange.share.wxconfig.ServiceProperty;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class RbacUserDetailsService implements UserDetailsService, SocialUserDetailsService, SocialUserDetailsExportService {

	@Autowired
	UserConnectionService userConnectionService;
	@Autowired
	UserBaseDao userBaseDao;
	@Autowired
	UserConnectionDao userConnectionDao;
	@Autowired
	UserBaseService userBaseService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public SocialUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("用户名密码登陆："+username);
		UserBase userBase= userBaseDao.findByUsername(username);
		log.info("结果："+JsonUtil.objectToString(userBase));
		return userBase;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
		return null;
	}

//	@Transactional(rollbackFor = Exception.class)
	@Override
	public SocialUserDetails loadUserByProvideIdAndProvideUserId(OpenIdAuthenticationToken authenticationToken) throws UsernameNotFoundException {

		if (StringUtils.isBlank(authenticationToken.getProviderId()) || StringUtils.isBlank((String) authenticationToken.getPrincipal())){
			log.error("参数为NULL");
			return null;
		}
		String userId = userConnectionService.getUserId(authenticationToken.getProviderId(), (String) authenticationToken.getPrincipal());
		UserBase userBase;
		if (StringUtils.isBlank(userId)){
			 userBase = new UserBase();
			userBase.setOpenId((String) authenticationToken.getPrincipal());
			String result;
			try {
				log.info("微信数据"+authenticationToken.getSessionKey());
				 result= AesCbcUtil.decrypt(authenticationToken.getEncryptedData(),authenticationToken.getSessionKey(),authenticationToken.getIv(),"UTF-8");

			} catch (Exception e) {
				log.error("微信解密失败",e);
				throw new RuntimeException("微信解密失败");
			}

			JSONObject userInfoJSON = JSONObject.fromObject(result);
				log.info("微信解密后结果"+userInfoJSON.getString("nickName"));
				userBase.setPortraitUrl(userInfoJSON.getString("avatarUrl"));
				userBase.setNickname(userInfoJSON.getString("nickName"));
				userBase.setGender((Integer) userInfoJSON.get("gender"));
				userBase.setCity(userInfoJSON.getString("city"));
				userBase.setProvince(userInfoJSON.getString("province"));
				userBase.setCountry(userInfoJSON.getString("country"));
				userBase.setCreateTime(System.currentTimeMillis()/1000);
				userBase.setLastLoginTime(System.currentTimeMillis()/1000);
				userBaseDao.save(userBase);
				UserConnection userConnection = new UserConnection();
				userConnection.setUserId(userBase.getUserId());
				userConnection.setProviderUserId((String) authenticationToken.getPrincipal());
				userConnection.setProviderId(authenticationToken.getProviderId());

				userConnectionDao.save(userConnection);


//			UserConnection userConnection = new UserConnection();
//			userConnection.setUserId(userBase.getUserId());
//			userConnectionDao.save(userConnection);
		}else {
			 userBase = userBaseService.getUserBase(userId);
			 log.info("userbase:"+ JsonUtil.objectToString(userBase));
		}

		return userBase;
	}
}
