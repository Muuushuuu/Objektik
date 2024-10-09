package com.example.objektik.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


// Utilisation de la police Roboto par défaut dans Android
val Typography = Typography(
    displayLarge = TextStyle(  // Header
        fontFamily = FontFamily.Default,  // Roboto par défaut
        fontWeight = FontWeight.Bold,
        fontSize = 38.sp
    ),
    displayMedium = TextStyle(  // Title
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(  // Subtitle
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(  // Body Bold
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(  // Body Regular
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)