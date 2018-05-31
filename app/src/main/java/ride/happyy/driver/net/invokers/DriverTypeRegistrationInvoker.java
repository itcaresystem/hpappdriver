package ride.happyy.driver.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.BasicParser;
import ride.happyy.driver.net.utils.WSConstants;

public class DriverTypeRegistrationInvoker extends BaseInvoker {

    public DriverTypeRegistrationInvoker() {
        super();
    }

    public DriverTypeRegistrationInvoker(HashMap<String, String> urlParams,
                                         JSONObject postData) {
        super(urlParams, postData);
    }

    public BasicBean invokeDriverTypeRegistrationWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.DRIVER_TYPE_REGISTRATION), WSConstants.PROTOCOL_HTTP, null, postData);

        String wsResponseString = webConnector.connectToPOST_service();

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
