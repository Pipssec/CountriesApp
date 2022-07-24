package com.example.countriesapp

data class Country (
    val name: String,
    val capital: String,
    val population: Long,
    val area: Long,
    val languages: List<Languages>,
)

data class Languages(
    val name: String,
)