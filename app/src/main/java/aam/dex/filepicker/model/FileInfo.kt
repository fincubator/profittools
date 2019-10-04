package aam.dex.filepicker.model

class FileInfo(
    val name: String,
    val path: String,
    val info: String,
    val color: Int,
    val type: Int
) {
    companion object {
        const val HOME = 0
        const val DIRECTORY = 1
        const val FILE = 2

        const val COLOR_GRAY = -4342339
        const val COLOR_BLUE = -10177034
        const val COLOR_GREEN = -10044566
        const val COLOR_RED = -1092784
        const val COLOR_YELLOW = -21760
        const val COLOR_PURPLE = -5552196
        const val COLOR_TEAL = -14244198

    }
}