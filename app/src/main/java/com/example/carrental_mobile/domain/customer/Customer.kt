package com.example.carrental_mobile.domain.customer

data class Customer(val id: Long,
                    val name: String,
                    val surname: String,
                    val identityCard: String,
                    val driverLicense: String,
                    val phone: String)