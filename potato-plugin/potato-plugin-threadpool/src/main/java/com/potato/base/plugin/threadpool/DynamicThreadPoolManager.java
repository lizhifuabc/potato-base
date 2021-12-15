package com.potato.base.plugin.threadpool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线程池配置管理
 *
 * @author lizhifu
 * @date 2021/12/14
 */
public class DynamicThreadPoolManager {
    /**
     * 存储线程池对象，Key:名称 Value:对象
     */
    private static final Map<String, DynamicThreadPoolExecutor> THREAD_POOL_EXECUTOR_MAP = new HashMap<>();

    public static void createPool(String threadPoolName, DynamicThreadPoolExecutor executor) {
        THREAD_POOL_EXECUTOR_MAP.putIfAbsent(threadPoolName, executor);
    }
    public static DynamicThreadPoolExecutor getPool(String threadPoolName) {
        return THREAD_POOL_EXECUTOR_MAP.get(threadPoolName);
    }
    public static List<String> list() {
        return new ArrayList<>(THREAD_POOL_EXECUTOR_MAP.keySet());
    }
}
