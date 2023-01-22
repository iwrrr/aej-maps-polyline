package com.iwrrr.mapspolyline.data.mapper

import com.iwrrr.mapspolyline.data.entity.LocationData
import com.iwrrr.mapspolyline.data.network.response.ReverseLocationResponse

object ResponseMapper {

    fun mapReverseLocationResponseToLocation(reverseLocationResponse: ReverseLocationResponse?): LocationData {
        val address = reverseLocationResponse?.data?.address.run {
            LocationData.Address(
                city = this?.city.orEmpty(),
                country = this?.country.orEmpty(),
                district = this?.district.orEmpty()
            )
        }
        val coordinate = reverseLocationResponse?.data?.coordinate.run {
            LocationData.Coordinate(
                latitude = this?.latitude ?: 0.0,
                longitude = this?.longitude ?: 0.0,
            )
        }
        val name = reverseLocationResponse?.data?.name.orEmpty()
        return LocationData(
            address = address,
            coordinate = coordinate,
            name = name
        )
    }
}