package com.example.shoppinglistapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextItem;
    private Button buttonAdd;
    private ListView listViewItems;

    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextItem = findViewById(R.id.editTextItem);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewItems = findViewById(R.id.listViewItems);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listViewItems.setAdapter(adapter);

        buttonAdd.setOnClickListener(v -> {
            String item = editTextItem.getText().toString().trim();
            if (!item.isEmpty()) {
                itemList.add(item);
                adapter.notifyDataSetChanged();
                editTextItem.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Please enter an item", Toast.LENGTH_SHORT).show();
            }
        });

        listViewItems.setOnItemLongClickListener((adapterView, view, position, id) -> {
            String selectedItem = itemList.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Item")
                    .setMessage("Delete \"" + selectedItem + "\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        itemList.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear) {
            if (!itemList.isEmpty()) {
                new AlertDialog.Builder(this)
                        .setTitle("Clear All Items")
                        .setMessage("Are you sure you want to clear the list?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            itemList.clear();
                            adapter.notifyDataSetChanged();
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                Toast.makeText(this, "The list is already empty", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle("About")
                    .setMessage("Shopping List App\nCreated by Soham Bhoir")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
