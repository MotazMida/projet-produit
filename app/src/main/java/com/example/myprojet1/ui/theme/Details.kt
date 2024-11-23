package com.example.myprojet1.ui.theme

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the passed data from the intent
        val productName = intent.getStringExtra("productName")
        val productDescription = intent.getStringExtra("productDescription")
        val productPrice = intent.getDoubleExtra("productPrice", 0.0)
        val productImage = intent.getIntExtra("productImage", 0)

        setContent {
            MyProjet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductDetailsScreen(
                        name = productName ?: "",
                        description = productDescription ?: "",
                        price = productPrice,
                        image = productImage,
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailsScreen(name: String, description: String, price: Double, image: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Price: $$price", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = description, fontSize = 16.sp)
    }
}