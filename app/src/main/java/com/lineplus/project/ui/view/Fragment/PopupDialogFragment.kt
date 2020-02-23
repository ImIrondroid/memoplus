package com.lineplus.project.ui.view.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.lineplus.project.R
import com.lineplus.project.util.OnImageSelectedListener
import kotlinx.android.synthetic.main.fragment_dialog_popup.*

class PopupDialogFragment(
    context : Context
) : Dialog(context) {

    private var onImageSelectedListener : OnImageSelectedListener? = null

    fun setOnImageSelectedListener(onItemSelectedListener: OnImageSelectedListener) {
        this.onImageSelectedListener = onItemSelectedListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutParams = WindowManager.LayoutParams().apply {
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.8f
        }
        window?.attributes = layoutParams

        setContentView(R.layout.fragment_dialog_popup)

        confirm.setOnClickListener {
            val editText = edit_link.text.toString()
            if(!editText.startsWith("https://") && !editText.startsWith("http://")) {
                showAlarm()
            } else {
                onImageSelectedListener?.invoke(it, edit_link.text.toString())
                this.dismiss()
            }
        }
        cancel.setOnClickListener {
            this.dismiss()
        }
    }

    private fun showAlarm() {
        val dialog = AlertDialog.Builder(this.context).apply {
            setTitle("주소를 다시 한 번 확인해 주세요.")
            setPositiveButton("확인") { dialog, which ->
                dialog.dismiss()
            }
        }.show()
    }
}