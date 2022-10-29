package android.project.shoppingapp.ui.products.adapter

import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.databinding.NewProductListBinding
import android.project.shoppingapp.databinding.ProductItemListBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewProductsLists : RecyclerView.Adapter<NewProductsLists.NewProductsViewHolder>() {



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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProductsViewHolder {
        val binding =
            NewProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewProductsViewHolder(binding)
    }



    override fun onBindViewHolder(holder: NewProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bindImage(product)
    }

    override fun getItemCount() = differ.currentList.size


    inner class NewProductsViewHolder(private val binding: NewProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindImage(product: Products) {
            Log.d("VOEWHOLDER", product.toString())
            Glide.with(binding.newItemImageView)
                .load(product.image)
                .into(binding.newItemImageView)

        }

    }


}