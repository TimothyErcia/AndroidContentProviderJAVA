package com.example.androidcontentproviderjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.androidcontentproviderjava.R;
import com.example.androidcontentproviderjava.model.Contact;
import com.example.androidcontentproviderjava.provider.ContactContentProvider;
import com.example.androidcontentproviderjava.repository.DBHelper;
import com.example.androidcontentproviderjava.viewmodel.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText editTextName, editTextNumber;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<Contact> arrayList = new ArrayList<>();
    private Button button1, button2, button3;

    private ContactViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextNumber = findViewById(R.id.editTextNumber);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItem(i);
            }
        });

        initializeData();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setText("");
                editTextNumber.setText("");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private void initializeData() {
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                Log.i(TAG, "onChanged: " + contacts.size());
                if(contacts.size() > 0) {
                    for(Contact data : contacts) {
                        arrayList.add(data);
                        adapter.add(data.getContactName());
                    }
                }
            }
        });
        viewModel.initializeData(getApplicationContext());
    }

    private void insertData() {
        if(editTextName.getText().toString() != "" || editTextNumber.getText().toString() != "") {
            viewModel.insertData(getApplicationContext(), editTextName.getText().toString(), editTextNumber.getText().toString());

            arrayList.add(new Contact(editTextName.getText().toString(), editTextNumber.getText().toString()));
            adapter.add(editTextName.getText().toString());
            adapter.notifyDataSetChanged();
            listView.setSelection(adapter.getCount() - 1);
        }
    }

    private void updateData() {
        if(editTextName.getText().toString() != "" || editTextNumber.getText().toString() != "") {
            viewModel.updateData(getApplicationContext(), editTextName.getText().toString(), editTextNumber.getText().toString());
            adapter.notifyDataSetChanged();
        }
    }

    private void selectItem(int position) {
        editTextName.setText(arrayList.get(position).getContactName());
        editTextNumber.setText(arrayList.get(position).getContactNumber());
    }
}