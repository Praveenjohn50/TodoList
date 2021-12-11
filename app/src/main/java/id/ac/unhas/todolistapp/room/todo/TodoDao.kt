package id.ac.unhas.todolistapp.room.todo

import androidx.lifecycle.LiveData
import androidx.room.*
/**
 * Created by Praveen John on 11/12/2021
 * Database Dao
 * */
@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun loadAllTodo(): LiveData<List<Todo>>?

    @Query("Select * from todo Where id =:id")
    suspend fun loadSingle(id : Int): Todo

    @Query("SELECT * FROM todo ORDER BY created_date DESC")
    fun sortCreated(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo ORDER BY due_date ASC")
    fun sortDue(): LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)
}