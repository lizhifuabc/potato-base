package com.potato.base.plugin.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义 ThreadPoolExecutor
 *
 * @author lizhifu
 * @date 2021/12/14
 */
public class DynamicThreadPoolExecutor extends ThreadPoolExecutor {
    /**
     * 队列容量
     */
    private volatile int capacity;
    /**
     * 拒绝次数
     */
    private static final AtomicInteger rejectCount = new AtomicInteger();

    public DynamicThreadPoolExecutor(int corePoolSize, int maximumPoolSize, int capacity) {
        // 默认 1000 毫秒，时间单位毫秒
        // 默认拒绝策略 AbortPolicy：抛出异常
        super(corePoolSize, maximumPoolSize, 1000L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(), new AbortPolicy());
        this.capacity = capacity;
    }

    /**
     * 队列容量接口
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void execute(Runnable command) {
        // https://www.cnblogs.com/thisiswhy/p/15457810.html
        BlockingQueue<Runnable> queue = getQueue();
        // 队列元素个数
        int queueSize = queue.size();
        // 队列剩余容量
        int remainingCapacity = queue.remainingCapacity();
        // 队列容量
        int queueCapacity = queueSize + remainingCapacity;
        // 最大线程数
        int maximumPoolSize = getMaximumPoolSize();
        // 当线程池的任务缓存队列已满，并且线程池中的线程数目达到maximumPoolSize时，就需要拒绝掉该任务
        // TODO 自定义队列 或者上层封装使用无界队列
        if(queueCapacity >= capacity && 1==1){

        }
        super.execute(command);

    }

    /**
     * A handler for rejected tasks that runs the rejected task
     * directly in the calling thread of the {@code execute} method,
     * unless the executor has been shut down, in which case the task
     * is discarded.
     */
    public static class CallerRunsPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code CallerRunsPolicy}.
         */
        public CallerRunsPolicy() { }

        /**
         * Executes task r in the caller's thread, unless the executor
         * has been shut down, in which case the task is discarded.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            rejectCount.incrementAndGet();
            if (!e.isShutdown()) {
                r.run();
            }
        }
    }

    /**
     * A handler for rejected tasks that throws a
     * {@code RejectedExecutionException}.
     */
    public static class AbortPolicy implements RejectedExecutionHandler {
        /**
         * Creates an {@code AbortPolicy}.
         */
        public AbortPolicy() { }

        /**
         * Always throws RejectedExecutionException.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         * @throws RejectedExecutionException always
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            rejectCount.incrementAndGet();
            throw new RejectedExecutionException("Task " + r.toString() +
                    " rejected from " +
                    e.toString());
        }
    }

    /**
     * A handler for rejected tasks that silently discards the
     * rejected task.
     */
    public static class DiscardPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code DiscardPolicy}.
         */
        public DiscardPolicy() { }

        /**
         * Does nothing, which has the effect of discarding task r.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            rejectCount.incrementAndGet();
        }
    }

    /**
     * A handler for rejected tasks that discards the oldest unhandled
     * request and then retries {@code execute}, unless the executor
     * is shut down, in which case the task is discarded.
     */
    public static class DiscardOldestPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code DiscardOldestPolicy} for the given executor.
         */
        public DiscardOldestPolicy() { }

        /**
         * Obtains and ignores the next task that the executor
         * would otherwise execute, if one is immediately available,
         * and then retries execution of task r, unless the executor
         * is shut down, in which case task r is instead discarded.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            rejectCount.incrementAndGet();
            if (!e.isShutdown()) {
                e.getQueue().poll();
                e.execute(r);
            }
        }
    }
}
