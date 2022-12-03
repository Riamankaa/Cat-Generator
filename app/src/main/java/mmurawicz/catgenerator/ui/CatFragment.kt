package mmurawicz.catgenerator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.slider.Slider
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import mmurawicz.catgenerator.R
import mmurawicz.catgenerator.databinding.FragmentCatBinding
import mmurawicz.catgenerator.utils.Status

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
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setupFiltersAdapter()
        setupColorsAdapter()

        binding.tietDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.updateDescriptionText(
                text.toString()
            )
        }
        binding.slrSize.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            viewModel.updateDescriptionSize(
                value.toInt()
            )
        })
        binding.btnGive.setOnClickListener { onButtonGiveClick() }
        setupObservers()

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTagsAdapter(tags: List<String>) {
        val tagsArrayAdapter = activity?.let { ArrayAdapter(it, R.layout.dropdown_item, tags) }
        binding.actvTag.setAdapter(tagsArrayAdapter)
    }

    private fun setupFiltersAdapter() {
        val filter = FilterItems.list.map { filterItem -> resources.getString(filterItem.text) }
        val filtersArrayAdapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, filter) }
        binding.actvFilter.setAdapter(filtersArrayAdapter)
    }

    private fun setupColorsAdapter() {
        val colors = ColorItems.list.map { colorItem -> resources.getString(colorItem.text) }
        val colorsArrayAdapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, colors) }
        binding.actvColor.setAdapter(colorsArrayAdapter)
        binding.actvColor.setOnItemClickListener { _, _, position, _ ->
            viewModel.updateDescriptionColor(ColorItems.list[position].color)
        }
    }

    private fun onButtonGiveClick() {
        val imageUri = "https://cataas.com/cat"
        Picasso.with(context).load(imageUri).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(
            NetworkPolicy.NO_CACHE).into(binding.ivCat)
    }

    private fun setupObservers() {
        viewModel.getTags().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data -> setupTagsAdapter(data) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(activity, "Nie udało się pobrać dostępnych tagów.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
