package com.homebrew.gifsygrid.network

import com.google.gson.annotations.SerializedName

class Meta(@SerializedName("status")
           val status: Int,

           @SerializedName("msg")
           val msg: String,

           @SerializedName("response_id")
           val response_id:
           String)