package com.android.wazzabysama.ui.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.android.wazzabysama.ui.util.model.AddressLocation
import java.text.SimpleDateFormat
import java.util.*

class Util {

    fun getCountry(code: String): String {
        val listCountries = Locale.getISOCountries()
        listCountries.forEach { country ->
            val locale = Locale(Locale.getDefault().isO3Language, country)
            if ((locale.country.lowercase()) == code.lowercase()) {
                return locale.displayCountry
            }
        }
        return ""
    }

    fun getDateFormatter(date: Date): String {
        val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())
        return formatter.format(date)
    }

    fun getDateTimeFormatter(date: Date): String { //"dd/MM/yyyy  HH:mm"
        val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy  HH:mm", Locale.getDefault())
        return formatter.format(date)
    }

    fun getCityNameGeocode(latitude: Double, longitude: Double, context: Context): AddressLocation {
        val geoCoder = Geocoder(context, Locale.getDefault())
        var address  = AddressLocation(
            city = null,
            country = null,
            state = null,
            zipCode = null,
            countryCode = null
        )

        if (Build.VERSION.SDK_INT == 33 ) {
            geoCoder.getFromLocation(latitude, longitude, 3, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        address = AddressLocation(
                            city = addresses!!.get(0).locality,
                            country = addresses!!.get(0).countryName,
                            state = addresses!!.get(0).adminArea,
                            zipCode = addresses!!.get(0).postalCode,
                            countryCode = addresses!!.get(0).countryCode
                        )
                    }

                    override fun onError(errorMessage: String?) {
                        super.onError(errorMessage)
                        address = AddressLocation(
                            city = null,
                            country = null,
                            state = null,
                            zipCode = null,
                            countryCode = null
                        )

                    }
                })

            return address
        } else {
            //adress =  geoCoder.getFromLocation( latitude,longitude, 3)!!.get(0)
            return AddressLocation(
                    city = geoCoder.getFromLocation( latitude,longitude, 3)!!.get(0).locality,
                    country = geoCoder.getFromLocation( latitude,longitude, 3)!!.get(0).countryName,
                    state = geoCoder.getFromLocation( latitude,longitude, 3)!!.get(0).adminArea,
                    zipCode = geoCoder.getFromLocation( latitude,longitude, 3)!!.get(0).postalCode,
                    countryCode  = geoCoder.getFromLocation( latitude,longitude, 3)!!.get(0).countryCode
            )
        }
    }

}