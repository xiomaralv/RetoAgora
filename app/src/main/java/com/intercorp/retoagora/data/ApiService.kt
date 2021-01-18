package com.intercorp.retoagora.data

import com.intercorp.retoagora.data.response.PeopleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://swapi.dev/api/people/?search=
    @GET("people")
    fun getPeople(
            @Query("search") search: String
    ): Call<PeopleResponse>
}