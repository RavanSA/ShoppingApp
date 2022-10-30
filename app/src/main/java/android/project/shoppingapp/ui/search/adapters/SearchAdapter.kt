package android.project.shoppingapp.ui.search.adapters

import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.databinding.SearchItemListBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,callback)

    private var onItemClickListener : ((Products)-> Unit) = {}

    fun setOnItemClickListener(listener : (Products)-> Unit){
        onItemClickListener = listener
    }


    inner class SearchViewHolder(private val binding : SearchItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(shopItem: Products){

            Glide.with(binding.searchItemImage)
                .load(shopItem.image)
                .into(binding.searchItemImage)

            binding.searchItemTitle.text = shopItem.title
            binding.searchItemPrice.text = "USD ${shopItem.price}"
            binding.searchItemRating.text = "${shopItem.ratingRate}"
            binding.searchItemReview.text = "${shopItem.ratingCount} Reviews"

            binding.searchItemView.setOnClickListener {
                onItemClickListener(shopItem)
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