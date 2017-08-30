package com.iot.core.oauth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.iot.common.utils.HttpClientUtils;
import com.iot.common.utils.WebUtils;
import com.iot.core.wrapper.ConfigFileWrapper;

/**
 * 2015/9/29
 * <p/>
 * 对需要保护的资源进行拦截过滤处理 需要与SHIRO的安全整合并加入到SHIRO 流程中 相关配置见 rs-security.xml 文件
 * 
 * @author Shengzhao Li
 */
public class OAuth2Filter extends AuthenticatingFilter implements
		InitializingBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OAuth2Filter.class);
	private String resourceId;

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) throws Exception {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		final String accessToken = getAccessToken(httpRequest);

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);

		JSONObject jb = HttpClientUtils.postJson(
				ConfigFileWrapper.OAUTH_CHECK_URL, params);

		String username = null;
		if (jb != null) {
			LOGGER.debug("Set username and clientId from AccessToken: {}");
		} else {
			LOGGER.debug("Not found AccessToken by access_token: {}",
					accessToken);
		}

		return new OAuth2Token(accessToken, resourceId).setUserId(username);
	}

	private String getAccessToken(HttpServletRequest httpRequest) {
		final String authorization = httpRequest.getHeader("Authorization");
		if (authorization != null) {
			// bearer ab1ade69-d122-4844-ab23-7b109ad977f0
			return authorization.substring(6).trim();
		}
		return httpRequest.getParameter(OAuth.OAUTH_ACCESS_TOKEN);
	}

	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		return executeLogin(request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException ae, ServletRequest request,
			ServletResponse response) {

		final OAuthResponse oAuthResponse;
		try {
			oAuthResponse = OAuthRSResponse.errorResponse(401)
					.setError(OAuthError.ResourceResponse.INVALID_TOKEN)
					.setErrorDescription(ae.getMessage()).buildJSONMessage();

			WebUtils.writeOAuthJsonResponse((HttpServletResponse) response,
					oAuthResponse);

		} catch (Exception e) {
			LOGGER.error("Build JSON message error", e);
			throw new IllegalStateException(e);
		}

		return false;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
