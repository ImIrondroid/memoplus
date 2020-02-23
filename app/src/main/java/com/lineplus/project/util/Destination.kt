package com.lineplus.project.util

import com.lineplus.project.ui.view.activity.MainActivity
import com.lineplus.project.ui.view.activity.AddMemoActivity
import com.lineplus.project.ui.view.activity.DetailedMemoActivity
import com.lineplus.project.ui.view.activity.EditMemoActivity

enum class Destination(
    name : String
) {
    toMainActivity(MainActivity::class.java.name),
    toAddMemoActivity(AddMemoActivity::class.java.name),
    toDetailedMemoActivity(DetailedMemoActivity::class.java.name),
    toEditMemoActivity(EditMemoActivity::class.java.name)
}
