package myandroidninja.wordpress.simplisqlite;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kavitha on 31-07-2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsAdapterHolder> {

private List<Contacts> contactsList;
    Activity activity;

    public ContactsAdapter(List<Contacts> contactsList,Activity activity) {
        this.contactsList = contactsList;
        this.activity = activity;
    }


    @Override
    public ContactsAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new ContactsAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactsAdapterHolder holder, final int position) {
final Contacts contacts = contactsList.get(position);
        holder.contactName.setText(contacts.get_name());
        holder.contactPhono.setText(contacts.get_phone_number());

        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alBuilder = new AlertDialog.Builder(activity).create();
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View dialogView = inflater.inflate(R.layout.edit_item,null);
                alBuilder.setView(dialogView);

                final EditText edit_phono = (EditText) dialogView.findViewById(R.id.edit_phono);
                final EditText edit_name = (EditText) dialogView.findViewById(R.id.edit_name);
                Button editButton = (Button) dialogView.findViewById(R.id.editButton);

                final Contacts contacts = contactsList.get(position);

                edit_phono.setText(contacts.get_phone_number());
                edit_name.setText(contacts.get_name());

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("SDgdsg","DSg "+contacts.get_id());

                        Contacts contactss = new Contacts(contacts.get_id(),edit_name.getText().toString(),
                                edit_phono.getText().toString());

                        SQLiteOpenHelper sqLiteOpenHelper  = new SQLiteOpenHelper(activity);
                       int i =  sqLiteOpenHelper.updateContact(contactss);
                           if (i == 1)
                           {
                               alBuilder.dismiss();
                               contactsList.set(position,contactss);
                               notifyDataSetChanged();
                           }

                    }
                });
                alBuilder.show();
            }
        });

        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(activity);
                Contacts c = contactsList.get(position);
                sqLiteOpenHelper.deleteContact(c);
                Log.i("position","position "+position);
                contactsList.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public  class  ContactsAdapterHolder extends RecyclerView.ViewHolder
    {
        public TextView contactName,contactPhono;
        Button editbutton,deletebutton;
        public ContactsAdapterHolder(View itemView) {
            super(itemView);
            contactName =  (TextView) itemView.findViewById(R.id.contactNameTv);
            contactPhono =  (TextView) itemView.findViewById(R.id.contactPhonoTv);

            editbutton =  (Button) itemView.findViewById(R.id.editbutton);
            deletebutton =  (Button) itemView.findViewById(R.id.deletebutton);
        }
    }



}
