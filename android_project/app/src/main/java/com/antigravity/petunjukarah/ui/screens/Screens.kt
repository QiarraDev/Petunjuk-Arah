package com.antigravity.petunjukarah.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.antigravity.petunjukarah.model.Destination
import com.antigravity.petunjukarah.model.Note
import com.antigravity.petunjukarah.model.Trip
import com.antigravity.petunjukarah.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(onNavigateToHome: () -> Unit = {}) {
    var notes by remember { mutableStateOf<List<Note>>(emptyList()) }
    var popularDestinations by remember { mutableStateOf<List<Destination>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {
            popularDestinations = RetrofitClient.instance.getDestinations().take(10)
            notes = RetrofitClient.instance.getNotes()
        } catch (e: Exception) {
        } finally {
            isLoading = false
        }
    }

    if (selectedNote != null) {
        EditNoteDialog(
            note = selectedNote!!,
            onDismiss = { selectedNote = null },
            onSave = { updatedNote ->
                scope.launch {
                    try {
                        val id = updatedNote.id ?: 0L
                        RetrofitClient.instance.updateNote(id, updatedNote)
                        Toast.makeText(context, "Catatan diperbarui!", Toast.LENGTH_SHORT).show()
                        notes = RetrofitClient.instance.getNotes()
                        selectedNote = null
                    } catch (e: Exception) {
                        Toast.makeText(context, "Gagal memperbarui catatan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFC)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Header(onNavigateToHome) }
        item { HeroCard() }
        item { SectionTitle("Destinasi Populer") }
        item { 
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(240.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF6366F1))
                }
            } else if (popularDestinations.isEmpty()) {
                PopularDestinations(getMockDestinations())
            } else {
                PopularDestinations(popularDestinations) 
            }
        }
        item { SectionTitle("Catatan Anda") }
        if (isLoading) {
            item { LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFF6366F1)) }
        } else if (notes.isEmpty()) {
            items(getMockNotes()) { note -> NoteCard(note, onClick = { selectedNote = it }) }
        } else {
            items(notes) { note -> NoteCard(note, onClick = { selectedNote = it }) }
        }
    }
}

@Composable
fun EditNoteDialog(note: Note, onDismiss: () -> Unit, onSave: (Note) -> Unit) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Catatan", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Isi Catatan") },
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(note.copy(title = title, content = content)) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
            ) {
                Text("Simpan", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = Color.Gray)
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(onNavigateToHome: () -> Unit = {}, onBack: () -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var destinations by remember { mutableStateOf<List<Destination>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val categories = listOf("Semua", "Wisata", "Hotel", "Kuliner", "Fasilitas")

    LaunchedEffect(searchQuery, selectedCategory) {
        isLoading = true
        delay(300)
        try {
            val allDestinations = if (searchQuery.length >= 2) {
                RetrofitClient.instance.searchDestinations(searchQuery)
            } else {
                RetrofitClient.instance.getDestinations()
            }
            
            destinations = if (selectedCategory == "Semua") {
                allDestinations
            } else {
                allDestinations.filter { it.category.contains(selectedCategory, ignoreCase = true) }
            }
        } catch (e: Exception) {
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFC)).padding(16.dp)) {
        Header(onNavigateToHome = onNavigateToHome, onBack = onBack)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Eksplorasi", fontSize = 28.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari lokasi, hotel, atau kuliner...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF6366F1)) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.Gray)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF6366F1),
                unfocusedBorderColor = Color(0xFFE2E8F0)
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF6366F1),
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = Color(0xFF64748B)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = Color(0xFFE2E8F0),
                        selectedBorderColor = Color(0xFF6366F1)
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF6366F1))
            }
        } else if (destinations.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.SearchOff, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Text("Hasil tidak ditemukan", color = Color.Gray, fontWeight = FontWeight.Medium)
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxSize()) {
                items(destinations) { dest ->
                    DestinationDetailedCard(dest)
                }
            }
        }
    }
}

@Composable
fun DestinationDetailedCard(dest: Destination) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth().clickable {
            Toast.makeText(context, "Detail: ${dest.name}", Toast.LENGTH_SHORT).show()
        },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                AsyncImage(
                    model = dest.imageUrl ?: "https://images.unsplash.com/photo-1544644181-1484b3fdfc62?q=80&w=800",
                    contentDescription = dest.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    modifier = Modifier.padding(12.dp).align(Alignment.TopEnd),
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFFFFB800))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(dest.rating.toString(), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(dest.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F172A))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFF6366F1))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(dest.location, fontSize = 14.sp, color = Color(0xFF64748B))
                        }
                    }
                    Surface(color = Color(0xFF6366F1).copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                        Text(dest.category, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = Color(0xFF6366F1), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse("google.navigation:q=${dest.lat},${dest.lon}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        try {
                            context.startActivity(mapIntent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Navigasi ke ${dest.name}", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
                ) {
                    Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Mulai Navigasi", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// Reuse existing components from previous Screens.kt version but with the new Explore logic
@Composable
fun PopularDestinations(destinations: List<Destination>) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(destinations) { dest ->
                DestinationItemCard(dest)
            }
        }

        // Left Arrow
        if (listState.firstVisibleItemIndex > 0) {
            Surface(
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 8.dp).size(40.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.8f),
                shadowElevation = 4.dp,
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem((listState.firstVisibleItemIndex - 1).coerceAtLeast(0))
                    }
                }
            ) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "Scroll Left", modifier = Modifier.padding(8.dp), tint = Color(0xFF6366F1))
            }
        }

        // Right Arrow
        if (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 < destinations.size - 1) {
            Surface(
                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 8.dp).size(40.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.8f),
                shadowElevation = 4.dp,
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem((listState.firstVisibleItemIndex + 1).coerceAtMost(destinations.size - 1))
                    }
                }
            ) {
                Icon(Icons.Default.ChevronRight, contentDescription = "Scroll Right", modifier = Modifier.padding(8.dp), tint = Color(0xFF6366F1))
            }
        }
    }
}

@Composable
fun DestinationItemCard(dest: Destination) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.width(220.dp).height(280.dp),
        shape = RoundedCornerShape(24.dp), 
        elevation = CardDefaults.cardElevation(4.dp), 
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(140.dp).background(Color(0xFFF1F5F9))) {
                AsyncImage(
                    model = dest.imageUrl ?: "https://images.unsplash.com/photo-1544644181-1484b3fdfc62?q=80&w=800",
                    contentDescription = dest.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(dest.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 1)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF6366F1))
                    Text(dest.location, fontSize = 12.sp, color = Color(0xFF64748B))
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse("google.navigation:q=${dest.lat},${dest.lon}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        try {
                            context.startActivity(mapIntent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Google Maps tidak terpasang.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
                ) {
                    Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Petunjuk Arah", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun TripPlannerScreen(onNavigateToHome: () -> Unit = {}, onBack: () -> Unit = {}) {
    var trips by remember { mutableStateOf<List<Trip>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        try { trips = RetrofitClient.instance.getTrips() } catch (e: Exception) {} finally { isLoading = false }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFC)).padding(16.dp)) {
        Header(onNavigateToHome = onNavigateToHome, onBack = onBack)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Rencana Perjalanan", fontSize = 28.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(16.dp))
        
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF6366F1))
            }
        } else if (trips.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.EventBusy, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Text("Belum ada rencana perjalanan", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(trips) { trip -> TripCard(trip) }
            }
        }
    }
}

@Composable
fun TripCard(trip: Trip) {
    Card(modifier = Modifier.fillMaxWidth().clickable {}, shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(trip.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
            Text(trip.destination, color = Color(0xFF6366F1), fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                val durationText = if (trip.duration != null) " (${trip.duration} Hari)" else ""
                Text("${trip.startDate}$durationText", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFC)).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(40.dp))
        Box(modifier = Modifier.size(120.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Color(0xFF6366F1), Color(0xFFA855F7))))) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.fillMaxSize().padding(24.dp), tint = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Admin Traveler", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("admin@petunjukarah.com", color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        ProfileMenuItem(Icons.Default.Favorite, "Favorit Saya")
        ProfileMenuItem(Icons.Default.History, "Riwayat Perjalanan")
        ProfileMenuItem(Icons.Default.Settings, "Pengaturan")
        ProfileMenuItem(Icons.Default.ExitToApp, "Keluar", tint = Color.Red)
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String, tint: Color = Color(0xFF0F172A)) {
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clip(RoundedCornerShape(16.dp)).clickable {
        Toast.makeText(context, "$title dibuka", Toast.LENGTH_SHORT).show()
    }.background(Color.White).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = if(tint == Color.Red) Color.Red else Color(0xFF6366F1))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontWeight = FontWeight.Bold, color = tint)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
    }
}

@Composable
fun NoteCard(note: Note, onClick: (Note) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(note) }, 
        shape = RoundedCornerShape(16.dp), 
        colors = CardDefaults.cardColors(containerColor = Color.White), 
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFFA855F7).copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.StickyNote2, contentDescription = null, tint = Color(0xFFA855F7))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(note.title, fontWeight = FontWeight.Bold)
                Text(note.content, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
            }
            Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(16.dp), tint = Color.LightGray)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A), modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
}

@Composable
fun Header(onNavigateToHome: () -> Unit = {}, onBack: (() -> Unit)? = null) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBack != null) {
                IconButton(onClick = onBack, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Column {
                Text("Selamat Datang, Traveler 👋", fontSize = 14.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                Text("Petunjuk Arah", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A))
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onNavigateToHome,
                modifier = Modifier.size(48.dp).background(Color(0xFF6366F1).copy(alpha = 0.1f), CircleShape)
            ) {
                Icon(Icons.Default.Home, contentDescription = "Home", tint = Color(0xFF6366F1))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFF6366F1).copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF6366F1))
            }
        }
    }
}

@Composable
fun HeroCard() {
    Box(
        modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(28.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF6366F1), Color(0xFFA855F7))))
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text("Jelajah Lagi.", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
            Text("Temukan rute tercepat untuk\nperjalanan tak terlupakan.", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp)) {
                Text("Eksplor Peta", color = Color(0xFF6366F1), fontWeight = FontWeight.Bold)
            }
        }
        Icon(Icons.Default.Route, contentDescription = null, modifier = Modifier.size(100.dp).align(Alignment.CenterEnd).offset(x = 20.dp), tint = Color.White.copy(alpha = 0.1f))
    }
}

private fun getMockDestinations() = listOf(
    Destination(name = "Candi Borobudur", location = "Magelang", rating = 4.9, price = "50.000", description = "Candi Buddha terbesar.", category = "Wisata", lat = -7.6078, lon = 110.2037, imageUrl = "https://images.unsplash.com/photo-1544644181-1484b3fdfc62?q=80&w=800"),
    Destination(name = "Pantai Kuta", location = "Bali", rating = 4.8, price = "Gratis", description = "Pantai ikonik.", category = "Wisata", lat = -8.7176, lon = 115.1691, imageUrl = "https://images.unsplash.com/photo-1539367628448-4bc5c9d171c8?q=80&w=800"),
    Destination(name = "Gunung Bromo", location = "Probolinggo", rating = 4.9, price = "27.500", description = "Kawah spektakuler.", category = "Wisata", lat = -7.9425, lon = 112.9531, imageUrl = "https://images.unsplash.com/photo-1589308078059-be1415e6b219?q=80&w=800")
)

private fun getMockNotes() = listOf(
    Note(id = 1, title = "Rencana Ke Bali", content = "Sewa motor di bandara"),
    Note(id = 2, title = "Tiket Kereta", content = "Beli tiket Argo Dwipangga")
)
