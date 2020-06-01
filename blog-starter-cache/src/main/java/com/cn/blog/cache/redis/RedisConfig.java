package com.cn.blog.cache.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Duration;

/**
 * Redis配置.
 *
 * 使用@EnableCaching开启声明式缓存支持. 之后就可以使用 @Cacheable/@CachePut/@CacheEvict 注解缓存数据.
 *
 * @version 1.0
 */
@Configuration
@EnableCaching
public class RedisConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    /**
     * 覆盖默认配置 RedisTemplate，使用 String 类型作为key，设置key/value的序列化规则
     */
    @Bean
    @SuppressWarnings("unchecked")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用 Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.createXmlMapper(false).build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和key的序列化规则
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

   /* @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }*/

    /**
     * SpringBoot配置redis作为默认缓存工具
     * SpringBoot2.0以上版本的配置
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<String,Object> redisTemplate){
        RedisCacheConfiguration defRedisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                //设置key为String
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getStringSerializer()))
                //设置value为自动转json的Object
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                //不缓存null
                .disableCachingNullValues()
                //缓存数据保存1小时
                .entryTtl(Duration.ofHours(1));
        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
                //Redis连接工厂
                .fromConnectionFactory(redisTemplate.getConnectionFactory())
                //缓存配置
                .cacheDefaults(defRedisCacheConfiguration)
                //配置同步修改或删除put/evict
                .transactionAware()
                .build();
        return redisCacheManager;
    }
}
