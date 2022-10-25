package android.project.shoppingapp.ui.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentLoginBinding
import android.project.shoppingapp.utils.Resources
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        isFormValid()
        registerUser()
        binding.btnRegister.setOnClickListener {
            loginViewModel.loginUser()
        }
    }




    private fun registerUser() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginFlow.collect { uiState ->
                    when (uiState) {
                        is Resources.Success -> {
                            Toast.makeText(requireContext(), "$uiState", Toast.LENGTH_LONG).show()
                            Log.d("LOGIN", uiState.toString())
                        }
                        is Resources.Loading -> {}
                        is Resources.Error -> {}
                        else -> {}
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
            etPassword.doAfterTextChanged {
                loginViewModel.setPassword(it.toString())
            }
        }
    }

    private fun isFormValid() {
        lifecycleScope.launch {
            loginViewModel.isFormValid.collect { value ->
                binding.btnRegister.isEnabled = value
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
                            if(!etEmail.text.isNullOrBlank()){
                                emailError.collect { if (it != "") { etEmail.error = it }
                                else{etEmail.error = getString(android.project.shoppingapp.R.string.app_name)} }
                            }
                        }

                        launch {
                            if(!etPassword.text.isNullOrBlank()){
                                passwordError.collect { if (it != "") { etPassword.error = it }
                                else{etPassword.error = getString(android.project.shoppingapp.R.string.app_name)} }
                            }
                        }

                    }
                }
            }
        }
    }

}