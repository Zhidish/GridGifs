package com.homebrew.gifsygrid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.homebrew.gifsygrid.R
import com.homebrew.gifsygrid.db.GifEntity
import java.io.File

class FullScreenPagerAdapter(
    private val gifList: List<GifEntity>
) : RecyclerView.Adapter<FullScreenPagerAdapter.FullScreenViewHolder>() {

    inner class FullScreenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gifImageView: ImageView = itemView.findViewById(R.id.fullScreenGifImageView)

        fun bind(gifEntity: GifEntity) {
            Glide.with(itemView.context)
                .asGif()
                .load(File(gifEntity.imagePath))
                .into(gifImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullScreenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_full_screen_gif, parent, false)
        return FullScreenViewHolder(view)
    }

    override fun onBindViewHolder(holder: FullScreenViewHolder, position: Int) {
        holder.bind(gifList[position])
    }

    override fun getItemCount(): Int = gifList.size
}