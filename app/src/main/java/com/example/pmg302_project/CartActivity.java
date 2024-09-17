// CartActivity.java
package com.example.pmg302_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmg302_project.adapter.ProductAdapter;
import com.example.pmg302_project.model.Product;
import com.example.pmg302_project.Utils.CartPreferences;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCart;
    private ProductAdapter productAdapter;
    private List<Product> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        cartList = CartPreferences.loadCart(this);
        productAdapter = new ProductAdapter(this, cartList, null);
        recyclerViewCart.setAdapter(productAdapter);

        TextView emptyCartMessage = findViewById(R.id.emptyCartMessage);
        if (cartList.isEmpty()) {
            emptyCartMessage.setVisibility(TextView.VISIBLE);
        } else {
            emptyCartMessage.setVisibility(TextView.GONE);
        }
    }
}