package com.intercorp.retoagora.ui.people.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intercorp.retoagora.data.repository.RetoAgoraRepository
import com.intercorp.retoagora.data.response.PeopleResponse
import com.intercorp.retoagora.data.response.Result
import org.koin.standalone.KoinComponent


class PeopleViewModel(val dataRepository: RetoAgoraRepository) : ViewModel(), KoinComponent {

    var listPeople = MutableLiveData<List<Result>>()

    init {
        listPeople.value = listOf()
    }

    fun getPeople(search: String) {

        dataRepository.getPeople(object : RetoAgoraRepository.OnPeopleData {
            override fun onSuccess(data: PeopleResponse) {
                listPeople.value = data.results
            }

            override fun onFailure() {
                //REQUEST FAILED
            }
        },search)
    }
}