package com.example.carrental_mobile.domain.car

data class Car(val id: Long,
               val mark: String,
               val model: String,
               val rate: String,
               val price: Double,
               val isRented: Boolean
               )