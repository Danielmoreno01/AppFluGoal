package com.example.flugoal.Screen

import android.util.Log
import android.widget.CalendarView
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flugoal.Model.Meta
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MetaViewModel
import com.example.flugoal.ViewModel.MovimientoViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.*
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaMetasScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    val usuarioId = usuarioViewModel.usuarioId.collectAsState().value
    val metaViewModel: MetaViewModel = viewModel()
    val movimientoViewModel: MovimientoViewModel = viewModel()
    val metas by metaViewModel.metas.collectAsState()
    val robotoFont = FontFamily(Font(R.font.concertone))
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFE3F2FD),
        targetValue = Color(0xFFF5F5F5),
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing), RepeatMode.Reverse)
    )

    LaunchedEffect(usuarioId) {
        usuarioId?.let { metaViewModel.cargarMetasPorUsuario(it.toString()) }
    }

    Scaffold(
        containerColor = animatedColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis Metas",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF080C23),
                        fontFamily = robotoFont,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = animatedColor)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Default.Home, contentDescription = "Volver al Home")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(animatedColor)) {

            CalendarioMetas(
                metas = metas,
                onDateClicked = { clickedDate ->
                    val meta = metas.firstOrNull { it.fechaFin == clickedDate.toString() }
                    meta?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            snackbarHostState.showSnackbar("Meta: ${it.nombre}")
                        }
                    }
                }
            )

            LazyColumn {
                items(metas) { meta ->
                    MetaItemStyled(
                        meta = meta,
                        movimientoViewModel = movimientoViewModel,
                        fontFamily = robotoFont,
                        onEdit = {
                            meta.id?.let { navController.navigate("editar_meta/$it") }
                        },
                        onDelete = {
                            meta.id?.let {
                                metaViewModel.eliminarMeta(it,
                                    onSuccess = { usuarioId?.let { id -> metaViewModel.cargarMetasPorUsuario(id) } },
                                    onError = { Log.e("ListaMetasScreen", "Error: $it") }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun MetaItemStyled(
    meta: Meta,
    movimientoViewModel: MovimientoViewModel,
    fontFamily: FontFamily,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var totalIngresado by remember { mutableStateOf<Double?>(null) }

    LaunchedEffect(meta.id) {
        meta.id?.let {
            totalIngresado = movimientoViewModel.obtenerTotalIngresadoEnMetaDirecto(it)
        }
    }

    val porcentaje = if (totalIngresado != null && meta.montoTotal > 0) {
        (totalIngresado!! / meta.montoTotal).coerceAtMost(1.0)
    } else 0.0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(meta.nombre, fontWeight = FontWeight.Bold, fontFamily = fontFamily, fontSize = 18.sp)
                Text("Meta: \$${meta.montoTotal}", fontFamily = fontFamily, color = Color(0xFF333333))
                Text("Ingresado: \$${String.format("%.2f", totalIngresado ?: 0.0)}", fontFamily = fontFamily, color = Color(0xFF00796B))
                Text("Inicio: ${meta.fechaInicio} - Fin: ${meta.fechaFin}", fontFamily = fontFamily, fontSize = 12.sp)
            }

            Box(
                modifier = Modifier.size(70.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = porcentaje.toFloat(),
                    modifier = Modifier.size(70.dp),
                    color = Color(0xFF81D4FA),
                    strokeWidth = 8.dp
                )
                Text(
                    if (totalIngresado == null) "..." else "${(porcentaje * 100).toInt()}%",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = fontFamily
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}

@Composable
fun CalendarioMetas(
    metas: List<Meta>,
    onDateClicked: (LocalDate) -> Unit
) {
    val metasFechas = remember(metas) {
        metas.mapNotNull { meta ->
            runCatching { LocalDate.parse(meta.fechaFin) }.getOrNull()
        }.toSet()
    }

    val today = LocalDate.now()
    val startMonth = YearMonth.now().minusMonths(6)
    val endMonth = YearMonth.now().plusMonths(6)
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = YearMonth.now(),
        firstDayOfWeek = DayOfWeek.MONDAY
    )

    HorizontalCalendar(
        state = state,
        dayContent = { day ->
            val isMetaDate = metasFechas.contains(day.date)
            val backgroundColor = if (isMetaDate) Color(0xFF81D4FA) else Color.Transparent
            val textColor = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(backgroundColor, shape = CircleShape)
                    .clickable { onDateClicked(day.date) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = textColor,
                    fontWeight = if (isMetaDate) FontWeight.Bold else FontWeight.Normal
                )
            }
        },
        monthHeader = { month ->
            Text(
                text = month.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    .replaceFirstChar { it.uppercaseChar() } + " ${month.yearMonth.year}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
