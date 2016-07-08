package restaurantfinder.example.tran.yelpfindrestaurants.model;

import org.scribe.model.Token;
import org.scribe.builder.api.DefaultApi10a;

/**
 * This class provides the authentication for the yelp v2 search function used in this application.
 * @author Todd Tran
 */
public class YelpApi2 extends DefaultApi10a {
    @Override
    public String getAccessTokenEndpoint() { return null; }

    @Override
    public String getAuthorizationUrl(Token arg0) {
      return null;
    }

    @Override
    public String getRequestTokenEndpoint() {
      return null;
    }
}