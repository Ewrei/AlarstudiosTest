package vitalij.robin.alarstudiostest.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import vitalij.robin.alarstudiostest.databinding.ItemMainBinding
import vitalij.robin.alarstudiostest.model.network.MainModel

class MainHolder(
    var binding: ItemMainBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MainModel) {
        binding.item = item
    }
}