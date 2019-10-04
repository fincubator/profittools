package aam.dex

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline func: (T) -> Any) {
    observe(owner, Observer<T> { func(it) })
}