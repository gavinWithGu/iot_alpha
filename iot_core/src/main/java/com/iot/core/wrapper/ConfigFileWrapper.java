package com.iot.core.wrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.web.multipart.MultipartFile;

import com.iot.common.utils.DateUtils;
import com.iot.common.utils.GeneralUtils;
import com.iot.common.utils.UUIDGenerator;
import com.iot.common.utils.log.LogUtils;
import com.iot.core.zookeeper.ZkConfig;
import com.iot.core.zookeeper.config.PropRepos;

public class ConfigFileWrapper {

	public static Properties propsSystem = new Properties();
	public static Properties propsJdbc = new Properties();
	public static Properties propsJms = new Properties();

	public static String TEST_CONFIG = "";

	public static String FILE_STORE_BASE_PATH;
	public static String WATERMARK_PIC_URL;

	public static String JDBC_DRIVERCLASSNAME;
	public static String JDBC_URL;
	public static String JDBC_USERNAME;
	public static String JDBC_PASSWORD;

	public static String JMS_QUEUE_NAME;
	public static String JMS_BROKE_URL;

	/**
	 * 文件上传路径相关
	 */
	public static String BASE_PATH; // 根路径
	public static String ABSOLUTE_FILEPATH; // 文件系统绝对路径
	public static String SUB_FILEPATH; // tomcat虚拟映射路径

	public static String OAUTH_CHECK_URL; // oauth server地址
	public static int QNIQUE_BEACON_ROUND_INTERVAL; // 数据清洗时间间隔

	public static int SENSOR_DATA_HISTORY_LIMIT; // sensor data历史页，显示的条数限制

	public static int STORM_EXECUTOR_NUMBER;
	public static int STORM_WORKER_NUMBER;

	public static String MQTT_URL;
	public static String MQTT_TOPIC_NAME;
	static {
		if (!ZkConfig.isZK_CLUSTER_ALIVE())
			loadFromLocal();
	}

	public static void refresh() {
		TEST_CONFIG = PropRepos.getContent("test.config");
		FILE_STORE_BASE_PATH = PropRepos.getContent("file.store.base.path");
		WATERMARK_PIC_URL = PropRepos.getContent("watermark.pic.url");

		JMS_QUEUE_NAME = PropRepos.getContent("jms.queue.name");
		JMS_BROKE_URL = PropRepos.getContent("jms.broke.url");

		ABSOLUTE_FILEPATH = PropRepos.getContent("absolute.filepath");
		SUB_FILEPATH = PropRepos.getContent("sub.path");

		BASE_PATH = ABSOLUTE_FILEPATH + "/" + SUB_FILEPATH;

		OAUTH_CHECK_URL = PropRepos.getContent("oauth.check.url");

		SENSOR_DATA_HISTORY_LIMIT = Integer.parseInt(PropRepos
				.getContent("sensor.data.history.limit"));

		// mqtt broker address
		MQTT_URL = PropRepos.getContent("mqtt.broker");
		MQTT_TOPIC_NAME= PropRepos.getContent("mqtt.topic.name");
		// storm参数
		STORM_EXECUTOR_NUMBER = Integer.parseInt(PropRepos
				.getContent("storm.executor.number"));

		STORM_WORKER_NUMBER = Integer.parseInt(PropRepos
				.getContent("storm.worker.number"));
		
		QNIQUE_BEACON_ROUND_INTERVAL = Integer.parseInt(PropRepos
				.getContent("qnique.beacon.round.interval"));

	}

	public static void loadFromLocal() {
		InputStream inSystem = ConfigFileWrapper.class.getClassLoader()
				.getResourceAsStream("local/system.properties");

		InputStream inJdbc = ConfigFileWrapper.class.getClassLoader()
				.getResourceAsStream("local/jdbc.properties");

		InputStream inJms = ConfigFileWrapper.class.getClassLoader()
				.getResourceAsStream("local/jms.properties");
		try {
			propsSystem.load(inSystem);
			propsJdbc.load(inJdbc);
			propsJms.load(inJms);

			if (GeneralUtils.isNotNullOrZeroLength(propsSystem
					.getProperty("test.config")))
				TEST_CONFIG = propsSystem.getProperty("test.config").trim();

			JDBC_DRIVERCLASSNAME = propsJdbc
					.getProperty("jdbc.driverClassName");
			JDBC_URL = propsJdbc.getProperty("jdbc.url");
			JDBC_USERNAME = propsJdbc.getProperty("jdbc.username");
			JDBC_PASSWORD = propsJdbc.getProperty("jdbc.password");

			JMS_QUEUE_NAME = propsJms.getProperty("jms.queue.name");
			JMS_BROKE_URL = propsJms.getProperty("jms.broke.url");

			ABSOLUTE_FILEPATH = propsSystem.getProperty("absolute.filepath");
			SUB_FILEPATH = propsSystem.getProperty("sub.path");

			BASE_PATH = ABSOLUTE_FILEPATH + "/" + SUB_FILEPATH;

			OAUTH_CHECK_URL = propsSystem.getProperty("oauth.check.url");

			QNIQUE_BEACON_ROUND_INTERVAL = Integer.parseInt(propsSystem
					.getProperty("qnique.beacon.round.interval"));

			SENSOR_DATA_HISTORY_LIMIT = Integer.parseInt(propsSystem
					.getProperty("sensor.data.history.limit"));

			// mqtt broker address
			MQTT_URL = propsSystem.getProperty("mqtt.broker");
			MQTT_TOPIC_NAME=  propsSystem.getProperty("mqtt.topic.name");
			// storm参数
			STORM_EXECUTOR_NUMBER = Integer.parseInt(propsSystem
					.getProperty("storm.executor.number"));
			STORM_WORKER_NUMBER = Integer.parseInt(propsSystem
					.getProperty("storm.worker.number"));
		} catch (Exception ex) {
			LogUtils.getInstance().errorSystem("ConfigFileUtil",
					"Error loading properties from Local file system!", ex);
		} finally {
			if (inSystem != null) {
				try {
					inSystem.close();
				} catch (IOException e) {

				}
			}
		}
	}

	/**
	 * 解压到指定目录
	 * 
	 * @param zipPath
	 * @param descDir
	 */
	public static void unZipFiles(String zipPath, String descDir)
			throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @param zipFile
	 * @param descDir
	 */
	@SuppressWarnings("rawtypes")
	public static String unZipFiles(File zipFile, String descDir)
			throws IOException {
		String indexPath = "";

		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}

			// 输出文件路径信息
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();

		}
		return indexPath;

	}

	public static String[] uploadFile(MultipartFile file)
			throws IllegalStateException, IOException {
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

			String basePath = BASE_PATH;
			File baseFile = new File(basePath);
			if (!baseFile.exists()) {
				baseFile.mkdirs();
			}
			String parentFileName = DateUtils.date2String(new Date(),
					"yyyy_MM_dd");
			basePath = basePath + "/" + parentFileName;
			baseFile = new File(basePath);
			if (!baseFile.exists()) {
				baseFile.mkdirs();
			}

			String name = DateUtils
					.getCurrentTime(DateUtils.YYYY_MM_DD_HH_MM_SS_SSS_PATTERN)
					+ UUIDGenerator.get32UUID();
			String finalFilePath = baseFile + "/" + name + "." + suffix;

			String sourceFinalFilePath = baseFile + "/" + name + "_source"
					+ "." + suffix;
			File finalFile = new File(finalFilePath);

			File sourcefinalFile = new File(sourceFinalFilePath);

			if (finalFile.exists()) {
				finalFile.delete();
			}

			if (sourcefinalFile.exists()) {
				sourcefinalFile.delete();
			}

			// 生产原始图片:picname+source+后缀
			file.transferTo(finalFile);

			// StringBuilder sb = new StringBuilder();
			// sb.append(basePath);
			// sb.append("/");
			// sb.append(name);
			// sb.append("/");

			if ("zip".equals(suffix)) {
				unZipFiles(finalFile, basePath + "/" + name + "/");
				return new String[] { basePath + "/" + name + "/",
						SUB_FILEPATH + "/" + parentFileName + "/" + name };
			}

			return new String[] {
					fileName,
					SUB_FILEPATH + "/" + parentFileName + "/" + name + "."
							+ suffix };
		} else {
			return null;
		}
	}

	public interface SENSOR_DATA_KEY {
		// uuid（6字节） + transmit_rssi（1） + battery（1） + temp温度（1） + humid湿度（1）
		int SENSOR_BASIC = 100;
		int BATTERY = SENSOR_BASIC + 1;
		int TEMPERATURE = SENSOR_BASIC + 2;
		int HUMIDITY = SENSOR_BASIC + 3;
	}
}
