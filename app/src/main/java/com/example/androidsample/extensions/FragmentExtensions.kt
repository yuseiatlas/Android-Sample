package com.example.androidsample.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding(
    crossinline bind: (View) -> T
): ReadOnlyProperty<Fragment, T> = object : ReadOnlyProperty<Fragment, T> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val value = requireView().getTag(property.name.hashCode()) as? T
        if (value != null) return value

        return bind(requireView()).also {
            requireView().setTag(property.name.hashCode(), it)
        }
    }
}
