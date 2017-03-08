package redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import baseFramework.exception.ServerException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author chao.li
 * @date 2017年1月9日
 */
public class Test {
	protected Logger logger = LogManager.getLogger(Test.class);

	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private ShardedJedisPool shardedJedisPool;

	public String read(String key) {

		try {
			return jedisCluster.get(key);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String readWithEx(String key) {

		try {
			return jedisCluster.get(key);
		} catch (Exception e) {
			throw new ServerException(e, "");
		}
	}

	public String read1(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

	public String read2(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return null;
	}

}
