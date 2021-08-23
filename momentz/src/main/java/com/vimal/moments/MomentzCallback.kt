package com.vimal.moments

import android.view.View

interface MomentzCallback{
    fun done()

    fun onNextCalled(view: View, momentz : Momentz, index: Int)
}
