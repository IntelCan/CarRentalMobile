package com.example.carrental_mobile.infrastructure.adapter.customer

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.carrental_mobile.R
import com.example.carrental_mobile.domain.customer.Customer

class CustomerAdapter(private val activity: Activity,
                      private val textViewResourceId: Int,
                      private val lCustomers: List<Customer>
): ArrayAdapter<Customer>(activity, textViewResourceId, lCustomers) {

    private var inflater: LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;

    override fun getCount(): Int {
        return lCustomers.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Customer {
        return lCustomers.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var vi = convertView
        val holder: CustomerViewHolder
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.customer_list_item, null)

                val name = vi?.findViewById<View>(R.id.customer_name) as TextView
                val surname = vi.findViewById<View>(R.id.customer_surname) as TextView
                val ic = vi.findViewById<View>(R.id.customer_ic) as TextView

                holder = CustomerViewHolder(name, surname, ic)
                vi.tag = holder
            } else {
                holder = vi?.tag as CustomerViewHolder
            }

            holder.name.text = lCustomers.get(position).name
            holder.surname.text = lCustomers.get(position).surname
            holder.ic.text = lCustomers.get(position).identityCard
        } catch (e: Exception) {
        }

        return vi!!
    }
}