package au.edu.swin.sdmd.sunrisekotlin

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.edu.swin.sdmd.sunrisekotlin.calc.AstronomicalCalendar
import au.edu.swin.sdmd.sunrisekotlin.calc.GeoLocation
import au.edu.swin.sdmd.sunrisekotlin.model.Location
import kotlinx.android.synthetic.main.recycle_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.ViewHolder>(){
    private var listofLocation: List<Location> = emptyList()
    fun set(items:List<Location>){
        listofLocation = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofLocation.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listofLocation[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(location: Location){
            val timezone = TimeZone.getTimeZone(location.country)
            val geoLocation = GeoLocation(location.name, location.latitude, location.longtitude, timezone)
            val ac = AstronomicalCalendar(geoLocation)
            ac.getCalendar().set(2019, 10, 15)
            val srise = ac.getSunrise()
            val sset = ac.getSunset()
            val dateformat = SimpleDateFormat("HH:mm")

            itemView.apply {
                locationTV3.text = location.name
                sunriseTimeTV.text = dateformat.format(srise)
                sunsetTimeTV.text = dateformat.format(sset)
            }
        } } }
//private fun updateTime(year: Int, monthOfYear: Int, dayOfMonth: Int) {
//    val tz = TimeZone.getDefault()
//    val geolocation = GeoLocation("Melbourne", -37.50, 145.01, tz)
//    val ac = AstronomicalCalendar(geolocation)
//    ac.getCalendar().set(year, monthOfYear, dayOfMonth)
//    val srise = ac.getSunrise()
//    val sset = ac.getSunset()
//
//    val sdf = SimpleDateFormat("HH:mm")
//
//    val sunriseTV = findViewById<TextView>(R.id.sunriseTimeTV)
//    val sunsetTV = findViewById<TextView>(R.id.sunsetTimeTV)
//    Log.d("SUNRISE Unformatted", srise.toString())
//
//    sunriseTV.setText(sdf.format(srise))
//    sunsetTV.setText(sdf.format(sset))
//}