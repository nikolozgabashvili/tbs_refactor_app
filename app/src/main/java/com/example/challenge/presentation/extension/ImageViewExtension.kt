package com.example.challenge.presentation.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.challenge.R


fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_load)
        .error(R.drawable.ic_error)
        .into(this)
}