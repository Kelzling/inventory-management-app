package nz.ac.ara.kev38.inventorymanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nz.ac.ara.kev38.inventorymanager.modelcode.SystemManager;

class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.NameViewHolder> {
    private final ArrayList<String> mInvNameList;
    private LayoutInflater mInflater;
    private Context mContext;
    private SystemManager mTheSystem;

    NameListAdapter(Context context, ArrayList<String> invNameList, SystemManager theSystem) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mInvNameList = invNameList;
        this.mTheSystem = theSystem;
    }

    @NonNull
    @Override
    public NameListAdapter.NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mInvNameView = mInflater.inflate(R.layout.inv_name_list_item, parent, false);
        return new NameViewHolder(mInvNameView, this, mTheSystem);
    }

    @Override
    public void onBindViewHolder(@NonNull NameListAdapter.NameViewHolder nameViewHolder, int position) {
        String invName = mInvNameList.get(position);

        nameViewHolder.invNameView.setText(invName);
    }

    @Override
    public int getItemCount() {
        return mInvNameList.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView invNameView;
        final NameListAdapter mAdapter;
        private final SystemManager mTheSystem;
        static final String EXTRA_INV_NAME = "nz.ac.ara.kev38.inventorymanager.extras.EXTRA_INV_NAME";

        NameViewHolder(@NonNull View itemView, NameListAdapter adapter, SystemManager theSystem) {
            super(itemView);
            this.mAdapter = adapter;
            this.invNameView = itemView.findViewById(R.id.txt_inv_name);
            this.mTheSystem = theSystem;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int listPosition = getLayoutPosition();
            String inventoryName = mInvNameList.get(listPosition);

            Intent intent = new Intent(mContext, InventoryHomeActivity.class);
            intent.putExtra(EXTRA_INV_NAME, inventoryName);
            intent.putExtra(MainActivity.EXTRA_SYSTEM, mTheSystem);
            mContext.startActivity(intent);
        }
    }
}
