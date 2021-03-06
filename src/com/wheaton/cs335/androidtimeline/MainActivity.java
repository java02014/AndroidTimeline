package com.wheaton.cs335.androidtimeline;

import java.util.ArrayList;
import java.util.Arrays;

import storage.AndroidDBHelper;
import model.Timeline;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;

public class MainActivity extends Activity {
	
	private ListView timelineList;
	
	private ArrayList<Timeline> timelines;
	
	private ArrayList<String> timelineNames;
	
	private StableArrayAdapter adapter;
	
	private AndroidDBHelper database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		database = new AndroidDBHelper(getBaseContext());
		
		timelines = new ArrayList<Timeline>(Arrays.asList(database.getTimelines()));
		timelineNames = new ArrayList<String>();

		for(Timeline t : timelines)
			timelineNames.add(t.getName());
		
		timelineList = (ListView) findViewById(R.id.timelineListView);
		adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, timelineNames);
		timelineList.setAdapter(adapter);
		
		final Activity activity = this;
		timelineList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				Intent intent = new Intent(activity, TimelineDisplayActivity.class);

				intent.putExtra("TIMELINE", timelines.get(position));
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		switch(item.getItemId()) {
		case R.id.AddEvent:
			Intent addEve = new Intent(this, AddEvent.class);
			addEve.putExtra("timelines", timelines);
			startActivity(addEve);
			break;
		case R.id.AddCategory:
			Intent addCat = new Intent(this, AddCategory.class);
			addCat.putExtra("timelines", timelines);
			startActivity(addCat);
			break;
		}
		
		return true;
	}

}
