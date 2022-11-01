package android.project.shoppingapp.ui.basket

import android.os.Bundle
import android.project.shoppingapp.databinding.BasketModalBottomSheetBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"
    }

    private lateinit var binding: BasketModalBottomSheetBinding
    private val basketViewModel by viewModels<BottomSheetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BasketModalBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subsribeUI()
        getTotalPrice()
    }

    private fun subsribeUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                basketViewModel.basketState.collect { products ->
                    val basketAdapter = BasketModalSheetAdapter()

                    binding.rvBasket.adapter = basketAdapter
                    basketAdapter.differ.submitList(products)
//                    basketAdapter.removeListener {
//                        basketViewModel.increaseProductQuantity(it.productId)
//                    }
                    basketAdapter.decreaseListener {
                        basketViewModel.decreaseProductQuantity(it.productId)
                    }
                    basketAdapter.increaseListener {
                        basketViewModel.increaseProductQuantity(it.productId)
                    }

                    basketAdapter.removeListener {
                        basketViewModel.deleteItemFromBasket(it.productId)
                    }
                    binding.cartItemsInfo.text = products?.size.toString()
                }


            }
        }
    }

    private fun getTotalPrice() {
        lifecycleScope.launch {
            basketViewModel.totalAmount.collect { price ->
                binding.cartItemsPrice.text = price.toString()
            }
        }
    }

}

//    override fun onClicked(basket: BasketEntity) {
//        binding.
//    }

