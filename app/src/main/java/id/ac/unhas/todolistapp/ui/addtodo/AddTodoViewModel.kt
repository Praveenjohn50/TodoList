package id.ac.unhas.todolistapp.ui.addtodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.ac.unhas.todolistapp.repository.TodoRepository
import id.ac.unhas.todolistapp.room.todo.Todo

/**
 * Created by Praveen John on 11/12/2021
 * ViewModel to add a new Task
 * */
class AddTodoViewModel(application: Application) : AndroidViewModel(application) {

    private val status = MutableLiveData<Boolean>()
    private val todoRepository = TodoRepository(application)

    val observableStatus: LiveData<Boolean>
        get() = status

    /**
     * Add new task
     * */
    fun addTodo(todo: Todo) {
        status.value = try {
            todoRepository.insert(todo)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}