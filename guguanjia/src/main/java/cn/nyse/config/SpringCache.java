package cn.nyse.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;

/**
 * 1.配置缓存管理器对象
 * 2.开启springCache的缓存注解支持
 * 3.需要管理放入缓存的实体类需要实现序列化
 * 4.在需要管理缓存的方法(服务层方法)上添加缓存注解
 */
@Configuration
@EnableCaching//开启springcache的注解支持
public class SpringCache {


    @Bean
    public CacheManager getCacheManager(RedisTemplate<String,Object> template){
        RedisCacheManager redisCacheManager = new RedisCacheManager(template);//创建缓存管理器对象
        //根据传入的缓存名字集合，创建并管理多个缓存对象
        ArrayList<String> cacheNames = new ArrayList<>();
        cacheNames.add("officeCache");
        redisCacheManager.setCacheNames(cacheNames);
        redisCacheManager.setDefaultExpiration(1800);//设置默认生存时间
        return redisCacheManager;
    }
}
