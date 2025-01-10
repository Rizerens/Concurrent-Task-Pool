package com.HomeWork.ThreadPool;

import com.HomeWork.TaskLogic.TaskFactory;
import com.HomeWork.TaskPool.Task;
import com.HomeWork.utils.TaskUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.HomeWork.utils.TaskUtils.completeTaskQueue;

/**
 * @author wushifeng
 * @version 1.0
 * @Description: 线程池测试类, 测试线程池的功能
 * @Date: 2025/01/10
 */
class ThreadPoolTest {
    TaskFactory taskFactory = new TaskFactory();                                    // 创建任务工厂
    ThreadPool threadPool = new ThreadPool(12);                             // 创建线程池

    /**
     * @Description: 初始化线程池
     * @author wushifeng
     */
    @BeforeEach
    void setUp() {
        threadPool.pause();
        completeTaskQueue.clear();
    }

    /**
     * @Description: 测试打印任务
     * @author wushifeng
     */
    @Test
    void printTask() {
        BlockingQueue<Task> tasks = taskFactory.createTaskSequence("PrintTask", 1000);
        threadPool.submitTasks(tasks);
        printInitializationSituation();
        threadPool.resume();
        threadPool.waitForCompletion();
        TaskUtils.printCompleteTaskQueue(completeTaskQueue);
    }

    /**
     * @Description: 测试文件写入任务
     * @author wushifeng
     */
    @Test
    void fileWriteTask() {
        BlockingQueue<Task> tasks = taskFactory.createTaskSequence("FileTask", 1000);
        threadPool.submitTasks(tasks);
        printInitializationSituation();
        threadPool.resume();
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadPool.getTaskQueue().getQueue().stream().limit(10).forEach(Task::cancel);
        threadPool.waitForCompletion();
        TaskUtils.printCompleteTaskQueue(completeTaskQueue);
    }

    /**
     * @Description: 测试计算任务
     * @author wushifeng
     */
    @Test
    void calculationTask() {
        BlockingQueue<Task> tasks = taskFactory.createTaskSequence("CalculationTask", 1000);
        threadPool.submitTasks(tasks);
        printInitializationSituation();
        threadPool.resume();
        threadPool.waitForCompletion();
        TaskUtils.printCompleteTaskQueue(completeTaskQueue);
        int sum = 0;
        for (Task task : completeTaskQueue){
            sum += (int) task.getResult();
        }
        System.out.println("加和结果：" + sum);
    }

    /**
     * @Description: 打印初始化任务池和线程池
     * @author wushifeng
     */
    void printInitializationSituation() {
        int maxDisplayTasks = 5;
        int maxDisplayThreads = 5;

        System.out.println("任务池");
        int taskCount = 0;
        for (Task task : threadPool.getTaskQueue().getQueue()) {
            if (taskCount >= maxDisplayTasks) {
                System.out.println("... 更多任务");
                break;
            }
            System.out.println(task.getTaskName());
            taskCount++;
        }
        if (threadPool.getTaskQueue().getQueue().size() > maxDisplayTasks) {
            System.out.println("总任务数: " + threadPool.getTaskQueue().getQueue().size());
        }

        System.out.println("线程池");
        int threadCount = 0;
        for (TaskThread taskThread : threadPool.getTaskThreads()) {
            if (threadCount >= maxDisplayThreads) {
                System.out.println("... 更多线程");
                break;
            }
            System.out.println(taskThread.getThreadName());
            threadCount++;
        }
        if (threadPool.getTaskThreads().size() > maxDisplayThreads) {
            System.out.println("总线程数: " + threadPool.getTaskThreads().size());
        }
    }

}