package ride.happyy.driver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.RatingDetailsBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.RatingDetailsParser;
import ride.happyy.driver.net.utils.WSConstants;



public class RatingDetailsInvoker extends BaseInvoker {

    public RatingDetailsInvoker() {
        super();
    }

    public RatingDetailsInvoker(HashMap<String, String> urlParams,
                                JSONObject postData) {
        super(urlParams, postData);
    }

    public RatingDetailsBean invokeRatingDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.RATING_DETAILS),
                WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        RatingDetailsBean ratingDetailsBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return ratingDetailsBean = null;
        } else {
            ratingDetailsBean = new RatingDetailsBean();
            RatingDetailsParser ratingDetailsParser = new RatingDetailsParser();
            ratingDetailsBean = ratingDetailsParser.parseRatingDetailsResponse(wsResponseString);
            return ratingDetailsBean;
        }
    }
}
