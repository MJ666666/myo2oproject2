package com.lmj.o2o.esservice.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticSerachConfig {
	@Value("${elasticsearch.ip}")
	private String ip;
	@Value("${elasticsearch.port}")
	private String port;
	@Value("${elasticsearch.clusterName}")
	private String clusterName;

	@Value("${elasticsearch.poolSize}")
	private String poolSize;

     //自动注入client 
	@Bean(name="client")
	public TransportClient esClint() throws UnknownHostException
	{
		Settings settings = Settings.builder().put("cluster.name", clusterName) // 集群名字
				.put("client.transport.sniff", true)// 增加嗅探机制，找到ES集群
				.put("thread_pool.search.size", Integer.parseInt(poolSize))// 线程池个数
				.build();
		InetSocketTransportAddress master=new InetSocketTransportAddress(InetAddress.getByName(ip),Integer.parseInt(port));
		TransportClient client =new PreBuiltTransportClient(settings).addTransportAddress(master);
		return client;	
	}
}