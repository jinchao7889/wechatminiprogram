/**
 * 
 */
package com.orange.security.rbac.service.impl;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.orange.share.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.orange.security.rbac.service.RbacService;


@Slf4j
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();

		if ("anonymousUser".equals(userId)) {
			return false;
		}
		boolean hasPermission = true;

		return hasPermission;
	}

}
