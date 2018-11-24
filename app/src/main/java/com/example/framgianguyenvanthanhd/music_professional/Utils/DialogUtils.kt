package com.example.framgianguyenvanthanhd.music_professional.Utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

/**
 * Created by admin on 11/24/2018.
 */
object DialogUtils {

    interface OnDialogClick {
        fun onClickOk()

        fun onCancel()
    }

    fun createDialogConfirm(context: Context, title: String, desc: String, listener: OnDialogClick) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        dialog.setMessage(desc)
        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog,ok ->
            listener.onClickOk()
            dialog.dismiss()
        })
        dialog.create().show()
    }

}