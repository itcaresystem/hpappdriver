package ride.happyy.driver.model

import com.google.android.gms.maps.model.LatLng



class RequestDetailsBean : BaseBean() {

    var requestID: String = ""
    var carType: String = ""
    var distance: String = ""
    var eta: String = ""
    var carTypeImage: String = ""
    var customerID: String = ""
    var customerName: String = ""
    var customerPhoto: String = ""
    var customerLocation: String = ""
    var customerLatitude: String = ""
    var customerLongitude: String = ""
    var destination_location: String =""
    var request_destination_latitude: String = ""
    var request_destination_longitude: String = ""


    fun getDestinationLatLng(): LatLng {
        return LatLng(dDestinationLatitude, dDestinationLongitude)
    }

    var dDestinationLatitude: Double = 0.0
        get() {
            try {
                return java.lang.Double.parseDouble(customerLatitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    var dDestinationLongitude: Double = 0.0
        get() {
            try {
                return java.lang.Double.parseDouble(customerLongitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }
}
