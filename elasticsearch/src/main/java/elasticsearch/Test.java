package elasticsearch;

import java.net.InetSocketAddress;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * @author chao.li
 * @date 2017年3月23日
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Settings settings = Settings.settingsBuilder().put("cluster.name", "inveno-ES")
				.put("client.transport.sniff", true).build();
		TransportClient transportClient = TransportClient.builder().settings(settings).build();
		Client client = transportClient
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("106.75.9.5", 9500)));
		GetResponse response = client.prepareGet("tchannel", "tchannel", "68138998").execute().actionGet();
		System.out.println(response.getSourceAsString());
	}

	public static void version5() throws Exception {
		/*
		 * // 设置集群名称 Settings settings = Settings.builder().put("cluster.name",
		 * "inveno-ES")// 设置ES实例的名称 .put("client.transport.sniff", true) //
		 * 自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中 .build(); // 创建client
		 * TransportClient client = new PreBuiltTransportClient(settings);
		 * client.addTransportAddress(new
		 * InetSocketTransportAddress(InetAddress.getByName("106.75.9.5"),
		 * 9400));
		 * 
		 * // 搜索数据 GetResponse response = client.prepareGet("tchannel",
		 * "tchannel", "68138998").execute().actionGet();
		 * 
		 * // 输出结果 System.out.println(response.getSourceAsString()); // 关闭client
		 * client.close();
		 */
	}

}
