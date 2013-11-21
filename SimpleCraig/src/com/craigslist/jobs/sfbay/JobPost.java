package com.craigslist.jobs.sfbay;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to represent a Craigslist job post.
 * 
 * @author martinpdd8@gmail.com
 *
 */
public class JobPost implements Parcelable {

	String description; // job description
	String location; // job location
	String date; // date posted
	
	/**
	 * Create job object
	 * @param desc  job description
	 * @param loc  job location
	 * @param date  date job was posted
	 */
	public JobPost(String desc, String loc, String date){
		this.description = desc;
		this.location = loc;
		this.date = date;
	}
	
	/**
	 * Creates a JobPost from a Parcel object.
	 * @param parcel  Parcel to load job description, location and date from.
	 */
	public JobPost(Parcel parcel){
		this.description = parcel.readString();
		this.location = parcel.readString();
		this.date  = parcel.readString();
	}
	@Override
	public String toString(){
		return date + ", " +  description + ", " + location;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(description);
		dest.writeString(location);
		dest.writeString(date);
	}
	
	public static Creator<JobPost> CREATOR = new Creator<JobPost>() {
        public JobPost createFromParcel(Parcel parcel) {
            return new JobPost(parcel);
        }

        public JobPost[] newArray(int size) {
            return new JobPost[size];
        }
    };
	
}
