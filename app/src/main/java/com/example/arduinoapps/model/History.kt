package com.example.arduinoapps.model

import java.util.*

data class History(
   var id : Int? = null,
   var id_user : Int? = null,
   var oxy_rate : String? = null,
   var category : String? = null,
   var result : String? = null,
   var status_oxy_rate : String? = null,
   var heart_rate : String? = null,
   var status_heart_rate : String? = null,
   var date : String? = null
)

