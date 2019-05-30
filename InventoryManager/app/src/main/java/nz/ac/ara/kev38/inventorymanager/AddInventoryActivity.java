package nz.ac.ara.kev38.inventorymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nz.ac.ara.kev38.inventorymanager.modelcode.SystemManager;

public class AddInventoryActivity extends AppCompatActivity {
    public final static String EXTRA_NEW_INV_NAME = "nz.ac.ara.kev38.extras.EXTRA_NEW_INV_NAME";
    public final static String EXTRA_NEW_INV_INFO = "nz.ac.ara.kev38.extras.EXTRA_NEW_INV_INFO";
    private EditText mNameInput;
    private EditText mInfoInput;
    private SystemManager mTheSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mTheSystem = intent.getParcelableExtra(MainActivity.EXTRA_SYSTEM);
        mNameInput = findViewById(R.id.ed_new_inv_name);
        mInfoInput = findViewById(R.id.ed_new_inv_info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            // return data to previous activity
            String newName = mNameInput.getText().toString();
            String newInfo = mInfoInput.getText().toString();

            if (mTheSystem.hasInventory(newName)) {
                Toast.makeText(getApplicationContext(), getString(R.string.duplicate_inventory_warning), Toast.LENGTH_SHORT).show();
            } else {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_NEW_INV_NAME, newName);
                replyIntent.putExtra(EXTRA_NEW_INV_INFO, newInfo);
                setResult(RESULT_OK, replyIntent);

                finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
