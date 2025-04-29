package com.example.student_housing.model

import com.example.student_housing.R

data class HousingListing(
    val id: Int,
    val title: String,
    val address: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Double,
    val distanceFromCampus: Double,
    val amenities: List<String>,
    val type: String, // "Apartment", "House", "Studio"
    val petsAllowed: Boolean,
    val furnished: Boolean,
    val imageResId: Int // Resource ID for the housing image
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
        distanceFromCampus = 0.5,
        amenities = listOf("Washer/Dryer", "Parking", "Gym"),
        type = "Apartment",
        petsAllowed = true,
        furnished = true,
        imageResId = R.drawable.cozy2bruniv
    ),
    HousingListing(
        id = 2,
        title = "Spacious 4BR House",
        address = "456 University Dr",
        price = 1500,
        bedrooms = 4,
        bathrooms = 2.5,
        distanceFromCampus = 1.2,
        amenities = listOf("Backyard", "Garage", "Fireplace"),
        type = "House",
        petsAllowed = true,
        furnished = false,
        imageResId = R.drawable.sapcious_4br
    ),
    HousingListing(
        id = 3,
        title = "Modern Studio Apartment",
        address = "789 Student Lane",
        price = 650,
        bedrooms = 0,
        bathrooms = 1.0,
        distanceFromCampus = 0.3,
        amenities = listOf("Elevator", "Security", "Bike Storage"),
        type = "Studio",
        petsAllowed = false,
        furnished = true,
        imageResId = R.drawable.modernstudio
    ),
    HousingListing(
        id = 4,
        title = "Luxury 3BR Penthouse",
        address = "321 Campus View",
        price = 2000,
        bedrooms = 3,
        bathrooms = 2.0,
        distanceFromCampus = 0.8,
        amenities = listOf("Pool", "Rooftop Deck", "Concierge"),
        type = "Apartment",
        petsAllowed = true,
        furnished = true,
        imageResId = R.drawable.lux_ph
    ),
    HousingListing(
        id = 5,
        title = "Quaint 1BR Cottage",
        address = "654 Garden St",
        price = 700,
        bedrooms = 1,
        bathrooms = 1.0,
        distanceFromCampus = 1.5,
        amenities = listOf("Garden", "Patio", "Storage"),
        type = "House",
        petsAllowed = true,
        furnished = false,
        imageResId = R.drawable.quaint_ctg
    ),
    HousingListing(
        id = 6,
        title = "Efficient Micro-Studio",
        address = "987 Student Commons",
        price = 550,
        bedrooms = 0,
        bathrooms = 1.0,
        distanceFromCampus = 0.2,
        amenities = listOf("Study Room", "Laundry", "Bike Rack"),
        type = "Studio",
        petsAllowed = false,
        furnished = true,
        imageResId = R.drawable.micro_studio
    )
) 