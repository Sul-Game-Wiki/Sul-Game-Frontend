package com.example.guhaejo.main.notification

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class AlarmDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        outRect.bottom = 50
    }
}