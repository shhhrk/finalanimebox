package com.example.animebox.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.animebox.ui.theme.AnimeBoxTheme
import android.app.Activity
import android.content.Intent

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isDarkTheme = prefs.getBoolean("DARK_THEME", false)
        
        setContent {
            var isDark by remember { mutableStateOf(isDarkTheme) }

            AnimeBoxTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingsScreen(
                        isDark = isDark,
                        onThemeChange = { checked ->
                            isDark = checked
                            prefs.edit().putBoolean("DARK_THEME", checked).apply()
                            AppCompatDelegate.setDefaultNightMode(
                                if (checked) AppCompatDelegate.MODE_NIGHT_YES
                                else AppCompatDelegate.MODE_NIGHT_NO
                            )
                        },
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    isDark: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                val intent = Intent(context, Class.forName("com.example.animebox.MainActivity"))
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                (context as? Activity)?.finish()
            }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_revert),
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(Modifier.height(40.dp))

        Text(
            text = if (isDark) "Тёмная тема" else "Светлая тема",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(16.dp))

        Switch(
            checked = isDark,
            onCheckedChange = onThemeChange
        )
    }
} 