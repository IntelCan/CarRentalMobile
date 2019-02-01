package com.example.carrental_mobile.infrastructure.adapter.car

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.carrental_mobile.R
import com.example.carrental_mobile.domain.car.Car

class CarAdapter(private val activity: Activity,
                 private val textViewResourceId: Int,
                 private val lCars: List<Car>
): ArrayAdapter<Car>(activity, textViewResourceId, lCars) {

    private var inflater: LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;

    override fun getCount(): Int {
        return lCars.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Car {
        return lCars.get(position)
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var vi = convertView
        val holder: CarViewHolder
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.car_list_item, null)

                val mark = vi?.findViewById<View>(R.id.car_mark) as TextView
                val model = vi.findViewById<View>(R.id.car_model) as TextView
                val rate = vi.findViewById<View>(R.id.car_rate) as TextView
                val price = vi.findViewById<View>(R.id.car_price) as TextView

                holder = CarViewHolder(mark, model, rate, price )
                vi.tag = holder
            } else {
                holder = vi?.tag as CarViewHolder
            }

            if (!lCars.get(position).isRented) {
                vi.setBackgroundColor(R.color.material_grey_300)
            }

            holder.mark.text = "Mark: ${lCars.get(position).mark}"
            holder.model.text = "Model: ${lCars.get(position).model}"
            holder.rate.text = "Rate: ${lCars.get(position).rate}"
            holder.price.text = "Rate: ${lCars.get(position).price}"
        } catch (e: Exception) {
        }

        return vi!!
    }
}