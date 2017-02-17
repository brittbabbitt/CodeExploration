package serviceOpsUtils;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;

public class PostRequest {

	private String url;
	private String body;
	private ValidatableResponse response;

	public PostRequest(String url, String body) {
		this.url = url;
		this.body = body;
		postRequestResponse();
	}

	private void postRequestResponse() {

		response = given().contentType("application/json").body(body).when().post(url).then();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ValidatableResponse getResponse(){
		return response;
	}


}
