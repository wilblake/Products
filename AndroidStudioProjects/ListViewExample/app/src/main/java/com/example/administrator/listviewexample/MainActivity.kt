package com.example.administrator.listviewexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val myStringArray = arrayOf("A", "B", "C")
        val myAdapter = ArrayAdapter<String>(
                    this,
                android.R.layout.simple_list_item_1,
                myStringArray)
        listView.setAdapter(myAdapter)

    }



}
