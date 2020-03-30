package com.memo.project.util

import android.view.View

typealias OnItemSelectedListener<T> = ((view : View, item : T, position : Int) -> Unit)
typealias OnImageSelectedListener = ((view : View, imagePath : String) -> Unit)