package perf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lichao
 * @date 2014年7月22日
 */
@Aspect
public class Perf4jAspect {
	private static Logger LOG = LoggerFactory.getLogger(StopWatch.DEFAULT_LOGGER_NAME);

	@Pointcut("execution(* webtest.CacheController.*(..))")
	private void pointcut() {
	}
	
	@Around("pointcut()")
	public Object statistic(ProceedingJoinPoint joinPoint) throws Throwable {
		
		LoggingStopWatch stopWatch = new MyStopWatch(LOG, Slf4JStopWatch.INFO_LEVEL, Slf4JStopWatch.INFO_LEVEL);

		// 超时时间1s
		stopWatch.setTimeThreshold(1000);
		stopWatch.setNormalAndSlowSuffixesEnabled(true);

		Throwable exceptionThrown = null;
		try {
			return joinPoint.proceed();
		} catch (Throwable t) {
			throw exceptionThrown = t;
		} finally {
			String tag = joinPoint.getSignature().getName();
			tag = (exceptionThrown == null) ? tag + ".success" : tag + ".failure";
			stopWatch.stop(tag);
		}

	}
}

class MyStopWatch extends Slf4JStopWatch {
	private static final long serialVersionUID = 1L;
	private static String fastSuffix = ".fast";
	private static String normalSuffix = ".normal";
	private static String slowSuffix = ".slow";
	private static String blockSuffix = ".block";
	private static long _80ms = 80l;
	private static long _150ms = 150l;
	private String tagName;

	public MyStopWatch(Logger logger, int normalPriority, int exceptionPriority) {
		super(logger, normalPriority, exceptionPriority);
	}

	@Override
	public String stop(String tag) {
		this.tagName = tag;
		return super.stop(tag);
	}

	@Override
	public String getTag() {
		long timeThreshold = getTimeThreshold();

		if (isNormalAndSlowSuffixesEnabled()) {
			if (getElapsedTime() <= _80ms) {
				return tagName + fastSuffix;
			} else if (getElapsedTime() <= _150ms) {
				return tagName + normalSuffix;
			} else if (getElapsedTime() <= timeThreshold) {
				return tagName + slowSuffix;
			} else {
				return tagName + blockSuffix;
			}
		} else {
			return super.getTag();
		}
	}
}