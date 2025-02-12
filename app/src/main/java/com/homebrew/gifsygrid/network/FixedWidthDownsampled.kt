package com.example.example

import com.google.gson.annotations.SerializedName


data class FixedWidthDownsampled (

  @SerializedName("height"    ) var height   : String? = null,
  @SerializedName("width"     ) var width    : String? = null,
  @SerializedName("size"      ) var size     : String? = null,
  @SerializedName("url"       ) var url      : String? = null,
  @SerializedName("webp_size" ) var webpSize : String? = null,
  @SerializedName("webp"      ) var webp     : String? = null

)