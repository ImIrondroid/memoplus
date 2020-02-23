package com.lineplus.project.util

import com.lineplus.project.ui.MainActivity
import com.lineplus.project.ui.view.AddMemoActivity
import com.lineplus.project.ui.view.DetailedMemoActivity
import com.lineplus.project.ui.view.EditMemoActivity

enum class Destination(
    name : String
) {
    toMainActivity(MainActivity::class.java.name),
    toAddMemoActivity(AddMemoActivity::class.java.name),
    toDetailedMemoActivity(DetailedMemoActivity::class.java.name),
    toEditMemoActivity(EditMemoActivity::class.java.name)
}
