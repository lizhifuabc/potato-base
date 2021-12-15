package com.potato.base.book.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 *
 * @author lizhifu
 * @date 2021/12/14
 */
public class ReentrantLockTest {
    private final static ReentrantLock lock = new ReentrantLock(true);
    public static void main(String[] args) {
        test();
    }
    public static void test () {
        // ReentrantLock.FairSync.lock()
        lock.lock();
        try {
            System.out.println("demo");
        } finally {
            lock.unlock();
        }
    }
}
