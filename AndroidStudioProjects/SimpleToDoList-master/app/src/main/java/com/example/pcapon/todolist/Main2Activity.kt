package com.example.pcapon.todolist

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*

class Main2Activity : AppCompatActivity() {

    companion object {
        val DESCRIPTION_TEXT = "description"
        val DATE = "20 jav 2018 18:20"
        val HOUR = "10:20"
        val POSITIONTASK = "BITE"
        val EDIT = "0"
    }

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val edit = intent.getIntExtra(EDIT, 0)
        val datetext = findViewById<EditText>(R.id.task_date)
        val timetext = findViewById<EditText>(R.id.task_time)


        if (edit == 1) {
            println("In 2nd activity " + intent.getStringExtra(POSITIONTASK))
            setTitle("Edit task")

            val buttontxt = findViewById<Button>(R.id.submit)
            val desctext = findViewById<EditText>(R.id.task_description)

            desctext.setText(intent.getStringExtra(DESCRIPTION_TEXT))
            timetext.setText(intent.getStringExtra(HOUR))
            datetext.setText(intent.getStringExtra(DATE))
            buttontxt.setText("Edit")
        }

        val hourSetListener = object  : TimePickerDialog.OnTimeSetListener{
            override  fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int){
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                updateHourInView()
            }
        }

        timetext.setOnClickListener(object : View.OnClickListener {
            override  fun onClick(view: View){
                TimePickerDialog(this@Main2Activity, hourSetListener,
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        true).show()
            }
        })

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        datetext.setOnClickListener(object : View.OnClickListener   {
            override fun onClick(view: View){
                DatePickerDialog(this@Main2Activity, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })


    }

    fun submitMe( @Suppress("UNUSED_PARAMETER") view: View ) {
        val data = Intent()

        val edit = intent.getIntExtra(EDIT, 0)
        val description = findViewById(R.id.task_description) as? EditText
        val hour = findViewById(R.id.task_time) as? EditText
        val date = findViewById(R.id.task_date) as? EditText

        if (edit == 1)
            data.putExtra(MainActivity.POSITION, intent.getStringExtra(POSITIONTASK))


        data.putExtra(MainActivity.DESCRIPTION_TEXT, description?.text.toString())
        data.putExtra(MainActivity.DATE, date?.text.toString())
        data.putExtra(MainActivity.HOUR, hour?.text.toString())
        //data.putExtras(bundle)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun updateDateInView() {
        val datetext = findViewById<EditText>(R.id.task_date)
        val myFormat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datetext.setText(sdf.format(cal.getTime()))
    }

    private  fun updateHourInView(){
        val timetext = findViewById<EditText>(R.id.task_time)
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        timetext.setText(sdf.format(cal.getTime()))
    }


}
