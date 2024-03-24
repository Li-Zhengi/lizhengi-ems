package com.lizhengi.utils;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * 对象复制工具类
 *
 * @author 栗筝i
 */
@Slf4j
public class BeanCopyUtil {

    /**
     * 复制一个对象的属性到目标对象中
     *
     * @param source      要复制属性的源对象
     * @param targetClass 目标对象的类
     * @param <T>         目标对象的泛型类型
     * @return 复制后的目标对象
     */
    public static <T> T copy(Object source, Class<T> targetClass) {
        // 判空
        if (source == null) {
            return null;
        }
        T object;
        try {
            // 实例化目标对象
            object = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("BeanCopyUtil copy error, source:{}, SourceClass:{}, TargetClass:{}"
                    , JSON.toJSONString(source), source.getClass(), targetClass);
            return null;
        }
        // 使用 BeanUtils.copyProperties 方法复制源对象的属性到目标对象
        BeanUtils.copyProperties(source, object);
        return object;
    }
}