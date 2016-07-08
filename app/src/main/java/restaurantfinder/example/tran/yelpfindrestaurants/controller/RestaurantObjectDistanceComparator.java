package restaurantfinder.example.tran.yelpfindrestaurants.controller;

import java.util.Comparator;

import restaurantfinder.example.tran.yelpfindrestaurants.model.BusinessObjects;

/**
 * This class compares restaurant objects based on the distance computed from the Yelp Search API.
 * @author Todd Tran
 */
public class RestaurantObjectDistanceComparator implements Comparator<BusinessObjects> {

    @Override
    public int compare(BusinessObjects o1, BusinessObjects o2)
    {
        // TODO Auto-generated method stub
        Double d1 = (o1).getDistance();
        Double d2 = (o2).getDistance();

        return d2.compareTo(d1);
    }
}