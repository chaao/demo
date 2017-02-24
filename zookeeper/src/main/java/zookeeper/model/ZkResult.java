package zookeeper.model;

import java.util.ArrayList;
import java.util.List;

public class ZkResult {

	private String result;
	/**
	 * 不允许的ip地址。初始化（系统重启）的时候会忽略这个。
	 */
	private List<String> notAllowIps = new ArrayList<String>();
	/**
	 * 成功或者失败的回调地址。
	 */
	private String callbackUrl;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getNotAllowIps() {
		return notAllowIps;
	}

	public void setNotAllowIps(List<String> notAllowIps) {
		this.notAllowIps = notAllowIps;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

}
