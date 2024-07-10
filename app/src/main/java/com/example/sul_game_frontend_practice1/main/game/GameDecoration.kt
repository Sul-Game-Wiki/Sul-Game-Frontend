package com.example.guhaejo.main.game

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GameDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) //각 아이템뷰의 순서 (index)
        outRect.bottom = 3
    }
}