package com.craigslist.jobs.sfbay;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

/**
 * AsyncTask to download and parse html info from craigslist.org on a background thread.
 * Craigslist url is set in Constants.java
 * 
 * @author martinpdd8@gmail.com
 *
 */
public class DownloadJobData extends AsyncTask<Void, Void, Void> {
	
	private Document doc;
	private Home home;
	
	/**
	 * Creates a DownloadJobData AsyncTask.
	 * @param home Calling activity 
	 */
	public DownloadJobData(Home home){
		super();
		this.home = home;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		doc = downloadPage(Constants.URL);
		return null;
	}

	@Override
	protected void onPostExecute(Void a) {
		parse(doc);
		LayoutManager.initList(home, home.jobPostList, home.jobListView, home.adapter);
		LayoutManager.hideProgress(home.progressBar, home.jobListView);
		// If loaded is true, we can assume this is a refresh, like a boss
		if(home.loaded || home.wasDisconnected){
			home.pullToRefreshAttacher.setRefreshComplete();
		}
        home.loaded = true;
        home.wasDisconnected = false;
	}

	/**
	 * 
	 * @param urlString  the target url to download
	 * @return  The Document object containing the downloaded html info for target url
	 */
	private static Document downloadPage(String urlString) {
        try {
         	Document html = Jsoup.connect(urlString).get();
        	 return html;
        } catch (IOException e) {
        	 e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * Give it a string, and it will remove the first "img" and the first "map" from it.
	 * @param str The string to cleanse.
	 * @return The input String "str", minus the words "img" and "map" if they were present. 
	 */
	private String cleanse(String str){
		final String img = "img";
		final String map = "map";
		final String pic = "pic";
		// If it doesn't contain either of these words, bail!
		if(!str.contains(img) && !str.contains(map) && !str.contains(pic)){
			return str;
		}
		// Otherwise remove them
		return str.replace(img, "").replace(map, "").replace(pic, "");
	}
	
	/**
     * Parses the required fields from the downloaded Document 
     */
    private void parse(Document doc){  
    	if(doc != null){
    		Elements urls = doc.select("div.content");

    		for (Element e : urls){
   			 	Elements elems = e.select("p.row");
   			 	for (Element ee : elems){
   			 		// Create new job with parsed info
   			 		JobPost job = new JobPost(cleanse(ee.select("a").text()), cleanse(ee.select("span.pnr").text()), ee.select("span.date").text());
   			 		// Schtick 'er on the end of the list
   			 		home.jobPostList.add(job);
   			 	}
    		}
    	}
    }
}