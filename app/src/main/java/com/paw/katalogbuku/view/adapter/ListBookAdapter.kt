package com.paw.katalogbuku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paw.katalogbuku.databinding.ItemBookBinding
import com.paw.katalogbuku.model.local.BookModel

class ListBookAdapter : ListAdapter<BookModel, ListBookAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookItem: BookModel) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(bookItem.photo)
                    .centerCrop()
                    .into(ivItemPhoto)
                tvItemName.text = bookItem.title
                tvItemAuthor.text = bookItem.author
                tvItemDesc.text = bookItem.desc
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListBookAdapter.MyViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListBookAdapter.MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookModel>() {
            override fun areItemsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
