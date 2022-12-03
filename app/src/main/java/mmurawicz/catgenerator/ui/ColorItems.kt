package mmurawicz.catgenerator.ui

import mmurawicz.catgenerator.R

object ColorItems {
    val list: List<ColorItem> = listOf(
        ColorItem(
            text = R.string.color_black,
            color = R.color.black
        ),
        ColorItem(
            text = R.string.color_white,
            color = R.color.white
        ),
        ColorItem(
            text = R.string.color_blue,
            color = R.color.blue
        ),
        ColorItem(
            text = R.string.color_red,
            color = R.color.red
        ),
        ColorItem(
            text = R.string.color_yellow,
            color = R.color.yellow
        )
    )
}

data class ColorItem(
    val text: Int,
    val color: Int,
)
