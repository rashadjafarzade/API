package api_automation.stepDefinition;


import api_automation.RequestBuilder.DummyAPIRequestBuilder;
import api_automation.utils.TestBase;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;


public class CrudOperations extends TestBase{
	String requestData;
	static Response response;
	String userID;
	Scenario scenario;	
	
	@Before
	public void keepScenario(Scenario scenario) {
		this.scenario=scenario;
	}
	

	@Given("User gets Employee List from Dummy Rest API")
	public void user_gets_employee_List_from_Dummy_Rest_API(){
		response =given().when().get(property.getProperty("DummyApiURI"));
		response.prettyPrint();
	}

	@Then("Validate if status code is {int}")
	public void validate_if_status_code_is(int statusCode) {
		Assert.assertEquals(statusCode, response.getStatusCode());
	}


	//User retrieve a single Employee Data from Dummy Rest API
	@Then("User retrieve a single Employee Data from Dummy Rest API")
	public void user_retrieve_a_single_Employee_Data_from_Dummy_Rest_API()  {
		response=given()
				.when()
				.get(property.getProperty("DummyApiURI"));
		response.prettyPrint();

		Map<String, Object> responseMap=JsonPath.read(response.asString(), "$.data");
		System.out.println(responseMap);


		responseMap.forEach((k, v)-> System.out.println("key: " + k+ " value" + v));
	}


	@Given("User creates request data with {string} , {double} , {int}")
	public void user_creates_request_data_with(String name, double salary, int age) throws JsonProcessingException {

		//Create request in Java object
		DummyAPIRequestBuilder reqBuilder=new DummyAPIRequestBuilder();
		reqBuilder.setName(name);
		reqBuilder.setAge(age);
		reqBuilder.setSalary(salary);
		//Convert object to string
		ObjectMapper obMap = new ObjectMapper();
		requestData=obMap.writerWithDefaultPrettyPrinter().writeValueAsString(reqBuilder);
		//write response to report
		scenario.write(requestData);
		scenario.embed(requestData.getBytes(), "application/json");
	}


	@When("User submits POST request to Dummy Rest API")
	public void user_submits_POST_request_to_Dummy_Rest_API() {
		      response=given()
							.contentType(ContentType.JSON)
							.body(requestData).
		         	  when()
		         	  	 	.post(property.getProperty("DummyApiURI"));
		 
		 String strResponse=response.prettyPrint();

			//write response to file
			scenario.write(strResponse);
	}
	

	@When("User validates if statusCode is {int}")
	public void user_validates_if_statusCode_is(int statusCode) {
	    Assert.assertEquals(statusCode, response.getStatusCode());
	}

	@Then("User retrieves userID from response")
	public void user_retrieves_userID_from_response() {
		
		 userID=JsonPath.read(response.asString(), "$.data.id").toString();
		 scenario.write("User ID::: "+userID);
	   
	}

	@Then("User deletes data with userID")
	public void user_deletes_data_with_userID() {
		Response resp=given()
				    .when()
				       .delete(property.getProperty("DummyApiURI")+"/"+userID);
		String strResponse=resp.prettyPrint();
		scenario.write(strResponse);
	}



}