package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewLists extends AppCompatActivity {

    ListView lv = null;
    //static SimpleAdapter adapter = null;
    static ArrayAdapter<String> adapter = null;
    static ArrayList<String> lists = new ArrayList<>();

    HashMap<String, String> resultsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lists);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        lv = findViewById(R.id.listView);

        //adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_1, lists);

        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, lists){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.shoppingList = MainActivity.shoppingLists.get(position);
                for (int i = 0; i<MainActivity.shoppingList.getProducts().size(); i++)
                {
                    resultsMap.clear();
                    resultsMap.put("First Line", MainActivity.shoppingList.getProducts().get(i).getNome());
                    resultsMap.put("Second Line", MainActivity.shoppingList.getProducts().get(i).getPreco() + "€/" + MainActivity.shoppingList.getProducts().get(i).getUnidade());
                }
                MainActivity.listItems.clear();
                MainActivity.listItems.add(resultsMap);
                MainActivity.title = MainActivity.shoppingList.getNome();
                MainActivity.totalPrice.setText(String.valueOf(MainActivity.shoppingList.getTotalPrice()) + "€");
                MainActivity.lv.setAdapter(MainActivity.adapter);
                Intent intent = new Intent();
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int arg2, long arg3)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Delete List");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lists.remove(arg2);
                        adapter.notifyDataSetChanged();
                        lv.setAdapter(adapter);
                        finish();
                    }
                });
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
