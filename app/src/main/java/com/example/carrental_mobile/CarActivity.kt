package com.example.carrental_mobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.carrental_mobile.domain.car.Car
import com.example.carrental_mobile.infrastructure.adapter.car.CarAdapter
import com.example.carrental_mobile.infrastructure.rest.api.CarApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_car.*

class CarActivity : AppCompatActivity() {

    private val TOKEN_KEY: String = "com.example.carrental_mobile.token"
    private var prefs: SharedPreferences? = null

    private var myCompositeDisposable: CompositeDisposable? = null
    private var token: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)
        setSupportActionBar(toolbar)
        title = "Cars"

        getCars()

    }


    private fun getCars() {
        myCompositeDisposable = CompositeDisposable()

        prefs = this.getSharedPreferences(TOKEN_KEY, Context.MODE_PRIVATE)

        token = prefs?.getString(TOKEN_KEY, "")
        myCompositeDisposable?.add(
            CarApi.create(token!!).getCars().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    this::showCars,
                    { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                ))
    }

    private fun showCars(cars: List<Car>) {
        val lvCustomers = findViewById<ListView>(R.id.cars)
        val customerAdapter = CarAdapter(this, 0, cars)

        lvCustomers.adapter = customerAdapter

        lvCustomers.onItemClickListener = AdapterView.OnItemClickListener { _, view, i, l ->
            Toast.makeText(
                this@CarActivity,
                "you selected attraction " + (i + 1),
                Toast.LENGTH_LONG
            ).show()
        }

        lvCustomers.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, view, i, l ->
            Toast.makeText(
                this@CarActivity,
                "you long tapped " + (i + 1),
                Toast.LENGTH_LONG).show()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
    }
}

