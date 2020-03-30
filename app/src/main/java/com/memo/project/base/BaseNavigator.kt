package com.memo.project.base

import com.memo.project.util.Destination


interface BaseNavigator {

    fun backActivity()
    fun nextActivity(destination: Destination)
    fun nextActivityFinish(destination: Destination)
    fun nextActivityClearTop(destination: Destination)
}
