package com.example.myprojet1

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myprojet1.data.Product
import com.example.myprojet1.ui.theme.MyProjet1Theme

class MainActivity5 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting the content with Compose UI
        setContent {
            MyProjet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dbHelper = remember { FeedReaderDbHelper(context) } // Helper to manage the database
    val products = remember { mutableStateListOf<Product>() }

    // Load products from the database on first composition
    LaunchedEffect(Unit) {
        products.clear()
        loadProductsFromDatabase(dbHelper, products)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddProductForm { name, description, price, imageRes ->
            // Insert product into database
            val success = insertProductIntoDatabase(dbHelper, name, description, price, imageRes)
            if (success) {
                products.add(Product(name, description, price, imageRes))
                Toast.makeText(context, "Product added successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to add product!", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProductList(products)
    }
}

@Composable
fun AddProductForm(onAddProduct: (String, String, Double, Int) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New Product", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            val priceValue = price.toDoubleOrNull()
            if (name.isNotEmpty() && description.isNotEmpty() && priceValue != null) {
                onAddProduct(name, description, priceValue, android.R.drawable.ic_menu_gallery)
            }
        }) {
            Text("Add Product")
        }
    }
}

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product)
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Text("Price: $${product.price}", style = MaterialTheme.typography.bodyMedium)
            Text(product.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// Helper Functions

fun loadProductsFromDatabase(dbHelper: FeedReaderDbHelper, products: MutableList<Product>) {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
        FeedReaderContract.FeedEntry.TABLE_NAME,
        null, // Select all columns
        null, null, null, null, null
    )

    with(cursor) {
        while (moveToNext()) {
            val name = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NOM))
            val description = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION))
            val price = getDouble(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PRIX))
            val image = getInt(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE))
            products.add(Product(name, description, price, image))
        }
        close()
    }
}

fun insertProductIntoDatabase(
    dbHelper: FeedReaderDbHelper,
    name: String,
    description: String,
    price: Double,
    imageRes: Int
): Boolean {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
        put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOM, name)
        put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description)
        put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRIX, price)
        put(FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE, imageRes)
    }

    return db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values) != -1L
}
