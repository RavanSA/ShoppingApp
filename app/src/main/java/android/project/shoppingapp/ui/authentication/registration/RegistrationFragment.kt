package android.project.shoppingapp.ui.authentication.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentRegistrationBinding
import android.project.shoppingapp.utils.Constants
import android.project.shoppingapp.utils.LoadingDialog
import android.project.shoppingapp.utils.Resources
import android.project.shoppingapp.utils.navgraph.ActivityNavGraph
import android.project.shoppingapp.utils.showCustomDialog
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var navController: NavController
    private val registrationViewModel by viewModels<RegistrationViewModel>()
    private val progressBar by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        isFormValid()
        registerUser()
        binding.btnRegister.setOnClickListener {
            registrationViewModel.signupUser()
        }
    }

    private fun registerUser() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(registrationViewModel) {
                    signup.collect { uiState ->
                        when (uiState) {
                            is Resources.Success -> {
                                progressBar.dismiss()
                                uiState.message?.let { showCustomDialog(it,Constants.SUCCES_DIALOG, requireContext()) }

                                Toast.makeText(requireContext(), "$uiState", Toast.LENGTH_LONG)
                                    .show()
                                Log.d("UISATELOG", uiState.toString())
                                setUserAuthenticated()
//                                ActivityNavGraph.startApplicationFlow(
//                                    requireActivity(),
//                                    requireContext()
//                                )
                                navController.navigate(R.id.action_authorizationFragment_to_productFragment)

                            }
                            is Resources.Loading -> {
                                progressBar.show()
                            }
                            is Resources.Error -> {
                                progressBar.dismiss()
                                uiState.message?.let { showCustomDialog(it, Constants.ERROR_DIALOG, requireContext()) }
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
            etRegisterUsername.doAfterTextChanged {
                registrationViewModel.setUserName(it.toString())
            }
            etRegisterEmail.doAfterTextChanged {
                registrationViewModel.setEmail(it.toString())
            }
            etRegisterPassword.doAfterTextChanged {
                registrationViewModel.setPassword(it.toString())
            }
            etRegisterConfirmPassword.doAfterTextChanged {
                registrationViewModel.setConfirmPassword(it.toString())
            }
        }
    }

    private fun isFormValid() {
        lifecycleScope.launch {
            registrationViewModel.isFormValid.collect { value ->
                binding.btnRegister.isEnabled = value
                setErrors()
            }
        }
    }

    private fun setErrors() {
        binding.apply {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    with(registrationViewModel) {

                        launch {
                            if (!etRegisterUsername.text.isNullOrBlank()) {
                                userNameError.collect {
                                    if (it != "") {
                                        tvRegisterUsernameError.text = it
                                    } else {
                                        tvRegisterUsernameError.text = ""
                                    }
                                }
                            }
                        }

                        launch {
                            if (!etRegisterEmail.text.isNullOrBlank()) {
                                emailError.collect {
                                    if (it != "") {
                                        tvRegisterEmailError.text = it
                                    } else {
                                        tvRegisterEmailError.text = ""
                                    }
                                }
                            }
                        }

                        launch {
                            if (!etRegisterPassword.text.isNullOrBlank()) {
                                passwordError.collect {
                                    if (it != "") {
                                        tvRegisterPasswordError.text = it
                                    } else {
                                        tvRegisterPasswordError.text = ""
                                    }
                                }
                            }
                        }

                        launch {
                            if (!etRegisterConfirmPassword.text.isNullOrBlank()) {
                                confirmPasswordError.collect {
                                    if (it != "") {
                                        tvRegisterConfirmPasswordError.text = it
                                    } else {
                                        tvRegisterConfirmPasswordError.text = ""
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