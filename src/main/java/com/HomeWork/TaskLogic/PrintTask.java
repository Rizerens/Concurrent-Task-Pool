package com.HomeWork.TaskLogic;

import com.HomeWork.TaskPool.Task;
import com.HomeWork.utils.TaskUtils;

/**
 * @author jiangguoliang
 * @version 1.0
 * @Description: 打印任务
 * @Date: 2025/01/08
 */
public class PrintTask implements Task.TaskLogic {
    private final int taskId;                                               // 任务ID
    private final String message;                                           // 任务输出信息

    /**
     * @Description: 构造函数
     * @param taskId 任务ID
     * @author jiangguoliang
     */
    public PrintTask(int taskId) {
        this.taskId = taskId;
        this.message = "任务【" + taskId + "】的输出";
    }

    /**
     * @Description: 执行任务
     * @return 任务结果
     * @author jiangguoliang
     */
    @Override
    public Object execute(){
        long delayMillis = TaskUtils.RandomTimeSimulation();
        System.out.println("任务【" + taskId + "】结果:{" + message + "},耗时：" + delayMillis + "ms");
        return message;
    }
}

