package com.example.flugoal.Screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun PerfilScreen(navController: NavHostController) {
    var userName by remember { mutableStateOf("Cargando...") }
    var userPhotoUrl by remember { mutableStateOf<String?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        // TODO: Implementar subida de imagen y actualización en la API
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(selectedImageUri)
                        .build()
                ),
                contentDescription = "Avatar del usuario",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
        } else if (!userPhotoUrl.isNullOrEmpty()) {
            AsyncImage(
                model = userPhotoUrl,
                contentDescription = "Avatar del usuario",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
        } else {
            AsyncImage(
                model = "https://cdn-icons-png.flaticon.com/512/565/565547.png",
                contentDescription = "Avatar predeterminado",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = userName,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { launcher.launch("image/*") },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text(text = "Cambiar foto", color = Color.White)
        }
    }
}
