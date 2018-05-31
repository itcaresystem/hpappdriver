package ride.happyy.driver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.RequestDetailsBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.RequestDetailsParser;
import ride.happyy.driver.net.utils.WSConstants;



public class RequestDetailsInvoker extends BaseInvoker {

    public RequestDetailsInvoker() {
        super();
    }

    public RequestDetailsInvoker(HashMap<String, String> urlParams,
                                 JSONObject postData) {
        super(urlParams, postData);
    }

    public RequestDetailsBean invokeRequestDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.REQUEST_DETAILS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        RequestDetailsBean requestDetailsBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return requestDetailsBean = null;
        } else {
            requestDetailsBean = new RequestDetailsBean();
            RequestDetailsParser requestDetailsParser = new RequestDetailsParser();
            requestDetailsBean = requestDetailsParser.parseRequestDetailsResponse(wsResponseString);
            return requestDetailsBean;
        }
    }
}
