package com.christianstowers.specialgearkotlin.ui.GearList

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.christianstowers.specialgearkotlin.R
import com.christianstowers.specialgearkotlin.data.GearAPI
import com.christianstowers.specialgearkotlin.data.entities.GearModel
import kotlinx.android.synthetic.main.layout_gear_recycler.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class GearListActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var gearListAdapter: GearListAdapter

    private val TAG = "GearListActivity"

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_gear_recycler)

        val toolbar = findViewById<Toolbar>(R.id.gear_toolbar)
        setSupportActionBar(toolbar)
//        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(false)


        recyclerView = findViewById(R.id.gear_recycler)
        gearListAdapter = GearListAdapter(this)
        gear_recycler.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = gearListAdapter

        val gearAPI = GearAPI.create().getGear()

        gearAPI.enqueue( object : Callback<List<GearModel>> {
            override fun onResponse(call: Call<List<GearModel>>, response: Response<List<GearModel>>) {
                Log.d(TAG, "STUFF: " + response.body() + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
                if(response?.body() != null)
                // sort gear list by order
                    Collections.sort(response.body()) { o1, o2 -> o1.order.compareTo(o2.order) }
                    gearListAdapter.setGearListItems(response.body()!!)
            }

            override fun onFailure(call: Call<List<GearModel>>?, t: Throwable?) {
                // nada
            }
        })

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.gear_menu, menu);
//        return true;
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.gear_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ride_app -> {
                if (openApp(this@GearListActivity, "com.specialized.ride")) {
                    openApp(this@GearListActivity, "com.specialized.ride")
                } else {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.specialized.ride")))
                    } catch (anfe: android.content.ActivityNotFoundException) {
                        val launchIntent = packageManager.getLaunchIntentForPackage("com.android.vending")
                        startActivity(launchIntent)
                    }

                }
                return true
            }
            R.id.logout ->{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you wish to logout?")
                builder.setNegativeButton("No") { dialog, which ->
                    // Dismiss Alert
                }
                builder.setPositiveButton("Yes") { dialog, which ->
                    val url = "https://www.specialized.com"
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
                builder.show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun openApp(context: Context, packageName: String): Boolean {
        val manager = context.packageManager

        try {
            val i = manager.getLaunchIntentForPackage(packageName) ?: return false
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            context.startActivity(i)
            return true
        } catch (e: ActivityNotFoundException) {
            return false
        }

    }

}