package id.ac.unhas.todolistapp.ui.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.unhas.todolistapp.room.todo.Todo
import id.ac.unhas.todolistapp.repository.TodoRepository

/**
 * Created by Praveen John on 11/12/2021
 * ViewModel to show the list
 * */
class TodoListViewModel(application: Application) : AndroidViewModel(application){

    private val todoRepository = TodoRepository(application)
    private val todoList : LiveData<List<Todo>>? = todoRepository.getTodoList()

    /**
     * Get full list
     * */
    fun getTodo(): LiveData<List<Todo>>? {
        return todoList
    }

    /**
     * delete a specific item from db
     * @param todo : item
     * */
    fun deleteTodo(todo: Todo) {
        todoRepository.delete(todo)
    }
}