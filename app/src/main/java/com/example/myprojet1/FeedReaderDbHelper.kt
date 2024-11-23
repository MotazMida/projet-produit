package com.example.myprojet1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Définition de la requête SQL pour créer la table
private const val SQL_CREATE_ENTRIES = """
    CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_NAME} (
        ${FeedReaderContract.FeedEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${FeedReaderContract.FeedEntry.COLUMN_NAME_NOM} TEXT NOT NULL,
        ${FeedReaderContract.FeedEntry.COLUMN_NAME_PRIX} REAL NOT NULL,
        ${FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION} TEXT,
        ${FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE} INTEGER
    )
"""

// Requête SQL pour supprimer la table si elle existe
private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"

// Classe pour gérer la base de données
class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Appelée lors de la création de la base de données
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    // Appelée lors de la mise à jour de la base de données
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES) // Supprime la table existante
        onCreate(db) // Recrée la table
    }

    companion object {
        // Version de la base de données (à incrémenter en cas de modification du schéma)
        const val DATABASE_VERSION = 1
        // Nom de la base de données
        const val DATABASE_NAME = "product.db"
    }
}
