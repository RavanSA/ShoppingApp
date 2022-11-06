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
                    with(binding) {
                        if (products?.size == 0) {
                            binding.cartCheckout.visibility = View.GONE
                        }
                        val basketAdapter = BasketModalSheetAdapter()
                        with(basketAdapter) {
                            rvBasket.adapter = basketAdapter
                            differ.submitList(products)
                            decreaseListener {
                                basketViewModel.decreaseProductQuantity(it.productId)
                            }
                            increaseListener {
                                basketViewModel.increaseProductQuantity(it.productId)
                            }

                            removeListener {
                                basketViewModel.deleteItemFromBasket(it.productId)
                            }
                            cartCheckout.setOnClickListener {
                                showAlertDialog(requireContext())
                            }
                            cartItemsInfo.text = "Total ${products?.size.toString()} items"
                        }
                    }
                }
            }
        }
    }

    private fun getTotalPrice() {
        lifecycleScope.launch {
            basketViewModel.totalAmount.collect { price ->
                binding.cartItemsPrice.text = price.toString() + " USD"
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