package ride.happyy.driver.model

import com.google.gson.annotations.SerializedName



class CountryBean : BaseBean(), Comparable<CountryBean> {

    var id: Int = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("dial_code")
    var dialCode: String = ""
    @SerializedName("code")
    var countryCode: String = ""

    override fun compareTo(other: CountryBean): Int {
        val comparison = dialCode.compareTo(other.dialCode)
        return if (comparison == 0) {
            0
        } else if (comparison > 0) {
            1
        } else
            -1
    }
}
