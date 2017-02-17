package serviceOpsUtils;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


public class GetRequest {
    private String url = null;
    private Response response = null;
    private Headers headers = null;
    private Map<String,String>  cookies = null;
    private String statusLine = null;
    private int statusCode = 0;

    //Constructor
    public GetRequest(String url){
        this.url = url;
    }

    /*
    @param: http api call in String form
    @return: true if object was received and stored; false if not
    @description: gets the request with a url and passes it
    to setProperties to parse the response for later use
     */
    public boolean getResponse(String url) {
        Response res =
                when()
                        .get(url)
                        .then()
                        .extract()
                        .response();

        if(res.getStatusCode() == 200) {
            setProperties(res);
            return true;
        }else{
            //TODO: Log and reset
            return false;
        }
    }

    private void setProperties(Response res){
        this.response = res;
        this.headers = res.getHeaders();
        this.cookies = res.getCookies();
        this.statusCode = res.getStatusCode();
        this.statusLine = res.getStatusLine();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Headers getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
