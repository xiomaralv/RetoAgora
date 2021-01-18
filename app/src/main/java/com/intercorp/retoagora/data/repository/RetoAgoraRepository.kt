package com.intercorp.retoagora.data.repository

import com.intercorp.retoagora.data.ApiService
import com.intercorp.retoagora.data.response.PeopleResponse
import retrofit2.Call
import retrofit2.Response

    class RetoAgoraRepository(val apiService: ApiService) {

        fun getPeople(onPeopleData: OnPeopleData, search:String) {
            apiService.getPeople(search).enqueue(object : retrofit2.Callback<PeopleResponse> {
                override fun onResponse(call: Call<PeopleResponse>, response: Response<PeopleResponse>) {
                    onPeopleData.onSuccess((response.body() as PeopleResponse))
                }

                override fun onFailure(call: Call<PeopleResponse>, t: Throwable) {
                    onPeopleData.onFailure()
                }
            })
        }

        interface OnPeopleData {
            fun onSuccess(data: PeopleResponse)
            fun onFailure()
        }
    }
