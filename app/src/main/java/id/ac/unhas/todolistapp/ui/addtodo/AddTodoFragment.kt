@file:Suppress("DEPRECATION")

package id.ac.unhas.todolistapp.ui.addtodo

import android.app.*
import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import id.ac.unhas.todolistapp.R
import id.ac.unhas.todolistapp.room.todo.Todo
import id.ac.unhas.todolistapp.receiver.AlarmReceiver
import kotlinx.android.synthetic.main.add_todo_fragment.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Praveen John on 11/12/2021
 * Fragment to add a new Task
 * */
class AddTodoFragment : Fragment() {

    private lateinit var listViewModel: AddTodoViewModel
    private lateinit var alarmReceiver: AlarmReceiver
    private var todoList: Todo? = null
    private var dateAndTimeFormat = SimpleDateFormat("hh:mm, dd MMM YYYY", Locale.getDefault())
    private var initialValue = ContentValues()

    /**
     * OnCreateView
     * @param inflater : Inflater
     * @param container : Container of the view
     * @param savedInstanceState : saved state
     * */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_todo_fragment, container, false)
    }

    /**
     * On Activity created
     * @param savedInstanceState : Bundle
     * */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listViewModel = ViewModelProviders.of(this).get(AddTodoViewModel::class.java)

        alarmReceiver = AlarmReceiver()

        add_button.setOnClickListener {
            val id = if (todoList != null) todoList?.id else null
            val title = add_todo.text.toString()
            val desc = add_description.text.toString()
            val create = System.currentTimeMillis()
            val update = System.currentTimeMillis()
            val dueDate = initialValue.get("add_dueDate")
            if (title != "" && desc != "" && dueDate != null) {
                val add = Todo(
                    id = id,
                    todo = title,
                    desc = desc,
                    createDate = create,
                    updateDate = update,
                    dueDate = dueDate as Long
                )
                listViewModel.addTodo(add)

                if (checkRemind.isChecked) context?.let { it1 ->
                    alarmReceiver.setReminder(
                        it1,
                        dueDate - 3600 * 1000,
                        title
                    )
                }
                else context?.let { it1 -> alarmReceiver.setReminder(it1, dueDate, title) }
            } else Toast.makeText(context, "Please Enter Data Correctly!", Toast.LENGTH_SHORT)
                .show()

            listViewModel.observableStatus.observe(viewLifecycleOwner, Observer { todo ->
                todo?.let { check(todo) }
            })
        }
        btn_date.setOnClickListener { pickDateTime() }
    }

    /**
     * Date and time picker
     * */
    private fun pickDateTime() {
        val dueDateTime = Calendar.getInstance()
        val startYear = dueDateTime.get(Calendar.YEAR)
        val startMonth = dueDateTime.get(Calendar.MONTH)
        val startDay = dueDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = dueDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = dueDateTime.get(Calendar.MINUTE)

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                TimePickerDialog(
                    requireContext(),
                    TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        val selectedDateTime = Calendar.getInstance()
                        selectedDateTime.set(year, month, day, hour, minute)
                        initialValue.put("add_dueDate", selectedDateTime.timeInMillis)
                        val showDateTime = dateAndTimeFormat.format(selectedDateTime.time)
                        add_date.setText(showDateTime)
                    },
                    startHour,
                    startMinute,
                    false
                ).show()
            },
            startYear,
            startMonth,
            startDay
        ).show()
    }

    /**
     * Added task check
     * */
    private fun check(status: Boolean) {
        when (status) {
            true -> {
                findNavController().popBackStack()
                Toast.makeText(context, "To-Do Added Successfully", Toast.LENGTH_SHORT).show()
            }
            false -> Toast.makeText(context, "To-Do Add Failed", Toast.LENGTH_SHORT).show()
        }
    }
}