package nacho.llorente.data.sources.di
import nacho.llorente.data.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nacho.llorente.data.sources.service.DidService
import nacho.llorente.data.sources.service.GeminiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    //ESTO ES AUTOMATICO, DECLARAS LA URL DEL API y EL API KEY QUE VAS A USAR,
    // ESTO NO SE PUEDE PONER AQUÍ PERO DE VERDAD HAY ALGUIEN QUE VAYA A VER ESTO?

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        // Interceptor para agregar la API key a todas las solicitudes
        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Basic aXRzbmFjaGV0dG8rY3VlbnRhdW5vQGdtYWlsLmNvbQ:A5khHb9hzlFvIOETsyfuO")  // Cambia esto por tu API key
                .build()
            chain.proceed(newRequest)
        }

        return OkHttpClient
            .Builder()
            .addInterceptor(apiKeyInterceptor)  // Agregamos el interceptor
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
         GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideConverterMoshiFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    //ESTO ES AUTOMATICO, DECLARAS EL SERVICIO QUE QUIERES USAR
    @Singleton
    @Provides
    fun provideDidService(retrofit: Retrofit): DidService {
        return retrofit.create(DidService::class.java)
    }

    @Singleton
    @Provides
    fun provideGeminiService(retrofit: Retrofit): GeminiService {
        return retrofit.create(GeminiService::class.java)
    }

}