package com.example.todobygama

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.text.SimpleDateFormat
import java.time.Clock
import java.util.*
import kotlin.system.measureNanoTime

class CreateTask : AppCompatActivity() {
    var mCategory: String = ""
    var mTitle: String = ""
    var mDescription: String =""
    var mDate: String = ""
    var mTime: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        //Setting Spinner
        val mySpinner = findViewById<Spinner>(R.id.spinner) as Spinner
        val options = arrayOf("University", "Home", "Office", "Personal")
        mySpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)
        mySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mCategory = options.get(p2)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@CreateTask, "No Category Selected", Toast.LENGTH_LONG).show()
            }

        }

        // Calender Functionality
        ic_calender.setOnClickListener{ view ->
            clickDatePicker()
            mDate = "11/20/2021"
            scheduled_date.setText(mDate)
        }

        // Clock Functionality
        ic_time.setOnClickListener{ view ->
            clickTimePicker()
            mTime = "02:00 PM"
            scheduled_time.setText(mTime)
        }

        //Save Button Functionality
        btn_save.setOnClickListener{
            if(create_title.text.toString().trim{it<=' '}.isNotEmpty()
                && task_description.text.toString().trim{it<=' '}.isNotEmpty()
                && mDate.isNotEmpty() && mTime.isNotEmpty()){
                mTitle = create_title.text.toString()
                mDescription = task_description.text.toString()
                DataObject.setData(mTitle, mDescription, mCategory, mDate, mTime)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else if(create_title.text.toString().trim{it<=' '}.isEmpty()) {
                Toast.makeText(this@CreateTask, "Please write the title", Toast.LENGTH_LONG).show()
            }
            else if(task_description.text.toString().trim{it<=' '}.isEmpty()) {
                Toast.makeText(this@CreateTask, "Please write a short description to remember", Toast.LENGTH_LONG).show()
            }
            else if(mDate.isEmpty()) {
                Toast.makeText(this@CreateTask, "Please select a Date", Toast.LENGTH_LONG).show()
            }
            else if(mTime.isEmpty()) {
                Toast.makeText(this@CreateTask, "Please select Time", Toast.LENGTH_LONG).show()
            }
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
                mDate = selectedDate
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
                mTime =
                    String.format("%02d", picker.hour - 12) + " : " + String.format(
                        "%02d",
                        picker.minute
                    ) + "PM"
            } else {
                mTime = String.format("%02d", picker.hour) + " : " + String.format(
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