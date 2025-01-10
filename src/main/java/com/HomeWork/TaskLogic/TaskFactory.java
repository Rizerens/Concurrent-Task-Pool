package com.HomeWork.TaskLogic;


import com.HomeWork.TaskPool.Task;
import com.HomeWork.TaskPool.Task.TaskLogic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wushifeng
 * @version 1.0
 * @Description: 工厂类, 用于创建任务
 * @Date: 2025/01/08
 */
public class TaskFactory {

    /**
     * 根据传入的类型和任务编号创建 Task 对象
     * @param taskType 任务类型
     * @param taskId 任务编号
     * @return 创建的 Task 对象
     * @author wushifeng
     */
    public Task createTask(String taskType, int taskId) {
        TaskLogic taskLogic;
        String taskName = switch (taskType.toLowerCase()) {
            case "filetask" -> {
                taskLogic = new FileWriteTask(taskId);
                yield "文件任务【" + taskId + "】";
            }
            case "calculationtask" -> {
                taskLogic = new CalculationTask(taskId);
                yield "计算任务【" + taskId + "】";
            }
            case "printtask" -> {
                taskLogic = new PrintTask(taskId);
                yield "打印任务【" + taskId + "】";
            }
            default -> throw new IllegalArgumentException("未知的任务类型: " + taskType);
        };

        return new Task(taskName, taskLogic);
    }

    /**
     * @Description: 创建任务序列
     * @param taskType 任务类型
     * @param taskNumber 任务数量
     * @return 任务列表
     * @author wushifeng
     */
    public BlockingQueue<Task> createTaskSequence(String taskType, int taskNumber) {
        BlockingQueue<Task> tasks = new LinkedBlockingQueue<>();
        for (int i = 0; i < taskNumber; i++) {
            Task task = createTask(taskType, i);
            tasks.add(task);
        }
        return tasks;
    }
}