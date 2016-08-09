package com.kong.parallax;

import com.kong.parallax.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ParallaxListView listview;
	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (ParallaxListView) findViewById(R.id.listview);
		
		listview.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		
		View headerView = View.inflate(this,R.layout.layout_header, null);
		ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
		listview.setParallaxImageView(imageView);
		
		listview.addHeaderView(headerView);
		
		listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, indexArr));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
