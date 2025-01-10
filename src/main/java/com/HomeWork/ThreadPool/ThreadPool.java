package com.HomeWork.ThreadPool;


import com.HomeWork.TaskPool.Task;
import com.HomeWork.TaskPool.TaskQueue;
import com.HomeWork.utils.TaskUtils;
import lombok.Getter;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangguoliang wushifeng
 * @version 1.0
 * @Description: 线程池, 管理线程及分配任务
 * @Date: 2025/01/09
 */
@Getter
public class ThreadPool implements Runnable {
    private final LinkedList<TaskThread> taskThreads = new LinkedList<>();                      // 线程池
    private final TaskQueue taskQueue = new TaskQueue();                                        // 任务队列
    private boolean isRunning;                                                                  // 线程池是否运行
    private volatile boolean isPaused;                                                          // 线程池是否暂停

    /**
     * @Description: 构造函数
     * @param poolSize 线程池大小
     * @author jiangguoliang
     */
    public ThreadPool(int poolSize) {
        this.isRunning = true;
        this.isPaused = true;
        for (int i = 0; i < poolSize; i++) {
            addThread();
        }
        new Thread(this).start();
    }

    /**
     * @Description: 添加线程
     * @author jiangguoliang
     */
    public void addThread() {
        if (taskThreads.size() >= TaskUtils.MAX_THREADS) {
            return;
        }
        TaskThread taskThread = new TaskThread(null);
        taskThreads.add(taskThread);
    }

    /**
     * @Description: 暂停线程池
     * @author jiangguoliang
     */
    public void pause() {
        isPaused = true;
    }

    /**
     * @Description: 恢复线程池
     * @author jiangguoliang
     */
    public void resume() {
        synchronized (this) {
            isPaused = false;
            notifyAll();
        }
    }

    /**
     * @Description: 提交任务
     * @param task 任务
     * @author wushifeng
     */
    public void submitTask(Task task) {
        synchronized (taskQueue) {
            taskQueue.addTask(task);
            taskQueue.notify();
        }
    }

    /**
     * @Description: 提交任务
     * @param tasks 任务列表
     * @author wushifeng
     */
    public void submitTasks(Iterable<Task> tasks) {
        for (Task task : tasks) {
            submitTask(task);
        }
    }

    /**
     * @Description: 停止线程池
     * @author wushifeng
     */
    public void shutdown() {
        for (TaskThread taskThread : taskThreads) {
            taskThread.stopThread();
        }
        isRunning = false;
    }

    /**
     * @Description: 运行线程池
     * @author wushifeng
     */
    @Override
    public void run() {
        while (isRunning) {
            synchronized (this) {
                while (isPaused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            synchronized (taskQueue) {
                while (taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                TaskThread taskThread = getIdleThread();
                if (taskThread != null) {
                    Task task = taskQueue.getTask();
                    System.out.println("线程【" + taskThread.getThreadId() + "】分配到任务【" + task.getTaskId() + "】");
                    taskThread.setTask(task);
                    taskThread.setStatus(ThreadStatus.RUNNING);
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    /**
     * @Description: 获取一个闲置的线程
     * @return 线程
     * @author jiangguoliang
     */
    private TaskThread getIdleThread() {
        for (TaskThread taskThread : taskThreads) {
            if (taskThread.getStatus() == ThreadStatus.IDLE) {
                return taskThread;
            }
        }
        return null;
    }

    /**
     * @Description: 等待任务完成
     * @author jiangguoliang
     */
    public void waitForCompletion() {
        while (true) {
            if (taskQueue.isEmpty()) {
                boolean allThreadsIdle = taskThreads.stream()
                        .allMatch(thread -> thread.getStatus() == ThreadStatus.IDLE);
                if (allThreadsIdle) {
                    shutdown();
                    System.out.println("所有任务已完成，线程池退出");
                    break;
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
