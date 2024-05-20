package com.paw.katalogbuku.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paw.katalogbuku.databinding.ItemBookBinding
import com.paw.katalogbuku.model.local.BookModel

class ListBookAdapter(
    private val isAdmin: Boolean,
    private val onEditClick: (BookModel) -> Unit,
    private val onDeleteClick: (BookModel) -> Unit
) : ListAdapter<BookModel, ListBookAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookItem: BookModel,
                 isAdmin: Boolean,
                 onEditClick: (BookModel) -> Unit,
                 onDeleteClick: (BookModel) -> Unit) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(bookItem.photo)
                    .centerCrop()
                    .into(ivItemPhoto)
                tvItemName.text = bookItem.title
                tvItemAuthor.text = bookItem.author
                tvItemDesc.text = bookItem.desc
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
