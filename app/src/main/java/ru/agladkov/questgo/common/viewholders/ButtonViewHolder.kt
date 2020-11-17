package ru.agladkov.questgo.common.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.models.ButtonCellModel

class ButtonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val actionButtonView: AppCompatButton = itemView.findViewById(R.id.actionButtonView)

    fun bind(model: ButtonCellModel?) {

    }
}