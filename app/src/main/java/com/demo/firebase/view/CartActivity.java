package com.demo.firebase.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.firebase.R;
import com.demo.firebase.StoreApplication;
import com.demo.firebase.model.Cart;
import com.demo.firebase.model.Product;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {

    public static final String RC_BUTTON_KEY = "button_label";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Cart cart = Cart.getInstance();
        if (cart.isEmpty()) {
            TextView emptyCart = findViewById(R.id.emptycart);
            emptyCart.setText(R.string.cart_is_empty);
            Button checkoutBtn = findViewById(R.id.checkout_btn);
            checkoutBtn.setVisibility(View.INVISIBLE);
        } else {
            RecyclerView view = findViewById(R.id.item_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            view.setLayoutManager(layoutManager);
            view.setAdapter(new CartItemAdapter());

            getRemoteConfigurationForButton();
        }
    }

    private void getRemoteConfigurationForButton() {
        Button button = findViewById(R.id.checkout_btn);
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings props = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0).build();
        config.setConfigSettingsAsync(props);
        config.setDefaultsAsync(new HashMap<String, Object>(){{
            put(RC_BUTTON_KEY, "default label");
        }});
        config.fetchAndActivate().addOnCompleteListener(task -> {
            String buttonText = config.getString(RC_BUTTON_KEY);
            button.setText(buttonText);
        });
    }

    public void checkout(View v) {
        Cart cart = Cart.getInstance();

        //track conversion for purchase
        logPurchase(cart);

        cart.pay();
        Intent intent = new Intent(this, PaidActivity.class);
        startActivity(intent);
    }

    private void logPurchase(Cart cart) {
        for (int i = 0; i < cart.getNumOfUniqueProducts(); i++) {
            String transactionId = UUID.randomUUID().toString();
            Product product = cart.getProduct(i);
            Bundle bundle = new Bundle();
            bundle.putDouble(FirebaseAnalytics.Param.VALUE, product.price);
            bundle.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
            bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, transactionId);
            StoreApplication.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);
        }

    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, CartActivity.class);
        activity.startActivity(intent);
    }
}
