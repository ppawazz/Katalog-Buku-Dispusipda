package com.paw.katalogbuku.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paw.katalogbuku.databinding.ItemBookBinding
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.view.ui.DetailActivity

class ListBookAdapter(
    private val isAdmin: Boolean,
    private val onEditClick: (BookItem) -> Unit,
    private val onDeleteClick: (BookItem) -> Unit
) : ListAdapter<BookItem, ListBookAdapter.BookViewHolder>(BookDiffCallback()) {

    private var fullBookList: MutableList<BookItem> = mutableListOf()

    fun submitFullList(books: List<BookItem>) {
        fullBookList = books.toMutableList()
        submitList(fullBookList)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullBookList
        } else {
            fullBookList.filter { it.title.contains(query, ignoreCase = true) }
        }
        submitList(filteredList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(book.cover)
                    .into(ivItemPhoto)
                tvItemTitle.text = book.title
                tvItemAuthor.text = book.author

                if (isAdmin) {
                    btnEdit.visibility = View.VISIBLE
                    btnDelete.visibility = View.VISIBLE
                    btnEdit.setOnClickListener { onEditClick(book) }
                    btnDelete.setOnClickListener { onDeleteClick(book) }
                } else {
                    btnEdit.visibility = View.GONE
                    btnDelete.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.BOOK_ITEM, book)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    class BookDiffCallback : DiffUtil.ItemCallback<BookItem>() {
        override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
            oldItem == newItem
    }
}

