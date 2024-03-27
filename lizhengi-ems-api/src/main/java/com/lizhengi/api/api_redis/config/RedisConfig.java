package com.lizhengi.api.api_redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 *
 * @author 栗筝i
 */
@Configuration
public class RedisConfig {

    /**
     * 配置 RedisTemplate，使用自定义的序列化策略来处理 String 类型的键和 Object 类型的值。
     * 主要通过设置键（key）和值（value）的序列化方式，来优化 Redis 的存取效率和易用性。
     *
     * @param redisConnectionFactory Redis 连接工厂，用于创建与 Redis 服务器的连接
     * @return 配置好的 RedisTemplate<String, Object> 实例
     */
    @Bean // 表示该方法将返回一个被 Spring 管理的对象
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置 Redis 连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 使用 StringRedisSerializer 来序列化和反序列化 redis 的 key 值
        // 这种序列化器能够保证 key 的清晰可读，便于管理和调试
        template.setKeySerializer(new StringRedisSerializer());

        // 定义 Jackson2JsonRedisSerializer 序列化对象，使用 Jackson 库将对象序列化为 JSON 字符串
        // 这样做可以保证 value 的可读性以及存储时的效率
        ObjectMapper objectMapper = new ObjectMapper();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =  new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        // 使用 ObjectMapper 进行转义设置，确保任何访问修饰符（如private）的属性都将被包含
        // 并且非 final 类型的对象的类型信息也会被存储下来，这样在反序列化时就能够正确地还原对象
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // 设置 value 的序列化规则和 key 的序列化规则
        // 这里为 value 设置了 Jackson2JsonRedisSerializer 序列化器，可以将对象有效地转换为 JSON 字符串存储
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 对于 Redis 的 hash 数据结构的 key 也使用 StringRedisSerializer 进行序列化，保证 key 的可读性
        template.setHashKeySerializer(new StringRedisSerializer());

        // 初始化 RedisTemplate 设置
        template.afterPropertiesSet();
        // 返回配置好的 RedisTemplate 实例
        return template;
    }
}
