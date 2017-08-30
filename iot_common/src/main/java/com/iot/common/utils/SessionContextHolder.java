/*    
 * Copyright (c) 2013 ISoftStone, Inc.
 * All right reserved.
 *
 * 文件名：      SessionContextHolder.java
 * 作者:     Jacky Wang
 * 创建日期： 2013-5-30 上午10:24:36
 * 版本：           
 *
 */
package com.iot.common.utils;

import javax.servlet.http.HttpSession;

import com.iot.common.constant.Constants;


/**
 * 用户Session信息全局上下文
 */
public class SessionContextHolder {
	private static ThreadLocal<HttpSession> contextHolder = new ThreadLocal<HttpSession>();

	public static HttpSession getSession() {
		return contextHolder.get();
	}

	public static void setSession(HttpSession session) {
		contextHolder.set(session);
	}

	public static Object getCurrentUser() {
		Object user = null;
		if (SessionContextHolder.getSession() != null) {
			user = SessionContextHolder.getSession().getAttribute(Constants.SESSION_KEY_LOGIN_USER);
		}
		return user;
	}
}
