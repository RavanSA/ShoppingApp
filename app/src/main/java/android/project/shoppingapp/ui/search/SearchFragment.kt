package android.project.shoppingapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentSearchBinding
import android.project.shoppingapp.ui.products.adapter.NewProductsLists
import android.project.shoppingapp.ui.products.adapter.ProductsAdapter
import android.project.shoppingapp.ui.search.viewmodel.SearchViewModel
import android.project.shoppingapp.utils.Resources
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel by viewModels<SearchViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeCategories()
    }


    private fun subscribeCategories() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.categoriesState.collect { categories ->
                    when (categories) {
                        is Resources.Success -> {
                            categories.data?.map { category ->
                                val chip = Chip(requireContext())
                                chip.text = category.toString()
                                binding.productsChipGroup.addView(chip)
                            }
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