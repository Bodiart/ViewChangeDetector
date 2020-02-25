package com.bodiart.view_change_detector

interface ViewChangeListener {
    fun changeDetected()
    fun changeCancelled()
}