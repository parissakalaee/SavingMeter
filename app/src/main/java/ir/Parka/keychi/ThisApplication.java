package ir.Parka.keychi;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class ThisApplication extends Application{

	public static final Handler HANDLER = new Handler();
	
	@Override
	public void onCreate() {
		super.onCreate();

        Fabric.with(this, new Crashlytics());
	}
}
