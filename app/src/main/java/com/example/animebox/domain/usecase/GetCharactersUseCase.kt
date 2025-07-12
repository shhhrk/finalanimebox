package com.example.animebox.domain.usecase

import com.example.animebox.domain.model.Character
import com.example.animebox.R
import kotlin.random.Random

class GetCharactersUseCase {
    fun getRandomCharacterFromList(characters: List<Character>): Character {
        val random = Random.nextInt(1000)
        return when {
            random < 350 -> characters[0]
            random < 700 -> characters[1]
            random < 825 -> characters[2]
            random < 950 -> characters[3]
            else -> characters[4]
        }
    }

    fun getNarutoCharacters() = listOf(
        Character("Хината", "Обычная", R.drawable.hinata),
        Character("Цунадэ", "Обычная", R.drawable.tsunade),
        Character("Сакура", "Редкая", R.drawable.sakura),
        Character("Рин", "Редкая", R.drawable.rin),
        Character("Кушина", "Легендарная", R.drawable.kushina)
    )
    fun getKaguyaCharacters() = listOf(
        Character("Кагуя", "Обычная", R.drawable.kaguya),
        Character("Мико", "Обычная", R.drawable.miko),
        Character("Чика", "Редкая", R.drawable.chika),
        Character("Нагиса", "Редкая", R.drawable.nagisa),
        Character("Ай", "Легендарная", R.drawable.ai)
    )
    fun getChainsawmanCharacters() = listOf(
        Character("Химено", "Обычная", R.drawable.himeno),
        Character("Кобени", "Обычная", R.drawable.kobeni),
        Character("Пауэр", "Редкая", R.drawable.power),
        Character("Почита", "Редкая", R.drawable.pochita),
        Character("Макима", "Легендарная", R.drawable.makima)
    )
    fun getBocchiCharacters() = listOf(
        Character("Кита", "Обычная", R.drawable.kita),
        Character("Рё", "Обычная", R.drawable.ryo),
        Character("Хитори", "Редкая", R.drawable.hitori),
        Character("Па-Сан", "Редкая", R.drawable.pasan),
        Character("Кикури", "Легендарная", R.drawable.kikuri)
    )
    fun getJojoCharacters() = listOf(
        Character("Джотаро", "Обычная", R.drawable.jotaro),
        Character("Цезарь", "Обычная", R.drawable.caesar),
        Character("Джоске", "Редкая", R.drawable.joske),
        Character("Дио", "Редкая", R.drawable.dio),
        Character("Спидвагон", "Легендарная", R.drawable.speedwagon)
    )
    fun getWindbreakerCharacters() = listOf(
        Character("Хаято", "Обычная", R.drawable.hayato),
        Character("Котоха", "Обычная", R.drawable.kotoha),
        Character("Тогаме", "Редкая", R.drawable.togame),
        Character("Умемия", "Редкая", R.drawable.umemiya),
        Character("Кирию", "Легендарная", R.drawable.kiry)
    )
    fun getDungeonMeshiCharacters() = listOf(
        Character("Фалин", "Обычная", R.drawable.falin),
        Character("Сенши", "Обычная", R.drawable.senshi),
        Character("Инутаде", "Редкая", R.drawable.inutade),
        Character("Изутсуми", "Редкая", R.drawable.izutsumi),
        Character("Марсиль", "Легендарная", R.drawable.marcille)
    )
} 