package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AndroidLauncher extends Activity {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch);

		final Button bt1 = (Button) findViewById(R.id.bt1);
		bt1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AndroidLauncher.this, InitMap.class);
				intent.putExtra("map", "attack");
				v.getContext().startActivity(intent);
			}
		});

		final Button bt2 = (Button) findViewById(R.id.bt2);
		bt2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AndroidLauncher.this, InitMap.class);
				intent.putExtra("map", "defence");
				v.getContext().startActivity(intent);
			}
		});



	}
}
