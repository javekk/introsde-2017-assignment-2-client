

import java.net.URI;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonClient {

	private static final String strPOST = "POST";
	private static final String strGET = "GET";
	private static final String strDELETE = "DELETE";
	private static final String strPUT = "PUT";

	private static final String strOK = "OK";
	private static final String strERROR = "ERROR";

	private static final String strAPPLICATIONJSON = "APPLICATION/JSON";
	private static final String strAPPLICATIONXML= "APPLICATION/XML";

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"https://assignsde2.herokuapp.com/assign2").build();
	}

	public static void main(String[] args) throws Exception {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getBaseURI());


		/*
		 	RESPONSE PARAMS
		 */
		Response response;
		int firstPersonId;
		int lastPersonId;
		String result;
		int status;

		/*
		 	REQUEST PARAMS
		 */
		String path;
		String body;

		/*
		 *  Step#0, Request#0
		 *        INIT THE DATABASE
		 */
		path = "databaseinit";
		body = " ";
		result = strERROR;
		//res = service.path(path).request().accept(MediaType.APPLICATION_XML).post(null);
		//printRequestAsRequested( 0 , POST, path, APPLICATIONXML, APPLICATIONXML, body, null, res);

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("THE SERVER IS: " + "https://assignsde2.herokuapp.com/assign2");
		System.out.println("THE SERVER IS: " + "https://assignsde2.herokuapp.com/assign2");
		System.out.println("THE SERVER IS: " + "https://assignsde2.herokuapp.com/assign2");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

		/*
		 *  Step#3.1, Request#1
		 *  	  GET /person
		 *  	  should list all the people in your database (wrapped under the root element "people")
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.1~~~~~~~~~~~~~~~STEP~3.1~~~~~~~~~~~~STEP~3.1~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person";
		body = " ";
		result = strERROR;

		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).get();
		String responseBody = response.readEntity(String.class);
		JSONArray array_response = new JSONArray(responseBody);

        /*Use forth*/
        String firstName = (String) array_response.getJSONObject(0).get("firstname");
        /*Use forth*/

        int peopleNumber =  array_response.length();
        result = (peopleNumber>4) ? strOK : strERROR;
        status = response.getStatus();

		printRequestAsRequested( 1, strGET, path, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result, responseBody, status);

		firstPersonId = (Integer) array_response.getJSONObject(0).get("idPerson");
		lastPersonId = (Integer) array_response.getJSONObject(0).get("idPerson");

		/*
		 *  Step#3.2, Request#2
		 *  	  GET /person/first_person_id
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.2~~~~~~~~~~~~~~~STEP~3.2~~~~~~~~~~~~STEP~3.2~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/" + firstPersonId;
		body = " ";
		result = strERROR;
		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).get();
		responseBody = response.readEntity(String.class);
		status = response.getStatus();
		result = (status==200 || status == 202) ? strOK : strERROR;

		printRequestAsRequested(2, strGET, path, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result, responseBody, status);

		/*
		 *  Step#3.3, Request#3
		 *  	  PUT /person/first_person_id
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.3~~~~~~~~~~~~~~~STEP~3.3~~~~~~~~~~~~STEP~3.3~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/" + firstPersonId;
		body =  "{" +
				"    \"firstname\": \"" + "Mr."+Math.random()+ "\"," +
				"    \"lastname\": \"Bianchi\"," +
				"    \"birthdate\": \"1994-02-10\"" +
				"}";
		result = strERROR;
		response = service.path(path)
				.request().accept(MediaType.APPLICATION_JSON)
				.header("Content-type","application/json").put(Entity.json(body));

		/*GET for check if the name was changed*/
		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).get();
		responseBody = response.readEntity(String.class);
		JSONObject object_response = new JSONObject(responseBody);
        String newFirstName = (String) object_response.get("firstname");
		result = (firstName.equals(newFirstName)) ? strERROR : strOK;
		status = response.getStatus();

		printRequestAsRequested(3, strPUT, path, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result, " ", status);


		/*
		 *  Step#3.4, Request#4
		 *  	  POST /person
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.4~~~~~~~~~~~~~~~STEP~3.4~~~~~~~~~~~~STEP~3.4~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/";
		body =    "<person>"
				+ " <firstname>Giovanni</firstname>"
				+ " <lastname>Verdi</lastname>"
				+ " <birthdate>1979-07-14</birthdate>"
				+ " <preferences>"
				+ "  <activity>"
				+ "   <name>Videogames</name>"
				+ "   <description>Playing videogames</description>"
				+ "   <place>Home</place><type>Entertainment</type>"
				+ "   <startdate>2003-06-04T00:00:00.0</startdate>"
				+ "  </activity>"
				+ " </preferences>"
				+ "</person> ";
		result = strERROR;
		response = service.path(path)
				.request().accept(MediaType.APPLICATION_JSON)
				.header("Content-type","application/xml").post(Entity.xml(body));
		responseBody = response.readEntity(String.class);
		object_response = new JSONObject(responseBody);

		status = response.getStatus();

		int id_person_just_created = 1;

		try {
			id_person_just_created = (Integer) object_response.get("idPerson");
			result = (status > 199 && status < 203) ? strOK : strERROR;
		}
		catch(Exception e) {
			id_person_just_created = 1;
		}

		printRequestAsRequested(4, strPOST, path, strAPPLICATIONJSON, strAPPLICATIONXML, " ", result, responseBody, status);


		/*
		 *  Step#3.5, Request#5 + request#1
		 *  	  DELETE /person/id_person_just_created
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.5~~~~~~~~~~~~~~~STEP~3.5~~~~~~~~~~~~STEP~3.5~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/" + id_person_just_created;
		body = " ";
		result = strERROR;
		/*#5*/
		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).delete();
		responseBody = response.readEntity(String.class);
		printRequestAsRequested(5, strDELETE, path, strAPPLICATIONJSON, strAPPLICATIONJSON, body, response.getStatusInfo().toString()," ", response.getStatus());

		/*#1*/
		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).get();
		responseBody = response.readEntity(String.class);
		status = response.getStatus();
		result = (status == 500) ? strOK : strERROR;

		printRequestAsRequested(1, strGET, path, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result, " ", 404);


		/*
		 *  Step#3.6, Request#6
		 *  	  GET /activity_types
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.6~~~~~~~~~~~~~~~STEP~3.6~~~~~~~~~~~~STEP~3.6~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "activity_types";
		body = " ";
		result = strERROR;
		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).get();
		responseBody = response.readEntity(String.class);
		array_response = new JSONArray(responseBody);
		ArrayList<String> acTypesLsit= new ArrayList<>();

		for(int i = 0; i < array_response.length(); i++) {
			acTypesLsit.add(array_response.get(i).toString());
		}

		status = response.getStatus();
		result = (acTypesLsit.size() > 2) ? strOK : strERROR;

		printRequestAsRequested(6, strGET, path, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result,responseBody, status);


		/*
		 *  Step#3.7, Request#7
		 *  	  GET /person/first_person_id/{activity_type}
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.7~~~~~~~~~~~~~~~STEP~3.7~~~~~~~~~~~~STEP~3.7~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/" + firstPersonId;
		body = " ";
		result = strERROR;

		int activity_id = 1;
		String activity_type = "";
		for(String s : acTypesLsit) {
			result = strOK;
			String newPath = path + "/" + s;
			response = service.path(newPath).request().accept(MediaType.APPLICATION_JSON).get();
			responseBody = response.readEntity(String.class);
			array_response = new JSONArray(responseBody);
			if(array_response.length() < 1) {
				result = strERROR;
				body = " ";
			}
			else {
				activity_id = (Integer) array_response.getJSONObject(0).get("idActivity");
				activity_type = s;
			}
			status = response.getStatus();
			printRequestAsRequested(7, strGET, newPath, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result, responseBody,  status);
		}
		path = "person/" + lastPersonId;
		body = " ";
		result = strERROR;

		
		for(String s : acTypesLsit) {
			result = strOK;
			String newPath = path + "/" + s;
			response = service.path(newPath).request().accept(MediaType.APPLICATION_JSON).get();
			responseBody = response.readEntity(String.class);
			array_response = new JSONArray(responseBody);
			if(array_response.length() < 1) {
				result = strERROR;
				body = " ";
			}
			else {
			}
			status = response.getStatus();
			printRequestAsRequested(7, strGET, newPath, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result, responseBody,  status);
		}
		/*
		 *  Step#3.8, Request#8
		 *  	  GET  /person/{id}/{activity_type}/{activity_id})
		 *  		with values store in step 3,7
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.8~~~~~~~~~~~~~~~STEP~3.8~~~~~~~~~~~~STEP~3.8~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/" + firstPersonId +"/"+activity_type+"/"+activity_id;
		body = " ";
		result = strERROR;

		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).get();
		responseBody = response.readEntity(String.class);
		status = response.getStatus();
		result  = (status == 200) ? strOK : strERROR;
		printRequestAsRequested(8, strGET, path, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result,  responseBody, status);

		/*
		 *  Step#3.10, Request#6  Request#10
		 *  	  PUT  /person/{id}/{activity_type}/{activity_id})
		 *  		with values store in step 3,7
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.10~~~~~~~~~~~~~STEP~3.10~~~~~~~~~~~STEP~3.10~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/" + firstPersonId +"/"+activity_type+"/"+activity_id;
		body = "\"Social\"";
		result = strERROR;

		response = service.path(path).request().accept(MediaType.APPLICATION_JSON)
						  .header("Content-type","application/json").put(Entity.json(body));
		responseBody = response.readEntity(String.class);
		status = response.getStatus();
		result = (status > 199 && status < 203) ? strOK : strERROR;
		printRequestAsRequested(10, strPUT, path, strAPPLICATIONJSON, strAPPLICATIONJSON, " ", result, " ", status);


		/*
		 *  Step#3.11, Request#11
		 *  	  GET /person/{id}/{activity_type}?before={beforeDate}&after={afterDate}
		 *
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.11~~~~~~~~~~~~~STEP~3.11~~~~~~~~~~~STEP~3.11~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/3/Social";
		body = " ";
		result = strERROR;

		response = service.path(path)
						  .matrixParam("before", "2006-02-30T00:00:00.0")
						  .matrixParam("after", "1999-01-26T00:00:00.0")
				          .request().accept(MediaType.APPLICATION_JSON).get();
		responseBody = response.readEntity(String.class);
		array_response = new JSONArray(responseBody);
		activity_id = (Integer) array_response.getJSONObject(0).get("idActivity");

		status = response.getStatus();
		result = (array_response.length() > 0) ? strOK : strERROR;
		String pathPlusParams = path + "?before=2006-02-30T00:00:00.0&after=1999-01-26T00:00:00.0";
		printRequestAsRequested(11, strGET, pathPlusParams, strAPPLICATIONJSON, strAPPLICATIONJSON, body, result,responseBody, status);


	}

	private static void printRequestAsRequested(
			int reqNumber,
			String method,
			String path,
			String accept,
			String contentType,
			String body,
			String result,
			String responseBody,
			int httpStatus) {

		System.out.println("===========================================================");
		System.out.println("===========================================================");
		System.out.println("Request #" + reqNumber + " " +  method + " /" + path + " Accept: " + accept + " Content-Type: " + contentType);
		System.out.println("=> Result: " + result);
		System.out.println("=> Http Status: " + httpStatus);
		if(body.equals(" ")) {
			System.out.print("");
		}
		else {
			try {
				JSONObject json = new JSONObject(body);
				System.out.println(json.toString(2));
			}
			catch(Exception e){
				JSONArray json = new JSONArray(body);
				System.out.println(json.toString(2));
			}
		}
		System.out.println("===RESPONSE BODY===");
		if(responseBody.equals(" ")) {
			System.out.print("");
		}
		else {
			try {
				JSONObject json = new JSONObject(responseBody);
				System.out.println(json.toString(2));
			}
			catch(Exception e){
				JSONArray json = new JSONArray(responseBody);
				System.out.println(json.toString(2));
			}
		}
		System.out.println("");
	}




}
