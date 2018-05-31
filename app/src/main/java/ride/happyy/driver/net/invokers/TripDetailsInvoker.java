package ride.happyy.driver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.TripBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.TripDetailsParser;
import ride.happyy.driver.net.utils.WSConstants;



public class TripDetailsInvoker extends BaseInvoker {

    public TripDetailsInvoker() {
        super();
    }

    public TripDetailsInvoker(HashMap<String, String> urlParams,
                              JSONObject postData) {
        super(urlParams, postData);
    }

    public TripBean invokeTripDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_DETAILS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        TripBean tripBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return tripBean = null;
        } else {
            tripBean = new TripBean();
            TripDetailsParser tripDetailsParser = new TripDetailsParser();
            tripBean = tripDetailsParser.parseTripDetailsResponse(wsResponseString);
            return tripBean;
        }
    }
}
