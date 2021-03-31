package vitalij.robin.alarstudiostest.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.model.network.MainModel

class MainAdapter(val onclick: (mainModel: MainModel) -> Unit) :
    PagedListAdapter<MainModel, MainHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_main,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
            holder.itemView.setOnClickListener {
                onclick(item)
            }
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<MainModel>() {
            override fun areItemsTheSame(
                oldItem: MainModel,
                newItem: MainModel
            ): Boolean {
                return oldItem.position == newItem.position
            }

            override fun areContentsTheSame(
                oldItem: MainModel,
                newItem: MainModel
            ): Boolean =
                newItem == oldItem
        }
    }
}
