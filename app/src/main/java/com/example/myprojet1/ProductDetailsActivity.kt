package com.example.myprojet1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprojet1.ui.theme.MyProjet1Theme // Consistent theme

class ProductDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyProjet1Theme { // Correct capitalization
                // Retrieve the passed data from the intent safely with null checks
                val productName = intent.getStringExtra("productName") ?: "Unknown Product"
                val productPrice = intent.getDoubleExtra("productPrice", 0.0)
                val productImage = intent.getIntExtra("productImage", 0)
                val productDescription = intent.getStringExtra("productDescription") ?: "No description available"

                // Display product details in the UI
                ProductDetailsScreen(productName, productPrice, productDescription, productImage)
            }
        }
    }
}

@Composable
fun ProductDetailsScreen(productName: String, productPrice: Double, productDescription: String, productImage: Int) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Display the product image
        Image(
            painter = painterResource(id = productImage),
            contentDescription = productName,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Display the product name
        Text(text = productName, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Display the product price
        Text(text = "Price: $${productPrice}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Display the product description
        Text(text = productDescription, fontSize = 16.sp)
    }
}
