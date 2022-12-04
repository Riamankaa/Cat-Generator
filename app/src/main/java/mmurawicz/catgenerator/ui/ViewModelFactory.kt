package mmurawicz.catgenerator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mmurawicz.catgenerator.network.ApiHelper
import mmurawicz.catgenerator.network.CataasRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatFragmentViewModel::class.java))
            return CatFragmentViewModel(CataasRepository(apiHelper)) as T
        throw IllegalArgumentException("Unknown class name")
    }
}
