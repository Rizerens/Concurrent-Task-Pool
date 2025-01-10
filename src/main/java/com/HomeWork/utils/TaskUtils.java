package com.HomeWork.utils;

import com.HomeWork.TaskPool.Task;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangguoliang wushifeng
 * @version 1.0
 * @Description: 工具类, 提供一些工具方法
 * @Date: 2025/01/05
 */
public class TaskUtils {
    private static int TASK_ID = 0;                                         // 任务ID计数器
    private static int THREAD_ID = 0;                                       // 线程ID计数器
    public static final int MAX_THREADS = 128;                              // 最大线程数
    public static final int MAX_TASKS = 10000;                              // 最大任务数
    public static Queue<Task> completeTaskQueue = new LinkedList<>();       // 完成任务队列
    private static final Random random = new Random();                      // 随机数生成器

    /**
     * @Description: 生成任务ID
     * @return 任务ID
     * @author wushifeng
     */
    public static int generateTaskId() {
        return TASK_ID++;
    }

    /**
     * @Description: 生成线程ID
     * @return 线程ID
     * @author jiangguoliang
     */
    public static int generateThreadId() {
        return THREAD_ID++;
    }

    /**
     * @Description: 打印完成队列情况
     * @param completeTaskQueue: 完成队列
     * @author wushifeng jiangguoliang
     */
    public static void printCompleteTaskQueue(Queue<Task> completeTaskQueue) {
        System.out.println("完成队列：");
        System.out.printf("%-10s %-30s %-30s%n", "任务ID", "任务结果", "任务异常");
        System.out.println("------------------------------------------------------------");

        int count = 0;
        for (Task task : completeTaskQueue) {
            if (count >= 50) {
                break;
            }
            System.out.printf("%-10d %-30s %-30s%n",
                    task.getTaskId(),
                    task.getResult() != null ? task.getResult().toString() : "N/A",
                    task.getException() != null ? task.getException().getMessage() : "N/A");
            count++;
        }

        if (completeTaskQueue.size() > 50) {
            System.out.println("... 共 " + completeTaskQueue.size() + " 个任务，仅显示前 50 个。");
        }
    }

    /**
     * @Description: 随机模拟延迟
     * @author wushifeng
     */
    public static long RandomTimeSimulation() {
        long delayMillis = 100 + random.nextInt(901);
        try {
            TimeUnit.MILLISECONDS.sleep(delayMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return delayMillis;
    }
}
