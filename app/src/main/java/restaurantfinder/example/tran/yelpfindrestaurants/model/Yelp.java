package restaurantfinder.example.tran.yelpfindrestaurants.model;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Class that allows the user to make a search using the yelp API
 */
public class Yelp {
    /**
     * Manages with the request and retrieval of tokens.
     */
    private OAuthService mService;
    /**
     * an object to perform authentication
     */
    private Token mAccessToken;

    /**
    * Setup the Yelp API OAuth credentials.
    * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
    * @param consumerKey Consumer key
    * @param consumerSecret Consumer secret
    * @param token Token
    * @param tokenSecret Token secret
    */
    public Yelp(String consumerKey, String consumerSecret, String token, String tokenSecret)
    {
        this.mService = new ServiceBuilder().provider(YelpApi2.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
        this.mAccessToken = new Token(token, tokenSecret);
    }

    /**
    * Search with term and location.
    *
    * @param term Search term
    * @param latitude Latitude
    * @param longitude Longitude
    * @param limitSearchValue number of businesses
    * @param sortValue sort type.
    * @return JSON string response
    */
    public String search(String term, double latitude, double longitude, Integer limitSearchValue, String sortValue)
    {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("ll", latitude + "," + longitude);
        request.addQuerystringParameter("limit", limitSearchValue.toString());
        request.addQuerystringParameter("sort", sortValue);
        this.mService.signRequest(this.mAccessToken, request);
        Response response = request.send();
        return response.getBody(); // returns the search results.
    }
}
