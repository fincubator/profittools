package aam.dex.filepicker

import aam.dex.filepicker.model.FileInfo
import java.io.File

private val archives = listOf("zip", "rar", "tar", "7z", "jar", "xz", "lz", "lzma")
private val docs = listOf("csv", "xls", "xlsx", "doc", "docx", "ppt", "pptx", "odt", "ods", "odp")
private val pdf = listOf("pdf", "djvu")
private val exec = listOf("apk", "exe", "msi")
private val media = listOf("png", "jpg", "gif", "bmp", "mp4", "avi")
private val music = listOf("mp3", "wav", "acc", "flac")

fun getColor(ext: String) =
    when (ext) {
        in archives -> FileInfo.COLOR_YELLOW
        in pdf -> FileInfo.COLOR_RED
        in docs -> FileInfo.COLOR_GREEN
        in exec -> FileInfo.COLOR_BLUE
        in music -> FileInfo.COLOR_TEAL
        in media -> -1
        "bin" -> FileInfo.COLOR_PURPLE
        else -> FileInfo.COLOR_GRAY
    }

fun Array<File>.extractInfo() = this.map {
        FileInfo(
            name = it.name,
            path = it.path,
            info = if (it.isDirectory) "Folder" else beautifySize(it.length()),
            color = getColor(it.extension),
            type = if (it.isDirectory) FileInfo.DIRECTORY else FileInfo.FILE
        )
    }.sortedWith(compareBy(FileInfo::type, FileInfo::name)).toMutableList()

fun beautifySize(size: Long) =
    when {
        size >= 1073741824  -> "${fastRound(size / 1073741824f)} GB"
        size >= 1048576     -> "${fastRound(size / 1048576f)} MB"
        size >= 1024        -> "${fastRound(size / 1024f)} KB"
        size > 1            -> "$size bytes"
        else                -> "1 byte"
    }

private fun fastRound(number: Float): Float {
    val tmp = number * 100
    return (if (tmp - tmp.toInt() >= 0.5f) tmp + 1 else tmp).toInt().toFloat() / 100
}