package android.project.shoppingapp.ui.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.project.shoppingapp.MainActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.ActivityMainBinding
import android.project.shoppingapp.databinding.CustomAlertDialogBinding
import android.project.shoppingapp.databinding.FragmentProfileBinding
import android.project.shoppingapp.ui.products.adapter.NewProductsLists
import android.project.shoppingapp.ui.products.adapter.ProductsAdapter
import android.project.shoppingapp.utils.Constants
import android.project.shoppingapp.utils.Resources
import android.project.shoppingapp.utils.customui.LoadingDialog
import android.project.shoppingapp.utils.customui.showCustomDialog
import android.util.Log
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels<ProfileViewModel>()
    lateinit var binding: FragmentProfileBinding
    private lateinit var navController: NavController

    private val progressBar by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        subscribeProductList()
        observeBasketAmount()
        binding.profileLogout.setOnClickListener {
            showAlertDialog(requireContext())
        }

        binding.ivProfileCart.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.actionGlobalBasketBottomSheet)
        }

    }

    private fun subscribeProductList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileInfo.collect { profileState ->
                    when (profileState) {
                        is ProfileState.Success -> {
                            progressBar.dismiss()
                            binding.tvProfileName.text = profileState.user.username
                            binding.tvProfileEmail.text = profileState.user.email
                        }
                        is ProfileState.Error -> {
                            progressBar.dismiss()
                            showCustomDialog(profileState.message, Constants.ERROR_DIALOG, requireContext())
                        }
                        is ProfileState.Loading -> {
                            progressBar.show()
                        }
                        else -> {}
                    }
                }
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
            tvErrorDialog.text = "Are you sure to logout?"
            btnYes.setOnClickListener {
                navController.navigate(R.id.action_profileFragment3_to_authorizationFragment3)
                viewModel.logout()
                dialog.dismiss()
                (requireActivity() as MainActivity).hideBottomNav(false)
            }
            btnNo.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.setContentView(binding.root)
        dialog.show()

    }


    private fun observeBasketAmount() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.totalAmount.collect { amount ->
                    binding.cartAmount.text = amount.toString() + "$"
                }
            }
        }
    }

}