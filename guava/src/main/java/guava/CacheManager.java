package guava;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;

/**
 * @author lichao
 * @date 2014年7月15日
 */
public class CacheManager {
	private static Cache<String, CachedObject> cache;
	private static final int maxExpireTime = 60;

	public CacheManager() {
		cache = CacheBuilder.newBuilder().expireAfterWrite(maxExpireTime, TimeUnit.SECONDS).initialCapacity(128).recordStats().build();
	}

	public void put(String key, CachedObject sessionCache) {
		cache.put(key, sessionCache);
	}

	public CachedObject get(String key) {
		return cache.getIfPresent(key);
	}

	public void delete(String key) {
		cache.invalidate(key);
	}

	public String getStatus() {
		CacheStats status = cache.stats();
		return status.toString();
	}

	public static void main(String[] args) throws Exception {
		CacheManager manager = new CacheManager();
		String key = "a";
		manager.put(key, new CachedObject("a"));
		System.out.println(manager.get(key));
		manager.delete(key);
		System.out.println(manager.get(key));
	}
}

class CachedObject {
	private String id;

	public CachedObject(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CachedObject [id=" + id + "]";
	}

}
