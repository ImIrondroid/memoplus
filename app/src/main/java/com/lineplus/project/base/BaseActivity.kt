package com.lineplus.project.base

import androidx.appcompat.app.AppCompatActivity
import com.lineplus.project.util.Destination

abstract class BaseActivity : AppCompatActivity(), BaseNavigator{

    override fun backActivity() {
        finish()
    }

    override fun nextActivity(destination: Destination) {
    }

    override fun nextActivityClearTop(destination: Destination) {
    }

    override fun nextActivityFinish(destination: Destination) {
    }

}