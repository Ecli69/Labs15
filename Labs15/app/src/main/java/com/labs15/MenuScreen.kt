package com.labs15

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.labs15.screens.ContactListScreen
import com.labs15.screens.TaskListScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MenuScreen(
    onTasks: () -> Unit,
    onContacts: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onTasks,
            shape = RoundedCornerShape(0.dp), // Прямоугольные кнопки
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Задания 1-4. Список дел")
        }

        Button(
            onClick = onContacts,
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Задание 5. Контакты")
        }
    }
}

@Composable
fun AppNavigation() {
    var screen by remember { mutableStateOf("menu") }


    when (screen) {
        "menu" -> MenuScreen(
            onTasks = { screen = "tasks" },
            onContacts = { screen = "contacts" }
        )
        "tasks" -> TaskListScreen(onBack = { screen = "menu" })
        "contacts" -> ContactListScreen(onBack = { screen = "menu" })
    }
}