package com.example.student_housing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.ArrowBack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudentHousingApp()
                }
            }
        }
    }
}

// data class for housing listings
data class HousingListing(
    val id: Int,
    val title: String,
    val address: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Double,
    val distanceFromCampus: Double
)

// mock data -> looking to convert this to DB later.
val mockHousingListings = listOf(
    HousingListing(
        id = 1,
        title = "Cozy 2BR Near University",
        address = "123 College Ave",
        price = 800,
        bedrooms = 2,
        bathrooms = 1.0,
        distanceFromCampus = 0.5
    ),
    HousingListing(
        id = 2,
        title = "Spacious 4BR House",
        address = "456 University Dr",
        price = 1500,
        bedrooms = 4,
        bathrooms = 2.5,
        distanceFromCampus = 1.2
    ),
    HousingListing(
        id = 3,
        title = "Modern Studio Apartment",
        address = "789 Student Lane",
        price = 650,
        bedrooms = 0,
        bathrooms = 1.0,
        distanceFromCampus = 0.3
    )
)

@Composable
fun StudentHousingApp() {
    var selectedListingId by remember { mutableStateOf<Int?>(null) }

    if (selectedListingId == null) {
        HomeScreen(onHousingClick = { id -> selectedListingId = id })
    } else {
        HousingDetailScreen(
            housingId = selectedListingId!!,
            onBackClick = { selectedListingId = null }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onHousingClick: (Int) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StudentPad") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Simple search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Search") }
            )

            // Listing items
            LazyColumn {
                items(mockHousingListings) { listing ->
                    HousingListingCard(listing = listing, onHousingClick = onHousingClick)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun HousingListingCard(listing: HousingListing, onHousingClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onHousingClick(listing.id) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Using a colored box instead of an image to avoid resource issues
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFF9FC5E8)),
                contentAlignment = Alignment.Center
            ) {
                Text("Housing Preview", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = listing.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = listing.address,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$${listing.price}/month",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "${listing.bedrooms}BR | ${listing.bathrooms}BA",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "${listing.distanceFromCampus} miles from campus",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HousingDetailScreen(housingId: Int, onBackClick: () -> Unit) {
    val listing = mockHousingListings.find { it.id == housingId } ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(listing.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = androidx.compose.ui.graphics.vector.rememberVectorPainter(
                                androidx.compose.material.icons.Icons.Default.ArrowBack
                            ),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF9FC5E8)),
                contentAlignment = Alignment.Center
            ) {
                Text("Housing Image", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = listing.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = listing.address,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            DetailRow("Price", "$${listing.price}/month")
            DetailRow("Bedrooms", "${listing.bedrooms}")
            DetailRow("Bathrooms", "${listing.bathrooms}")
            DetailRow("Distance from Campus", "${listing.distanceFromCampus} miles")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Add Contact functionality */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Contact Landlord")
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(onHousingClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ListingCardPreview() {
    MaterialTheme {
        HousingListingCard(
            listing = mockHousingListings[0],
            onHousingClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MaterialTheme {
        HousingDetailScreen(housingId = 1, onBackClick = {})
    }
}

