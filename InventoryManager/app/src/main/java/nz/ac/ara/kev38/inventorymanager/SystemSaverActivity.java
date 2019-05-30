package nz.ac.ara.kev38.inventorymanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nz.ac.ara.kev38.inventorymanager.modelcode.SystemManager;

public class SystemSaverActivity extends AppCompatActivity {
    protected SystemManager mTheSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            SystemManager theSystem = savedInstanceState.getParcelable("the_system");
            mTheSystem = theSystem;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mTheSystem.saveState();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mTheSystem.saveState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("the_system", mTheSystem);
    }
}
