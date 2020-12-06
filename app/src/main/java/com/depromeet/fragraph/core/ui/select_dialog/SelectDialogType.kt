package com.depromeet.fragraph.core.ui.select_dialog


const val HIDE_FLAG = -1
const val SAVE_MEMO_DELETE = 0
const val SAVE_MEMO_CONFIRM = 1
const val OUT_SESSION_CANCEL = 2
const val OUT_SESSION_CONFIRM = 3
const val FINISH_SESSION_CANCEL = 4
const val FINISH_SESSION_CONFIRM = 5

enum class SelectDialogType constructor(val leftFlag: Int, val rightFlag: Int) {
    MEMO_ON_WRITING(SAVE_MEMO_DELETE, SAVE_MEMO_CONFIRM),
    SESSION_OUT(OUT_SESSION_CANCEL, OUT_SESSION_CONFIRM),
    SESSION_FINISH(FINISH_SESSION_CANCEL, FINISH_SESSION_CONFIRM),
    HIDE_DIALOG(HIDE_FLAG, HIDE_FLAG);
}