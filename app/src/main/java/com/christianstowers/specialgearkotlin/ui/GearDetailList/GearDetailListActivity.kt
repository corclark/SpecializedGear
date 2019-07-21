package com.christianstowers.specialgearkotlin.ui.GearDetailList

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.christianstowers.specialgearkotlin.R
import com.christianstowers.specialgearkotlin.data.GearAPI
import com.christianstowers.specialgearkotlin.data.entities.GearDetailModel
import com.christianstowers.specialgearkotlin.ui.GearList.GearListActivity
import kotlinx.android.synthetic.main.layout_gear_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GearDetailListActivity : AppCompatActivity() {

    private val TAG = "GearDetailListActivity"

    lateinit var recyclerView: RecyclerView
    lateinit var gearDetailListAdapter: GearDetailListAdapter

    internal var passedInGearID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_gear_detail)

        val toolbar = findViewById<Toolbar>(R.id.gear_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        val i = intent
        val bundle = i.extras
        if (bundle != null) {
            passedInGearID = bundle.getInt("gearID")
        }

        recyclerView = findViewById(R.id.subgear_recycler)
        gearDetailListAdapter = GearDetailListAdapter(this)
        subgear_recycler.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = gearDetailListAdapter

        toolbar.setNavigationOnClickListener { v: View ->
            val intent = Intent(this, GearListActivity::class.java)
            startActivity(intent)
        }

        val gearDetailName: TextView = findViewById(R.id.gear_detail_name)
        val gearDetailDesc: TextView = findViewById(R.id.gear_detail_desc)
        val gearDetailImage: ImageView = findViewById(R.id.gear_detail_img)
//        val gearDetailPrice: TextView = findViewById(R.id.gear_detail_price)

        val gearAPI = GearAPI.create().getGearDetail(passedInGearID)

        gearAPI.enqueue( object : Callback<GearDetailModel> {
            override fun onResponse(call: Call<GearDetailModel>, response: Response<GearDetailModel>) {
                if(response.body() != null)
                    gearDetailName.text = response.body()?.name
                    gearDetailDesc.text = response.body()?.description
                    Glide.with(this@GearDetailListActivity).load(response.body()?.image)
                        .into(gearDetailImage)
                    response.body()?.subGears?.let { gearDetailListAdapter.setSubGearListItems(it) }

//                var i: Int?
//                var fullPrice: Float? = 0.0f
//                i = 0
//                while (i < response.body()?.subGears?.size) {
//                    val price = mSubGearArrayList.get(i).getPrice()
//                    if (price != null) {
//                        fullPrice += java.lang.Float.valueOf(price!!)
//                    }
//                    i++
//                }
            }
            override fun onFailure(call: Call<GearDetailModel>?, t: Throwable?) {
                // nada
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.gear_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ride_app -> {
                if (openApp(this@GearDetailListActivity, "com.specialized.ride")) {
                    openApp(this@GearDetailListActivity, "com.specialized.ride")
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