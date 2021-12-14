package com.potato.base.book.lock;

/**
 * 同步代码块
 *
 * @author lizhifu
 * @date 2021/12/13
 */
public class SynchronizedTest {
    public int i;
    /**
     * 修饰方法
     */
    public synchronized void method(){
        i++;
    }
}
