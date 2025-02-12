package com.example.example

import com.google.gson.annotations.SerializedName


data class Analytics (

  @SerializedName("onload"  ) var onload  : Onload?  = Onload(),
  @SerializedName("onclick" ) var onclick : Onclick? = Onclick(),
  @SerializedName("onsent"  ) var onsent  : Onsent?  = Onsent()

)