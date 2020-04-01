package com.lmj.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description Redis集群配置
 * @Author zhengxiao
 * @Date 2018/12/4
 */
@Configuration
@ConditionalOnClass({JedisCluster.class})
public class RedisConfig {
    /**
     * redis.cluster
     */
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

    /**
     * 连接池中的最大空闲连接
     */
    @Value("${redis.pool.max-idle}")
    private int maxIdle;

    /**
     * 连接池最大阻塞等待时间（使用负值表示没有限制）
     */
    @Value("${redis.pool.max-wait}")
    private long maxWaitMillis;

    /**
     * 连接超时时间
     */
    @Value("${spring.redis.timeout}")
    private int timeout;

    /**
     * 命令超时时间
     */
    @Value("${spring.redis.commandTimeout}")
    private int commandTimeout;

    /**
     * 集群密码
     */
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 读取数据超时时间
     */
    @Value("${spring.redis.soTimeout}")
    private int soTimeout;

    /**
     * 超时重连最大次数
     */
    @Value("${spring.redis.maxAttempts}")
    private int maxAttempts;

    /**
     * 最大连接数
     */
    @Value("${redis.pool.max-active}")
    private int maxActive;

    @Bean
    public JedisCluster getJedisCluster() {
        String[] cNodes = clusterNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        //分割出集群节点
        for(String node : cNodes) {
            String[] hp = node.split(":");
            nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxTotal(maxActive);
        //创建集群对象
//        return new JedisCluster(nodes, commandTimeout, soTimeout, maxAttempts, password, jedisPoolConfig);
        // windows
//        JedisCluster jedisCluster = new JedisCluster(nodes, commandTimeout, soTimeout, maxAttempts, null, jedisPoolConfig);

        // linux
        JedisCluster jedisCluster = new JedisCluster(nodes, commandTimeout, soTimeout, maxAttempts, password, jedisPoolConfig);
        System.out.println(jedisCluster.get("a"));

        return jedisCluster;
    }

    /**
     * 设置数据存入redis 的序列化方式
     * redisTemplate序列化默认使用的jdkSerializeable,存储二进制字节码,导致key会出现乱码，所以自定义
     * 序列化类
     *
     * @paramredisConnectionFactory
     */
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
