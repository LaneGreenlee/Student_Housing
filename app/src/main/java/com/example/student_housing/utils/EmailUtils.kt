package com.example.student_housing.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.student_housing.model.HousingListing

object EmailUtils {
    private const val TEST_EMAIL = "studentpad.testing@gmail.com"
    private const val EMAIL_SUBJECT = "StudentPad - Housing Inquiry"

    fun createEmailIntent(context: Context, listing: HousingListing): Intent {
        val emailBody = """
            Hello,
            
            I am interested in the following property:
            
            Title: ${listing.title}
            Address: ${listing.address}
            Price: $${listing.price}/month
            Bedrooms: ${listing.bedrooms}
            Bathrooms: ${listing.bathrooms}
            
            Please provide more information about this property.
            
            Thank you,
            [Your Name]
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(TEST_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
            putExtra(Intent.EXTRA_TEXT, emailBody)
        }

        return intent
    }
} 