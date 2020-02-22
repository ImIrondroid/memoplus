package com.lineplus.project.base

import androidx.appcompat.app.AppCompatActivity
import com.lineplus.project.util.Destination

abstract class BaseActivity : AppCompatActivity(), BaseNavigator{

    override fun backActivity() {
        onBackPressed()
    }

    override fun nextActivity(destination: Destination) {
        onBackPressed()
    }

    override fun nextActivityClearTop(destination: Destination) {
        onBackPressed()
    }

    override fun nextActivityFinish(destination: Destination) {
        onBackPressed()
    }

}