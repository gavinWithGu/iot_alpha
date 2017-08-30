
package com.iot.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.iot.common.constant.Constants;
import com.iot.common.utils.SessionContextHolder;

/**
 * @ClassName: LoginFilter
 */
public class LoginFilter extends HttpServlet implements Filter {

	public Logger logger = Logger.getLogger(LoginFilter.class);

	private FilterConfig filterConfig;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// 把session放到上下文中
		SessionContextHolder.setSession(session);
		String path = req.getContextPath();

		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		String requestPath = req.getServletPath();
		logger.info("requestPath " + requestPath);
		try {
			boolean flag = (requestPath.indexOf("login.jsp") != -1)
					|| (requestPath.indexOf("mkRandCodePic.do") != -1)
					|| (requestPath.indexOf("merchantReg.jsp") != -1)
					|| (requestPath.indexOf("login.do") != -1
							|| (requestPath.indexOf("error-session-out.html") != -1)
							|| (requestPath.indexOf("pwd.jsp") != -1)
							|| (requestPath.indexOf("pwd2.jsp") != -1)
							|| (requestPath.indexOf("pwd3.jsp") != -1)
							|| (requestPath.indexOf("pwd4.jsp") != -1)
							|| (requestPath.indexOf("pwd5.jsp") != -1)
							|| (requestPath.indexOf("checkUserInfo.do") != -1)
							|| (requestPath.indexOf("sendValidateCode.do") != -1)
							|| (requestPath.indexOf("validateGetPwdBackCode.do") != -1)
							|| (requestPath.indexOf("changePwd.do") != -1)
							|| (requestPath.indexOf("index.html") != -1)
							|| (requestPath.indexOf("register.html") != -1)
							|| (requestPath.indexOf("sendCode.do") != -1)
							|| (requestPath.indexOf("register.do") != -1)
							|| (requestPath.indexOf("forgetPassword.html") != -1)
							|| (requestPath.indexOf("checkRepeat.do") != -1)
							|| (requestPath.indexOf("checkCode.do") != -1)
							|| (requestPath.indexOf("checkLogin.do") != -1)
							|| (requestPath.indexOf("helpfile.html") != -1)
							|| (requestPath.indexOf("serCon.html") != -1)
							|| (requestPath.indexOf("footerInfo.html") != -1)
							|| (requestPath.indexOf("prizeDraw.do") != -1)
							|| (requestPath.indexOf("sendRandCode.do") != -1) 
							|| (requestPath.indexOf("checkRandCode.do") != -1)
							|| (requestPath.indexOf("beaconOwner/insert.do") != -1)
							
					);
			if (!flag) {
				// 如果session不为空，则可以浏览其他页面
				if (session.getAttribute(Constants.SESSION_KEY_LOGIN_USER) != null) {
					filterChain.doFilter(request, response);
					return;
				} else {
					res.setHeader("sessionstatus", "timeout");
					res.setStatus(201);
					// 跳转到登陆页
					response.getWriter().write(
							"<script language=\"javascript\">window.opener=null; window.location.href = '"
									+ basePath
									+ "error-session-out.html';</script>");
					return;
				}
			} else {
				filterChain.doFilter(request, response);
				return;
			}

		} catch (Exception e) {
			filterConfig.getServletContext().log(e.getMessage());
			// 跳转到错误页
			res.sendRedirect(basePath + "error-500.html");
			return;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
