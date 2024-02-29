package com.example.xiao.logmanager.server.common.enums.converter;

import cn.hutool.core.util.ObjectUtil;
import com.example.xiao.logmanager.server.common.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 枚举编码 -> 枚举 转化器
 * </p>
 *
 */
public class IntegerToEnumConverter<T extends BaseEnum> implements Converter<Integer, T> {
    private Map<Integer, T> enumMap = new HashMap<>();

    public IntegerToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.getCode(), e);
        }
    }

    @Override
    public T convert(Integer source) {
        T t = enumMap.get(source);
        if (ObjectUtil.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
