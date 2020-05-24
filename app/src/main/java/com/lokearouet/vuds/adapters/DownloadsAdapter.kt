package com.lokearouet.vuds.adapters


import android.content.Intent
import android.net.Uri
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.lokearouet.vuds.R
import com.lokearouet.vuds.database.Download
import kotlinx.android.synthetic.main.fragment_downloads.view.*


class DownloadsAdapter : RecyclerView.Adapter<DownloadsAdapter.ViewHolder>() {

    private var mValues: List<Download> = emptyList()

    fun addItems(items: List<Download>){
        mValues = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_downloads, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadsAdapter.ViewHolder, position: Int) {
        val item = mValues[position]

        with(holder.itemView) {
            title_tv.text = item.name
            download_pb.progress = item.downloadedPercent.toInt()
            download_percent_tv.text = "${item.downloadedPercent} %"
            val totalSize = Formatter.formatShortFileSize(context, item.totalSize)
            val downloadedSize = Formatter.formatShortFileSize(context, item.downloadedSize)
            download_size_tv.text = "${downloadedSize}/${totalSize}"
            if(item.mediaType == "audio"){
                format_ic.setImageResource(R.drawable.ic_audio_24dp)
            }else{
                format_ic.setImageResource(R.drawable.ic_video_24dp)
            }
            setOnClickListener(View.OnClickListener {
                openFolder(item.downloadedPath, it)
            })
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    private fun openFolder(path: String, view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri: Uri = Uri.parse(path + "/")
        intent.setDataAndType(uri, "*/*")
        startActivity(view.context, Intent.createChooser(intent, "Open folder"), null)
    }
}
