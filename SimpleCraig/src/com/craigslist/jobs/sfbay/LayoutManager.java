package com.craigslist.jobs.sfbay;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.craigslist.jobs.sfbay.R;

/**
 * Class to hold calls which manipulate layouts and views. 
 * 
 * @author martinpdd8@gmail.com
 *
 */
public class LayoutManager {
	
	/**
	 * Sets up list adapter 
	 * @param activity  context
	 * @param jobPostList  The data to populate list view with
	 * @param jobListView  The ListView to populate
	 * @param adapter  The JobPostListAdapter to initialise
	 */
	protected static void initList(Activity activity, JobPostList jobPostList, ListView jobListView, JobPostListAdapter adapter){
		adapter = new JobPostListAdapter(activity, jobPostList);
		jobListView.setAdapter(adapter);
	}
	
	/**
	 * Hides indeterminate spinner and shows the list view
	 * @param progressBar  The ProgressBar to hide
	 * @param jobListView  The ListView to show
	 */
	protected static void hideProgress(ProgressBar progressBar, ListView jobListView){
		progressBar.setVisibility(View.GONE);
		jobListView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Shows indeterminate spinner and hides the list view
	 * @param progressBar  The ProgressBar to show
	 * @param jobListView  The ListView to hide
	 */	
	protected static void showProgress(ProgressBar progressBar, ListView jobListView){
		progressBar.setVisibility(View.VISIBLE);
		jobListView.setVisibility(View.GONE);
	}
	
	/**
	 * Displays connection error message in a toast popup
	 * @param activity  context
	 */
	protected static void showNotConnectedToast(Activity activity){
		Toast.makeText(activity, activity.getResources().getString(R.string.not_connected_message), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Shows a hidden TextView that displays a connectivity error message.
	 * @param activity context
	 * @param progressBar The ProgressBar to hide.
	 */
	protected static void showNotConnectedText(Activity activity, ProgressBar progressBar){
		TextView textView = (TextView) activity.findViewById(R.id.no_connection);
		textView.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
	}

}
