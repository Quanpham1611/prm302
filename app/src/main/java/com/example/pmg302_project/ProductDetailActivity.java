package com.example.pmg302_project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Nhận dữ liệu từ Intent
        int id = getIntent().getIntExtra("productId", -1);
        String name = getIntent().getStringExtra("name");
        String imageLink = getIntent().getStringExtra("imageLink");
        double price = getIntent().getDoubleExtra("price", 0);
        double rate = getIntent().getDoubleExtra("rate", 0);
        int purchaseCount = getIntent().getIntExtra("purchaseCount", 0);
        String description = getIntent().getStringExtra("description");
        System.out.println(id);
        // Ánh xạ các view
        TextView productName = findViewById(R.id.productName);
        ImageView productImage = findViewById(R.id.productImage);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productRate = findViewById(R.id.productRate);
        TextView productPurchaseCount = findViewById(R.id.productPurchaseCount);
        TextView productDescription = findViewById(R.id.productDescription);

        // Gán dữ liệu vào các view
        productName.setText(name);
        Picasso.get().load(imageLink).into(productImage);
        productPrice.setText(String.format("Giá: $%.2f", price));
        productRate.setText(String.format("Đánh giá: %.1f", rate));
        productPurchaseCount.setText(String.format("Lượt mua: %d", purchaseCount));
        productDescription.setText(description);
    }
}