package com.gojek.assignment.utils

import com.gojek.assignment.databinding.ToolbarBinding

fun setUpToolbar(
    binding: ToolbarBinding,
    title: String? = null,
    menuClick: (() -> Unit)? = null
) {
    //set text for title
    if (!title.isNullOrEmpty()) {
        val textTitle = binding.toolbarTitle
        textTitle.show()
        textTitle.text = title
    } else {
        binding.toolbarTitle.hide()
    }

    binding.imageOverflowMenu.setOnClickListener {
        menuClick?.invoke()
    }
}
