package io.github.horaciocome1.lucia

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.github.horaciocome1.lucia.api.LuciaApi
import io.github.horaciocome1.lucia.api.LuciaInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ViewModelComponent::class, ActivityComponent::class, SingletonComponent::class)
class LuciaModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val logging = HttpLoggingInterceptor()
            .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE)
        val okHttp = OkHttpClient.Builder()
            .addInterceptor(LuciaInterceptor())
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttp)
            .build()
    }

    @Provides
    fun provideLuciaApi(retrofit: Retrofit): LuciaApi = retrofit.create(LuciaApi::class.java)
}