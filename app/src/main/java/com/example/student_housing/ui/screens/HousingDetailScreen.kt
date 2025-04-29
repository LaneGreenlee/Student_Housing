package com.example.student_housing.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_housing.model.HousingListing
import com.example.student_housing.model.mockHousingListings
import com.example.student_housing.utils.EmailUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HousingDetailScreen(housingId: Int, onBackClick: () -> Unit) {
    val listing = mockHousingListings.find { it.id == housingId } ?: return
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(listing.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = androidx.compose.ui.graphics.vector.rememberVectorPainter(
                                Icons.Default.ArrowBack
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = listing.imageResId),
                    contentDescription = listing.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
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
            DetailRow("Property Type", listing.type)
            DetailRow("Pets Allowed", if (listing.petsAllowed) "Yes" else "No")
            DetailRow("Furnished", if (listing.furnished) "Yes" else "No")
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Amenities:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            listing.amenities.forEach { amenity ->
                Text(
                    text = "• $amenity",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { 
                    val emailIntent = EmailUtils.createEmailIntent(context, listing)
                    context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
                },
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