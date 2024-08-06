package com.eriks.android;

import android.os.Bundle;
import android.view.Window;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.eriks.android.repository.*;
import com.eriks.core.GameController;
import com.eriks.core.TaskController;
import com.eriks.core.ui.UIController;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);

		config.hideStatusBar = true;
		config.useImmersiveMode = true;

		SQLiteHelper dbHelper = new SQLiteHelper(getApplicationContext());
		GameController.INSTANCE.setPackageRepository(new AndroidPackageRepository(dbHelper.getWritableDatabase()));
		GameController.INSTANCE.setParamRepository(new AndroidParamRepository(dbHelper.getWritableDatabase()));
		GameController.INSTANCE.setCardRepository(new AndroidCardRepository(dbHelper.getWritableDatabase()));
		TaskController.INSTANCE.setTaskRepository(new AndroidTaskRepository(dbHelper.getWritableDatabase()));

		GameController.INSTANCE.startup();

		initialize(UIController.INSTANCE, config);
	}
}
