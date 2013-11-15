package com.newjourney.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.newjourney.android.animation.AnimActivity;
import com.newjourney.android.animation.ZoomActivity;
import com.newjourney.android.asynctask.AsyncTaskAActivitity;
import com.newjourney.android.demo.WebViewActivity;
import com.newjourney.android.media.AudioCaptureActivity;
import com.newjourney.android.widget.DotsViewSample;

public class MainActivity extends Activity implements OnChildClickListener {
	
	private static List<Group> groups = new ArrayList<Group>();;
	
	static {
		Group group;
		group = addGroup("Animation");
		group.addChild("Zoom", ZoomActivity.class);
		group.addChild("Anim", AnimActivity.class);
		group.addChild("Animation item", ZoomActivity.class);
		group.addChild("Animation item", ZoomActivity.class);
		group.addChild("Animation item", ZoomActivity.class);
		group = addGroup("Views");
		group.addChild("DotsView", DotsViewSample.class);
		group.addChild("AsyncTaskAActivitity", AsyncTaskAActivitity.class);
		group.addChild("Views item", ZoomActivity.class);
		group.addChild("Views item", ZoomActivity.class);
		group.addChild("Views item", ZoomActivity.class);
		group.addChild("Views item", ZoomActivity.class);
		group.addChild("Views item", ZoomActivity.class);
		group = addGroup("Media");
		group.addChild("AudioRecord", AudioCaptureActivity.class);
		group.addChild("Media Item", WebViewActivity.class);
		group.addChild("Media Item", ZoomActivity.class);
		group.addChild("Media Item", ZoomActivity.class);
		group.addChild("Media Item", ZoomActivity.class);
	}
	
	private static Group addGroup(String title){
		Group group = new Group();
		group.title = title;
		groups.add(group);
		return group;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ExpandableListView exListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		exListView.setAdapter(new MenuListAdapter(this, groups));
		exListView.setOnChildClickListener(this);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		Child child = groups.get(groupPosition).children.get(childPosition);
		Intent intent = new Intent(this, child.activityClass);
		intent.putExtra("title", child.title);
		startActivity(intent);
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	static class MenuListAdapter  extends BaseExpandableListAdapter {
		
		private Context context;
		List<Group> groups;
		
		public MenuListAdapter(Context context, List<Group> groups) {
			this.context = context;
			this.groups = groups;
		}
		
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		
		@Override
		public boolean hasStableIds() {
			return false;
		}
		
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);				
			}
			TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
			Group group = (Group) getGroup(groupPosition);
			textView.setText(group.title);
			return convertView;
		}
		
		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}
		
		@Override
		public int getGroupCount() {
			return groups.size();
		}
		
		@Override
		public Object getGroup(int groupPosition) {
			return groups.get(groupPosition);
		}
		
		@Override
		public int getChildrenCount(int groupPosition) {
			return groups.get(groupPosition).children.size();
		}
		
		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);				
			}
			TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
			Child child = (Child) getChild(groupPosition, childPosition);
			textView.setText(child.title);
			return convertView;
		}
		
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}
		
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return groups.get(groupPosition).children.get(childPosition);
		}
	};
	
	static class Group {
		String title;
		List<Child> children = new ArrayList<Child>();
		void addChild(String title, Class<?> activityClass) {
			Child child = new Child();
			child.title = title;
			child.activityClass = activityClass;
			children.add(child);
		}
	}	
	
	static class Child {
		String title;
		Class<?> activityClass;
	}

}

