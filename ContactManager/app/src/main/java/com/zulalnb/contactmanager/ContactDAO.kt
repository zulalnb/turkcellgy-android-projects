package com.zulalnb.contactmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ContactDAO(context: Context) {

    private val databaseHelper: DatabaseOpenHelper = DatabaseOpenHelper(
        context.applicationContext,
        "ContactDb",
        null,
        1
    )

    private fun <T> withDatabase(
        operation: (SQLiteDatabase) -> T
    ): T {
        val database = databaseHelper.writableDatabase
        return operation(database)
    }

    private fun cursorToContact(
        cursor: android.database.Cursor
    ): Contact {
        val createdAtIdx = cursor.getColumnIndex("createdAt")
        val updatedAtIdx = cursor.getColumnIndex("updatedAt")

        return Contact(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
            lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
            email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
            createdAt = if (createdAtIdx != -1) cursor.getString(createdAtIdx) else null,
            updatedAt = if (updatedAtIdx != -1) cursor.getString(updatedAtIdx) else null
        )
    }

    fun getAll(): ArrayList<Contact> {
        return withDatabase { database ->
            val contacts = ArrayList<Contact>()

            val sql = """SELECT * FROM Contact 
                |ORDER BY firstName ASC, 
                |lastName ASC""".trimMargin()
            val cursor = database.rawQuery(sql, null)

            cursor.use {
                if (it.moveToFirst()) {
                    do {
                        contacts.add(cursorToContact(it))
                    } while (it.moveToNext())
                }
            }

            contacts
        }
    }

    fun getByOffset(limit: Int, offset: Int): ArrayList<Contact> {
        return withDatabase { database ->
            val contacts = ArrayList<Contact>()

            val sql = """SELECT * FROM Contact 
                |ORDER BY firstName ASC, 
                |lastName ASC 
                |LIMIT ? 
                |OFFSET ?""".trimMargin()
            val cursor = database.rawQuery(
                sql,
                arrayOf(limit.toString(), offset.toString())
            )

            cursor.use {
                if (it.moveToFirst()) {
                    do {
                        contacts.add(cursorToContact(it))
                    } while (it.moveToNext())
                }
            }

            contacts
        }
    }

    fun getById(id: Int): Contact? {
        return withDatabase { database ->
            val sql = "SELECT * FROM Contact WHERE id = ?"

            val cursor = database.rawQuery(
                sql,
                arrayOf(id.toString())
            )

            cursor.use {
                if (it.moveToFirst()) {
                    cursorToContact(it)
                } else {
                    null
                }
            }
        }
    }

    fun isEmailExists(email: String, excludeId: Int? = null): Boolean {
        return withDatabase { database ->
            val sql = if (excludeId != null) {
                "SELECT COUNT(*) FROM Contact WHERE email = ? AND id != ?"
            } else {
                "SELECT COUNT(*) FROM Contact WHERE email = ?"
            }

            val args = if (excludeId != null) {
                arrayOf(email, excludeId.toString())
            } else {
                arrayOf(email)
            }

            val cursor = database.rawQuery(sql, args)
            var count = 0
            cursor.use {
                if (it.moveToFirst()) {
                    count = it.getInt(0)
                }
            }
            count > 0
        }
    }

    fun add(contact: Contact): Long {
        return withDatabase { database ->
            val values = ContentValues().apply {
                put("firstName", contact.firstName)
                put("lastName", contact.lastName)
                put("email", contact.email)
            }

            database.insert("Contact", null, values)
        }
    }

    fun update(contact: Contact): Int {
        return withDatabase { database ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val currentTimestamp = dateFormat.format(Date())

            val values = ContentValues().apply {
                put("firstName", contact.firstName)
                put("lastName", contact.lastName)
                put("email", contact.email)
                put("updatedAt", currentTimestamp)
            }

            database.update(
                "Contact",
                values,
                "id = ?",
                arrayOf(contact.id.toString())
            )
        }
    }

    fun delete(id: Int): Int {
        return withDatabase { database ->
            database.delete(
                "Contact",
                "id = ?",
                arrayOf(id.toString())
            )
        }
    }

    fun close() {
        databaseHelper.close()
    }
}
