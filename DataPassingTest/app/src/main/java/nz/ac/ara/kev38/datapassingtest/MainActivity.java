package nz.ac.ara.kev38.datapassingtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileNotFoundException;

import nz.ac.ara.kev38.datapassingtest.modelcode.*;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_SYSTEM = "nz.ac.ara.kev38.datapassingtest.extra.SYSTEM";
    private SystemManager mTheSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            SystemLoader theLoader = new SystemLoader(SystemManager.SYSTEM_ID);
            mTheSystem = theLoader.createObjects();
        } catch (FileNotFoundException e) {
            mTheSystem = new SystemManager();
        }

        mTheSystem.addInventory("test", "testiiinnnngggg");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, TestActivity.class);
            mTheSystem.saveState();
            intent.putExtra(EXTRA_SYSTEM, mTheSystem);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
