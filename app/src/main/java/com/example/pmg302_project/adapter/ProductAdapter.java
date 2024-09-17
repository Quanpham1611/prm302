package com.example.pmg302_project.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmg302_project.R;
import com.example.pmg302_project.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private OnAddToCartClickListener onAddToCartClickListener;

    public ProductAdapter(Context context, List<Product> productList , OnAddToCartClickListener onAddToCartClickListener) {
        this.context = context;
        this.productList = productList;
        this.onAddToCartClickListener = onAddToCartClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + "$");
        holder.productDescription.setText(String.valueOf(product.getDescription()));
        holder.productRate.setText("Đánh giá: " + product.getRate() + "/5");
        holder.productPurchase.setText("Lượt mua: " + product.getPurchaseCount());
        Picasso.get().load(product.getImageLink()).into(holder.productImage);
        holder.addToCartButton.setOnClickListener(v -> {
            showAddToCartDialog(product);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface OnAddToCartClickListener {
        void onAddToCartClick(Product product, int quantity, String size);
    }

    private void showAddToCartDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_to_cart, null);
        builder.setView(dialogView);

        ImageView productImage = dialogView.findViewById(R.id.productImage);
        TextView productName = dialogView.findViewById(R.id.productName);
        Spinner sizeSpinner = dialogView.findViewById(R.id.sizeSpinner);
        EditText quantityEditText = dialogView.findViewById(R.id.quantityEditText);
        TextView totalPriceTextView = dialogView.findViewById(R.id.totalPriceTextView);
        Button addToCartButton = dialogView.findViewById(R.id.addToCartButton);

        Picasso.get().load(product.getImageLink()).into(productImage);
        productName.setText(product.getName());

        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int quantity = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                double totalPrice = quantity * product.getPrice();
                totalPriceTextView.setText("Thành tiền: " + totalPrice + "$");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AlertDialog dialog = builder.create();
        addToCartButton.setOnClickListener(v -> {
            String quantityStr = quantityEditText.getText().toString();
            if (quantityStr.isEmpty() || Integer.parseInt(quantityStr) <= 0) {
                quantityEditText.setError("Vui lòng nhập số lượng!");
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            String size = sizeSpinner.getSelectedItem().toString();
            if (onAddToCartClickListener != null) {
                onAddToCartClickListener.onAddToCartClick(product, quantity, size);
            }
            dialog.dismiss();
        });

        dialog.show();
    }
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productDescription;
        TextView productRate;
        TextView productPurchase;
        Button addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productRate = itemView.findViewById(R.id.productRate);
            productPurchase = itemView.findViewById(R.id.productPurchase);
            addToCartButton = itemView.findViewById(R.id.button);
        }
    }
}
