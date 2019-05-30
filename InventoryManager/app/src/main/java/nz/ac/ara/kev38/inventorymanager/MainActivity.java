package nz.ac.ara.kev38.inventorymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import nz.ac.ara.kev38.inventorymanager.modelcode.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_SYSTEM = "nz.ac.ara.kev38.inventorymanager.extras.EXTRA_SYSTEM";
    public static final int NEW_INV_REQUEST = 1;
    private RecyclerView mInventoryNameView;
    private ArrayList<String> mInventoryNamesList = new ArrayList<>();
    private SystemManager mTheSystem;
    private NameListAdapter mNameListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mInventoryNameView = findViewById(R.id.inv_name_r_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddInventoryActivity.class);
                intent.putExtra(EXTRA_SYSTEM, mTheSystem);
                startActivityForResult(intent, NEW_INV_REQUEST);
            }
        });

        // initialise the system
        try {
            SystemLoader theLoader = new SystemLoader(getApplicationContext().getFilesDir().toString(), SystemManager.SYSTEM_ID);
            mTheSystem = theLoader.createObjects();
        } catch (FileNotFoundException e) {
            mTheSystem = new SystemManager();
        }
        mTheSystem.setFilesDir(getApplicationContext().getFilesDir().toString());

        // populate the recycler view
        mInventoryNamesList = mTheSystem.getInventoryNames();
        mNameListAdapter = new NameListAdapter(this, mInventoryNamesList, mTheSystem);
        mInventoryNameView.setAdapter(mNameListAdapter);
        mInventoryNameView.setLayoutManager(new LinearLayoutManager(this));

        mTheSystem.saveState();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_INV_REQUEST) {
            if (resultCode == RESULT_OK) {
                String newName = data.getStringExtra(AddInventoryActivity.EXTRA_NEW_INV_NAME);
                String newInfo = data.getStringExtra(AddInventoryActivity.EXTRA_NEW_INV_INFO);

                mTheSystem.addInventory(newName, newInfo);
                int currentSize = mInventoryNamesList.size();
                mInventoryNamesList = mTheSystem.getInventoryNames();
                mInventoryNameView.getAdapter().notifyItemInserted(currentSize);
            }
        }
    }

   /* @Override
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
    }*/
}
