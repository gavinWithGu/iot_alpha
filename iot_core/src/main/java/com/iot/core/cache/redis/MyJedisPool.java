package com.iot.core.cache.redis;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class MyJedisPool {

	private static final MyJedisPool instance = new MyJedisPool();

	private MyJedisPool() {
	}

	// jedis池
	private static JedisPool pool;
	// shardedJedis池
	private static ShardedJedisPool shardPool;
	// 静态代码初始化池配置
	static {
		try {

			// 加载redis配置文件
			ResourceBundle bundle = ResourceBundle.getBundle("redis");
			if (bundle == null) {
				throw new IllegalArgumentException(
						"[redis.properties] is not found!");
			}
			// 创建jedis池配置实例
			JedisPoolConfig config = new JedisPoolConfig();

			int maxActivity = Integer.valueOf(bundle
					.getString("redis.pool.maxActive"));
			int maxIdle = Integer.valueOf(bundle
					.getString("redis.pool.maxIdle"));
			long maxWait = Long.valueOf(bundle.getString("redis.pool.maxWait"));
			boolean testOnBorrow = Boolean.valueOf(bundle
					.getString("redis.pool.testOnBorrow"));
			boolean onreturn = Boolean.valueOf(bundle
					.getString("redis.pool.testOnReturn"));
			// 设置池配置项值
			config.setMaxActive(maxActivity);
			config.setMaxIdle(maxIdle);
			config.setMaxWait(maxWait);
			config.setTestOnBorrow(testOnBorrow);
			config.setTestOnReturn(onreturn);

			// 根据配置实例化jedis池
			pool = new JedisPool(config, bundle.getString("redis.ip"),
					Integer.valueOf(bundle.getString("redis.port")));

			// // 创建多个redis共享服务
			// JedisShardInfo jedisShardInfo1 = new JedisShardInfo(
			// bundle.getString("redis1.ip"), Integer.valueOf(bundle
			// .getString("redis.port")));
			//
			// JedisShardInfo jedisShardInfo2 = new JedisShardInfo(
			// bundle.getString("redis2.ip"), Integer.valueOf(bundle
			// .getString("redis.port")));
			//
			// List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
			// list.add(jedisShardInfo1);
			// list.add(jedisShardInfo2);
			//
			// // 根据配置文件,创建shared池实例
			// shardPool = new ShardedJedisPool(config, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MyJedisPool getInstance() {
		return instance;
	}

	public JedisPool getPool() {
		return pool;
	}

	public void returnResource(Jedis jedis) {
		pool.returnResource(jedis);
	}

	/**
	 * 测试jedis池方法
	 */
	public static void test1() {
		// 从jedis池中获取一个jedis实例
		Jedis jedis = pool.getResource();

		// 获取jedis实例后可以对redis服务进行一系列的操作
		jedis.set("name", "jack");
		System.out.println(jedis.get("name"));
		jedis.del("name");
		System.out.println(jedis.exists("name"));

		// 释放对象池，即获取jedis实例使用后要将对象还回去
		pool.returnResource(jedis);
	}

	/**
	 * 测试shardedJedis池方法
	 */
	public static void test2() {
		// 从shard池中获取shardJedis实例
		ShardedJedis shardJedis = shardPool.getResource();

		// 向redis服务插入两个key-value对象
		shardJedis.set("aaa", "jackson_aaa");
		System.out.println(shardJedis.get("aaa"));
		shardJedis.set("zzz", "jackson_zzz");
		System.out.println(shardJedis.get("zzz"));

		// 释放资源
		shardPool.returnResource(shardJedis);
	}

	public static void main(String[] args) {
		// test1();//执行test1方法
		test2();// 执行test2方法
	}
}
