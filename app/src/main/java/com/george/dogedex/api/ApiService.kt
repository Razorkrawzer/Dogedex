package com.george.dogedex.api

import com.george.dogedex.*
import com.george.dogedex.api.dto.AddDogToUserDTO
import com.george.dogedex.api.dto.LoginDTO
import com.george.dogedex.api.dto.SignUpDTO
import com.george.dogedex.api.responses.DogListApiResponse
import com.george.dogedex.api.responses.AuthApiResponse
import com.george.dogedex.api.responses.DefaultResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()


interface ApiService{
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO) : DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse
}

object DogsApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}