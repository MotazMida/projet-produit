package com.example.myprojet1
import android.provider.BaseColumns

object FeedReaderContract {
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "produit"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NOM = "nom_produit"
        const val COLUMN_NAME_PRIX = "prix"
        const val COLUMN_NAME_DESCRIPTION = "description"
        const val COLUMN_NAME_IMAGE = "image"

    }
}