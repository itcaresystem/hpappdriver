package ride.happyy.driver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.AccessibilityBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.DriverAccessibilityParser;
import ride.happyy.driver.net.utils.WSConstants;

public class DriverAccessibilityInvoker extends BaseInvoker {

    public DriverAccessibilityInvoker() {
        super();
    }

    public DriverAccessibilityInvoker(HashMap<String, String> urlParams,
                                      JSONObject postData) {
        super(urlParams, postData);
    }

    public AccessibilityBean invokeDriverAccessibilityInvokerWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.DRIVER_ACCESSIBILITY_FETCH), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        AccessibilityBean accessibilityBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return accessibilityBean = null;
        } else {
            accessibilityBean = new AccessibilityBean();
            DriverAccessibilityParser driverAccessibilityParser = new DriverAccessibilityParser();
            accessibilityBean = driverAccessibilityParser.parseDriverAccessibilityResponse(wsResponseString);
            return accessibilityBean;
        }
    }
}
