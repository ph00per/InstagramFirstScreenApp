package com.ph00.instagramfirstpageapp.di

import com.ph00.instagramfirstpageapp.data.api.InstagramFakeApi
import com.ph00.instagramfirstpageapp.data.repositories.PostsRepository
import com.ph00.instagramfirstpageapp.ui.adapters.AdItemDelegateAdapter
import com.ph00.instagramfirstpageapp.ui.adapters.PostItemDelegateAdapter
import com.ph00.instagramfirstpageapp.utils.Constants.Companion.BASE_URL
import com.ph00.instagramfirstpageapp.viewmodels.PostListViewModel
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val netModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    single { Picasso.Builder(androidContext()).build() }
}

val apiModule = module {
    single { get<Retrofit>().create(InstagramFakeApi::class.java) }
}

val repositoryModule = module {
    single { PostsRepository(get()) }
}
val viewModelModule = module {
    viewModel { PostListViewModel(get()) }
}