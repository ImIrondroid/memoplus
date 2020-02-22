package com.lineplus.project.di

import com.lineplus.project.base.BaseNavigator
import com.lineplus.project.data.local.db.MemoDatabase
import com.lineplus.project.ui.adapter.MemoAdapter
import com.lineplus.project.ui.adapter.PictureAdapter
import com.lineplus.project.ui.viewmodel.MemoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

//single 전역으로 사용
val appModule : Module = module {
    single { MemoDatabase.getInstance(get()) }
}

//viewModel, factory 사용
val viewModule : Module = module {
    factory { PictureAdapter() }
    factory { MemoAdapter() }
    viewModel { MemoViewModel<BaseNavigator>(get(), get()) }
}

val module = listOf(appModule, viewModule)
