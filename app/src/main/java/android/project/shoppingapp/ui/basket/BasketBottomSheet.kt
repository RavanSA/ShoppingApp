package android.project.shoppingapp.ui.basket

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.BasketModalBottomSheetBinding
import android.project.shoppingapp.databinding.CustomAlertDialogBinding
import android.project.shoppingapp.utils.Constants
import android.project.shoppingapp.utils.customui.showCustomDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BasketModalBottomSheetBinding
    private val basketViewModel by viewModels<BottomSheetViewModel>()
    private lateinit var navController: NavController

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
        navController = findNavController()
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
                    binding.cartCheckout.setOnClickListener {
                        showAlertDialog(requireContext())
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


    fun showAlertDialog( context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding = CustomAlertDialogBinding
            .inflate(LayoutInflater.from(context))


        with(binding) {
            tvErrorDialog.text = "Are you sure to continue payment process?"
            btnYes.setOnClickListener {
                basketViewModel.deleteAllProductFromBasket()
                dialog.dismiss()
                showCustomDialog("Successfull", Constants.SUCCES_DIALOG, requireContext())
                navController.navigate(R.id.action_basketBottomSheet_to_productFragment2)
            }
            btnNo.setOnClickListener {
                dialog.dismiss()
            }
            ivErrorDialog.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_info_24)
            )
        }
        dialog.setContentView(binding.root)
        dialog.show()

    }


}

//    override fun onClicked(basket: BasketEntity) {
//        binding.
//    }

