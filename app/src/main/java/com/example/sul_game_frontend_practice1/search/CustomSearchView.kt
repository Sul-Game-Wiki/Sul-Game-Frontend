package com.example.sul_game_frontend_practice1.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import com.example.sul_game_frontend_practice1.databinding.CustomSearchviewBinding

class CustomSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: CustomSearchviewBinding = CustomSearchviewBinding.inflate(
        LayoutInflater.from(context), this, true)

    private val searchEditText: EditText = binding.searchSrcText
    private val searchButton: ImageView = binding.searchButton
    private var queryTextListener: OnQueryTextListener? = null

    init {
        searchButton.setOnClickListener {
            queryTextListener?.onQueryTextSubmit(searchEditText.text.toString())
        }

        searchEditText.addTextChangedListener { text ->
            queryTextListener?.onQueryTextChange(text.toString())
        }
    }

    fun setOnQueryTextListener(listener: OnQueryTextListener) {
        queryTextListener = listener
    }

    var query: String
        get() = searchEditText.text.toString()
        set(value) {
            searchEditText.setText(value)
        }

    fun setQuery(query: String, submit: Boolean) {
        searchEditText.setText(query)
        if (submit) {
            queryTextListener?.onQueryTextSubmit(query)
        }
    }

    interface OnQueryTextListener {
        fun onQueryTextChange(newText: String): Boolean
        fun onQueryTextSubmit(query: String): Boolean
    }
}
