package com.demo.firebase.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.firebase.R;
import com.demo.firebase.StoreApplication;
import com.demo.firebase.model.MockInventory;
import com.demo.firebase.model.Product;
import com.demo.firebase.model.Section;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Collection;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class SectionActivity extends AppCompatActivity {

    private static final String SECTION_NAME = "section";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        TextView title = findViewById(R.id.title);
        try {
            Section section = Section.valueOf(getSectionFromIntent());
            title.setText(section.title);
            setUpProductsList(section);

            Bundle bundle = new Bundle();
            bundle.putString(Param.ITEM_CATEGORY, section.title);
            StoreApplication.logEvent(Event.VIEW_ITEM_LIST, bundle);
            FirebaseCrashlytics.getInstance().log("Entering category view of: " + section.title);

        } catch (IllegalArgumentException e) {
            FirebaseCrashlytics.getInstance().log("Category which doesn't exist");
            showPageDoesNotExistDialog();
        }
    }

    private void showPageDoesNotExistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.page_not_found)
                .setPositiveButton(R.string.continue_shop, (dialog, id) -> MainActivity.navigate(this));
        builder.create().show();
    }

    private void setUpProductsList(Section section) {
        RecyclerView view = findViewById(R.id.section_product_list);
        view.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        Collection<Product> products = new MockInventory().productsForSection(section);
        ProductListAdapter adapter = new ProductListAdapter(products, R.layout.product_as_list);
        view.setAdapter(adapter);
    }

    private String getSectionFromIntent() {
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            //handle Deep links: extract section name from the last segment of the URI
            return data.getLastPathSegment().toUpperCase();
        } else {
            return intent.getStringExtra(SECTION_NAME).toUpperCase();
        }
    }

    public void cartBtnClick(View v) {
        CartActivity.navigate(this);
    }

    public static void navigateToSectionPage(Activity activity, Section section) {
        Intent intent = new Intent(activity, SectionActivity.class);
        intent.putExtra(SECTION_NAME, section.toString());
        activity.startActivity(intent);
    }
}
