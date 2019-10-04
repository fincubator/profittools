package aam.dex.filepicker

import aam.dex.R
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import aam.dex.filepicker.model.FileInfo
import kotlinx.android.synthetic.main.item_file.view.*

class FilePickerRecyclerAdapter(
    private val context: Context,
    private val nextListener: (String) -> Unit,
    private val prevListener: () -> Unit,
    private val resultListener: (String) -> Unit
) : RecyclerView.Adapter<FilePickerRecyclerAdapter.FileViewHolder>() {

    private var files: List<FileInfo> = emptyList()

    override fun getItemCount() = files.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false))

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.fileInfo = files[position]
    }

    fun notifyWith(newFiles: List<FileInfo>) {
        files = newFiles
        notifyDataSetChanged()
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var fileInfo: FileInfo? = null
            set(value) { value?.let { fileInfo ->
                itemView.fileNameTextView.text = fileInfo.name
                itemView.fileInfoTextView.text = fileInfo.info

                if(fileInfo.color == -1) {
                    itemView.fileIconImageView.visibility = View.INVISIBLE
                    Glide.with(context).load(fileInfo.path).into(itemView.fileImageView)
                } else {
                    itemView.fileIconImageView.visibility = View.VISIBLE
                    Glide.with(context).load(ColorDrawable(fileInfo.color)).into(itemView.fileImageView)
                }

                when(fileInfo.type) {
                    FileInfo.HOME -> {
                        Glide.with(context).load(R.drawable.ic_arrow_up).into(itemView.fileIconImageView)
                        itemView.setOnClickListener { prevListener() }
                    }
                    FileInfo.DIRECTORY -> {
                        Glide.with(context).load(R.drawable.ic_folder).into(itemView.fileIconImageView)
                        itemView.setOnClickListener { nextListener(fileInfo.path) }
                    }
                    FileInfo.FILE -> {
                        Glide.with(context).load(R.drawable.ic_file).into(itemView.fileIconImageView)
                        itemView.setOnClickListener { resultListener(fileInfo.path) }
                    }
                }
            }
            field = value
        }
    }
}