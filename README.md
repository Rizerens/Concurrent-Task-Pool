# 项目概述 #
 * 本项目是一个基于 Java 的并发任务池，主要包括任务池管理、线程池管理和任务执行逻辑。
## 主要功能 ##
### 任务队列管理 ###
 * 使用阻塞队列实现任务队列，支持添加、获取和检查队列是否为空等操作。
 * 队列最大容量可配置，默认值为`1000`。
### 线程池管理 ### 
 * 实现了一个简单的线程池，支持添加线程、暂停、恢复和停止线程池等操作。
 * 线程池大小可配置，默认值为`10`。
 * 线程池中的线程会从任务队列中获取任务并执行。
### 任务执行逻辑 ###
 * 定义了任务的基本结构，包括任务ID、任务名、任务逻辑和任务状态等。
 * 提供了任务执行、取消和查询任务状态等功能。
 * 任务逻辑通过接口实现，支持自定义任务逻辑。
### 任务类型 ###
 * 目前支持打印任务、计算任务和文件写入任务。
   - 打印任务用于输出指定信息。
   - 计算任务用于执行简单的计算操作。
   - 文件写入任务用于将指定内容写入文件。
### 项目结构 ###
 - `src/main/java/com/HomeWork/TaskPool/` 包含任务队列和线程池相关的类。
 - `src/main/java/com/HomeWork/TaskLogic/` 包含任务执行逻辑相关的类。
 - `src/main/java/com/HomeWork/utils/` 包含工具类。
## 如何使用 ##
 - 创建线程池  
```
ThreadPool threadPool = new ThreadPool(10);
```
 - 提交任务
```
Task task = new Task("任务名", taskLogic);
threadPool.submitTask(task);
```
 - 等待任务完成
```
threadPool.waitForCompletion();
```
 - 停止线程池
```
threadPool.shutdown();
```

### 示例代码 ###
 - 以下是一个简单的示例，展示如何使用本项目的线程池提交任务并等待任务完成。
```
public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(5);
    
            for (int i = 0; i < 10; i++) {
                Task task = new Task("任务" + i, new PrintTask(i));
                threadPool.submitTask(task);
            }
    
            threadPool.waitForCompletion();
            threadPool.shutdown();
    }
}
```