package info.sul_game.config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

//import android.util.Log
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//object RetrofitClient {
//    private const val BASE_URL = "http://220.85.169.165:8085/"
//

//
//
//    val API: API = retrofit.create(API::class.java)
//}


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import info.sul_game.api.MemberApi
import info.sul_game.api.GameListApi
import info.sul_game.api.HomeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.sul-game.info/api/"
    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY               //okhttp에서 어떤부분을 로깅할지 지정


        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
//            .addInterceptor { chain ->
//                val original = chain.request()
//                val requestBuilder = original.newBuilder()                      // 리퀘스트 빌더 만들고
//                    .header("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")    // 거기에 헤더 추가
//                val request = requestBuilder.build()
//                chain.proceed(request)
//            }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)             //타임아웃 조건 추가
            .addNetworkInterceptor(interceptor)                     //로그용 okhttp 달아주고
            .build()
    }

    private val gson: Gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)                              // baseurl 달고
        .addConverterFactory(GsonConverterFactory.create(gson))     // json을 data class로 편하게 바꿔주는 gson
        .client(createOkHttpClient())               // okhttp 클라이언트 달고
        .build()

    val memberApiService: MemberApi by lazy {
        retrofit.create(MemberApi::class.java)
    }
    val gameListService: GameListApi by lazy {
        retrofit.create(GameListApi::class.java)
    }
    val homeApiService: HomeApi by lazy {
        retrofit.create(HomeApi::class.java)
    }
}

