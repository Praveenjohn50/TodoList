@file:Suppress("DEPRECATION", "SENSELESS_COMPARISON")

package id.ac.unhas.todolistapp.ui.edittodo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import id.ac.unhas.todolistapp.R
import id.ac.unhas.todolistapp.room.todo.Todo
import id.ac.unhas.todolistapp.receiver.AlarmReceiver
import kotlinx.android.synthetic.main.edit_fragment.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Praveen John on 11/12/2021
 * Fragment to edit a new Task
 * */
class EditFragment : Fragment() {

    private lateinit var viewModel: EditViewModel
    private lateinit var alarmReceiver: AlarmReceiver
    private val args by navArgs<EditFragmentArgs>()
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
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    /**
     * On Activity created
     * @param savedInstanceState : Bundle
     * */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)

        viewModel.observableCurrentTodo.observe(viewLifecycleOwner, Observer { currentTodo ->
            currentTodo?.let { initCurrentTodo(currentTodo) } ?: todoNotFound()
        })

        viewModel.getTodoData(args.todoId)

        alarmReceiver = AlarmReceiver()

        update_button.setOnClickListener {
            val id = args.todoId
            val title = update_title.text.toString()
            val desc = update_description.text.toString()
            val create = initialValue.get("created_date")
            val dueDate = initialValue.get("update_dueDate")
            val update = System.currentTimeMillis()
            if(title != "" && desc != "" && dueDate != null) {
                val add = Todo(
                    id = id,
                    todo = title,
                    desc = desc,
                    createDate = create as Long,
                    dueDate = dueDate as Long,
                    updateDate = update
                )
                viewModel.updateTodo(add)

                if(checkRemindUpdate.isChecked) context?.let { it1 -> alarmReceiver.setReminder(it1, dueDate - 3600 * 1000, title) }
                else context?.let { it1 -> alarmReceiver.setReminder(it1, dueDate, title) }
            } else Toast.makeText(context,"Please Enter Data Correctly!", Toast.LENGTH_SHORT).show()

            viewModel.observableEditStatus.observe(viewLifecycleOwner, Observer { editStatus ->
                editStatus?.let { check(editStatus) }
            })
        }
        btn_updateDate.setOnClickListener {pickDateTime()}
    }

    /**
     * Initialize current time
     * */
    private fun initCurrentTodo(todo: Todo) {
        initialValue.put("created_date", todo.createDate)
        update_title.setText(todo.todo)
        update_description.setText(todo.desc)
        update_dueDate.setText(dateAndTimeFormat.format(todo.dueDate))
    }

    /**
     * Pick date and time
     * */
    private fun pickDateTime() {
        val dueDateTime = Calendar.getInstance()
        val startYear = dueDateTime.get(Calendar.YEAR)
        val startMonth = dueDateTime.get(Calendar.MONTH)
        val startDay = dueDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = dueDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = dueDateTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val selectedDateTime = Calendar.getInstance()
                selectedDateTime.set(year, month, day, hour, minute)
                initialValue.put("update_dueDate", selectedDateTime.timeInMillis)
                val showDateTime = dateAndTimeFormat.format(selectedDateTime.time)
                update_dueDate.setText(showDateTime)
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

    /**
     * Update status check
     * */
    private fun check(status: Boolean) {
        when (status) {
            true -> {
                findNavController().popBackStack()
                Toast.makeText(context,"To-Do Updated Successfully", Toast.LENGTH_SHORT).show()
            }
            false -> Toast.makeText(context,"To-Do Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Not found
     * */
    private fun todoNotFound() {
        view?.let {
            Snackbar.make(it, R.string.error_loading_data, Snackbar.LENGTH_LONG).show()
        }
    }
}
