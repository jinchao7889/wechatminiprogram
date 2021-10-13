/**
 * 
 */
package com.orange.security.app.authentication.openid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orange.share.util.JsonUtil;
import com.orange.share.wxconfig.ServiceProperty;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.orange.security.core.properties.SecurityConstants;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	// ~ Static fields/initializers
	// =====================================================================================

	private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
	private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
	private boolean postOnly = true;
	private String codeParameter = "code";
	private String viParameter = "vi";
	private String dataParameter = "encryptedData";
	// ~ Constructors
	// ===================================================================================================

	public OpenIdAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID, "POST"));
	}

	// ~ Methods
	// ========================================================================================================

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String openid = obtainOpenId(request);
		String sessionKey="";
		Map map=null;
		if (openid==null){
			String code = obtainCode(request);
			if (code == null) {
				throw new RuntimeException("code不能为null");
			}

			try {
				map= simpleClientHttpRequestFactory(code, ServiceProperty.WX_APP_ID,ServiceProperty.WX_APP_KEY);
			} catch (IOException e) {
				e.printStackTrace();
			}
			openid= (String) map.get("openid");
			sessionKey=(String) map.get("session_key");
		}

		String providerId = obtainProviderId(request);
		String vi = obtainVi(request);
		String encryptedData = obtainencryptedData(request);
		log.info("key:"+providerIdParameter+",providerId:"+ vi);
		if (StringUtils.isBlank(vi)){
			throw new RuntimeException("vi不能为null");
		}
		if (StringUtils.isBlank(encryptedData)){
			throw new RuntimeException("encryptedData不能为null");
		}
		if ( StringUtils.isBlank(openid)) {

			throw new RuntimeException("openid不能为null");
		}
		if (StringUtils.isBlank(providerId)) {
			throw new RuntimeException("providerId不能为null");
		}

		OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openid, providerId,sessionKey,encryptedData,vi);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}


	/**
	 * 获取openId
	 */
	protected String obtainOpenId(HttpServletRequest request) {
		return request.getParameter(openIdParameter);
	}
	/**
	 * 获取提供商id
	 */
	protected String obtainCode(HttpServletRequest request) {
		return request.getParameter(codeParameter);
	}
	/**
	 * 获取微信传入的CODE
	 */
	protected String obtainProviderId(HttpServletRequest request) {
		return request.getParameter(providerIdParameter);
	}
	protected String obtainVi(HttpServletRequest request) {
		return request.getParameter(viParameter);
	}
	protected String obtainencryptedData(HttpServletRequest request) {
		return request.getParameter(dataParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 *
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details
	 *            set
	 */
	protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from
	 * the login request.
	 *
	 *
	 *            the parameter name. Defaults to "username".
	 */
	public void setOpenIdParameter(String openIdParameter) {
		Assert.hasText(openIdParameter, "Username parameter must not be empty or null");
		this.openIdParameter = openIdParameter;
	}


	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getOpenIdParameter() {
		return openIdParameter;
	}

	public String getProviderIdParameter() {
		return providerIdParameter;
	}

	public void setProviderIdParameter(String providerIdParameter) {
		this.providerIdParameter = providerIdParameter;
	}

	public Map simpleClientHttpRequestFactory(String code, String appId, String appKey) throws IOException {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(2000);
		requestFactory.setReadTimeout(2000);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
		Map map = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appKey+"&js_code="+code+"&grant_type=authorization_code", Map.class);
		return map;
	}
	public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
		public WxMappingJackson2HttpMessageConverter(){
			List<MediaType> mediaTypes = new ArrayList<>();
			mediaTypes.add(MediaType.TEXT_PLAIN);
			setSupportedMediaTypes(mediaTypes);// tag6
		}
	}
}
