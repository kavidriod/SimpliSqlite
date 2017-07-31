package myandroidninja.wordpress.simplisqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Button addButton,viewButton;
    EditText input_phono,input_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SimpliSqlite");
        setSupportActionBar(toolbar);


        input_phono = (EditText) findViewById(R.id.input_phono);
        input_name = (EditText) findViewById(R.id.input_name);

        viewButton = (Button) findViewById(R.id.viewButton);
        addButton = (Button) findViewById(R.id.addButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input_name.getText().toString();
                String phono = input_phono.getText().toString();

                // Inserting Contacts
                Log.d("Insert: ", "Inserting ..");

                SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(getApplicationContext());
                sqLiteOpenHelper.addContact(new Contacts(name,phono));
            }
        });


        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewActivity.class));
            }
        });



    }
}
