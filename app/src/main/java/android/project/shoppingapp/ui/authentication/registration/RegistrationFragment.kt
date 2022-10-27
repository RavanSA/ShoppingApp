package android.project.shoppingapp.ui.authentication.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentRegistrationBinding
import android.project.shoppingapp.utils.Resources
import android.project.shoppingapp.utils.navgraph.ActivityNavGraph
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val registrationViewModel by viewModels<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
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
                                Toast.makeText(requireContext(), "$uiState", Toast.LENGTH_LONG)
                                    .show()
                                Log.d("UISATELOG", uiState.toString())
                                setUserAuthenticated()
                                ActivityNavGraph.startApplicationFlow(requireActivity(),requireContext())
                            }
                            is Resources.Loading -> {}
                            is Resources.Error -> {}
                            else -> {}
                        }
                    }
                }
            }
        }
    }


    private fun initListeners() {
        with(binding) {
            etUserName.doAfterTextChanged {
                registrationViewModel.setUserName(it.toString())
            }
            etEmail.doAfterTextChanged {
                registrationViewModel.setEmail(it.toString())
            }
            etPassword.doAfterTextChanged {
                registrationViewModel.setPassword(it.toString())
            }
            etConfirmPassword.doAfterTextChanged {
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
                            if(!etUserName.text.isNullOrBlank()) {
                                userNameError.collect { if (it != "") { etUserName.error = it }
                            else{etUserName.error = getString(R.string.app_name)} }
                            }
                        }

                        launch {
                            if(!etEmail.text.isNullOrBlank()){
                            emailError.collect { if (it != "") { etEmail.error = it }
                            else{etEmail.error = getString(R.string.app_name)} }
                            }
                        }

                        launch {
                            if(!etPassword.text.isNullOrBlank()){
                            passwordError.collect { if (it != "") { etPassword.error = it }
                            else{etPassword.error = getString(R.string.app_name)} }
                            }
                        }

                        launch {
                            if (!etPassword.text.isNullOrBlank()){
                            confirmPasswordError.collect { if (it != "") { etConfirmPassword.error = it }
                            else{etConfirmPassword.error = getString(R.string.app_name)} }
                            }
                        }

                    }
                }
            }
        }
    }


}