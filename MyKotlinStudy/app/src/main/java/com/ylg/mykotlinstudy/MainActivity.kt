package com.ylg.mykotlinstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    var TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        //设置固定大小
        //recyclerView.setHasFixedSize(true);

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager?.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = linearLayoutManager

        adapter = RecyclerAdapter(this)
        //recyclerView?.adapter = adapter
        recyclerView?.setAdapter(adapter)

    }
}