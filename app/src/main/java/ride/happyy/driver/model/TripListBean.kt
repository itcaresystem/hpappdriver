package ride.happyy.driver.model

import java.util.*



class TripListBean : BaseBean() {

    var totalFare: String = ""
    var totalTimeOnline: String = ""
    var totalRidesTaken: Int = 0
    var trips: ArrayList<TripBean> = ArrayList()
    var pagination: PaginationBean = PaginationBean()
}
