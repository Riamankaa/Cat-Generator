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
    var descriptionText: MutableLiveData<String> = MutableLiveData()
    var descriptionSize: MutableLiveData<Int> = MutableLiveData(15)
    var descriptionColor: MutableLiveData<Int> = MutableLiveData(ColorItems.list[0].color)

    var selectedTag: String = String()
    var selectedFilter: FilterItem = FilterItems.list[0]

    fun updateDescriptionVisibility() {
        descriptionVisibility.value = if (descriptionVisibility.value == View.GONE) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun updateDescriptionText(descriptionText: String) {
        this.descriptionText.value = descriptionText
    }

    fun updateDescriptionSize(descriptionSize: Int) {
        this.descriptionSize.value = descriptionSize
    }

    fun updateDescriptionColor(descriptionColor: Int) {
        this.descriptionColor.value = descriptionColor
    }

    fun getTags() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = CataasRepository(ApiHelper(RetrofitBuilder.apiService)).getTags()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getImageUrl(): String {
        return PicassoUrlBuilder.getImageUrl(selectedTag, selectedFilter.filterName)
    }

    fun getProcessedTags(tags: List<String>, emptyName: String): List<String> {
        val processedTags = tags.map {it.ifEmpty {
            emptyName
        }}
        return processedTags
    }
}
