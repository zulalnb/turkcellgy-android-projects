package com.zulalnb.contactmanager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """CREATE TABLE Contact (
            |id INTEGER PRIMARY KEY AUTOINCREMENT,
            |firstName TEXT NOT NULL,
            |lastName TEXT NOT NULL,
            |email TEXT NOT NULL UNIQUE,
            |createdAt TEXT NOT NULL DEFAULT (datetime('now', 'localtime')),
            |updatedAt TEXT NOT NULL DEFAULT (datetime('now', 'localtime'))
            |)""".trimMargin()

        db!!.execSQL(sql)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {

    }
}
