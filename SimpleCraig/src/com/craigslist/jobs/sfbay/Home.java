package com.craigslist.jobs.sfbay;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.craigslist.jobs.sfbay.R;

/**
 * Main screen of the app. 
 * Downloads and displays a list of software/QA/DBA jobs located in the San Francisco Bay Area from craigslist.org.
 * URL set in Constants.java
 * 
 * @author martinpdd8@gmail.com
 *
 */
public class Home extends Activity implements PullToRefreshAttacher.OnRefreshListener {
	
	// List stuff to display a list of jobs
	protected JobPostList jobPostList;
	protected ListView jobListView;
	protected JobPostListAdapter adapter;
	
	// Pull to refresh functionality
	protected PullToRefreshLayout pullToRefreshLayout;
    protected PullToRefreshAttacher pullToRefreshAttacher;
    
    // Progress bar - indeterminate spinning wheel 
    protected ProgressBar progressBar;
    
    // Reference to self
	protected Activity activity;
    
	// Keeps track of whether or not data has been loaded
    protected boolean loaded;
    // Tracks if the app was opened offline this session
    protected boolean wasDisconnected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		
		// init
		jobPostList = new JobPostList();
		pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_layout);
		jobListView = (ListView) findViewById(R.id.list);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		activity = this;
		
		// savedInstanceState shouldn't be null if we're re-onCreate-ing from an orientation change
		if(savedInstanceState != null){
			JobPostList temp = savedInstanceState.getParcelable("jobPostList");
			if(temp != null){
				this.jobPostList = temp;
			}
			// loaded will be false anyway if value not found in Bundle
			loaded = savedInstanceState.getBoolean("loaded");
		}
		// If this is an initial launch, i.e. loaded is false
		// Download a fresh job list
		if(!loaded && isOnline(this)){
			new DownloadJobData(this).execute();
		}
		// If the device is offline
		else if(!loaded && !isOnline(this)) {
			LayoutManager.showNotConnectedText(this, progressBar);
		}
		// If the data has been loaded previously, initialise the UI again
		else {
			LayoutManager.initList(this, jobPostList, jobListView, adapter);
			LayoutManager.hideProgress(progressBar, jobListView);
		}
		
		pullToRefreshAttacher = PullToRefreshAttacher.get(this);
    	pullToRefreshLayout.setPullToRefreshAttacher(pullToRefreshAttacher, this);
	}
	
	 /**
     * Code to run when a refresh is triggered from a pull down.
     */
	@Override
	public void onRefreshStarted(View view) {
		if(isOnline(this)){
			new DownloadJobData(this).execute();
		}
		else {
			pullToRefreshAttacher.setRefreshComplete();
			wasDisconnected = true;
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		// save the downloaded data, save the world
		outState.putParcelable("jobPostList", jobPostList);
		// save fact that we have downloaded already
		outState.putBoolean("loaded", loaded);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.home, menu);
//		return true;
//	}
	
	/**
	 * 
	 * @param activity  Context for which to check.
	 * @return  True if device is connected to the internet. 
	 *          False if device has no internet connection.
	 */
	public static boolean isOnline(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
