package au.edu.swin.sdmd.sunrisekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.*
import au.edu.swin.sdmd.sunrisekotlin.calc.AstronomicalCalendar
import au.edu.swin.sdmd.sunrisekotlin.calc.GeoLocation
import au.edu.swin.sdmd.sunrisekotlin.model.Location
import au.edu.swin.sdmd.sunrisekotlin.model.RawLocation
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var locationList: MutableList<Location> = mutableListOf()
    private var rawLocationList: MutableList<RawLocation> = mutableListOf()
    private val adapter = LocationAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        debugButton.setOnClickListener {
            reload()
        }

        adapter.set(listOf(
            Location(
                name = "test",
                latitude = 30.4,
                longtitude = 30.4,
                country = "test"
            ),
            Location(
                name = "test",
                latitude = 30.4,
                longtitude = 30.4,
                country = "test"
            ),
            Location(
                name = "test",
                latitude = 30.4,
                longtitude = 30.4,
                country = "test"
            ),
            Location(
                name = "test",
                latitude = 30.4,
                longtitude = 30.4,
                country = "test"
            )
        ))
        reload()
        val layout = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        locationRecycleView.layoutManager = layout
        locationRecycleView.adapter = adapter

        //initializeUI()
    }

    private fun reload(){
        val file: String = applicationContext.assets.open("au_locations.txt").bufferedReader().use { it.readText() }

        val splitArray = file.split(')')
        splitArray.forEachIndexed { index, value ->
            rawLocationList.add(RawLocation(raw = value))
        }

//        rawLocationList.forEach {
//            Log.i("Print", it.toString())
//        }

        rawLocationList.forEach { rawString ->
            val split = rawString.raw.split(",")
            Log.i("Print", split[0])
            Log.i("Print", split.count().toString())
            Log.i("Print", split[1])
            Log.i("Print", split[2])
            Log.i("Pint", split[3].toString())
            locationList.add(
                Location(
                    name = split[0],
                    latitude = split[1].toDouble(),
                    longtitude = split[2].toDouble(),
                    country = split[3]
                )
            )
        }

//        locationList.forEach {
//            Log.i("Print", it.name)
//            Log.i("Print", it.latitude.toString())
//            Log.i("Print", it.longtitude.toString())
//            Log.i("Print", it.country)
//        }
        adapter.set(locationList)
    }





















    private fun initializeUI() {
        //val dp = findViewById<DatePicker>(R.id.datePicker)
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        //dp.init(year, month, day, dateChangeHandler) // setup initial values and reg. handler
        updateTime(year, month, day)
    }


    private fun updateTime(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val tz = TimeZone.getDefault()
        val geolocation = GeoLocation("Melbourne", -37.50, 145.01, tz)
        val ac = AstronomicalCalendar(geolocation)
        ac.getCalendar().set(year, monthOfYear, dayOfMonth)
        val srise = ac.getSunrise()
        val sset = ac.getSunset()

        val sdf = SimpleDateFormat("HH:mm")

        val sunriseTV = findViewById<TextView>(R.id.sunriseTimeTV)
        val sunsetTV = findViewById<TextView>(R.id.sunsetTimeTV)
        Log.d("SUNRISE Unformatted", srise.toString())

        sunriseTV.setText(sdf.format(srise))
        sunsetTV.setText(sdf.format(sset))
    }

    internal var dateChangeHandler: DatePicker.OnDateChangedListener =
        DatePicker.OnDateChangedListener { dp, year, monthOfYear, dayOfMonth ->
            updateTime(
                year,
                monthOfYear,
                dayOfMonth
            )
        }
}
