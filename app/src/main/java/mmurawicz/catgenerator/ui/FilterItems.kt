package mmurawicz.catgenerator.ui

import mmurawicz.catgenerator.R

object FilterItems {
    val list: List<FilterItem> = listOf(
        FilterItem(
            text = R.string.filter_no,
            filterName = ""
        ),
        FilterItem(
            text = R.string.filter_blur,
            filterName = "blur"
        ),
        FilterItem(
            text = R.string.filter_mono,
            filterName = "mono"
        ),
        FilterItem(
            text = R.string.filter_sepia,
            filterName = "sepia"
        ),
        FilterItem(
            text = R.string.filter_negative,
            filterName = "negative"
        ),
        FilterItem(
            text = R.string.filter_paint,
            filterName = "paint"
        ),
        FilterItem(
            text = R.string.filter_pixel,
            filterName = "pixel"
        )
    )
}

data class FilterItem(
    val text: Int,
    val filterName: String
)