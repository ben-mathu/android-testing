package com.example.android.architecture.blueprints.todoapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailViewModel
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel

/**
 * @time 22/12/20
 */
@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory (
        private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            (TasksViewModel(tasksRepository) as T)
}

@Suppress("UNCHECKED_CAST")
class TasksDetailViewModelFactory (
        private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            (TaskDetailViewModel(tasksRepository) as T)
}