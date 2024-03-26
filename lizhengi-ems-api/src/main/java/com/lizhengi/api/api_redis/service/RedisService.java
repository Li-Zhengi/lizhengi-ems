package com.lizhengi.api.api_redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务类
 *
 * @author liziheng
 */
@Service
public class RedisService {

    /**
     * 使用 StringRedisTemplate 来操作 Redis 中的字符串数据
     */
    private final StringRedisTemplate stringRedisTemplate;
    /**
     * 使用 RedisTemplate 来操作 Redis 中的对象数据，键为 String 类型，值为 Object 类型
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过构造器注入 StringRedisTemplate 和 RedisTemplate 实例
     *
     * @param stringRedisTemplate 操作字符串的 RedisTemplate
     * @param redisTemplate       操作对象的 RedisTemplate
     */
    @Autowired
    public RedisService(StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisTemplate) {
        // 初始化 stringRedisTemplate 实例
        this.stringRedisTemplate = stringRedisTemplate;
        // 初始化 redisTemplate 实例
        this.redisTemplate = redisTemplate;
    }


    // =================================== String ====================================

    /**
     * 根据给定的 key 获取其对应的值。
     *
     * @param key 键名
     * @return 返回 key 对应的值，如果不存在则返回 null
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 将指定的 key-value 对存储到 Redis 中。
     *
     * @param key   键名
     * @param value 键值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将指定的 key-value 对存储到 Redis 中，并设置过期时间。
     *
     * @param key     键名
     * @param value   键值
     * @param timeout 过期时间长度
     * @param unit    过期时间的单位（秒、分钟、小时等）
     */
    public void setWithExpire(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 对指定 key 的值进行自增操作。
     * 如果 key 不存在，那么在进行自增操作之前，会先创建该 key，并将其值设置为 increment 参数的值。
     *
     * @param key       键名
     * @param increment 自增的量
     * @return 自增操作后的值
     */
    public Long increment(String key, long increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 对指定 key 的值进行自减操作。
     * 如果 key 不存在，那么在进行自减操作之前，会先创建该 key，并将其值设置为 -decrement 参数的值。
     *
     * @param key       键名
     * @param decrement 自减的量
     * @return 自减操作后的值
     */
    public Long decrement(String key, long decrement) {
        return stringRedisTemplate.opsForValue().increment(key, -decrement);
    }

    /**
     * 获取指定 key 的当前值，并更新为新的值。
     *
     * @param key   键名
     * @param value 新的键值
     * @return 更新前的旧值，如果 key 不存在，则返回 null
     */
    public String getAndSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }


    // =================================== Common ====================================

    /**
     * 根据给定的 key 部分名称，查找匹配的所有 key 列表。
     *
     * @param partialKey 部分 key 名称，使用 '*' 作为通配符。例如："user:*"。
     * @return 匹配的 key 集合。如果没有找到匹配的 key，则返回一个空集合。
     */
    public Set<String> findKeysByPartialName(String partialKey) {
        return redisTemplate.keys(partialKey);
    }

    /**
     * 判断指定的 key 是否存在于 Redis 中。
     *
     * @param key 要判断的键名
     * @return 存在返回 true，不存在返回 false
     */
    public Boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 获取指定 key 的剩余过期时间。
     *
     * @param key      要查询的键名
     * @param timeUnit 时间单位（秒、分钟、小时等）
     * @return 剩余过期时间，单位由 timeUnit 参数指定。如果 key 不存在，返回 -2；如果 key 存在但没有设置过期时间，返回 -1。
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 为指定的 key 设置过期时间。
     *
     * @param key     要设置过期时间的键名
     * @param timeout 过期时间长度
     * @param unit    时间单位（秒、分钟、小时等）
     * @return 设置成功返回 true，失败返回 false
     */
    public Boolean setExpire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 移除指定 key 的过期时间，使其变为持久化的 key。
     *
     * @param key 要移除过期时间的键名
     * @return 移除成功返回 true，失败返回 false
     */
    public Boolean persist(String key) {
        return stringRedisTemplate.persist(key);
    }

    /**
     * 删除指定的 key。
     *
     * @param key 要删除的键名
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除给定的多个 key。
     *
     * @param keys 要删除的键名数组
     * @return 成功删除的键的数量
     */
    public Long deleteMultipleKeys(String... keys) {
        return stringRedisTemplate.delete(Arrays.asList(keys));
    }

    // =================================== Hash ====================================

    /**
     * 向指定的 Hash 中添加一个键值对。
     * 如果 Hash 不存在，会自动创建。
     *
     * @param hashKey Hash 的名称
     * @param key     键
     * @param value   值
     */
    public void putToHash(String hashKey, String key, Object value) {
        redisTemplate.opsForHash().put(hashKey, key, value);
    }

    /**
     * 从指定的 Hash 中获取一个键对应的值。
     *
     * @param hashKey Hash 的名称
     * @param key     键
     * @return 键对应的值，如果不存在则返回 null
     */
    public Object getFromHash(String hashKey, String key) {
        return redisTemplate.opsForHash().get(hashKey, key);
    }

    /**
     * 从指定的 Hash 中删除一个或多个键及其对应的值。
     *
     * @param hashKey Hash 的名称
     * @param keys    要删除的键数组
     * @return 删除成功的数量
     */
    public Long removeFromHash(String hashKey, Object... keys) {
        return redisTemplate.opsForHash().delete(hashKey, keys);
    }

    /**
     * 检查指定的 Hash 中是否存在给定的键。
     *
     * @param hashKey Hash 的名称
     * @param key     键
     * @return 如果存在返回 true，否则返回 false
     */
    public Boolean hasKeyInHash(String hashKey, String key) {
        return redisTemplate.opsForHash().hasKey(hashKey, key);
    }

    /**
     * 获取指定 Hash 的大小，即其中键值对的数量。
     *
     * @param hashKey Hash 的名称
     * @return Hash 的大小
     */
    public Long getHashSize(String hashKey) {
        return redisTemplate.opsForHash().size(hashKey);
    }

    /**
     * 获取指定 Hash 中的所有键值对。
     *
     * @param hashKey Hash 的名称
     * @return 一个包含所有键值对的 Map，如果 Hash 不存在，则返回一个空 Map
     */
    public Map<Object, Object> getAllEntriesFromHash(String hashKey) {
        return redisTemplate.opsForHash().entries(hashKey);
    }

    // =================================== Set ====================================

    /**
     * 向指定的 Set 中添加元素。
     *
     * @param key    Set 的键名
     * @param values 要添加的值
     * @return 添加成功的元素数量
     */
    public Long addToSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 从指定的 Set 中移除元素。
     *
     * @param key    Set 的键名
     * @param values 要移除的值
     * @return 移除成功的元素数量
     */
    public Long removeFromSet(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 获取指定 Set 中的所有元素。
     *
     * @param key Set 的键名
     * @return 该 Set 中的所有元素
     */
    public Set<Object> membersOfSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 判断一个元素是否是指定 Set 的成员。
     *
     * @param key   Set 的键名
     * @param value 要判断的值
     * @return 如果是成员则返回 true，否则返回 false
     */
    public Boolean isMemberOfSet(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    // =================================== ZSet ====================================

    /**
     * 向指定的 ZSet 添加元素，每个元素关联一个分数。
     *
     * @param key   ZSet 的键名
     * @param value 要添加的值
     * @param score 该值的分数
     * @return 添加成功返回 true，失败返回 false
     */
    public Boolean addToZSet(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 从指定的 ZSet 中移除元素。
     *
     * @param key    ZSet 的键名
     * @param values 要移除的值
     * @return 移除成功的元素数量
     */
    public Long removeFromZSet(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 获取指定 ZSet 中的元素范围，根据元素的分数进行排序。
     *
     * @param key   ZSet 的键名
     * @param start 开始位置（0 是第一个元素）
     * @param end   结束位置（-1 表示最后一个元素）
     * @return 指定范围内的有序元素集合
     */
    public Set<Object> rangeOfZSet(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取指定 ZSet 中所有元素及其分数的集合。
     *
     * @param key ZSet 的键名
     * @return 包含所有元素及其分数的集合
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScoresOfZSet(String key) {
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
    }

    // =================================== List ====================================

    /**
     * 向指定的 List 中从左侧（头部）添加元素。
     *
     * @param key List 的键名
     * @param value 要添加的值
     * @return 操作后 List 的长度
     */
    public Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 向指定的 List 中从右侧（尾部）添加元素。
     *
     * @param key List 的键名
     * @param value 要添加的值
     * @return 操作后 List 的长度
     */
    public Long rightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 从指定的 List 中从左侧（头部）弹出一个元素。
     *
     * @param key List 的键名
     * @return 被弹出的元素，如果 List 为空，则返回 null
     */
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 从指定的 List 中从右侧（尾部）弹出一个元素。
     *
     * @param key List 的键名
     * @return 被弹出的元素，如果 List 为空，则返回 null
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 获取指定 List 中指定范围内的元素。
     *
     * @param key List 的键名
     * @param start 起始索引（包含）
     * @param end 结束索引（包含），-1 表示最后一个元素
     * @return 指定范围内的元素列表
     */
    public List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取指定 List 的长度。
     *
     * @param key List 的键名
     * @return List 的长度
     */
    public Long size(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 在指定 List 中的指定位置设置元素的值。
     *
     * @param key List 的键名
     * @param index 索引位置
     * @param value 要设置的值
     */
    public void set(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除指定 List 中值等于 value 的元素。
     *
     * @param key List 的键名
     * @param count 移除元素的数量，如果为负数则从尾到头进行移除
     * @param value 要移除的元素值
     * @return 实际移除的元素数量
     */
    public Long remove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

}

