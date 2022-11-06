package android.project.shoppingapp.ui.products.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class ProductBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("productImageUrl")
        fun loadImage(view: ImageView, url: String) {
            Glide.with(view.context).load(url).into(view)
        }
    }

}