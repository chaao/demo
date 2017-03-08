package redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import baseFramework.exception.ServerException;
import baseFramework.redis.JedisPoolExecutor;
import baseFramework.redis.ShardedJedisExecutor;
import redis.clients.jedis.JedisCluster;

/**
 * @author chao.li
 * @date 2017年1月9日
 */
public class Test {
	protected Logger logger = LogManager.getLogger(Test.class);

	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private JedisPoolExecutor jedisPoolExecutor;
	@Autowired
	private ShardedJedisExecutor shardedJedisExecutor;

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

	public String readWithPool(String key) {
		return jedisPoolExecutor.get(key);
	}

	public String readWithShard(String key) {
		return shardedJedisExecutor.get(key);
	}

}
