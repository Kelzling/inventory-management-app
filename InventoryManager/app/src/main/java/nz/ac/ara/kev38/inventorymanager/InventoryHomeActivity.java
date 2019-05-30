package nz.ac.ara.kev38.inventorymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import nz.ac.ara.kev38.inventorymanager.modelcode.Inventory;
import nz.ac.ara.kev38.inventorymanager.modelcode.InventoryNotFoundException;

public class InventoryHomeActivity extends SystemSaverActivity {
    private Inventory mTheInventory;
    private TextView mTxtOtherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Might Get Done: Implement Edit Inventory", Toast.LENGTH_SHORT).show();
            }
        });

        // get data out
        Intent intent = getIntent();
        mTheSystem = intent.getParcelableExtra(MainActivity.EXTRA_SYSTEM);
        String inventoryName = intent.getStringExtra(NameListAdapter.NameViewHolder.EXTRA_INV_NAME);

        // do the things and set up the activity
        setTitle(inventoryName + " - Home");
        try {
            mTheInventory = mTheSystem.getInventory(inventoryName);
            mTxtOtherInfo = findViewById(R.id.txt_inv_home_name);
            mTxtOtherInfo.setText("Other Info: " + mTheInventory.getOtherInfo());
        } catch (InventoryNotFoundException e) {
            Toast.makeText(this, "Inventory was not found", Toast.LENGTH_SHORT).show();
            Intent backIntent = new Intent(InventoryHomeActivity.this, MainActivity.class);
            startActivity(backIntent);
        }
    }

    public void launchItemView(View view) {
        Toast.makeText(this, "To Do: Implement Item View", Toast.LENGTH_SHORT).show();
    }

    public void launchShoppingList(View view) {
        Toast.makeText(this, "To Do: Implement Shopping List", Toast.LENGTH_SHORT).show();
    }

    public void launchReports(View view) {
        Toast.makeText(this, "Unlikely To Be Implemented In This Prototype: Reporting Features", Toast.LENGTH_SHORT).show();
    }
}
