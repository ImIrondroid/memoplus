package com.memo.project.di

import com.memo.project.base.BaseNavigator
import com.memo.project.data.local.db.MemoDatabase
import com.memo.project.ui.adapter.MemoAdapter
import com.memo.project.ui.adapter.PictureAdapter
import com.memo.project.ui.viewmodel.DetailViewModel
import com.memo.project.ui.viewmodel.MemoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule : Module = module {

    single { MemoDatabase.getInstance(get()) }
}

val viewModule : Module = module {

    factory { PictureAdapter(true) }
    factory { MemoAdapter() }

    viewModel { MemoViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val module = listOf(appModule, viewModule)
