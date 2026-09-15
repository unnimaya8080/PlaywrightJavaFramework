package tests;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;


public class PlaywrightAPITest1 {

    @Test
    public void loginTest()
    {
        //To create the Payload in key value pairs we are using HashMap
        HashMap<Object,Object> loginPayload=new HashMap<>();
        loginPayload.put("email","unnimaya8080@gmail.com");
        loginPayload.put("password","Qwerty@123");



        Playwright playwright= Playwright.create(); //Start playwright engine
        //Create a API request context and store it in the variable apiRequest
        APIRequestContext apiRequest = playwright.request().newContext();
        //post takes two arguments
        // 1st arg--url
        // 2nd arg: specify the options for your request(Data/ body, Headers, Query params, Timeout etc)
        APIResponse apiResponse=apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
                RequestOptions.create().setData(loginPayload));

        //Playwright Java, ok() is true when the response status is in the 200–299 range.
        Assert.assertTrue(apiResponse.ok());
        System.out.println(apiResponse.text());
        String token= JsonPath.read(apiResponse.text(),"$.token");
        System.out.println("success"+token);


        //create event
        //To create payload for create event request
        HashMap<Object,Object> createEventPayload = new HashMap<>();
        createEventPayload.put("title","EventHub Playwright API test");
        createEventPayload.put("description", "Test test");
        createEventPayload.put("category", "Workshop");
        createEventPayload.put("venue", "Test venue");
        createEventPayload.put("city", "Test city");
        createEventPayload.put("eventDate", "2026-08-19T10:50:00.000Z");
        createEventPayload.put("price", 100);
        createEventPayload.put("totalSeats", 60);


        APIResponse createEventResponse=apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/events"
        ,RequestOptions.create().setHeader("Authorization","Bearer "+token)
                        .setData(createEventPayload));

        //Assertion
        Assert.assertTrue(createEventResponse.ok(), "Response received successfully");

        //To get the id of the event
        int eventId=JsonPath.read(createEventResponse.text(),"$.data.id");
        System.out.println("event Id is " +eventId);



        //To get all events

        APIResponse getEventsResponse = apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setQueryParam("page","1").setQueryParam("limit","12")
                        .setHeader("Authorization","Bearer "+token));

        Assert.assertTrue(getEventsResponse.ok()," Get event response success");

        //TO validate events in the reponse contains the event just created

        //To retrive all evnts
        List<Integer> allEvents=JsonPath.read(getEventsResponse.text(),"$.data[*].id");
        System.out.println("Events before delecting"+allEvents);

        //Check if the eventId is there in the list
        Assert.assertTrue(allEvents.contains(eventId), "The list contains the created Event Id");


        //DELETE RESPONSE
        APIResponse deleteResponse = apiRequest.delete("https://api.eventhub.rahulshettyacademy.com/api/events/"+eventId
                ,RequestOptions.create().setHeader("Authorization","Bearer "+token));
        Assert.assertTrue(deleteResponse.ok());


        //To retrive all evnts post delete
        APIResponse postDeleteGetEventsResponse = apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setQueryParam("page","1").setQueryParam("limit","12")
                        .setHeader("Authorization","Bearer "+token));
        List<Integer> allEventsPostDelete=JsonPath.read(postDeleteGetEventsResponse.text(),"$.data[*].id");
        System.out.println("Events after deleting"+allEventsPostDelete);
        Assert.assertFalse(allEventsPostDelete.contains(eventId));













    }


}
