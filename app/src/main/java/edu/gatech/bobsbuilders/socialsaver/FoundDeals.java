package edu.gatech.bobsbuilders.socialsaver;

import com.parse.ParseGeoPoint;

import java.util.Date;

/**
 * Created by Mitchell on 2/25/15.
 */
class FoundDeals {

    private String objectID, email, item, found, foundLocation;
    private Date saleEndDate;
    private Number maxPrice;
    private ParseGeoPoint point;

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Number getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Number maxPrice) {
        this.maxPrice = maxPrice;
    }

    /*
    public Date getSaleEndDate() {
        return saleEndDate;
    }

    public void setSaleEndDate(Date saleEndDate) {
        this.saleEndDate = saleEndDate;
    }
    */

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public String getFoundLocation() {
        return foundLocation;
    }

    public void setFoundLocation(String foundLocation) {
        this.foundLocation = foundLocation;
    }

    public ParseGeoPoint getGeoPoint() {
        return point;
    }

    public void setGeoPoint(ParseGeoPoint point) {
        this.point = point;
    }

}
