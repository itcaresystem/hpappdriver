package ride.happyy.driver.model

import java.util.*



class WeeklyEarningsBean : BaseBean() {

    var weekOfYear: Int = 0
    var weekStart: Long = 0
    var weekEnd: Long = 0
    var year: Int = 0
    var totalPayout: String = ""
    var dailyEarnings: ArrayList<DailyEarningBean> = ArrayList()
}
