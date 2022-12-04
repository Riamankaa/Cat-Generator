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
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import mmurawicz.catgenerator.R
import mmurawicz.catgenerator.databinding.FragmentCatBinding
import mmurawicz.catgenerator.utils.Resource
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

        setupViews()

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        setupFilters()
        setupColors()
        setupDescription()
        setupSliderSize()
        setupButtonGive()
        setupGetTagsObserver()
        loadImage()
    }

    private fun setupFilters() {
        val filter = FilterItems.list.map { filterItem -> resources.getString(filterItem.text) }
        val filtersArrayAdapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, filter) }
        binding.actvFilter.setAdapter(filtersArrayAdapter)

        binding.actvFilter.setOnItemClickListener { _, _, position, _ ->
            viewModel.setSelectedFilter(position)
        }

        setDefaultFilter()
    }

    private fun setDefaultFilter() {
        binding.actvFilter.setText(resources.getString(viewModel.getDefaultFilterText()), false)
    }

    private fun setupColors() {
        val colors = ColorItems.list.map { colorItem -> resources.getString(colorItem.text) }
        val colorsArrayAdapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, colors) }
        binding.actvColor.setAdapter(colorsArrayAdapter)

        binding.actvColor.setOnItemClickListener { _, _, position, _ ->
            viewModel.updateDescriptionColor(position)
        }

        setDefaultColor()
    }

    private fun setDefaultColor() {
        binding.actvColor.setText(resources.getString(viewModel.getDefaultColorText()), false)
    }

    private fun setupDescription() {
        binding.tietDescription.doOnTextChanged { text, _, _, _ ->
            viewModel.updateDescriptionText(
                text.toString()
            )
        }
    }

    private fun setupSliderSize() {
        binding.slrSize.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            viewModel.updateDescriptionSize(
                value
            )
        })
    }

    private fun setupButtonGive() {
        binding.btnGive.setOnClickListener { loadImage() }
    }

    private fun loadImage() {
        viewModel.showImageLoading()
        Picasso.with(context).load(viewModel.getImageUrl()).memoryPolicy(MemoryPolicy.NO_CACHE)
            .networkPolicy(
                NetworkPolicy.NO_CACHE
            ).into(binding.ivCat, object : Callback {
                override fun onSuccess() {
                    onLoadImageResponseSuccess()
                }

                override fun onError() {
                    onLoadImageResponseError()
                }
            })
    }

    private fun onLoadImageResponseSuccess() {
        viewModel.hideImageLoading()
    }

    private fun onLoadImageResponseError() {
        viewModel.hideImageLoading()
        Toast.makeText(
            activity,
            R.string.connection_error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setupGetTagsObserver() {
        viewModel.getTags().observe(viewLifecycleOwner) {
            it?.let {
                processTagsStatus(it)
            }
        }
    }

    private fun processTagsStatus(resource: Resource<List<String>>) {
        when (resource.status) {
            Status.SUCCESS -> {
                onGetTagResponseSuccess(resource)
            }
            Status.ERROR -> {
                onGetTagResponseError()
            }
        }
    }

    private fun onGetTagResponseSuccess(resource: Resource<List<String>>) {
        resource.data?.let { data -> setupTags(data) }
    }

    private fun setupTags(tags: List<String>) {
        viewModel.tags = tags
        viewModel.processTags(resources.getString(R.string.tag_random))
        val tagsArrayAdapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, viewModel.processedTags) }
        binding.actvTag.setAdapter(tagsArrayAdapter)

        binding.actvTag.setOnItemClickListener { _, _, position, _ ->
            viewModel.setSelectedTag(position)
        }

        setDefaultTag()
    }

    private fun setDefaultTag() {
        binding.actvTag.setText(viewModel.getDefaultProcessedTag(), false)
        viewModel.setDefaultTagSelected()
    }

    private fun onGetTagResponseError() {
        Toast.makeText(
            activity,
            R.string.connection_error,
            Toast.LENGTH_LONG
        ).show()
    }
}
