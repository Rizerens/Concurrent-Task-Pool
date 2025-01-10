package com.HomeWork.ThreadPool;

/**
 * @author wushifeng
 * @version 1.0
 * @Description: 线程状态, 线程池中线程状态
 * @Date: 2025/01/06
 */
public enum ThreadStatus {
    IDLE,                                                                   // 线程空闲
    RUNNING,                                                                // 线程执行
    TERMINATED                                                              // 线程终止
}
