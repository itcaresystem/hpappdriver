package ride.happyy.driver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.IssueListBean;
import ride.happyy.driver.net.ServiceNames;
import ride.happyy.driver.net.WebConnector;
import ride.happyy.driver.net.parsers.IssueListParser;
import ride.happyy.driver.net.utils.WSConstants;



public class IssueListInvoker extends BaseInvoker {

    public IssueListInvoker() {
        super();
    }

    public IssueListInvoker(HashMap<String, String> urlParams,
                            JSONObject postData) {
        super(urlParams, postData);
    }

    public IssueListBean invokeIssueListWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.ISSUE_LIST),
                WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        IssueListBean issueListBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return issueListBean = null;
        } else {
            issueListBean = new IssueListBean();
            IssueListParser issueListParser = new IssueListParser();
            issueListBean = issueListParser.parseIssueListResponse(wsResponseString);
            return issueListBean;
        }
    }
}
