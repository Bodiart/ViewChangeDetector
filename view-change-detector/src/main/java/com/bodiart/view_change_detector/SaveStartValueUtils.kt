package com.bodiart.view_change_detector

import android.util.SparseArray
import android.widget.EditText

class SaveStartValueUtils {
    companion object {

        fun saveStartValueEditText(editText: EditText, editTextStartValues: SparseArray<String>) {
            editTextStartValues.put(editText.id, editText.text.toString())
        }

    }
}