package dev.archfoundry.marsphotosapp.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface MarsApiService{
    @GET(value= "photos")
    suspend fun getPhotos(): List<MarsPhoto>
}
