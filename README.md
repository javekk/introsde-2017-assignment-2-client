# introsde-2017-assignment-2-client

## 1. Identification
* Perini Raffaele 196339
* raffaele.perini@studenti.unitn.it
* __My Partner__: Giovanni Rafael Vuolo
* __My Server URL__: https://intro2sdeass2.herokuapp.com/sdelab/
* __His Server URL__: https://assignsde2.herokuapp.com/assign2/
* __My Git Repo__: https://github.com/javekk/introsde-2017-assignment-2-server
* __His Git Repo__: https://github.com/chany93/introsde-2017-assignment-2-server

## 2. Project
This is the client for the server <_His Server URL_>. In this client are implemented all the steps required.

#### 2.1. Code
The project is structured as follow:
* __ivy.xml__ : it is used for handling the dependencies
* __build.xml__ : contains the ant script for running the Project
* __src__ : contains the two clients (__json__ and __xml__) which perform the tasks described in the next paragrafs. These classes implement the http requests for the server.
#### 2.2. Task
The two clients do the following tasks both in __JSON__ and __XML__ :
* __Step 3.1__. Send (__GET BASE_URL/person__). Calculate how many people are in the response. If more than 4, result is OK, else is ERROR (less than 5 persons). Save into a variable id of the first person (first_person_id) and of the last person (last_person_id)
* __Step 3.2__. Send (__GET BASE_URL/person/{first_person_id}__) for first_person_id. If the responses for this is 200 or 202, the result is OK.
* __Step 3.3__. Send (__PUT__BASE_URL/person/{first_person_id}__)for first_person_id changing the firstname. If the responses has the name changed, the result is OK. It uses a new name created from a random number.
* __Step 3.4__. Send (__POST BASE_URL/person__) to create a new person (with one activity preference) using an XML. Store the id of the new person. If the answer is 201(or 202, or 200) with a person in the body who has an ID, the result is OK.
* __Step 3.5__. Send (__DELETE BASE_URL/person({id}__) for the person you have just created. Then send (__GET BASE_URL/person/{id}__) with the id of that person. If the answer is 404, your result is OK.
* __Step 3.6__. Send the (__GET BASE_URL/activity_types__ ). If response contains more than 2 activity_types - result is OK, else is ERROR (less than 3 activity_types ). Save all activity Types into array (activity_types)
* __Step 3.7__. Send (__GET BASE_URL/person/{id}/{activity_type}__) for the first person you obtained at the beginning (first_person_id) and the last person (last_person_id), and for each activity type from activity_types. If no response has at least one activity the result is ERROR (no data at all) else result is OK. Store one activity_id and one activity_type.
* __Step 3.8__. Send (__GET BASE_URL/person/{id}/{activity_type}/{activity_id}__) for the stored activity_id and activity_type. If the response is 200, result is OK, else is ERROR.
* __Step 3.10__. Send (__PUT /person/{id}/{activity_type}/{activity_id}__) using the {activity_id} or the activity created in the previous step and updating the value at will. Then check that the value was updated. If it was, result is OK, else is ERROR
* __Step 3.11__. Send (__GET /person/{id}/{activity_type}?before={beforeDate}&after={afterDate}__)for an activity_type, before and after dates given by your fellow student (who implemented the server). If status is 200 and there is at least one activity in the body, result is OK, else is ERROR

## 3. Execution
Run the following for the execution

  ```
   ant execute.client
  ```
it will run the program and it will create the __client-server-json.log__ and the __client-server-xml.log__
## 4. Additional Notes
Actually the step#3.9 is not implemented
