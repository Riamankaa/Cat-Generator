package mmurawicz.catgenerator.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mmurawicz.catgenerator.R
import mmurawicz.catgenerator.network.ApiHelper
import mmurawicz.catgenerator.network.CataasRepository
import mmurawicz.catgenerator.network.RetrofitBuilder
import mmurawicz.catgenerator.utils.Resource

class CatFragmentViewModel : ViewModel() {
    var descriptionConfigVisibility : MutableLiveData<Int> = MutableLiveData(View.GONE)
    var descriptionText : MutableLiveData<String> = MutableLiveData()
    var descriptionSize : MutableLiveData<Int> = MutableLiveData()
    var descriptionColor : MutableLiveData<Int> = MutableLiveData(R.color.white)

    fun updateDescriptionConfigVisibility() {
        descriptionConfigVisibility.value = if (descriptionConfigVisibility.value == View.GONE) {
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
}
