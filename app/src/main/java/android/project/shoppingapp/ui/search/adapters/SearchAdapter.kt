package android.project.shoppingapp.ui.search.adapters

import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.databinding.SearchItemListBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class SearchAdapter(
    private val listener: SearchItemListener
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,callback)


    inner class SearchViewHolder(private val binding : SearchItemListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindData(product: Products){
            binding.product = product

            itemView.setOnClickListener {
                listener.onClicked(product)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val shopItem = differ.currentList[position]
        holder.bindData(shopItem)
    }

    override fun getItemCount() = differ.currentList.size

}

interface SearchItemListener {
    fun onClicked(product: Products)
}

