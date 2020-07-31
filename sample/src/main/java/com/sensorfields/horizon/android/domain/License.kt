package com.sensorfields.horizon.android.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class License(val key: String, val name: String, val body: String) : Parcelable
