/**
 * 
 */
package com.orange.security.rbac.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.orange.security.core.authorize.AuthorizeConfigProvider;


@Component
@Order(Integer.MAX_VALUE)
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

	/* (non-Javadoc)
	 * @see com.imooc.security.core.authorize.AuthorizeConfigProvider#config(org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
	 */
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config
			.antMatchers("/fonts/**"

					,"/bookkeeping_book/weixin/api/wxRefundNotify"
					,"/bookkeeping_book/weixin/api/wxNotify"

					,"/rotation_chart/get_main_page_chart"


					,"/product_type/get/**"
					,"/product/get_sell_well"
					,"/shop_carousel_img/get/**"
					,"/product/get_page"
					, "/product/get/**"
					 ,"/product_comment/get_page"

					,"/activity/get_page"
					,"/activity/get/**"
					,"/activity_leave_message/get_page"
					,"/activity_leave_message/sec/get_page"


					,"/travels_pool/get/travelsContent/**"
					,"/travels_comment/get_page"
		 			,"/travels_comment/sec/get_page"

					,"/travels_pool/get_page"
					,"/travels_pool/get/getTripDetail/**"
					,"/travels_pool/get/tripContent/**"
					, "/trip_questions/get_page"
					,"/travels_pool/trip/get_page"

		 			,"/**/*.html"
					,"/**/*.js"
					,"/**/*.css"
					,"/**/*.json"
					,"/**/*.svg"
					,"/hello"
					,"/travels_pool/get_page"
			).permitAll()
			.antMatchers(HttpMethod.GET, 

					"/admin/me",
					"/resource").authenticated()
			.anyRequest()
				.access("@rbacService.hasPermission(request, authentication)");
		return true;
	}

}
