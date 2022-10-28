package android.project.shoppingapp.ui.products.adapter

import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.databinding.ProductItemListBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {



    private val callback = object : DiffUtil.ItemCallback<ProductsDTOItem>() {
        override fun areItemsTheSame(oldItem: ProductsDTOItem, newItem: ProductsDTOItem): Boolean {
            Log.d("ADAPTERITEM1", oldItem.toString())
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductsDTOItem,
            newItem: ProductsDTOItem
        ): Boolean {
            Log.d("ADAPTERITEM2", oldItem.toString())
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ProductItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        Log.d("ADAPTERITEMdıfer", product.toString())
        holder.bindData(product)
    }

    override fun getItemCount() = differ.currentList.size


    inner class ProductsViewHolder(private val binding: ProductItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(product: ProductsDTOItem) {
            Log.d("VOEWHOLDER", product.toString())
            Glide.with(binding.itemImage)
                .load(product.image)
                .into(binding.itemImage)

            binding.itemTitle.text = product.title
            binding.itemPrice.text = "USD ${product.price}"
            binding.itemRating.text = "${product.rating.rate}"
            binding.itemReview.text = "${product.rating.count} Reviews"


        }

    }


}