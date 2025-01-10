package com.HomeWork.TaskPool;

/**
 * @author wushifeng
 * @version 1.0
 * @Description: <code>Task</code>状态枚举类, 描述任务状态
 * @Date: 2025/01/05
 */
public enum TaskStatus {
    PENDING,                                                    // 任务未开始
    RUNNING,                                                    // 任务进行中
    COMPLETED,                                                  // 任务已完成
    CANCELLED,                                                  // 任务已取消
    FAILED                                                      // 任务执行失败
}