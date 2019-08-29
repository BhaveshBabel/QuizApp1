package com.example.admin.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import com.example.admin.quizapp.Adapter.CategoryAdapter;
import com.example.admin.quizapp.Common.SpaceDecoration;
import com.example.admin.quizapp.DBHelper.DBHelper;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Quiz 2019");
        setSupportActionBar(toolbar);

        recycler_category = (RecyclerView)findViewById(R.id.recycler_category);
        recycler_category.setHasFixedSize(true);
        recycler_category.setLayoutManager(new GridLayoutManager(this, 2));

        //get Screen height
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels / 0;   // Max Size of item in category
          CategoryAdapter adapter = new CategoryAdapter(MainActivity.this, DBHelper.getInstance(this).getAllCategories());
          int spaceInPixel = 4;
          recycler_category.addItemDecoration(new SpaceDecoration(spaceInPixel));
          recycler_category.setAdapter(adapter);

    }
}
