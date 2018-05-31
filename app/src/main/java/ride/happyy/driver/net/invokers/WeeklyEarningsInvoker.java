package ride.happyy.driver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.WeeklyEarningsBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.WeeklyEarningsParser;
import ride.happyy.driver.net.utils.WSConstants;



public class WeeklyEarningsInvoker extends BaseInvoker {

    public WeeklyEarningsInvoker() {
        super();
    }

    public WeeklyEarningsInvoker(HashMap<String, String> urlParams,
                                 JSONObject postData) {
        super(urlParams, postData);
    }

    public WeeklyEarningsBean invokeWeeklyEarningsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.WEEKLY_EARNINGS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        WeeklyEarningsBean weeklyEarningsBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return weeklyEarningsBean = null;
        } else {
            weeklyEarningsBean = new WeeklyEarningsBean();
            WeeklyEarningsParser weeklyEarningsParser = new WeeklyEarningsParser();
            weeklyEarningsBean = weeklyEarningsParser.parseWeeklyEarningsResponse(wsResponseString);
            return weeklyEarningsBean;
        }
    }
}
