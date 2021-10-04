package com.example.clock.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.clock.R

@BindingAdapter("app:updateVisibility")
fun updateVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:iconSrc")
fun setImageResource(imageView: ImageView, iconName: String?) {
    val context = imageView.context
    var id = iconName?.let {
        context.resources.getIdentifier(iconName, "drawable", context.packageName)
    } ?: R.drawable.no_icon
    if (id == 0) id = R.drawable.no_icon
    imageView.setImageResource(id)
}
