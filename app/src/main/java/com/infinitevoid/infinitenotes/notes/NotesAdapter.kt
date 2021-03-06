package com.infinitevoid.infinitenotes.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.infinitevoid.infinitenotes.databinding.ListItemNoteBinding
import com.infinitevoid.infinitenotes.models.Note

interface OnItemClickListener {
    fun onClick(vh: View, item: Note)
}

class NotesAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Note, NotesAdapter.ViewHolder>(NoteDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun get(position: Int): Note {
        return getItem(position)
    }

    class ViewHolder private constructor(
        private val binding: ListItemNoteBinding,
        private val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.note = item
            binding.root.setOnClickListener { listener.onClick(binding.root, item) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, listener: OnItemClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNoteBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener)
            }
        }
    }
}


class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}