package com.christianstowers.specialgearkotlin.ui.GearList

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.christianstowers.specialgearkotlin.R
import com.christianstowers.specialgearkotlin.RecyclerAdapter
import kotlinx.android.synthetic.main.layout_gear_recycler.*

class GearListActivity : AppCompatActivity {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_gear_recycler)

        recyclerView = findViewById(R.id.gear_recycler)
        recyclerAdapter = RecyclerAdapter(this)
        gear_recycler.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = recyclerAdapter

        val gearAPI = GearAPI.create().

    }

}