package com.example.student_housing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.student_housing.ui.screens.HomeScreen
import com.example.student_housing.ui.screens.HousingDetailScreen
import androidx.compose.runtime.*


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

