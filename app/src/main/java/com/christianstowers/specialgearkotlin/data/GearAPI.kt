package com.christianstowers.specialgearkotlin.data

import com.christianstowers.specialgearkotlin.data.entities.GearModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GearAPI {

    @GET("api/gear/")
    fun getGear() : Call<List<GearModel>>

//    @GET("api/gear/{id}/")
//    fun getGearDetail(@Path("id") Integer id)

    companion object {

        var BASE_URL = "https://interview.retul.com/"

        fun create() : GearAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(GearAPI::class.java)

        }

    }

}