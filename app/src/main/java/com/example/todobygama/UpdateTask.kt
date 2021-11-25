package com.example.todobygama

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.activity_update_task.*
import kotlinx.android.synthetic.main.activity_update_task.create_title
import kotlinx.android.synthetic.main.activity_update_task.ic_calender
import kotlinx.android.synthetic.main.activity_update_task.ic_time
import kotlinx.android.synthetic.main.activity_update_task.task_description
import java.util.*

class UpdateTask : AppCompatActivity() {
    var title: String = ""
    var description: String =""
    var category: String = ""
    var date: String = ""
    var time: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)

        //Setting Spinner
        val mySpinner = findViewById<Spinner>(R.id.spinner) as Spinner
        val options = arrayOf("University", "Home", "Office", "Personal")
        mySpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)
        mySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                category = options.get(p2)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@UpdateTask, "No Category Selected", Toast.LENGTH_LONG).show()
            }
        }

        val pos = intent.getIntExtra("id", -1)
        if(pos!=-1){
            title = DataObject.getData(pos).CardTaskTitle
            description = DataObject.getData(pos).CardTaskDescription
            category = DataObject.getData(pos).CardTaskCategory
            time = DataObject.getData(pos).CardTaskTime
            date = DataObject.getData(pos).CardTaskDate
            create_title.setText(title)
            task_description.setText(description)

        }
    }

    private fun clickDatePicker(){
        var date: String =""
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "${selectedMonth + 1}/$selectedDayOfMonth/$selectedYear"
                date =selectedDate
            },
            year,
            month,
            day
        )
        dpd.show()
    }

    private fun clickTimePicker(){
        var picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager, "pickerFragement")

        picker.addOnPositiveButtonClickListener {

            if (picker.hour > 12) {
                time =
                    String.format("%02d", picker.hour - 12) + " : " + String.format(
                        "%02d",
                        picker.minute
                    ) + "PM"
            } else {
                time = String.format("%02d", picker.hour) + " : " + String.format(
                    "%02d",
                    picker.minute
                ) + "AM"
            }
            var calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

        }
    }
}