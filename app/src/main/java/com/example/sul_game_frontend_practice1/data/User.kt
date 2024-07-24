package com.example.sul_game_frontend_practice1.data

import java.time.LocalDate
import java.time.Period

data class User(
    var name : String,
    var organization : String,
    var profileImage : Int,
    var email : String,
    var birthDate : LocalDate,
){
    fun getAge() : Int {
        val currentDate = LocalDate.now()
        val period = Period.between(birthDate, currentDate)
        var age = period.years

        if(currentDate.isBefore(birthDate.plusYears(age.toLong())))
            age -= 1

        return age
    }
}
