package com.yatendratgy.codealpha_e_commerce;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.models.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView ivProduct;
    private TextView tvName, tvPrice, tvDescription;
    private Button btnBuy;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        db = FirebaseFirestore.getInstance();

        ivProduct = findViewById(R.id.ivProduct);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        btnBuy = findViewById(R.id.btnBuy);

        String productId = getIntent().getStringExtra("productId");
        if (productId != null) {
            loadProductDetails(productId);
        }

        btnBuy.setOnClickListener(v -> {
            // Implement payment logic here
            Toast.makeText(this, "Purchase initiated", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadProductDetails(String productId) {
        DocumentReference docRef = db.collection("products").document(productId);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Product product = documentSnapshot.toObject(Product.class);
                if (product != null) {
                    tvName.setText(product.getName());
                    tvPrice.setText(String.format("$%.2f", product.getPrice()));
                    tvDescription.setText(product.getDescription());

                    if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
                        Picasso.get().load(product.getImageUrl()).into(ivProduct);
                    }
                }
            }
        });
    }
}
