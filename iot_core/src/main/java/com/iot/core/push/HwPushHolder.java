package com.iot.core.push;

import java.io.IOException;

import nsp.NSPClient;
import nsp.support.common.NSPException;

import com.iot.common.utils.log.LogUtils;

public class HwPushHolder {

	private NSPClient client;

	private HwPushHolder() {

	}

	private static class SingleClassHolder {
		private static HwPushHolder instance = new HwPushHolder();
		static {
			LogUtils.getInstance().infoSystem("HwPushHolder",
					"Instance init...");
		}
	}

	public static HwPushHolder getInstance() {
		return SingleClassHolder.instance;
	}

	public void refreshNSPClient(String token) throws NSPException, IOException {
		client = new NSPClient(token);
		client.initHttpConnections(30, 50);// 设置每个路由的连接数和最大连接数
		client.initKeyStoreStream(
				HwPushHolder.class.getResource("/hwpush/mykeystorebj.jks")
						.openStream(), "123456");// 如果访问https必须导入证书流和密码
	}

	public NSPClient getNspClient() {
		return client;
	}

}
