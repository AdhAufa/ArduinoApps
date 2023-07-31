package com.hypoxia.arduinoapps.model

data class User(
    var email : String? = null,
    var gender : String? = null,
    var id_user : Int? = null,
    var nama_user : String? = null,
    var no_hp : String? = null,
    var username : String? = null,
    var token : String? = null,
    var refresh_token : String? = null
)
