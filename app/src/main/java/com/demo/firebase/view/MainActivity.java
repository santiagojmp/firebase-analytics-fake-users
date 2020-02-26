package com.demo.firebase.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.firebase.BiasedRandom;
import com.demo.firebase.Log;
import com.demo.firebase.R;
import com.demo.firebase.model.MockInventory;
import com.demo.firebase.model.Product;
import com.demo.firebase.model.Section;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Collection;
import java.util.Random;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;
import static com.demo.firebase.model.Section.NEW_ARRIVAL;
import static com.demo.firebase.model.Section.RECOMMENDATION;

public class MainActivity extends AppCompatActivity {

    public static final int NUMBER_OF_USERS = 20_000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
        String userId = Integer.toString(BiasedRandom.dateBiasedUserId(NUMBER_OF_USERS));
        Log.i("User id: " + userId);
        analytics.setUserId(userId);

        RecyclerView newArrivalView = findViewById(R.id.new_arrivals_view);
        renderProducts(NEW_ARRIVAL, newArrivalView);
        RecyclerView recommendView = findViewById(R.id.recommendation_view);
        renderProducts(RECOMMENDATION, recommendView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // settings is the only option for now
        new SizeSelectionDialog(this).show();
        return true;
    }

    private void renderProducts(Section section, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL, false));
        Collection<Product> products = new MockInventory().productsForSection(section);
        ProductListAdapter adapter = new ProductListAdapter(products, R.layout.product_as_icons);
        view.setAdapter(adapter);
    }

    public void cartBtnClick(View v) {
        CartActivity.navigate(this);
    }

    public void recommendClick(View v) {
        SectionActivity.navigateToSectionPage(this, RECOMMENDATION);
    }

    public void newArrClick(View v) {
        SectionActivity.navigateToSectionPage(this, NEW_ARRIVAL);
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}