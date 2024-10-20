package com.homebrew.gifsygrid.network

import com.google.gson.annotations.SerializedName

class SearchData(@SerializedName("data")
                 val data: List<Gif>,

                 @SerializedName("meta")
                 val meta: Meta,

                 @SerializedName("pagination")
                 val pagination: Pagination)