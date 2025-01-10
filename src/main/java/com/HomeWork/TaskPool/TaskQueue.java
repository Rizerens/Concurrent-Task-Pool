package com.HomeWork.TaskPool;

import com.HomeWork.utils.TaskUtils;
import lombok.Getter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wushifeng
 * @version 1.0
 * @Description: 任务队列, 用于存储任务
 * @Date: 2025/01/05
 */
@Getter
public class TaskQueue {
    private final BlockingQueue<Task> queue;                                     // 阻塞队列, 用于存储任务
    private final int maxSize = TaskUtils.MAX_TASKS;                             // 队列最大容量

    /**
     * @Description: 创建一个任务队列
     * @author wushifeng
     */
    public TaskQueue() {
        this.queue = new LinkedBlockingQueue<>(maxSize);
    }

    /**
     * @Description: 添加任务到队列
     * @param task: 任务
     * @author wushifeng
     */
    public void addTask(Task task) {
        if (queue.size() > maxSize) {
            return;
        }

        try {
            queue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @Description: 获取队列中的任务
     * @return 任务
     * @author wushifeng
     */
    public Task getTask() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("获取任务失败：" + e.getMessage());
            return null;
        }
    }

    /**
     * @Description: 检查队列是否为空
     * @return boolean
     * @author wushifeng
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

