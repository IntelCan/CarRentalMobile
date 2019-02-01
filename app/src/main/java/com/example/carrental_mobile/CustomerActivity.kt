package com.example.carrental_mobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.carrental_mobile.domain.customer.Customer
import com.example.carrental_mobile.infrastructure.adapter.customer.CustomerAdapter
import com.example.carrental_mobile.infrastructure.rest.api.CustomerApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_customer.*

class CustomerActivity : AppCompatActivity() {

    private val TOKEN_KEY: String = "com.example.carrental_mobile.token"
    private var prefs: SharedPreferences? = null

    private var myCompositeDisposable: CompositeDisposable? = null
    private var token: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        setSupportActionBar(toolbar)
        title = "Customers"


        getCustomers()

        add_customer_button.setOnClickListener { view ->
            Snackbar.make(view, "Action not implemented jet", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun getCustomers() {
        myCompositeDisposable = CompositeDisposable()

        prefs = this.getSharedPreferences(TOKEN_KEY, Context.MODE_PRIVATE)

        token = prefs?.getString(TOKEN_KEY, "")
        myCompositeDisposable?.add(
            CustomerApi.create(token!!).getCustomers().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    this::showCustomers,
                    { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                ))
    }

    private fun showCustomers(customers: List<Customer>) {
        val lvCustomers = findViewById<ListView>(R.id.customers)
        val customerAdapter = CustomerAdapter(this, 0,  customers)

        lvCustomers.adapter = customerAdapter

        lvCustomers.onItemClickListener = AdapterView.OnItemClickListener {
                _, view, i, l ->
            Toast.makeText(this@CustomerActivity,
                "you selected attraction " + (i + 1),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
    }

}
