package mmurawicz.catgenerator.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mmurawicz.catgenerator.network.ApiHelper
import mmurawicz.catgenerator.network.CataasRepository
import mmurawicz.catgenerator.network.PicassoUrlBuilder
import mmurawicz.catgenerator.network.RetrofitBuilder
import mmurawicz.catgenerator.utils.Resource

class CatFragmentViewModel : ViewModel() {
    var descriptionVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
    var progressIndicatorVisibility: MutableLiveData<Int> = MutableLiveData(View.INVISIBLE)
    var descriptionText: MutableLiveData<String> = MutableLiveData()
    var descriptionSize: MutableLiveData<Int> = MutableLiveData(15)
    var selectedColor: MutableLiveData<Int> =
        MutableLiveData(ColorItems.list[DEFAULT_ITEM_INDEX].color)
    var isButtonGiveEnabled: MutableLiveData<Boolean> = MutableLiveData(true)

    lateinit var tags: List<String>
    lateinit var processedTags: List<String>
    var selectedTag: String = String()
    var selectedFilter: FilterItem = FilterItems.list[DEFAULT_ITEM_INDEX]

    fun updateDescriptionVisibility() {
        descriptionVisibility.value = if (descriptionVisibility.value == View.GONE) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun updateDescriptionColor(position: Int) {
        this.selectedColor.value = ColorItems.list[position].color
    }

    fun updateDescriptionText(descriptionText: String) {
        this.descriptionText.value = descriptionText
    }

    fun updateDescriptionSize(descriptionSize: Float) {
        this.descriptionSize.value = descriptionSize.toInt()
    }

    fun setSelectedFilter(position: Int) {
        selectedFilter = FilterItems.list[position]
    }

    fun getDefaultFilterText(): Int {
        return FilterItems.list[DEFAULT_ITEM_INDEX].text
    }

    fun getDefaultColorText(): Int {
        return ColorItems.list[DEFAULT_ITEM_INDEX].text
    }

    fun getTags() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = CataasRepository(ApiHelper(RetrofitBuilder.apiService)).getTags()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun processTags(emptyName: String) {
        processedTags = tags.map {
            it.ifEmpty {
                emptyName
            }
        }
    }

    fun setSelectedTag(position: Int) {
        selectedTag = tags[position]
    }

    fun getDefaultProcessedTag(): String {
        return processedTags[DEFAULT_ITEM_INDEX]
    }

    fun setDefaultTagSelected() {
        selectedTag = tags[DEFAULT_ITEM_INDEX]
    }

    fun getImageUrl(): String {
        return PicassoUrlBuilder.getImageUrl(selectedTag, selectedFilter.filterName)
    }

    fun showImageLoading() {
        isButtonGiveEnabled.value = false
        progressIndicatorVisibility.value = View.VISIBLE
    }

    fun hideImageLoading() {
        isButtonGiveEnabled.value = true
        progressIndicatorVisibility.value = View.INVISIBLE
    }

    companion object {
        const val DEFAULT_ITEM_INDEX = 0
    }
}
