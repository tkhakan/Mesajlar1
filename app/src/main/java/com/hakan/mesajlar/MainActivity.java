package com.hakan.mesajlar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.hakan.mesajlar.view.CircleImageView;
import com.hakan.mesajlar.view.CircleLayout;
import com.hakan.mesajlar.view.CircleLayout.OnCenterClickListener;
import com.hakan.mesajlar.view.CircleLayout.OnItemClickListener;
import com.hakan.mesajlar.view.CircleLayout.OnItemSelectedListener;
import com.hakan.mesajlar.view.CircleLayout.OnRotationFinishedListener;

public class MainActivity extends Activity implements OnItemSelectedListener,
		OnItemClickListener, OnRotationFinishedListener, OnCenterClickListener {
	TextView selectedTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CircleLayout circleMenu = (CircleLayout) findViewById(R.id.main_circle_layout);
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);
		circleMenu.setOnRotationFinishedListener(this);
		circleMenu.setOnCenterClickListener(this);

		selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
		selectedTextView.setText(((CircleImageView) circleMenu
				.getSelectedItem()).getName());
	}

	@Override
	public void onItemSelected(View view, String name) {
		selectedTextView.setText(name);

		switch (view.getId()) {
			
			case R.id.mesajyaz:
				// Handle myspace selection
				break;
			case R.id.gelenmesaj:
				// Handle twitter selection
				break;
			case R.id.gidenmesaj:
				// Handle wordpress selection
				break;
		}
	}

	@Override
	public void onItemClick(View view, String name) {
		//Toast.makeText(getApplicationContext(),
			//	getResources().getString(R.string.start_app) + " " + name,
			//	Toast.LENGTH_SHORT).show();

		switch (view.getId()) {
			
			case R.id.mesajyaz:
				Intent intent=new Intent(MainActivity.this,MesajYaz.class);
				startActivity(intent);

				break;
			case R.id.gelenmesaj:
				Intent intent1=new Intent(MainActivity.this,GelenMesaj.class);
				startActivity(intent1);

				break;
			case R.id.gidenmesaj:
				Intent intent2=new Intent(MainActivity.this,GidenMesaj.class);
				startActivity(intent2);

				break;
		}
	}

	@Override
	public void onCenterClick() {
		Toast.makeText(getApplicationContext(), R.string.center_click,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRotationFinished(View view, String name) {
		Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,
				view.getHeight() / 2);
		animation.setDuration(250);
		view.startAnimation(animation);
	}

}
