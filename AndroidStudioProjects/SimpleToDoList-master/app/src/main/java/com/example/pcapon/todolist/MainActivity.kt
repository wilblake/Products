package com.example.pcapon.todolist

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.Toast
import android.widget.AdapterView
import java.io.Serializable

// The question mark indicates that this variable can contain null
// A data class is a POJO (Plain Old Java Object) which Kotlin will create
// containing the mentioned private member variables and getters/setters for each.
// It will also contain a default constructor and an overloaded constructor with the
// private member variables listed.
data class Task(var title: String?,
                var description: String?,
                var date: String?,
                var hour: String?,
                var completed: Boolean = false) : Serializable

class MainActivity : AppCompatActivity() {

    //The question mark indicates that this variable can contain null
    private var tasks = mutableListOf<Task?>()

    var adapter: customAdapter? = customAdapter(this, tasks)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mListView = (items_list) findViewById(R.id.items_list)



        val lv = findViewById<ListView>(R.id.items_list)


        lv.setOnItemClickListener { _, _, position, _ ->

            Toast.makeText(this@MainActivity, "You have bite Clicked " + position, Toast.LENGTH_SHORT).show()
            showSimpleAlert()
        }

        lv.adapter = adapter

       registerForContextMenu(lv)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menu?.setHeaderTitle("Action")
        menu?.add(1, 1, 1, "Delete")
        menu?.add(1, 2, 1, "Edit")
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val info = item?.menuInfo as AdapterView.AdapterContextMenuInfo

        when (item.title){
            "Delete" -> deleteTask(info.position)
            "Edit" ->   editTask(info.position)
        }

        return super.onContextItemSelected(item)
    }

    private fun deleteTask(position: Int){
        adapter?.removeTask(position)
        Toast.makeText(applicationContext, "Task deleted", Toast.LENGTH_SHORT).show()

    }

    private fun showSimpleAlert() {


        val simpleAlert = AlertDialog.Builder(this@MainActivity).create()
        simpleAlert.setTitle("Alert")
        simpleAlert.setMessage("Show simple Alert")

        simpleAlert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
            _, _ ->
            Toast.makeText(applicationContext, "You clicked on OK", Toast.LENGTH_SHORT).show()
        })

        simpleAlert.show()
    }

    class customAdapter(context: Context, var tasks: MutableList<Task?>): BaseAdapter() {

        private val mContext: Context

        init {
            mContext = context
        }

        override fun getCount(): Int {
            return tasks.size
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }
        override fun getItem(p0: Int): Any {
            return "testString"
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {




            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_main, p2, false)
            val desc = rowMain.findViewById<TextView>(R.id.description)
            val actualtask = tasks.get(p0)

            desc.text = actualtask?.description
            val dateHour = rowMain.findViewById<TextView>(R.id.dateHour)
            dateHour.text = actualtask?.date + " " + actualtask?.hour

            return rowMain

        }

        fun editTask(position: Int, task: Task)
        {
            tasks.set(position, task)
            notifyDataSetChanged()
        }

        fun removeTask(position: Int)
        {
            tasks.removeAt(position)
            notifyDataSetChanged()
        }

        fun addTask(task: Task) {
            println(tasks)
            tasks.add(task)
            notifyDataSetChanged()
        }
    }

    fun editTask(position: Int){

        println(adapter?.tasks)
        println(position)
        val task = adapter?.tasks?.get(position)
        val stringpos = position.toString()
        val randomIntent = Intent(this, Main2Activity::class.java)

        randomIntent.putExtra(Main2Activity.DESCRIPTION_TEXT, task?.description)
        randomIntent.putExtra(Main2Activity.DATE, task?.date)
        randomIntent.putExtra(Main2Activity.HOUR, task?.hour)
        randomIntent.putExtra(Main2Activity.POSITIONTASK, stringpos)
        randomIntent.putExtra(Main2Activity.EDIT, 1)
        startActivityForResult(randomIntent, EDIT_TASK_REQUEST)
    }

    fun addNewTask(@Suppress("UNUSED_PARAMETER") view: View) {
        val randomIntent = Intent(this, Main2Activity::class.java)
        startActivityForResult(randomIntent, ADD_TASK_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            var task = Task("", data?.getStringExtra(DESCRIPTION_TEXT), data?.getStringExtra(DATE), data?.getStringExtra(HOUR), false)
            adapter?.addTask(task)
        }
        if (requestCode == EDIT_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            val positionstring = data?.getStringExtra(POSITION)
            val posint = positionstring?.toInt()
            var task = Task("", data?.getStringExtra(DESCRIPTION_TEXT), data?.getStringExtra(DATE), data?.getStringExtra(HOUR), false)
            adapter?.editTask(posint!!, task)
        }

    }

    override fun onResume() {
        super.onResume()
        println("ON RESUME")
        val tasksfile = Storage.readData(this)

        // We only want to set the tasks if the list is already empty.
        if (adapter?.tasks != null && (adapter?.tasks?.isEmpty() ?: true)) {
            adapter?.tasks = tasksfile
        }
    }

    override fun onPause() {
        super.onPause()
        println("ONPAUSE")
        Storage.writeData(this, adapter?.tasks!!)
    }

    companion object {
        private val ADD_TASK_REQUEST = 0
        private val EDIT_TASK_REQUEST = 1
        val DESCRIPTION_TEXT = "description"
        val DATE = "20 jav 2018 18:20"
        val HOUR = "10:20"
        val POSITION = "0"
    }

}
