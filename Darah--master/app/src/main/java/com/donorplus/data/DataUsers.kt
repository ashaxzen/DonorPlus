package com.donorplus.data

data class DataUsers(
    val id: String,
    val tipeDarah: String,
    val expiredDate: String,
    val location: String,
    val sts: Boolean,
    val uid: String

    ) {
    constructor() : this("", "", "", "", true, "")
}

