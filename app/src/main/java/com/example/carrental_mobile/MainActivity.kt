package com.example.carrental_mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private var goToCustomers: LinearLayout? = null
    private var goToCars: LinearLayout? = null
    private var goToRental: LinearLayout? = null
    private var goToFavourties: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Menu"

        goToCustomers = findViewById<LinearLayout>(R.id.go_to_customers)
        goToCustomers!!.setOnClickListener { redirectToCustomers() }

        goToCars = findViewById<LinearLayout>(R.id.go_to_cars)
        goToCars!!.setOnClickListener { redirectToCars() }

        goToRental = findViewById<LinearLayout>(R.id.go_to_rental)
        goToRental!!.setOnClickListener { redirectToRental() }

        goToFavourties = findViewById<LinearLayout>(R.id.go_to_favourite)
        goToFavourties!!.setOnClickListener { redirectToFavourites() }

    }

    fun redirectToCustomers() {
        val intent = Intent(this, CustomerActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun redirectToCars() {
        val intent = Intent(this, CarActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun redirectToRental() {
        val intent = Intent(this, CustomerActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun redirectToFavourites() {
        val intent = Intent(this, CustomerActivity::class.java)
        startActivityForResult(intent, 1)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Naciśnij jeszcze raz aby wyjść z IKMS", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}
