package com.paw.katalogbuku.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paw.katalogbuku.databinding.ItemBookBinding
import com.paw.katalogbuku.model.remote.response.BookResponse

class ListBookAdapter(
    private val isAdmin: Boolean,
    private val onEditClick: (BookResponse) -> Unit,
    private val onDeleteClick: (BookResponse) -> Unit
) : ListAdapter<BookResponse, ListBookAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookItem: BookResponse,
                 isAdmin: Boolean,
                 onEditClick: (BookResponse) -> Unit,
                 onDeleteClick: (BookResponse) -> Unit) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(bookItem.cover)
                    .centerCrop()
                    .into(ivItemPhoto)
                tvItemName.text = bookItem.title
                tvItemAuthor.text = bookItem.author
                if (isAdmin) {
                    btnEdit.visibility = View.VISIBLE
                    btnDelete.visibility = View.VISIBLE
                    btnEdit.setOnClickListener { onEditClick(bookItem) }
                    btnDelete.setOnClickListener { onDeleteClick(bookItem) }
                } else {
                    btnEdit.visibility = View.GONE
                    btnDelete.visibility = View.GONE
                }
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
        holder.bind(data, isAdmin, onEditClick, onDeleteClick)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookResponse>() {
            override fun areItemsTheSame(oldItem: BookResponse, newItem: BookResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BookResponse, newItem: BookResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}
