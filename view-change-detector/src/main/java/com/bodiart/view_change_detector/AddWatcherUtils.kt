package com.bodiart.view_change_detector

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.widget.EditText
import androidx.core.util.valueIterator
import java.lang.ref.WeakReference

class AddWatcherUtils(
    private val viewChangeListener: ViewChangeListener,
    private val editTextStartValues: SparseArray<String>,
    private val editTextLastValues: SparseArray<String>,
    private val viewsStatus: SparseBooleanArray
) {


    fun addWatcherEditText(editText: EditText) {
        // add view status (false for default)
        viewsStatus.put(editText.id, false)

        // add text watcher
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s ?: return

                try {

                    val newValue = s.toString()
                    val oldValue = editTextStartValues[editText.id]

                    editTextLastValues.put(editText.id, editText.text.toString())

                    if (newValue != oldValue) {
                        viewsStatus.put(editText.id, true)
                        viewChangeListener.changeDetected()
                    } else {
                        viewsStatus.put(editText.id, false)
                        checkForChangeCancelled()
                    }

                } catch (ex: Exception) {
                    Log.d("AddWatcherUtils", "afterTextChanged failed", ex)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {}
        })
    }

    /**
     * Check all views
     * if there values is start values - call detect cancelled
     * */
    private fun checkForChangeCancelled() {
        val falseStatusesShouldBe = viewsStatus.size()
        var falseStatuses = 0

        for (viewStatus in viewsStatus.valueIterator()) {
            if (!viewStatus)
                falseStatuses++
        }

        if (falseStatuses == falseStatusesShouldBe)
            viewChangeListener.changeCancelled()
    }
}