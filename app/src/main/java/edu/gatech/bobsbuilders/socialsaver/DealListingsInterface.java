package edu.gatech.bobsbuilders.socialsaver;

import com.parse.ParseGeoPoint;

import java.util.Date;

/**
 * Created by ${USER} on ${DATE}.
 */
public interface DealListingsInterface {

    String getObjectID();

    void setObjectID(String objectID);

    String getEmail();

    void setEmail(String email);

    String getItem();

    void setItem(String item);

    Number getMaxPrice();

    void setMaxPrice(Number maxPrice);

    Date getSaleEndDate();

    void setSaleEndDate(Date saleEndDate);

    String getFound();

    void setFound(String found);

    String getFoundLocation();

    void setFoundLocation(String foundLocation);

    ParseGeoPoint getGeoPoint();

    void setGeoPoint(ParseGeoPoint point);

}
