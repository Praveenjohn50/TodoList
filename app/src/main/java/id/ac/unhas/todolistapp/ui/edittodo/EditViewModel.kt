package id.ac.unhas.todolistapp.ui.edittodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.ac.unhas.todolistapp.repository.TodoRepository
import id.ac.unhas.todolistapp.room.todo.Todo
import kotlinx.coroutines.runBlocking

/**
 * Created by Praveen John on 11/12/2021
 * ViewModel to edit a new Task
 * */
class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val currentTodo = MutableLiveData<Todo>()
    private val editStatus = MutableLiveData<Boolean>()
    private val todoRepository = TodoRepository(application)

    val observableCurrentTodo: LiveData<Todo>
        get() = currentTodo

    val observableEditStatus: LiveData<Boolean>
        get() = editStatus

    fun getTodoData(id: Int) = runBlocking {
        currentTodo.value = todoRepository.getTodo(id)
    }

    fun updateTodo(todo: Todo) {

        editStatus.value = try {
            todoRepository.update(todo)
            true

        } catch (e: IllegalArgumentException) {
            false
        }
    }
}