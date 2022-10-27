package android.project.shoppingapp.ui.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentLoginBinding
import android.project.shoppingapp.utils.*
import android.project.shoppingapp.utils.navgraph.ActivityNavGraph
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val progressBar by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        isFormValid()
        registerUser()
        binding.btnLogin.setOnClickListener {
            loginViewModel.loginUser()
        }
    }

    private fun registerUser() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(loginViewModel) {
                    loginFlow.collect { uiState ->
                        when (uiState) {
                            is Resources.Success -> {
                                progressBar.dismiss()

                                uiState.message?.let { showCustomDialog(it,Constants.SUCCES_DIALOG, requireContext()) }

                                setUserAuthenticated()
                                Toast.makeText(requireContext(), "$uiState", Toast.LENGTH_LONG)
                                    .show()
//                                ActivityNavGraph.startApplicationFlow(
//                                    requireActivity(), requireContext()
//                                )
                                navController.navigate(R.id.action_authorizationFragment_to_productFragment)

                            }
                            is Resources.Loading -> {
                                progressBar.show()
                            }
                            is Resources.Error -> {
                                progressBar.dismiss()
                                uiState.message?.let { showCustomDialog(it, Constants.ERROR_DIALOG, requireContext()) }
                                uiState.message?.let { Log.d("Error dialog", it) }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            etEmail.doAfterTextChanged {
                loginViewModel.setEmail(it.toString())
            }
            etLoginPassword.doAfterTextChanged {
                loginViewModel.setPassword(it.toString())
            }
        }
    }

    private fun isFormValid() {
        lifecycleScope.launch {
            loginViewModel.isFormValid.collect { value ->
                binding.btnLogin.isEnabled = value
                setErrors()
            }
        }
    }


    private fun setErrors() {
        binding.apply {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    with(loginViewModel) {

                        launch {
                            if (!etEmail.text.isNullOrBlank()) {
                                emailError.collect {
                                    if (it != "") {
                                        tvEmailLoginError.text = it
                                        ivLoginEmailError.visibility = View.VISIBLE
                                    } else {
                                        tvEmailLoginError.text = ""
                                        ivLoginEmailError.visibility = View.GONE
                                    }
                                }
                            }
                        }

                        launch {
                            if (!etLoginPassword.text.isNullOrBlank()) {
                                passwordError.collect {
                                    if (it != "") {
                                        tvPasswordLoginError.text = it
                                        ivLoginPasswordError.visibility = View.VISIBLE
                                        tvPasswordLoginError.setTextColor(
                                            ContextCompat.getColor(
                                                requireContext(), R.color.colorRed
                                            )
                                        )
                                    } else {

                                        tvPasswordLoginError.text =
                                            getString(R.string.tv_login_password_error)
                                        tvPasswordLoginError.setTextColor(
                                            ContextCompat.getColor(
                                                requireContext(), R.color.colorPrimary
                                            )
                                        )
                                        ivLoginPasswordError.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}