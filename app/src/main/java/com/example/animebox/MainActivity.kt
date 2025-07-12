package com.example.animebox

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import com.example.animebox.data.AppDatabase
import com.example.animebox.data.CharacterEntity
import com.example.animebox.data.CrystalsEntity
import com.example.animebox.domain.model.Character
import com.example.animebox.ui.components.AdDialog
import com.example.animebox.ui.components.CharacterDialog
import com.example.animebox.ui.theme.AnimeBoxTheme
import com.example.animebox.ui.theme.Mint
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animebox.ui.anime.AnimeScreen
import com.example.animebox.ui.anime.AnimeViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.ui.platform.testTag

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isDarkTheme = prefs.getBoolean("DARK_THEME", false)
        
        setContent {
            AnimeBoxTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "menu"
                    ) {
                        composable("menu") {
                            MenuScreen(db, isDarkTheme, navController)
                        }
                        composable("anime_api") {
                            AnimeApiScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuScreen(db: AppDatabase, isDarkTheme: Boolean, navController: NavHostController) {
    var crystals by remember { mutableStateOf(0) }
    var showCharacterDialog by remember { mutableStateOf<Character?>(null) }
    var showAdDialog by remember { mutableStateOf(false) }
    var isAdButtonEnabled by remember { mutableStateOf(true) }
    var showAnimeScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        crystals = db.crystalsDao().getCrystals()?.amount ?: 0
    }

    val narutoCharacters = listOf(
        Character("Хината", "Обычная", R.drawable.hinata),
        Character("Цунадэ", "Обычная", R.drawable.tsunade),
        Character("Сакура", "Редкая", R.drawable.sakura),
        Character("Рин", "Редкая", R.drawable.rin),
        Character("Кушина", "Легендарная", R.drawable.kushina)
    )
    val kaguyaCharacters = listOf(
        Character("Кагуя", "Обычная", R.drawable.kaguya),
        Character("Мико", "Обычная", R.drawable.miko),
        Character("Чика", "Редкая", R.drawable.chika),
        Character("Нагиса", "Редкая", R.drawable.nagisa),
        Character("Ай", "Легендарная", R.drawable.ai)
    )
    val chainsawmanCharacters = listOf(
        Character("Химено", "Обычная", R.drawable.himeno),
        Character("Кобени", "Обычная", R.drawable.kobeni),
        Character("Пауэр", "Редкая", R.drawable.power),
        Character("Почита", "Редкая", R.drawable.pochita),
        Character("Макима", "Легендарная", R.drawable.makima)
    )
    val bocchitherockerCharacters = listOf(
        Character("Кита", "Обычная", R.drawable.kita),
        Character("Рё", "Обычная", R.drawable.ryo),
        Character("Хитори", "Редкая", R.drawable.hitori),
        Character("Па-Сан", "Редкая", R.drawable.pasan),
        Character("Кикури", "Легендарная", R.drawable.kikuri)
    )
    val jojoCharacters = listOf(
        Character("Джотаро", "Обычная", R.drawable.jotaro),
        Character("Цезарь", "Обычная", R.drawable.caesar),
        Character("Джоске", "Редкая", R.drawable.joske),
        Character("Дио", "Редкая", R.drawable.dio),
        Character("Спидвагон", "Легендарная", R.drawable.speedwagon)
    )
    val windbreakerCharacters = listOf(
        Character("Хаято", "Обычная", R.drawable.hayato),
        Character("Котоха", "Обычная", R.drawable.kotoha),
        Character("Тогаме", "Редкая", R.drawable.togame),
        Character("Умемия", "Редкая", R.drawable.umemiya),
        Character("Кирию", "Легендарная", R.drawable.kiry)
    )
    val dungeonmeshiCharacters = listOf(
        Character("Фалин", "Обычная", R.drawable.falin),
        Character("Сенши", "Обычная", R.drawable.senshi),
        Character("Инутаде", "Редкая", R.drawable.inutade),
        Character("Изутсуми", "Редкая", R.drawable.izutsumi),
        Character("Марсиль", "Легендарная", R.drawable.marcille)
    )

    fun openAnimeBox(characters: List<Character>) {
        if (crystals >= 50) {
            crystals -= 50
            scope.launch {
                db.crystalsDao().setCrystals(CrystalsEntity(amount = crystals))
                val random = Random.nextInt(1000)
                val obtainedCharacter = when {
                    random < 350 -> characters[0] // обычный 1
                    random < 700 -> characters[1] // обычный 2
                    random < 825 -> characters[2] // редкий 1
                    random < 950 -> characters[3] // редкий 2
                    else -> characters[4] // легендарный
                }
                showCharacterDialog = obtainedCharacter
            }
        } else {
            Toast.makeText(context, "Недостаточно кристаллов!", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = if (isDarkTheme) R.drawable.darkwallpaper else R.drawable.lightwallpaper),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = if (isDarkTheme) 0.6f else 1f),
            contentScale = ContentScale.Crop
        )
        if (isDarkTheme) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Кристаллы: $crystals💎",
                    fontSize = 24.sp,
                    color = Color.White
                )
                
                IconButton(
                    onClick = {
                        context.startActivity(
                            android.content.Intent(context, com.example.animebox.ui.settings.SettingsActivity::class.java)
                        )
                    }
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_manage),
                        contentDescription = "Настройки",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    LootboxCard(imageRes = R.drawable.naruto, title = "Наруто", onClick = { openAnimeBox(narutoCharacters) })
                    LootboxCard(imageRes = R.drawable.loveiswar, title = "Кагуя хочет влюбиться", onClick = { openAnimeBox(kaguyaCharacters) })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    LootboxCard(imageRes = R.drawable.chainsawman, title = "Человек-бензопила", onClick = { openAnimeBox(chainsawmanCharacters) })
                    LootboxCard(imageRes = R.drawable.bocchitherocker, title = "Тихоня-рокер", onClick = { openAnimeBox(bocchitherockerCharacters) })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    LootboxCard(imageRes = R.drawable.jojo, title = "Джоджо", onClick = { openAnimeBox(jojoCharacters) })
                    LootboxCard(imageRes = R.drawable.windbreaker, title = "Ветролом", onClick = { openAnimeBox(windbreakerCharacters) })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    LootboxCard(imageRes = R.drawable.dungeonmeshi, title = "Подземелья вкусностей", onClick = { openAnimeBox(dungeonmeshiCharacters) })
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigate("anime_api") },
                    modifier = Modifier.size(82.dp).testTag("api_button"),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.apibut),
                        contentDescription = "Аниме онлайн (API)",
                        modifier = Modifier.size(82.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .clip(CircleShape)
                        .border(width = 3.dp, color = Color(0xFFCCCCCC), shape = CircleShape)
                        .clickable {
                            context.startActivity(
                                android.content.Intent(context, com.example.animebox.ui.collection.CollectionActivity::class.java)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.q),
                        contentDescription = "Моя коллекция персонажей",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            val adButtonModifier = if (isAdButtonEnabled) {
                Modifier
                    .width(140.dp)
                    .height(44.dp)
                    .clickable {
                        isAdButtonEnabled = false
                        showAdDialog = true
                    }
            } else {
                Modifier
                    .width(140.dp)
                    .height(44.dp)
            }
            Box(
                modifier = adButtonModifier
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = Color(0xE6FFFFFF),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Реклама",
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        showCharacterDialog?.let { character ->
            CharacterDialog(
                character = character,
                onDismiss = { showCharacterDialog = null },
                onConfirm = {
                    scope.launch {
                        val exists = db.characterDao().getByNameAndRarity(character.name, character.rarity)
                        if (exists == null) {
                            db.characterDao().insert(
                                CharacterEntity(
                                    name = character.name,
                                    rarity = character.rarity,
                                    imagePath = character.imageRes.toString()
                                )
                            )
                        }
                    }
                }
            )
        }
        if (showAdDialog) {
            AdDialog(
                onDismiss = { 
                    showAdDialog = false
                    isAdButtonEnabled = true
                },
                onComplete = {
                    crystals += 3000
                    scope.launch {
                        db.crystalsDao().setCrystals(CrystalsEntity(amount = crystals))
                    }
                    Toast.makeText(context, "+3000 кристаллов💎!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    if (showAnimeScreen) {
        val viewModel: AnimeViewModel = hiltViewModel()
        LaunchedEffect(Unit) { viewModel.loadAnime() }
        Box(modifier = Modifier.fillMaxSize()) {
            AnimeScreen(viewModel = viewModel)
            Button(
                onClick = { showAnimeScreen = false },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            ) {
                Text("Назад")
            }
        }
    }
}

@Composable
fun AnimeApiScreen(navController: NavHostController) {
    val viewModel: AnimeViewModel = hiltViewModel()
    LaunchedEffect(Unit) { viewModel.loadAnime() }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_revert),
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Аниме онлайн (API)", style = MaterialTheme.typography.titleLarge)
        }
        AnimeScreen(viewModel = viewModel)
    }
}

@Composable
fun LootboxCard(imageRes: Int, title: String, onClick: () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()
    Column(
        modifier = Modifier
            .padding(6.dp)
            .width(110.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}