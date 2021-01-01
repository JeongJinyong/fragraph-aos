package com.depromeet.fragraph.core.ui.select_dialog

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.depromeet.fragraph.R

@BindingAdapter("bind_select_dialog_title")
fun TextView.bindSelectDialogTitle(type: SelectDialogType) {
    this.text = when (type) {
        SelectDialogType.MEMO_ON_WRITING -> resources.getString(R.string.memo_on_writing_title)
        SelectDialogType.SESSION_OUT -> resources.getString(R.string.session_out_title)
        SelectDialogType.SESSION_FINISH -> resources.getString(R.string.session_finish_title)
        SelectDialogType.HOME_HISTORY_DELETE -> resources.getString(R.string.home_history_delete_title)
        SelectDialogType.HOME_HISTORY_EDIT -> resources.getString(R.string.home_history_edit_title)
        else -> resources.getString(R.string.common_unknown)
    }
}

@BindingAdapter("bind_select_dialog_description")
fun TextView.bindSelectDialogDescription(type: SelectDialogType) {
    this.text = when (type) {
        SelectDialogType.MEMO_ON_WRITING -> resources.getString(R.string.memo_on_writing_description)
        SelectDialogType.SESSION_OUT -> resources.getString(R.string.session_out_description)
        SelectDialogType.SESSION_FINISH -> resources.getString(R.string.session_finish_description)
        else -> resources.getString(R.string.common_unknown)
    }

    this.visibility = when (type) {
        SelectDialogType.MEMO_ON_WRITING -> View.VISIBLE
        SelectDialogType.SESSION_OUT -> View.VISIBLE
        SelectDialogType.SESSION_FINISH -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("bind_select_dialog_right")
fun TextView.bindSelectDialogRightBtn(type: SelectDialogType) {
    this.text = when (type) {
        SelectDialogType.MEMO_ON_WRITING -> resources.getString(R.string.common_back)
        SelectDialogType.SESSION_OUT -> resources.getString(R.string.common_exit)
        SelectDialogType.SESSION_FINISH -> resources.getString(R.string.common_confirm)
        SelectDialogType.HOME_HISTORY_DELETE -> resources.getString(R.string.common_delete)
        SelectDialogType.HOME_HISTORY_EDIT -> resources.getString(R.string.common_edit)
        else -> resources.getString(R.string.common_unknown)
    }
}

@BindingAdapter("bind_select_dialog_left")
fun TextView.bindSelectDialogLeftBtn(type: SelectDialogType) {
    this.text = when (type) {
        SelectDialogType.MEMO_ON_WRITING -> resources.getString(R.string.common_out)
        SelectDialogType.SESSION_OUT -> resources.getString(R.string.common_save)
        SelectDialogType.SESSION_FINISH -> ""
        SelectDialogType.HOME_HISTORY_DELETE -> resources.getString(R.string.common_cancel)
        SelectDialogType.HOME_HISTORY_EDIT -> resources.getString(R.string.common_cancel)
        else -> resources.getString(R.string.common_unknown)
    }
}