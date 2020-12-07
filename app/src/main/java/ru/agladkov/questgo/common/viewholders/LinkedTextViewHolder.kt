package ru.agladkov.questgo.common.viewholders

import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.models.LinkModel
import ru.agladkov.questgo.common.models.LinkedTextCellModel

interface LinkedTextDelegate {
    fun onClick(model: LinkModel)
}

class LinkedTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val linkedTextView: AppCompatTextView = itemView.findViewById(R.id.linkedTextView)

    var delegate: LinkedTextDelegate? = null

    fun bind(model: LinkedTextCellModel?) {
        model?.let {
            val spannableString = SpannableString(model.text)

            for (linkModel in model.selectedStrings) {
                val clickableSpan = object : ClickableSpan() {
                    override fun updateDrawState(textPaint: TextPaint) {
                        // use this to change the link color
                        textPaint.color = ContextCompat.getColor(itemView.context, R.color.dark_tint_primary)
                        // toggle below value to enable/disable
                        // the underline shown below the clickable text
                        textPaint.isUnderlineText = true
                    }

                    override fun onClick(view: View) {
                        Selection.setSelection((view as TextView).text as Spannable, 0)
                        view.invalidate()
                        delegate?.onClick(linkModel)
                    }
                }

                val startIndexOfLink = model.text.toLowerCase()
                    .indexOf(linkModel.selectedText.toLowerCase())

                spannableString.setSpan(
                    clickableSpan,
                    startIndexOfLink,
                    startIndexOfLink + linkModel.selectedText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            linkedTextView.movementMethod = LinkMovementMethod.getInstance()
            linkedTextView.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }
}