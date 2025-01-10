package com.HomeWork.TaskLogic;

import com.HomeWork.TaskPool.Task;
import com.HomeWork.utils.TaskUtils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author jiangguoliang
 * @version 1.0
 * @Description: 打印任务
 * @Date: 2025/01/08
 */
public class FileWriteTask implements Task.TaskLogic {
    private final int taskId;                                                  // 任务ID
    private final String content;                                              // 任务内容
    private final String fileName;                                             // 文件名
    private final String message;                                              // 任务输出信息

    /**
     * @Description: 构造函数
     * @param taskId 任务ID
     * @author jiangguoliang
     */
    public FileWriteTask(int taskId) {
        this.taskId = taskId;
        this.content = "任务【" + taskId + "】的内容";
        this.fileName = "文件" + (taskId % 10) + ".txt";
        this.message = "向" + fileName + "写入" + content;
    }

    /**
     * @Description: 执行任务
     * @return Object 任务结果
     * @author jiangguoliang
     */
    @Override
    public Object execute(){
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(content + "\n");
            long delayMillis = TaskUtils.RandomTimeSimulation();
            System.out.println("任务【" + taskId + "】结果:{" + message + "},耗时：" + delayMillis + "ms");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return message;
    }
}
