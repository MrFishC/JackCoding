package cn.jack.tasktool.flow

interface ITaskCreator {
    fun createTask(taskName: String): Task
}