package com.example.todobygama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_new_task.setOnClickListener{
            val intent = Intent(this, CreateTask::class.java)
            startActivity(intent)
        }
        btn_delete_all.setOnClickListener{
            DataObject.deleteAll()
        }
        recycler_view.adapter = Adaptor(DataObject.getAllData())
        recycler_view.layoutManager = LinearLayoutManager(this)
    }
}