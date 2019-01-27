package com.example.carrental_mobile.domain.customer

data class Customer(private val id: Long,
                    private val name: String,
                    private val surname: String,
                    private val identityCard: String,
                    private val driverLicense: String,
                    private val phone: String)