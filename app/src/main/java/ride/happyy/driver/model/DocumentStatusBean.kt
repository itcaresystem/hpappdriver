package ride.happyy.driver.model

import ride.happyy.driver.util.AppConstants
import java.util.*



class DocumentStatusBean : BaseBean() {

    var documents: ArrayList<DocumentBean> = ArrayList()

    val isAllDocumentsUploaded: Boolean
        get() {


            if (!documents.isEmpty()) {
                for (bean in documents) {
                    if (!bean.isUploaded || bean.documentStatus != AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                            && bean.documentStatus != AppConstants.DOCUMENT_STATUS_APPROVED) {
                        return false
                    }
                }
            }

            return true
        }
}
