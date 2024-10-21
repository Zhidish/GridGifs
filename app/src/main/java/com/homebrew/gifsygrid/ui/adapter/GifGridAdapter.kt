package com.homebrew.gifsygrid.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.homebrew.gifsygrid.R
import com.homebrew.gifsygrid.db.GifEntity
import com.homebrew.gifsygrid.network.Gif
import java.io.File


class GifGridAdapter(
    private val context: Context,
    private val onItemClick: (Int, List<GifEntity>) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : PagingDataAdapter<GifEntity, GifGridAdapter.GifViewHolder>(GifComparator) {

    inner class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gifImageView: ImageView = itemView.findViewById(R.id.gifItem)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        fun bind(gifEntity: GifEntity?) {
            if (gifEntity == null) return

            // Load GIF from local file
            Glide.with(context)
                .asGif()
                .load(File(gifEntity.imagePath))
                .override(400,400)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(gifImageView)

            itemView.setOnClickListener {
                // Get the current list of items
                val currentList = snapshot().items
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position, currentList)
                }
            }

            deleteButton.setOnClickListener {
                onDeleteClick(gifEntity.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gifEntity = getItem(position)
        holder.bind(gifEntity)
    }

    object GifComparator : DiffUtil.ItemCallback<GifEntity>() {
        override fun areItemsTheSame(oldItem: GifEntity, newItem: GifEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifEntity, newItem: GifEntity): Boolean {
            return oldItem == newItem
        }
    }

}

