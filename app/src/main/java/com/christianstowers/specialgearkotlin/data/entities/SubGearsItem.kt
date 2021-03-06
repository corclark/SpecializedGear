package com.christianstowers.specialgearkotlin.data.entities

import com.google.gson.annotations.SerializedName

data class SubGearsItem(@SerializedName("id")
                        val id: Int = 0,
                        @SerializedName("image")
                        val image: String = "",
                        @SerializedName("name")
                        val name: String = "",
                        @SerializedName("order")
                        val order: Int = 0,
                        @SerializedName("price")
                        val price: String = "",
                        @SerializedName("description")
                        val description: String = "")