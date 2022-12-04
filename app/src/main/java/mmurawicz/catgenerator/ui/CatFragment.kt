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
        binding.actvTag.setOnItemClickListener { _, _, position, _ ->
            viewModel.selectedTag = tags[position]
        }
        binding.actvTag.setText(tags[0], false)
        viewModel.selectedTag = tags[0]
    }

    private fun setupFiltersAdapter() {
        val filter = FilterItems.list.map { filterItem -> resources.getString(filterItem.text) }
        val filtersArrayAdapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, filter) }
        binding.actvFilter.setAdapter(filtersArrayAdapter)
        binding.actvFilter.setOnItemClickListener { _, _, position, _ ->
            viewModel.selectedFilter = FilterItems.list[position]
        }
        binding.actvFilter.setText(resources.getString(FilterItems.list[0].text), false)
    }

    private fun setupColorsAdapter() {
        val colors = ColorItems.list.map { colorItem -> resources.getString(colorItem.text) }
        val colorsArrayAdapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, colors) }
        binding.actvColor.setAdapter(colorsArrayAdapter)
        binding.actvColor.setOnItemClickListener { _, _, position, _ ->
            viewModel.updateDescriptionColor(ColorItems.list[position].color)
        }
        binding.actvColor.setText(resources.getString(ColorItems.list[0].text), false)
    }

    private fun onButtonGiveClick() {
        var imageUri = if (viewModel.selectedTag != "") {
            "https://cataas.com/cat/"
        } else {
            "https://cataas.com/cat"
        }
        imageUri += viewModel.selectedTag + "?filter=" + viewModel.selectedFilter.filterName
        Picasso.with(context).load(imageUri).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(
            NetworkPolicy.NO_CACHE
        ).into(binding.ivCat)
    }

    private fun setupObservers() {
        viewModel.getTags().observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data -> setupTagsAdapter(data) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            activity,
                            R.string.get_tags_error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}
