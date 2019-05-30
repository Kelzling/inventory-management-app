package nz.ac.ara.kev38.datapassingtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import nz.ac.ara.kev38.datapassingtest.modelcode.SystemManager;

public class TestActivity extends AppCompatActivity {
    private TextView mTestTextView;
    private SystemManager mTheSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        mTheSystem = intent.getParcelableExtra(MainActivity.EXTRA_SYSTEM);
        String message = mTheSystem.getInventoryNames().get(0);

        mTestTextView = findViewById(R.id.test_display);
        mTestTextView.setText(message);
    }
}
