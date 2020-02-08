package com.example.codemyfirst.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.codemyfirst.MyAdapter
import com.example.codemyfirst.R
import com.example.codemyfirst.model.Data
import com.example.codemyfirst.model.Reqres
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dataList : MutableList<Data> = mutableListOf()
    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup Adapter
        myAdapter = MyAdapter(dataList)

        //setup recyclerview
        my_recycler_view.layoutManager=LinearLayoutManager(this)
        my_recycler_view.addItemDecoration(DividerItemDecoration(this,OrientationHelper.VERTICAL))
        my_recycler_view.adapter = myAdapter

        //setup Android Networking
        AndroidNetworking.initialize(this)
        AndroidNetworking.get("https://reqres.in/api/users?page=1")
            .build()
            .getAsObject(Reqres::class.java,object : ParsedRequestListener<Reqres>{
                override fun onResponse(response: Reqres) {
                    dataList.addAll(response.data)
                    myAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {

                }
            })
    }
}
