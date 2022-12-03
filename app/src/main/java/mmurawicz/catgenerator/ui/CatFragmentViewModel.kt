package mmurawicz.catgenerator.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatFragmentViewModel : ViewModel() {
    var descriptionConfigVisibility : MutableLiveData<Int> = MutableLiveData(View.GONE)

    fun updateDescriptionConfigVisibility() {
        descriptionConfigVisibility.value = if (descriptionConfigVisibility.value == View.GONE) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
