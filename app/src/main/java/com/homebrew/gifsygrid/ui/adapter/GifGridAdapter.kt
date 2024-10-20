package com.homebrew.gifsygrid.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.homebrew.gifsygrid.R
import com.homebrew.gifsygrid.network.Gif


class GifGridAdapter(
    private val context: Context,
    private var gifUrls: List<Gif>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<GifGridAdapter.GifViewHolder>() {

    inner class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gifImageView: ImageView = itemView.findViewById(R.id.gifItem)

        fun bind(position: Int) {
            Glide.with(context)
                .asGif()
                .load(gifUrls[position].images?.fixedWidth?.url)
                .into(gifImageView)


            Log.e("Giphy","${gifUrls[position].images?.fixedWidth?.url}")
            itemView.setOnClickListener {
                onItemClick(position)  // Handle click and pass the position
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = gifUrls.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateGrid(list : List<Gif>){
        gifUrls = list
        notifyDataSetChanged()
    }
}