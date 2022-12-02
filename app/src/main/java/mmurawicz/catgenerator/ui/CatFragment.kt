package mmurawicz.catgenerator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import mmurawicz.catgenerator.R
import mmurawicz.catgenerator.databinding.FragmentCatBinding

class CatFragment : Fragment() {

    private val viewModel by viewModels<CatFragmentViewModel>()
    private var _binding: FragmentCatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCatBinding.inflate(inflater, container, false)

        setupTagsAdapter()
        setupFiltersAdapter()

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTagsAdapter() {
        val tags = resources.getStringArray(R.array.tags)
        val tagsArrayAdapter = activity?.let { ArrayAdapter(it, R.layout.dropdown_item, tags) }
        binding.actvTag.setAdapter(tagsArrayAdapter)
    }

    private fun setupFiltersAdapter() {
        val filters = resources.getStringArray(R.array.filters)
        val filtersArrayAdapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, filters) }
        binding.actvFilter.setAdapter(filtersArrayAdapter)
    }
}
