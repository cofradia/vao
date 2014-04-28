package com.cofradia.vao.listeners;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.cofradia.vao.EventListTask;


public class EventListEndlessScrollListener implements OnScrollListener {
    private int visibleThreshold = 2;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private boolean first_time;
	private EventListTask eventListTask;

    public EventListEndlessScrollListener(Context context) {
    	super();
    	first_time=true;
    	eventListTask = new EventListTask(context);
	}

    public EventListEndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
	
		   if (loading) {
	            if (totalItemCount > previousTotal) {
	                loading = false;
	                previousTotal = totalItemCount;
	                currentPage++;
	            }
	        }
	        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
	            // I load the next page of gigs using a background task,
	            // but you can call any function here.
	            eventListTask.doEventList(currentPage + 1,first_time);
	            first_time=false;
	            loading = true;
	        }
	        
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

}
