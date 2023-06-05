package com.example.arduinoapps.model

import java.util.*

data class History(
   var id_history : Int? = null,
    var heartRate : Int? = null,
    var spo : Double? = null,
    var temperature : Double? = null,
    var result : String? = null,
    var time_stamp : Date? = null
)
