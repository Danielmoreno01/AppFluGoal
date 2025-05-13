package com.example.flugoal


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.compose.foundation.border

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Path

import androidx.compose.ui.text.TextStyle


@Composable
fun LoginScreen(navController: NavController) {
    val imageUrls = listOf(
        "https://drive.google.com/uc?export=download&id=1Wy3OERgKsQhthB-mtyhhQzjWKPNEomf8",
        "https://drive.google.com/uc?export=download&id=1908z-047YryOk8MLo2xwMrCqeuDBsUjJ",
        "https://drive.google.com/uc?export=download&id=116CwXTtht4xpMgBplr0JZLaK6aqcquQ6"
    )

    // Define colors to match the design
    val tealColor = Color(0xFF9FE0DC)
    val orangeColor = Color(0xFFE85D2A)
    val darkNavyColor = Color(0xFF1A2B3D)

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = screenHeight * 0.5f // Made carousel larger

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background diagonal split (teal and orange)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height * 0.35f)
                close()
            }
            drawPath(
                path = path,
                color = tealColor
            )

            val orangePath = Path().apply {
                moveTo(0f, size.height * 0.35f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(
                path = orangePath,
                color = orangeColor
            )
        }

        // Login Card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top spacer
            Spacer(modifier = Modifier.height(16.dp))

            // Carousel positioned more to the top
            ImageCarousel(imageUrls = imageUrls, imageHeight = imageHeight)

            // Login card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Account header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = darkNavyColor,
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "My Account",
                            style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp
                            )
                        )
                    }

                    // Profile icon
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(top = 16.dp, bottom = 24.dp)
                            .background(Color.LightGray, CircleShape)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // Login fields
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        label = { Text("Login") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = Color.Gray
                            )
                        },
                        trailingIcon = {
                            Switch(
                                checked = true,
                                onCheckedChange = {},
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = orangeColor,
                                    checkedTrackColor = orangeColor.copy(alpha = 0.5f)
                                )
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = orangeColor
                        )
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password",
                                tint = Color.Gray
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = orangeColor
                        )
                    )

                    // Forgot password link
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Forgot password?",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        )
                    }

                    // Sign in button
                    Button(
                        onClick = { navController.navigate("logueo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = orangeColor
                        )
                    ) {
                        Text(
                            text = "Sign in",
                            style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }

            // Register button at bottom
            TextButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "No tienes cuenta? Regístrate",
                    style = TextStyle(
                        color = darkNavyColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(imageUrls: List<String>, imageHeight: Dp) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Cambio automático de imagen cada 3 segundos
    LaunchedEffect(Unit) {
        while (true) {
            delay(7000)
            val nextIndex = (listState.firstVisibleItemIndex + 1) % imageUrls.size
            coroutineScope.launch {
                listState.animateScrollToItem(nextIndex)
            }
        }
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * 0.75f
    val sidePadding = (screenWidth - itemWidth) / 2

    Box(modifier = Modifier.height(imageHeight)) {
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = sidePadding),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(imageUrls.size) { index ->
                val painter = rememberAsyncImagePainter(imageUrls[index])
                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .height(imageHeight)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFe0f7fa), // azul claro
                                    Color.White
                                )
                            )
                        )
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Imagen carrusel",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }
        }
    }
}
