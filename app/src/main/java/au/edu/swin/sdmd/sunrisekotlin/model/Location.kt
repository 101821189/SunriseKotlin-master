package au.edu.swin.sdmd.sunrisekotlin.model

data class Location(
    val name: String,
    val latitude: Double,
    val longtitude: Double,
    val country: String
)

data class RawLocation(
    val raw: String
)



