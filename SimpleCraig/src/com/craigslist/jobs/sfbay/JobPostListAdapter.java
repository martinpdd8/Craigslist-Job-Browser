package com.craigslist.jobs.sfbay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.craigslist.jobs.sfbay.R;



/**
 * A custom list adapter class to handle the layout "list_item" and input data from a JobPostList.
 * 
 * @author martinpdd8@gmail.com
 *
 */
final class JobPostListAdapter extends BaseAdapter {
	
	  private final Context context;
	  private JobPostList items;

	  /**
	   * Creates a new JobPostListAdapter.
	   * @param context 
	   * @param items 
	   */
	  public JobPostListAdapter(Context context, JobPostList items) {
	    this.context = context;
	    this.items = items;
	  }

	  @Override 
	  public View getView(int position, View view, ViewGroup parent) {
		  ViewHolder holder;
		  if (view == null) {
			  view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
	     	  holder = new ViewHolder();
	     	  holder.description = (TextView) view.findViewById(R.id.description);
	     	  holder.location = (TextView) view.findViewById(R.id.location);
	     	  holder.date = (TextView) view.findViewById(R.id.date);
	     	  view.setTag(holder);
		  } else {
			  holder = (ViewHolder) view.getTag();
		  }

		  // Get the image URL for the current position.
		  JobPost job = getItem(position);
		  if(job != null){
			  holder.description.setText(job.description);
			  holder.location.setText(job.location);
	    	  holder.date.setText(job.date);
		  }
		  return view;
	  }

	  @Override 
	  public int getCount() {
		  return items.size();
	  }

	  @Override 
	  public JobPost getItem(int position) {
		  return items.get(position);
	  }

	  @Override 
	  public long getItemId(int position) {
		  return position;
	  }

	  static class ViewHolder {
		  TextView description;
		  TextView location;
		  TextView date;
	  }
	}
