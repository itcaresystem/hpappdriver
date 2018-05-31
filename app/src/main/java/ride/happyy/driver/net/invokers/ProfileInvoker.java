package ride.happyy.driver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.ProfileBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.ProfileParser;
import ride.happyy.driver.net.utils.WSConstants;



public class ProfileInvoker extends BaseInvoker {

    public ProfileInvoker() {
        super();
    }

    public ProfileInvoker(HashMap<String, String> urlParams,
                          JSONObject postData) {
        super(urlParams, postData);
    }

    public ProfileBean invokeProfileWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.PROFILE), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        ProfileBean profileBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return profileBean = null;
        } else {
            profileBean = new ProfileBean();
            ProfileParser profileParser = new ProfileParser();
            profileBean = profileParser.parseProfileResponse(wsResponseString);
            return profileBean;
        }
    }
}
