package com.memo.project.util

import com.memo.project.ui.view.activity.AddMemoActivity
import com.memo.project.ui.view.activity.DetailedMemoActivity
import com.memo.project.ui.view.activity.EditMemoActivity
import com.memo.project.ui.view.activity.MainActivity

enum class Destination(
    name : String
) {
    toMainActivity(MainActivity::class.java.name),
    toAddMemoActivity(AddMemoActivity::class.java.name),
    toDetailedMemoActivity(DetailedMemoActivity::class.java.name),
    toEditMemoActivity(EditMemoActivity::class.java.name)
}
