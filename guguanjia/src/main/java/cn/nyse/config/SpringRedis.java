package cn.nyse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource(value="classpath:redis.properties",encoding = "utf-8")
/**
 * 1.配置连接redis数据源
 * 2.配置RedisTemplate的key和value的序列化策略
 */
public class SpringRedis {

    @Value("${host}")
    private String host;
    @Value("${port}")
    private int port;
    @Value("${password}")
    private String password;
    @Value("${maxIdle}")
    private int maxIdle;
    @Value("${minIdle}")
    private int minIdle;

    //1.配置连接redis数据源
    @Bean
    public RedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        //设置连接池配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        factory.setPoolConfig(jedisPoolConfig);
        return factory;
    }


    //2.配置RedisTemplate的key和value的序列化策略

    /**
     * 为了方便管理数据库，在数据库中也可以看到key和value数据的结构，需要自定义序列化策略
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> getRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //设置key为非hash的序列化和反序列化策略
        template.setKeySerializer(template.getStringSerializer());
        //设置value为非hash类型的序列化和反序列化策略
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //该序列化器不能解决对象里面有复杂属性的序列化和反序列化问题
//        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        template.setValueSerializer(jsonRedisSerializer);

        //设置hash类型
        template.setHashKeySerializer(template.getStringSerializer());
        template.setHashValueSerializer(jsonRedisSerializer);


        return template;
    }
}
