package com.memo.project.di

import com.memo.project.data.local.db.MemoDatabase
import com.memo.project.data.repository.MemoRepository
import com.memo.project.data.repository.MemoRepositoryImpl
import com.memo.project.ui.adapter.MemoAdapter
import com.memo.project.ui.adapter.PictureAdapter
import com.memo.project.ui.viewmodel.DetailViewModel
import com.memo.project.ui.viewmodel.MemoViewModel
import com.memo.project.util.rx.AppSchedulerProvider
import com.memo.project.util.rx.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule : Module = module {

    single { MemoDatabase.getInstance(get()) }
    single { AppSchedulerProvider() as SchedulerProvider }
    single { MemoRepositoryImpl(get(), get()) as MemoRepository}
}

val viewModule : Module = module {

    factory { PictureAdapter(true) }
    factory { MemoAdapter() }

    viewModel { MemoViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val module = listOf(appModule, viewModule)
