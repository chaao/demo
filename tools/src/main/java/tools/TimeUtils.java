package tools;

import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * @author lichao
 *
 */
public class TimeUtils {
	
	public static final long _1s = 1000;
	public static final long _1m = _1s * 60;
	public static final long _1h = _1m * 60;
	public static final long _1d = _1h * 24;
	
	public static String passedTime(long time) {
		long now = System.currentTimeMillis();

		long period = now - time;
		if (period >= _1d) {
			return DurationFormatUtils.formatPeriod(now - time, now, "d天前");
		} else {
			return DurationFormatUtils.formatPeriod(now - time, now, "H小时前");
		}

	}
}
