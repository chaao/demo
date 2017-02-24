package pool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author lichao
 * @date 2014年6月20日
 */
public class ObjectsPool {
	// 设置后进先出的池策略
	private boolean lifo = true;
	// 允许每个KEY最大空闲对象数
	private int maxIdlePerKey = 8;
	// 允许每个KEY最大对象数
	private int maxTotalPerKey = 8;
	// 允许最大活动对象数
	private int maxTotal = 24;
	// 允许每个KEY最小空闲对象数
	private int minIdlePerKey = 0;
	// 允许最大等待时间毫秒数
	private int maxWaitMillis = 3000;
	// 当池耗尽的时候是否block
	private boolean blockWhenExhausted = true;
	// 指明是否在创建对象时进行检验
	private boolean testOnCreate = false;
	// 指明是否在从池中取出对象前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个.
	private boolean testOnBorrow = false;
	// 指明是否在归还到池中前进行检验
	private boolean testOnReturn = false;
	// 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除.
	private boolean testWhileIdle = false;
	/**
	 * 设定在进行后台对象清理时，每次检查对象数,
	 * 如果这个值不是正数，则每次检查的对象数是检查时池内对象的总数乘以这个值的负倒数再向上取整的结果――也就是说
	 * 如果这个值是-2（-3、-4、-5……）的话，那么每次大约检查当时池内对象总数的1/2（1/3、1/4、1/5……）左右
	 **/
	private int numTestsPerEvictionRun = 3;
	// 被空闲对象回收器回收前在池中保持空闲状态的最小时间毫秒数
	private long minEvictableIdleTimeMillis = 1000L * 60L * 30L;
	// 在空闲连接回收器线程运行期间休眠的时间毫秒数. 如果设置为非正数,则不运行空闲连接回收器线程
	private long timeBetweenEvictionRunsMillis = -1L;

	KeyedObjectPool<String, StringBuffer> objectPool = null;

	public void init() {

		GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();

		config.setLifo(lifo);
		config.setMaxIdlePerKey(maxIdlePerKey);
		config.setMaxTotalPerKey(maxTotalPerKey);
		config.setMaxTotal(maxTotal);
		config.setMinIdlePerKey(minIdlePerKey);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setBlockWhenExhausted(blockWhenExhausted);
		config.setTestOnCreate(testOnCreate);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		config.setTestWhileIdle(testWhileIdle);
		config.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

		objectPool = new GenericKeyedObjectPool<String, StringBuffer>(new BaseKeyedPooledObjectFactory<String, StringBuffer>() {

			@Override
			public StringBuffer create(String key) throws Exception {
				return new StringBuffer();
			}

			@Override
			public PooledObject<StringBuffer> wrap(StringBuffer value) {
				return new DefaultPooledObject<StringBuffer>(value);
			}

			@Override
			public void passivateObject(String key, PooledObject<StringBuffer> pooledObject) throws Exception {
				pooledObject.getObject().setLength(0);
			}

		});
	}
}
