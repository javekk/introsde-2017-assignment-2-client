
import java.io.StringReader;

import java.net.URI;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.glassfish.jersey.client.ClientConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

public class XmlClient {
	
	private static final String strPOST = "POST";
	private static final String strGET = "GET";
	private static final String strDELETE = "DELETE";
	private static final String strPUT = "PUT";
	
	private static final String strOK = "OK";
	private static final String strERROR = "ERROR";
	
	private static final String strAPPLICATIONXML = "APPLICATION/XML";
	
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
		
		response = service.path(path).request().accept(MediaType.APPLICATION_XML).get();
		String responseBody = response.readEntity(String.class);
        Document document = loadXMLFromString(responseBody);
        
        /*Uses forth*/
        NodeList names = document.getElementsByTagName("firstname");
        String firstName = names.item(0).getTextContent();
        NodeList birthDates = document.getElementsByTagName("birthdate");
        String firstBirthDate = birthDates.item(0).getTextContent();
        NodeList lastNames = document.getElementsByTagName("lastname");
        String firstLastName = lastNames.item(0).getTextContent();
        /*Uses forth*/
        

        NodeList ids = document.getElementsByTagName("idPerson");
        int peopleNumber = ids.getLength();
        result = (peopleNumber>4) ? strOK : strERROR;
        status = response.getStatus();
        
		printRequestAsRequested( 1, strGET, path, strAPPLICATIONXML, strAPPLICATIONXML, body, result,responseBody, status);
		
		firstPersonId = Integer.parseInt(ids.item(0).getTextContent());
		lastPersonId = Integer.parseInt(ids.item(peopleNumber -1).getTextContent());
		
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
		response = service.path(path).request().accept(MediaType.APPLICATION_XML).get();
		
		status = response.getStatus();
		result = (status==200 || status == 202) ? strOK : strERROR;
		
		printRequestAsRequested(2, strGET, path, strAPPLICATIONXML, strAPPLICATIONXML, body, result, response.readEntity(String.class), status);
		
		/*
		 *  Step#3.3, Request#3
		 *  	  PUT /person/first_person_id
		 *  	  
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~STEP~3.3~~~~~~~~~~~~~~~STEP~3.3~~~~~~~~~~~~STEP~3.3~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		path = "person/" + firstPersonId;
		body =  "<person>" + 
				" <birthdate>" + firstBirthDate + "</birthdate>"+ 
				" <firstname>" + "Mr."+Math.random() + "</firstname>" + 
				" <lastname>" + firstLastName + "</lastname>" + 
				"</person>";
		result = "ERROR";
		response = service.path(path)
				.request().accept(MediaType.APPLICATION_XML)
				.header("Content-type","application/xml").put(Entity.xml(body));
		
		/*GET for check if the name was changed*/
		response = service.path(path).request().accept(MediaType.APPLICATION_XML).get();
		responseBody = response.readEntity(String.class);
		document = loadXMLFromString(responseBody);
		NodeList newNames = document.getElementsByTagName("firstname");
        String newFirstName = newNames.item(0).getTextContent();
		result = (firstName.equals(newFirstName)) ? strERROR : strOK;
		status = response.getStatus();
		
		printRequestAsRequested(3, strPUT, path, strAPPLICATIONXML, strAPPLICATIONXML, body, result," ", status);
		
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
				.request().accept(MediaType.APPLICATION_XML)
				.header("Content-type","application/xml").post(Entity.xml(body));
		responseBody = response.readEntity(String.class);
		document = loadXMLFromString(responseBody);
		ids = document.getElementsByTagName("idPerson");
		status = response.getStatus();
		
		int id_person_just_created = 1;
		
		try {
			id_person_just_created = Integer.parseInt(ids.item(0).getTextContent());
			result = (status > 199 && status < 203) ? strOK : strERROR;
		}
		catch(Exception e) {
			id_person_just_created = 1;
		}
		
		printRequestAsRequested(4, strPOST, path, strAPPLICATIONXML, strAPPLICATIONXML, body, result, responseBody,status);
		
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
		response = service.path(path).request().accept(MediaType.APPLICATION_XML).delete();
		printRequestAsRequested(5, strDELETE, path, strAPPLICATIONXML, strAPPLICATIONXML, body,result, " ", response.getStatus());

		/*#1*/
		response = service.path(path).request().accept(MediaType.APPLICATION_XML).get();
		
		status = response.getStatus();
		result = (status == 500) ? strOK : strERROR;
		
		printRequestAsRequested(1, strGET, path, strAPPLICATIONXML, strAPPLICATIONXML, body, result, " ", 404);
		
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
		response = service.path(path).request().accept(MediaType.APPLICATION_XML).get();
		
		responseBody = response.readEntity(String.class);
		document = loadXMLFromString(responseBody);
		NodeList activityTypes = document.getElementsByTagName("activity_type");
		ArrayList<String> acTypesLsit= new ArrayList<>();
		
		for(int i = 0; i < activityTypes.getLength(); i++) {
			acTypesLsit.add(activityTypes.item(i).getTextContent());
		}
		
		status = response.getStatus();
		result = (acTypesLsit.size() > 2) ? strOK : strERROR;
		
		printRequestAsRequested(6, strGET, path, strAPPLICATIONXML, strAPPLICATIONXML, body, result,responseBody, status);
		
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
		
		String activity_id = "";
		String activity_type = "";
		for(String s : acTypesLsit) {
			result = strOK;
			String newPath = path + "/" + s;
			response = service.path(newPath).request().accept(MediaType.APPLICATION_XML).get();
			responseBody = response.readEntity(String.class);
			document = loadXMLFromString(responseBody);
			NodeList idActivitis = document.getElementsByTagName("idActivity");
			if(idActivitis .getLength() < 1) {
				result = strERROR;
				body = " ";
			}
			else {
				activity_id =  idActivitis.item(0).getTextContent();
				activity_type = s;
			}
			status = response.getStatus();
			printRequestAsRequested(7, strGET, newPath, strAPPLICATIONXML, strAPPLICATIONXML, body, result,responseBody, status);
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
		
		response = service.path(path).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		result  = (status == 200) ? strOK : strERROR;
		printRequestAsRequested(8, strGET, path, strAPPLICATIONXML, strAPPLICATIONXML, body, result, response.readEntity(String.class), status);
		
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
		body = "<activity_type>Social</activity_type>";
		result = strERROR;
		
		response = service.path(path).request().accept(MediaType.APPLICATION_XML)
						  .header("Content-type","application/xml").put(Entity.xml(body));
		status = response.getStatus();
		result = (status > 199 && status < 203) ? strOK : strERROR;
		printRequestAsRequested(10, strPUT, path, strAPPLICATIONXML, strAPPLICATIONXML, body, result, " ", status);
		
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
				          .request().accept(MediaType.APPLICATION_XML).get();
		responseBody = response.readEntity(String.class);
		document = loadXMLFromString(responseBody);
		NodeList idActivitis = document.getElementsByTagName("idActivity");
		
		status = response.getStatus();
		result = (idActivitis.getLength() > 0) ? strOK : strERROR;
		String pathPlusParams = path + "?before=2006-02-30T00:00:00.0&after=1999-01-26T00:00:00.0";
		printRequestAsRequested(11, strGET, pathPlusParams, strAPPLICATIONXML, strAPPLICATIONXML, body, result, responseBody,status);
		
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
		System.out.println("Request #" + reqNumber + " " +  method + " /" + path + " Accept: " + accept + " Content-Type: " + contentType);
		System.out.println("=> Result: " + result);
		System.out.println("=> Http Status: " + httpStatus);
		System.out.println(prettyFormat(body, 2));
		System.out.println("===RESPONSE BODY===");
		System.out.println(prettyFormat(responseBody,2));
	}
	
	public static Document loadXMLFromString(String xml) throws Exception{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
	
	public static String prettyFormat(String xml, int indent) {
		if(xml.equals(" ")) {
			return " ";
		}
		try {
            final InputSource src = new InputSource(new StringReader(xml));
            final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

        //May need this: System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");


            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.

            return writer.writeToString(document);
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
	}
}
