package ride.happyy.driver.net.invokers;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.BasicParser;
import ride.happyy.driver.net.utils.WSConstants;

public class DocumentUploadInvoker extends BaseInvoker {

    public DocumentUploadInvoker() {
        super();
    }

    public DocumentUploadInvoker(HashMap<String, String> urlParams,
                                 JSONObject postData) {
        super(urlParams, postData);

    }

    public BasicBean invokeDocumentUploadWS(ArrayList<String> fileList) {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.DOCUMENT_UPDATE),
                WSConstants.PROTOCOL_HTTP, null, postData, fileList);

        String wsResponseString = webConnector.connectToMULTIPART_POST_service("document_update");

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        BasicBean basicBean = null;
        if (wsResponseString.equals("")) {

            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            BasicParser basicParser = new BasicParser();
            basicBean = basicParser.parseBasicResponse(wsResponseString);
            return basicBean;
        }
    }
}
