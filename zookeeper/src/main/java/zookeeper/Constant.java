package zookeeper;

import zookeeper.annotation.ZkConfigProperty;
import zookeeper.annotation.ZkConfigType;

@ZkConfigType(path = "/")
public class Constant {

	@ZkConfigProperty(path = "a")
	public static int a = 10;
	@ZkConfigProperty(path = "b")
	public static String b = "abcabc";
	@ZkConfigProperty(path = "c")
	public static boolean c = true;
}
