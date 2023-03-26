package com.android.wazzabysama.presentation.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import com.android.wazzabysama.ui.util.model.AddressLocation
import com.google.android.gms.location.LocationServices
import java.util.*

data class LocationData(val addressLocation: AddressLocation? = null,
                        val exception: Exception? = null)
class LocationLiveData(context: Context): LiveData<LocationData>() {
    private val appContext = context.applicationContext
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext)
    val geoCoder = Geocoder(context, Locale.getDefault())

    // Used to track the first LiveData Subscriber
    // to send the last know location immediately
    private var firstSubscriber = true

    override fun onActive() {
        super.onActive()
        if (firstSubscriber) {
            requestLocation()
            firstSubscriber = false
        }
    }

    private fun requestLocation() {

        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                Log.d("MALEO9393", "Testing !! Testing !! ${location}")
                if (location !== null) {
                    Log.d("MALEO9393", "Testing !! Testing !!")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        geoCoder.getFromLocation(
                            location!!.latitude,
                            location!!.longitude,
                            1,
                            Geocoder.GeocodeListener { addresses ->
                                value = LocationData(
                                    addressLocation = AddressLocation(
                                        city = addresses.get(0).locality,
                                        country = addresses.get(0).countryName,
                                        state = addresses.get(0).adminArea,
                                        zipCode = addresses.get(0).postalCode,
                                        countryCode = addresses.get(0).countryCode
                                    )
                                )
                            })
                    } else {
                        value = LocationData(
                            addressLocation = AddressLocation(
                                city = geoCoder.getFromLocation( location.latitude, location.longitude, 3)!!.get(0).locality,
                                country = geoCoder.getFromLocation( location.latitude, location.longitude, 3)!!.get(0).countryName,
                                state = geoCoder.getFromLocation( location.latitude, location.longitude, 3)!!.get(0).adminArea,
                                zipCode = geoCoder.getFromLocation( location.latitude, location.longitude, 3)!!.get(0).postalCode,
                                countryCode  = geoCoder.getFromLocation( location.latitude, location.longitude, 3)!!.get(0).countryCode
                            )
                        )

                    }
                }

            }

            fusedLocationClient.lastLocation.addOnFailureListener {exception ->
                value = LocationData(exception = exception)
            }
        } catch (e: SecurityException) {
            value = LocationData(exception = e)
        }

    }
}