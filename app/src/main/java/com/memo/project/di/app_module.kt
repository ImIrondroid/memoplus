package com.memo.project.di

import com.memo.project.base.BaseNavigator
import com.memo.project.data.local.db.MemoDatabase
import com.memo.project.ui.adapter.MemoAdapter
import com.memo.project.ui.adapter.PictureAdapter
import com.memo.project.ui.viewmodel.MemoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

//single 전역으로 사용
val appModule : Module = module {
    single { MemoDatabase.getInstance(get()) }
}

//viewModel, factory 사용
val viewModule : Module = module {
    factory { PictureAdapter(true) }
    factory { MemoAdapter() }
    viewModel { MemoViewModel<BaseNavigator>(get(), get()) }
}

val module = listOf(appModule, viewModule)
