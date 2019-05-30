package nz.ac.ara.kev38.testapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nz.ac.ara.kev38.testapp2.modelcode.*;

public class SecondTestActivity extends AppCompatActivity {
    private LinearLayout mTestContainer;
    private SystemManager mTheSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_test);
        mTestContainer = findViewById(R.id.text_container);

        Intent intent = getIntent();
        mTheSystem = intent.getParcelableExtra(MainActivity.EXTRA_SYSTEM);
        ArrayList<String> names = mTheSystem.getInventoryNames();

        for (String name : names) {
            TextView newTextView = new TextView(this);
            newTextView.setText("Inventory Name: " + name);
            mTestContainer.addView(newTextView);
        }
    }
}
