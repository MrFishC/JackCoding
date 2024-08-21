package cn.jack.tasktool.flow

/**
 * @创建者 Jack
 * @创建时间 2024-06-30 13:59
 * @描述 task运行监听器
 */
interface TaskListener {
    fun onStart(task: Task)

    fun onRunning(task: Task)

    fun onFinished(task: Task)
}