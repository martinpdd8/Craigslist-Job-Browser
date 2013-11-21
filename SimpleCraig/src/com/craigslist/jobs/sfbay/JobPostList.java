package com.craigslist.jobs.sfbay;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to manage an ArrayList of JobPost objects.
 * Benefits readability and usability.
 * 
 * @author martinpdd8@gmail.com
 *
 */
public class JobPostList implements Parcelable {
	
	private List<JobPost> items;
	
	/**
	 * Creates a new JobPostList and initialises empty list.
	 */
	public JobPostList(){
		items = new ArrayList<JobPost>();
	}

	/**
	 * Creates a new JobPostList from a Parcel object.
	 * @param parcel The Parcel object to read from.
	 */
	public JobPostList(Parcel parcel){
		parcel.readTypedList(items, JobPost.CREATOR);
	}
	
	@Override
	public String toString(){
		String temp = "[";
		for (JobPost s : items){
			temp += " " + s.description + ", ";
		}
		temp += " ]";
		return temp;
	}
	
	/**
	 * @param position The position of the JobPost to get in the list.
	 * @return JobPost Returns the JobPost at "position", or null if the list was empty.
	 */
	public JobPost get(int position){
		if(!items.isEmpty()){
			return items.get(position);
		}
		return null;
	}
	
	/**
	 * Adds a JobPost to the end of the list.
	 * @param jobPost 
	 */
	public void add(JobPost jobPost){
		items.add(jobPost);
	}
	
	/**
	 * Adds a JobPost to the front of the list. 
	 * @param releaseItem
	 */
	public void addToFront(JobPost jobPost){
		items.add(0, jobPost);
	}
	
	/**
	 * @return size of list
	 */
	public int size(){
		return items.size();
	}
	
	/**	
	 * Removes a number of items from the end of this JobPostList.
	 * @param numberOfItemsToRemove : ...
	 */
	public void removeFromEnd(int numberOfItemsToRemove){
		items = items.subList(0, items.size() - numberOfItemsToRemove);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(items);
	}
	
	public static Creator<JobPostList> CREATOR = new Creator<JobPostList>() {
        public JobPostList createFromParcel(Parcel parcel) {
            return new JobPostList(parcel);
        }

        public JobPostList[] newArray(int size) {
            return new JobPostList[size];
        }
    };
	
}
