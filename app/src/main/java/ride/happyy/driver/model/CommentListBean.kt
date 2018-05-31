package ride.happyy.driver.model



class CommentListBean : BaseBean() {

    var comments: ArrayList<CommentBean> = ArrayList()
    var pagination: PaginationBean = PaginationBean()
}
