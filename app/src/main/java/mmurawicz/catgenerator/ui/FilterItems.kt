package mmurawicz.catgenerator.ui

import mmurawicz.catgenerator.R

object FilterItems {
    val list: List<FilterItem> = listOf(
        FilterItem(
            text = R.string.filter_blur
        ),
        FilterItem(
            text = R.string.filter_mono
        ),
        FilterItem(
            text = R.string.filter_sepia
        ),
        FilterItem(
            text = R.string.filter_negative
        ),
        FilterItem(
            text = R.string.filter_paint
        ),
        FilterItem(
            text = R.string.filter_pixel
        )
    )
}

data class FilterItem(
    val text: Int,
)