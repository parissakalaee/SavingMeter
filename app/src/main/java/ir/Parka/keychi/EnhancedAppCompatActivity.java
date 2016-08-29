package ir.Parka.keychi;

import android.support.v7.app.AppCompatActivity;

public abstract class EnhancedAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        G.currentActivity = this;
        super.onResume();
    }

}
