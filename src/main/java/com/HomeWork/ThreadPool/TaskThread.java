package com.HomeWork.ThreadPool;

import com.HomeWork.TaskPool.Task;
import com.HomeWork.utils.TaskUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

import static com.HomeWork.utils.TaskUtils.completeTaskQueue;

/**
 * @author jiangguoliang
 * @version 1.0
 * @Description: 线程池线程, 用于执行任务
 * @Date: 2025/01/08
 */
@Getter
@Setter
public class TaskThread implements Runnable {
    private int threadId;                                                       // 线程id
    private String threadName;                                                  // 线程名
    private Task task;                                                          // 当前线程正在执行的任务
    private volatile ThreadStatus status;                                       // 线程状态

    /**
     * @Description: 构造函数
     * @param task: 任务
     * @author jiangguoliang
     */
    public TaskThread(Task task) {
        this.threadId = TaskUtils.generateThreadId();
        this.threadName = "线程【" + threadId + "】";
        this.status = ThreadStatus.IDLE;
        this.task = task;
        new Thread(this).start();
    }

    /**
     * @Description: 线程运行
     * @author jiangguoliang
     */
    @Override
    public void run() {
        while (status != ThreadStatus.TERMINATED) {
            if(task != null){
                task.execute();
                this.status = ThreadStatus.IDLE;
                completeTaskQueue.add(task);
                task = null;
            }else{
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @Description: 停止线程
     * @author jiangguoliang
     */
    public void stopThread() {
        this.status = ThreadStatus.TERMINATED;
        if (task != null) {
            task.cancel();
        }
    }
}

