package com.iwrrr.mapspolyline.data.network

import com.iwrrr.mapspolyline.data.network.response.ReverseLocationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET(Endpoint.REVERSE)
    suspend fun reverseCoordinate(
        @Query("coordinate") coordinate: String
    ): Response<ReverseLocationResponse>

    companion object {
        private const val BASE_URL = "https://b46c-180-252-127-21.ap.ngrok.io"

        fun create(): WebServices {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebServices::class.java)
        }
    }

    object Endpoint {
        const val SEARCH = "/api/v1/location/search"
        const val REVERSE = "/api/v1/location/reverse"
        const val ROUTES = "/api/v1/location/routes"
    }
}