package com.lineplus.project.base

import com.lineplus.project.util.Destination

interface BaseNavigator {

    fun backActivity()
    fun nextActivity(destination: Destination)
    fun nextActivityFinish(destination: Destination)
    fun nextActivityClearTop(destination: Destination)
}
