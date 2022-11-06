package android.project.shoppingapp.ui.products.adapter

import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.databinding.ProductItemListBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductsAdapter(
    private val listener: ProductListener
) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {


    private val callback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Products,
            newItem: Products
        ): Boolean {
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
        holder.bindData(product)
    }

    override fun getItemCount() = differ.currentList.size


    inner class ProductsViewHolder(private val binding: ProductItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(product: Products) {
            Glide.with(binding.itemImage)
                .load(product.image)
                .into(binding.itemImage)

            binding.product = product
            itemView.setOnClickListener {
                listener.onClicked(product)
            }
        }
    }

}


interface ProductListener {
    fun onClicked(product: Products)
}