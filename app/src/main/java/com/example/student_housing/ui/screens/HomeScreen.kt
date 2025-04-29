package com.example.student_housing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.student_housing.model.HousingListing
import com.example.student_housing.model.mockHousingListings
import com.example.student_housing.ui.components.HousingListingCard
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onHousingClick: (Int) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var minPrice by remember { mutableIntStateOf(0) }
    var maxPrice by remember { mutableIntStateOf(3000) }
    var selectedType by remember { mutableStateOf<String?>(null) }
    var petsAllowed by remember { mutableStateOf<Boolean?>(null) }
    var furnished by remember { mutableStateOf<Boolean?>(null) }
    var minBedrooms by remember { mutableIntStateOf(0) }
    var isFiltersExpanded by remember { mutableStateOf(false) }

    val filteredListings = mockHousingListings.filter { listing ->
        (searchQuery.isEmpty() || 
            listing.title.contains(searchQuery, ignoreCase = true) ||
            listing.address.contains(searchQuery, ignoreCase = true)) &&
        listing.price in minPrice..maxPrice &&
        (selectedType == null || listing.type == selectedType) &&
        (petsAllowed == null || listing.petsAllowed == petsAllowed) &&
        (furnished == null || listing.furnished == furnished) &&
        listing.bedrooms >= minBedrooms
    }

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
            // Search and Filter Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Search bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Search") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Filter toggle button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isFiltersExpanded = !isFiltersExpanded },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Filters",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Icon(
                            imageVector = if (isFiltersExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isFiltersExpanded) "Collapse filters" else "Expand filters"
                        )
                    }

                    // Animated filter section
                    AnimatedVisibility(
                        visible = isFiltersExpanded,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))

                            // Price range
                            Text("Price Range: $${minPrice} - $${maxPrice}")
                            RangeSlider(
                                value = minPrice.toFloat()..maxPrice.toFloat(),
                                onValueChange = { range ->
                                    minPrice = range.start.toInt()
                                    maxPrice = range.endInclusive.toInt()
                                },
                                valueRange = 0f..3000f,
                                steps = 30
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Property type filter
                            Text("Property Type")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                FilterChip(
                                    selected = selectedType == "Apartment",
                                    onClick = { selectedType = if (selectedType == "Apartment") null else "Apartment" },
                                    label = { Text("Apartment") }
                                )
                                FilterChip(
                                    selected = selectedType == "House",
                                    onClick = { selectedType = if (selectedType == "House") null else "House" },
                                    label = { Text("House") }
                                )
                                FilterChip(
                                    selected = selectedType == "Studio",
                                    onClick = { selectedType = if (selectedType == "Studio") null else "Studio" },
                                    label = { Text("Studio") }
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Additional filters
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                FilterChip(
                                    selected = petsAllowed == true,
                                    onClick = { petsAllowed = if (petsAllowed == true) null else true },
                                    label = { Text("Pets Allowed") }
                                )
                                FilterChip(
                                    selected = furnished == true,
                                    onClick = { furnished = if (furnished == true) null else true },
                                    label = { Text("Furnished") }
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Bedrooms filter
                            Text("Minimum Bedrooms: $minBedrooms")
                            Slider(
                                value = minBedrooms.toFloat(),
                                onValueChange = { minBedrooms = it.toInt() },
                                valueRange = 0f..4f,
                                steps = 4
                            )
                        }
                    }
                }
            }

            // Results count
            Text(
                text = "${filteredListings.size} results found",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            // Listing items
            LazyColumn {
                items(filteredListings) { listing ->
                    HousingListingCard(listing = listing, onHousingClick = onHousingClick)
                    Divider()
                }
            }
        }
    }
} 