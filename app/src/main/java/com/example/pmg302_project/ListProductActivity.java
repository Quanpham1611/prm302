package com.example.pmg302_project;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.Toolbar; // Correct import
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmg302_project.adapter.ProductAdapter;
import com.example.pmg302_project.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListProductActivity extends AppCompatActivity {
    private Toolbar toolbar;
    OkHttpClient client = new OkHttpClient();
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Nhận loại sản phẩm từ Intent
        String productType = getIntent().getStringExtra("PRODUCT_TYPE");
        if (productType != null) {
            getSupportActionBar().setTitle(productType);
        }

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách sản phẩm (trống) và adapter trước khi fetch data
        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        fetchProduct(productType);
    }

    private void fetchProduct(String productType) {
        String url = "http://172.20.109.44:8081/api/product?type=" + productType;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d(TAG, "Sending request to: " + request.url());
                    Log.d(TAG, "body: " + responseData);
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        runOnUiThread(() -> {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Product product = new Product(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("description"),
                                            jsonObject.getDouble("price"),
                                            jsonObject.getString("imageLink"),
                                            jsonObject.getString("type"),
                                            jsonObject.getDouble("rate"),
                                            jsonObject.getInt("purchaseCount")
                                    );
                                    productList.add(product);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            productAdapter.notifyDataSetChanged(); // Cập nhật adapter
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
