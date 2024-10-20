package com.example.example

import com.google.gson.annotations.SerializedName


data class Images (
  @SerializedName("original"                 ) var original               : Original?               = Original(),
  @SerializedName("fixed_height"             ) var fixedHeight            : FixedHeight?            = FixedHeight(),
  @SerializedName("fixed_height_downsampled" ) var fixedHeightDownsampled : FixedHeightDownsampled? = FixedHeightDownsampled(),
  @SerializedName("fixed_height_small"       ) var fixedHeightSmall       : FixedHeightSmall?       = FixedHeightSmall(),
  @SerializedName("fixed_width"              ) var fixedWidth             : FixedWidth?             = FixedWidth(),
  @SerializedName("fixed_width_downsampled"  ) var fixedWidthDownsampled  : FixedWidthDownsampled?  = FixedWidthDownsampled(),
  @SerializedName("fixed_width_small"        ) var fixedWidthSmall        : FixedWidthSmall?        = FixedWidthSmall()

)