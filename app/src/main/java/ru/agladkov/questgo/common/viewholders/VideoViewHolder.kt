package ru.agladkov.questgo.common.viewholders

import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.models.VideoCellModel

interface VideoCellDelegate {
    fun onPlayClick(model: VideoCellModel?)
}

class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val contentVideoView: FrameLayout = itemView.findViewById(R.id.contentVideoView)

    var delegate: VideoCellDelegate? = null

    fun bind(model: VideoCellModel?) {
        contentVideoView.setOnClickListener {
            delegate?.onPlayClick(model = model)
        }
    }
}