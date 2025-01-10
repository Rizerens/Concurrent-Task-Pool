package com.HomeWork.TaskPool;

import com.HomeWork.utils.TaskUtils;
import lombok.Getter;

/**
 * @author wushifeng
 * @version 1.0
 * @Description: 任务类, 用于封装任务信息
 * @Date: 2025/01/05
 */
@Getter
public class Task {
    private final int taskId;                                                   // 任务唯一标识
    private final String taskName;                                              // 任务名
    private final TaskLogic taskLogic;                                          // 任务逻辑
    private volatile TaskStatus status;                                         // 任务状态
    private Object result;                                                      // 任务结果
    private Exception exception;                                                // 任务失败异常

    /**
     * @Description: 构造函数
     * @param taskName 任务名称
     * @param taskLogic 任务逻辑
     * @author wushifeng
     */
    public Task(String taskName, TaskLogic taskLogic) {
        this.taskId = TaskUtils.generateTaskId();
        this.taskName = taskName;
        this.taskLogic = taskLogic;
        this.status = TaskStatus.PENDING;
    }

    /**
     * @Description: 执行任务
     * @author wushifeng
     */
    public synchronized void execute() {
        if (status == TaskStatus.CANCELLED) {
            System.out.println("编号[" + taskId + "]任务" + taskName + "已取消。跳过执行。");
            return;
        }

        try {
            System.out.println("编号[" + taskId + "]任务" + taskName + "正在启动。");
            status = TaskStatus.RUNNING;
            result = taskLogic.execute(); // 执行任务逻辑
            status = TaskStatus.COMPLETED;
            System.out.println("编号[" + taskId + "]任务" + taskName + "成功完成。");
        } catch (Exception e) {
            status = TaskStatus.FAILED;
            exception = e;
            System.err.println("编号[" + taskId + "]任务" + taskName + "失败，出现异常：" + e.getMessage());
        }
    }

    /**
     * @Description: 取消任务
     * @author wushifeng
     */
    public synchronized void cancel() {
        if (status == TaskStatus.PENDING || status == TaskStatus.RUNNING) {
            status = TaskStatus.CANCELLED;
            System.out.println("编号[" + taskId + "]任务" + taskName + "已被取消。");
        }
    }

    /**
     * @Description: 任务逻辑接口, 用于封装任务逻辑
     * @author wushifeng
     */
    @FunctionalInterface
    public interface TaskLogic {
        Object execute() throws Exception;
    }
}
