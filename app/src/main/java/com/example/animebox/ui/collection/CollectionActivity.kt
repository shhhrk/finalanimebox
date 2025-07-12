package com.example.animebox.ui.collection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.animebox.R
import com.example.animebox.data.AppDatabase
import com.example.animebox.domain.model.Character
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

class CollectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CollectionScreen(onBack = { finish() })
                }
            }
        }
    }
}

@Composable
fun CollectionScreen(onBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val characterDao = remember { db.characterDao() }
    var characters by remember { mutableStateOf(listOf<Character>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val allCharacters = characterDao.getAll()
        characters = allCharacters.map {
            Character(
                name = it.name,
                rarity = it.rarity,
                imageRes = it.imagePath.toIntOrNull() ?: R.mipmap.ic_launcher
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBack) { Text("Назад") }
            Button(onClick = {
                scope.launch {
                    characterDao.getAll().forEach { characterDao.delete(it) }
                    characters = emptyList()
                }
            }) { Text("Очистить коллекцию") }
        }
        Spacer(Modifier.height(16.dp))
        if (characters.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Коллекция пуста", style = MaterialTheme.typography.titleLarge)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(characters) { character ->
                    CharacterListItem(character = character)
                }
            }
        }
    }
} 