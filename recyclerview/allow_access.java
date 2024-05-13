//package com.a.acs2;
//
//
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class allow_access extends AppCompatActivity implements MyAdapter.OnItemClickListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//
//        List<Item> items = new ArrayList<Item>();
//        items.add(new Item("John wick","john.wick@email.com",R.drawable.math));
//        items.add(new Item("Robert j","robert.j@email.com",R.drawable.math));
//
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items));
//
//    }
//    @Override
//    public void onItemClick(Item item) {
//        // Handle item click here
//        Toast.makeText(this, "Clicked on: " + item.getName(), Toast.LENGTH_SHORT).show();
//    }
//
//}

package com.a.acs2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class allow_access extends AppCompatActivity  implements SelectListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_access);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("John wick","john.wick@email.com",R.drawable.math));
        items.add(new Item("Robert j","robert.j@email.com",R.drawable.math));
        items.add(new Item("James Gunn","james.gunn@email.com",R.drawable.math));





        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items,this));

    }

    @Override
    public void onIteamClicked(Item item) {
        // Inside your activity class
        Toast.makeText(this, item.getName(), Toast.LENGTH_SHORT).show();

    }
}