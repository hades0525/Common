package com.citycloud.ccuap.ybhw.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;

/**
 * List 工具类
 *
 * @author meiming_mm@163.com
 * @date
 */
@Slf4j
public class ListUtil {

    /**
     * list convert map
     */
    public static <K, V> Map<K, V> toMap(List<V> list, Function<? super V, ? extends K> keyMapper) {

        Map<K, V> map = new HashMap<K, V>();
        if (list != null) {
            for (V value : list) {

                try {
                    K key = keyMapper.apply(value);
                    map.put(key, value);
                } catch (Exception e) {
                    log.error("field can't match the key!", e);
                }

            }

        }

        return map;
    }

    /**
     * list convert map
     */
    public static <K, V> Map<K, V> toMap(List<V> list, String fieldKey) {

        Map<K, V> map = new HashMap<K, V>();
        if (list != null) {
            for (V value : list) {

                try {
                    @SuppressWarnings("unchecked")
                    K k = (K) ReflectUtil.getter(value, fieldKey);
                    map.put(k, value);
                } catch (Exception e) {
                    log.error("field can't match the key!", e);
                }

            }

        }

        return map;
    }

    /**
     * list convert map
     */
    public static <K, V> Map<K, List<V>> toMapList(List<V> list, String fieldKey) {

        Map<K, List<V>> map = new HashMap<>();
        if (list != null) {
            for (V value : list) {

                try {
                    @SuppressWarnings("unchecked")
                    K k = (K) ReflectUtil.getter(value, fieldKey);
                    if (map.containsKey(k)) {
                        map.get(k).add(value);
                    } else {
                        List<V> vList = new ArrayList<>();
                        vList.add(value);
                        map.put(k, vList);
                    }
                } catch (Exception e) {
                    log.error("field can't match the key!", e);
                }

            }

        }

        return map;
    }

    /**
     * list convert map
     */
    public static <K, V> Map<K, List<V>> toMapList(List<V> list,
        Function<? super V, ? extends K> keyMapper) {

        Map<K, List<V>> map = new HashMap<>();
        if (list != null) {
            for (V value : list) {

                try {
                    K key = keyMapper.apply(value);
                    if (map.containsKey(key)) {
                        map.get(key).add(value);
                    } else {
                        List<V> vList = new ArrayList<>();
                        vList.add(value);
                        map.put(key, vList);
                    }
                } catch (Exception e) {
                    log.error("field can't match the key!", e);
                }

            }

        }

        return map;
    }

    /**
     * 获取list中其中一列值
     */
    public static <E, T> List<E> toColumnList(List<T> list, String fieldKey) {
        if (list != null) {
            List<E> arrayList = new ArrayList<>();
            for (T t : list) {
                try {
                    @SuppressWarnings("unchecked")
                    E e = (E) ReflectUtil.getter(t, fieldKey);
                    arrayList.add(e);
                } catch (Exception e) {
                    log.error("field can't match the key!", e);
                }
            }
            return arrayList;
        }
        return null;
    }

    /**
     * 获取list中其中一列值
     */
    public static <E, T> List<E> toColumnList(List<T> list,
        Function<? super T, ? extends E> keyMapper) {
        if (list != null) {
            List<E> arrayList = new ArrayList<>();
            for (T t : list) {
                try {
                    E e = keyMapper.apply(t);
                    arrayList.add(e);
                } catch (Exception e) {
                    log.error("field can't match the key!", e);
                }
            }
            return arrayList;
        }
        return null;
    }

    /**
     * map  to list
     */
    public static <K, V> List<V> toList(Map<K, V> map) {

        if (map != null) {
            return new ArrayList<>(map.values());
        }
        return null;
    }

    /**
     * 去重
     */
    public static <T> List<T> toUnique(List<T> list) {
        if (list != null) {
            Set<T> set = new HashSet<>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
        }
        return list;
    }

    /**
     * remove one
     */
    public static <T> boolean toRemoveObject(List<T> list, T o) {
        boolean result = false;
        if (list != null) {
            if (o instanceof Integer) {
                int index = list.indexOf(o);
                if (index > -1 && index < list.size()) {
                    T remove = list.remove(index);
                    result = remove != null;
                }
            } else {
                result = list.remove(o);
            }
        }
        return result;
    }


}
