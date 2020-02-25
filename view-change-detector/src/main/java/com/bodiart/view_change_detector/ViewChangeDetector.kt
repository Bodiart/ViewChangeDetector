package com.bodiart.view_change_detector

import android.text.Editable
import android.text.TextWatcher
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.View
import android.widget.EditText
import androidx.core.util.valueIterator

class ViewChangeDetector(internal val viewChangeListener: ViewChangeListener) {

    // array of views and there detect status [viewId to Bool] (true - change detected, false - change cancelled)
    // we need it to check detect cancel
    private val viewsStatus = SparseBooleanArray()
    // array for edit text start values [viewId to startValue]
    private val editTextStartValues = SparseArray<String>()
    // array for edit text last values [viewId to startValue]
    private val editTextLastValues = SparseArray<String>()
    // array of views witch should detect changes
    private var viewsForDetect = ArrayList<View>()
    // utils for detecting changes
    private val addWatcherUtils = AddWatcherUtils(
        viewChangeListener, editTextStartValues, editTextLastValues, viewsStatus
    )


    fun startDetecting(vararg views: View) {
        clearViewsForDetect()
        viewsForDetect = ArrayList(views.asList())

        views.forEach { view ->
            saveStartValue(view)
            addWatcher(view)
        }
    }

    private fun clearViewsForDetect() {
        viewsForDetect.clear()
    }

    private fun saveStartValue(view: View) {
        when (view) {
            is EditText -> {
                SaveStartValueUtils.saveStartValueEditText(view, editTextStartValues)
            }
        }
    }

    private fun addWatcher(view: View) {
        when (view) {
            is EditText -> {
                addWatcherUtils.addWatcherEditText(view)
            }
        }
    }
}