package com.intercorp.retoagora.data.response

data class PeopleResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)