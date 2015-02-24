package edu.gatech.bobsbuilders.socialsaver;

public class UserListings implements Listings {
	private String name;
	private String userimage;
	private String currentrate;
    private String email;
    private String totalSales;
    private String objectID;

    public String getObjectID() { return objectID; }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getCurrentRate() {
        return currentrate;
    }

    public void setCurrentRate(String currentrate) {
        this.currentrate = currentrate;
    }

    public String getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(String totalSales) { this.totalSales = totalSales; }

	public String getUserImage() {
		return userimage;
	}

	public void setUserImage(String userimage) {
		this.userimage = userimage;
	}
}