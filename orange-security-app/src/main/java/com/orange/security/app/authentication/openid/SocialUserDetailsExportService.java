package com.orange.security.app.authentication.openid;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

public interface SocialUserDetailsExportService {
    SocialUserDetails loadUserByProvideIdAndProvideUserId(OpenIdAuthenticationToken authenticationToken) throws UsernameNotFoundException;
}
