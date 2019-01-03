package com.shopp.listapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shopp.listapp.adapters.DataAdapter;
import com.shopp.listapp.listeners.ViewClickListener;
import com.shopp.listapp.models.DataModel;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements ValueEventListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_totalamount)
    TextView txt_totalamount;
    @BindView(R.id.fab)
    FloatingActionButton fab_btn;
    @BindView(R.id.recycler_home)
    RecyclerView recyclerView;


    Util util;
    Context context;
    private DatabaseReference mDatabase;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Shopping List");
        context = HomeActivity.this;
        util = Util.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        String userId = currentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Shopping List").child(userId);
        mDatabase.keepSynced(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        // Sum of amount
        mDatabase.addValueEventListener(this);

    }

    @OnClick(R.id.fab)
    public void fabBtnClick() {
        AddDialog addDialog = new AddDialog(context, 0, null, (flag, args) -> {
            String userId = mDatabase.push().getKey();
            String date = DateFormat.getDateInstance().format(new Date());
            DataModel dataModel = new DataModel(args[0], Double.parseDouble(args[1]), args[2], date, userId);
            mDatabase.child(userId).setValue(dataModel);
            util.showSuccessToast(context, "Data added successfully");
        });
        addDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataAdapter adapter = new DataAdapter(DataModel.class, R.layout.row_data, DataHolder.class, mDatabase, context, listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public ViewClickListener listener = new ViewClickListener() {
        @Override
        public void getData(DataModel model) {
            AddDialog addDialog = new AddDialog(context, 1, model, (flag, args) -> {
                String userId = model.getmId();
                String date = DateFormat.getDateInstance().format(new Date());
                DataModel dataModel = new DataModel(args[0], Double.parseDouble(args[1]), args[2], date, userId);
                mDatabase.child(userId).setValue(dataModel);
                util.showSuccessToast(context, "Data updated successfully");
            });
            addDialog.show();
        }

        @Override
        public void delete(DataModel model) {
            mDatabase.child(model.getmId()).removeValue();
        }
    };

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        double sumTotal = 0;
        for (DataSnapshot snap : dataSnapshot.getChildren()) {
            DataModel dataModel = snap.getValue(DataModel.class);
            sumTotal += dataModel.getaAmount();
        }
        String sumTotalStr = util.getFormatedString(sumTotal);
        txt_totalamount.setText(sumTotalStr);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                mFirebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
