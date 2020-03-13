package edu.mines.csci448.kcooper_A2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Game(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var date: Date = Date(),
                var winner: String? = null,
                var loser: String? = null)