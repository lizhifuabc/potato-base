package com.potato.base.book.lock;

/**
 * 同步代码块
 *
 * @author lizhifu
 * @date 2021/12/13
 */
public class SynchronizedTest2 {
    public int i;

    public void method(){
        synchronized(this){
            i++;
        }
    }
}
