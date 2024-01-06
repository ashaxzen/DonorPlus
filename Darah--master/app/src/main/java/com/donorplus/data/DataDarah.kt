package com.donorplus.data

data class DataDarah(
    val id: String,
    val tipeDarah: String,
    val expiredDate: String,
    val location: String,
    val sts: Boolean,

) {
    constructor() : this("", "", "", "", true)
}

