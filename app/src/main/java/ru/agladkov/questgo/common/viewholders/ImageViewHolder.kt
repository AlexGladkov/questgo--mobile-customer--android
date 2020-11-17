package ru.agladkov.questgo.common.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.models.ImageCellModel

class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val contentImageView: AppCompatImageView = itemView.findViewById(R.id.contentImageView)

    fun bind(model: ImageCellModel?) {
        Glide
            .with(contentImageView)
            .load(model?.value.orEmpty())
            .into(contentImageView)
    }
}