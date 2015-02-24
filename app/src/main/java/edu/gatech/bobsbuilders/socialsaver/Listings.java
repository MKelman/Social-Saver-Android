package edu.gatech.bobsbuilders.socialsaver;

/**
 * interface for the listings
 */
interface Listings {
    String getObjectID();

    void setObjectID(String objectID);

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    String getCurrentRate();

    void setCurrentRate(String currentrate);

    String getTotalSales();

    void setTotalSales(String totalSales);

    String getUserImage();

    void setUserImage(String userimage);
}
