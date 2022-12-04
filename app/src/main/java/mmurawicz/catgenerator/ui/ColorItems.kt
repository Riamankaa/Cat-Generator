package mmurawicz.catgenerator.ui

import mmurawicz.catgenerator.R

object ColorItems {
    val list: List<ColorItem> = listOf(
        ColorItem(
            text = R.string.color_violet_catnip,
            color = R.color.violet_catnip
        ),
        ColorItem(
            text = R.string.color_orange_salmon,
            color = R.color.orange_salmon
        ),
        ColorItem(
            text = R.string.color_grey_mousey,
            color = R.color.grey_mousey
        ),
        ColorItem(
            text = R.string.color_red_laser,
            color = R.color.red_laser
        ),
        ColorItem(
            text = R.string.color_brown_cartoon,
            color = R.color.brown_cartoon
        ),
        ColorItem(
            text = R.string.color_white_milky,
            color = R.color.white_milky
        )
    )
}

data class ColorItem(
    val text: Int,
    val color: Int,
)
