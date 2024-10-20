package com.homebrew.gifsygrid.network

import com.google.gson.annotations.SerializedName

class Pagination(@SerializedName("total_count")
                 val total_count: Int,

                 @SerializedName("count")
                 val count: Int,

                 @SerializedName("offset")
                 val offset: Int
)