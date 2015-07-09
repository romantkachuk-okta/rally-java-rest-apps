//This code toggles Custom Text field value on a User from empty to current milliseconds, and from a non-empty value to empty

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.UpdateResponse;
import java.util.Date;


public class ClearCustomUserTextField{

    private static final String SERVER_URL = "https://rally1.rallydev.com";
    private static final String API_KEY = "_cUi7..."; //use your ApiKey

    private static final String TEXT_FIELD = "c_Text"; // Name of the field to change

    private static final String USER_REF = "https://rally1.rallydev.com/slm/webservice/v2.0/user/34496097048"; // Referece to a User

    public static void main(String[] args) throws IOException, URISyntaxException {
        ClearCustomUserTextField.fillField();
        ClearCustomUserTextField.clearField();
    }

    private static RallyRestApi createRestApi() throws URISyntaxException {
        RallyRestApi restApi = new RallyRestApi(new URI(SERVER_URL), API_KEY);
        restApi.setApplicationName("ClearCustomUserTextField");

        return restApi;
    }

    public static void clearField() throws URISyntaxException, IOException {
        System.out.println("Clearing the value...");

        RallyRestApi restApi = createRestApi();
        try {
            update(restApi, USER_REF, "");
        } finally {
            restApi.close();
        }
    }

    public static void fillField() throws URISyntaxException, IOException {

        String newComment = new Date().getTime()+""; //converting long to string
        System.out.println("Updating value to: " + newComment);

        RallyRestApi restApi = createRestApi();
        try {
            update(restApi, USER_REF, newComment);
        } finally {
            restApi.close();
        }
    }

    private static void update(RallyRestApi restApi, String userRef, String comment ) throws URISyntaxException,IOException {
        JsonObject userUpdate = new JsonObject();
        userUpdate.addProperty(TEXT_FIELD, comment);
        UpdateRequest updateRequest = new UpdateRequest(userRef,userUpdate);
        UpdateResponse updateResponse = restApi.update(updateRequest);

        if (updateResponse.wasSuccessful()) {
            System.out.println("Successfully updated user");
        } else {
            System.out.println("Error occurred attempting to update user");
            String[] errorList;
            errorList = updateResponse.getErrors();
            for (int e=0;e<errorList.length;e++) {
                System.out.println(errorList[e]);
            }
        }
    }
}
