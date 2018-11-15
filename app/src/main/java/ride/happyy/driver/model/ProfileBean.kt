package ride.happyy.driver.model



class ProfileBean : BaseBean() {

    var isPhoneVerified: Boolean = false
    var id: String = ""
    var username: String = ""
    var authToken: String = ""
    var name: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var address: String = ""
    var profilePhoto: String = ""
    var coverPhoto: String = ""
    var email: String = ""
    var phone: String = ""
    var DOB: String = ""
    var gender: String = ""
    var location: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var country: String = ""
    var state: String = ""
    var city: String = ""
    var postalCode: String = ""
    var vehicle_type: String =""
    var is_active: String =""
    var total_trips : Int =0
    var total_earn : Int =0
    var total_due : Int =0
    var ref_bonus : Int =0
    var driver_rating : Int =0
    var commission_rate : Int =0
    var vehicle_no : String =""
    var carBrand : String =""
    var carFitnessCertificateNo : String =""
}
