package android.project.shoppingapp.ui.basket

import android.project.shoppingapp.data.local.database.entity.BasketEntity
import android.project.shoppingapp.databinding.BasketItemBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BasketModalSheetAdapter : RecyclerView.Adapter<BasketModalSheetAdapter.BottomSheetBasketViewHolder>() {


    private val callback = object : DiffUtil.ItemCallback<BasketEntity>() {
        override fun areItemsTheSame(oldItem: BasketEntity, newItem: BasketEntity): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: BasketEntity, newItem: BasketEntity): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    private var increaseQuantityListener : ((BasketEntity) -> Unit) = {}

    private var decreaseQuantityListener : ((BasketEntity) -> Unit) = {}

    private var removeQuantityListener : ((BasketEntity) -> Unit) = {}


    fun removeListener(listener : (BasketEntity) -> Unit){
        removeQuantityListener = listener
    }

    fun increaseListener(listener : (BasketEntity) -> Unit){
        increaseQuantityListener = listener
    }

    fun decreaseListener(listener : (BasketEntity) -> Unit){
        decreaseQuantityListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetBasketViewHolder {
        val binding = BasketItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return BottomSheetBasketViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: BottomSheetBasketViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    inner class BottomSheetBasketViewHolder(val binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(basketItem: BasketEntity) {
                binding.cartItemName.text = basketItem.title
                binding.cartItemPrice.text = basketItem.title
                binding.cartItemQuantity.text = basketItem.productQuantity.toString()
            Glide.with(binding.cartItemImage)
                .load(basketItem.image)
                .into(binding.cartItemImage)

            binding.cartItemDelete.setOnClickListener {
                removeQuantityListener(basketItem)
            }

            binding.cartItemPlus.setOnClickListener {
                increaseQuantityListener(basketItem)
            }

            binding.cartItemMinus.setOnClickListener {
                decreaseQuantityListener(basketItem)
            }
        }
    }

}