package ride.happyy.driver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.HelpBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.HelpParser;
import ride.happyy.driver.net.utils.WSConstants;


public class HelpInvoker extends BaseInvoker {

    public HelpInvoker() {
        super();
    }

    public HelpInvoker(HashMap<String, String> urlParams,
                       JSONObject postData) {
        super(urlParams, postData);
    }

    public HelpBean invokeHelpWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.HELP_PAGE), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        HelpBean helpBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return helpBean = null;
        } else {
            helpBean = new HelpBean();
            HelpParser helpParser = new HelpParser();
            helpBean = helpParser.parseHelpResponse(wsResponseString);
            return helpBean;
        }
    }
}
