package com.labs15.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.labs15.model.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    var allContacts by remember {
        mutableStateOf(
            listOf(
                Contact(1, "Мику", "+7 900 111-22-33", "WHAT@mail.ru", true),
                Contact(2, "Алексей", "+7 900 222-33-44", "alex@mail.ru"),
                Contact(3, "Борис", "+7 900 333-44-55", "boris@mail.ru"),
                Contact(4, "НЕ ЗВОНИТЬ!", "+7 800 555-35-35", "nope@mail.ru"),
                Contact(5, "Григорий", "+7 900 555-66-77", "FNAF9@mail.ru"),
                Contact(6, "Эммет", "+7 900 777-33-87", "LegoMovie@mail.ru", true),
                Contact(7, "Алан", "+7 900 777-88-99", "FoxIDK@mail.ru"),
                Contact(8, "Гастер", "+7 666 666-66-66", "WDGaster@mail.ru", true),
                Contact(9, "Луиджи", "+7 900 999-00-11", "green@mail.ru"),
                Contact(10, "Айво", "+7 900 000-11-22", "Egg@mail.ru"),
                Contact(11, "Уильям", "+7 900 198-87-87", "TheFUNone@mail.ru", true),
                Contact(12, "Питер", "+7 900 234-56-78", "spdrmn@mail.ru")
            )
        )
    }

    var searchQuery by remember { mutableStateOf("") }

    val filteredContacts = allContacts
        .filter { it.name.contains(searchQuery, ignoreCase = true) }
        .sortedBy { it.name }

    val groupedContacts = filteredContacts.groupBy { it.name.first().uppercaseChar() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Контакты") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Назад") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск по имени") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                groupedContacts.forEach { (initial, contacts) ->
                    item(key = "header_$initial") {
                        Text(
                            text = initial.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    items(contacts, key = { it.id }) { contact ->
                        ContactItem(
                            contact = contact,
                            onClick = {
                                Toast.makeText(context, "${contact.name}, ${contact.email}", Toast.LENGTH_SHORT).show()
                            },
                            onFavoriteClick = {
                                allContacts = allContacts.map {
                                    if (it.id == contact.id) it.copy(isFavorite = !it.isFavorite) else it
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color = Color(0xFF0000F3), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contact.name.first().toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = contact.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = contact.phone, fontSize = 14.sp, color = Color.Gray)
        }

        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (contact.isFavorite) Icons.Filled.Star else Icons.Filled.Star,
                contentDescription = "Избранное",
                tint = if (contact.isFavorite) Color(0xFF0000F7) else Color.Gray
            )
        }
    }
}