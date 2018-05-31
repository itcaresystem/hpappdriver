package ride.happyy.driver.model



class DocumentBean : BaseBean() {

    var id: String = ""
    var type: Int = 0
    var name: String = ""
    var isUploaded: Boolean = false
    /**
     * Document Status - current status of the document
     * 0 - Not Uploaded
     * 1 - Document Uploaded, Pending Approval
     * 2 - Document Uploaded and Approved
     * 3 - Document Uploaded and Rejected
     */
    var documentStatus: Int = 0
    var documentURL: String = ""
}
