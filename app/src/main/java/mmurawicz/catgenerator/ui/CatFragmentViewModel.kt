package mmurawicz.catgenerator.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mmurawicz.catgenerator.R

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
}
