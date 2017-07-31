package myandroidninja.wordpress.simplisqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

public class ViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
       SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(getApplicationContext());

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contacts> contactsList = sqLiteOpenHelper.getAllContacts();

ContactsAdapter contactsAdapter = new ContactsAdapter(contactsList,ViewActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);
        contactsAdapter.notifyDataSetChanged();

        for (Contacts cn : contactsList) {
            String log = "Id: "+cn.get_id()+" ,Name: " + cn.get_name() + " ,Phone: " + cn.get_phone_number();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

    }

}
