package com.zulalnb.todolistdatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class TodoDAO(context: Context) {
    private val databaseHelper: DatabaseOpenHelper
    private var database: SQLiteDatabase? = null

    init {
        databaseHelper = DatabaseOpenHelper(context, "TodoDb", null, 1)
    }

    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        if (database != null && database!!.isOpen) {
            database!!.close()
        }
    }

    fun getAll(): ArrayList<Todo> {
        val todoList = ArrayList<Todo>()
        open()

        val sql = """SELECT * FROM Todo"""
        val c = database!!.rawQuery(sql, null)

        c.use {
            if (it.moveToFirst()) {
                do {
                    val todo = Todo().apply {
                        id = it.getInt(it.getColumnIndexOrThrow("id"))
                        title = it.getString(it.getColumnIndexOrThrow("title"))
                        content = it.getString(it.getColumnIndexOrThrow("content"))
                    }

                    todoList.add(todo)

                } while (it.moveToNext())
            }
        }

        close()

        return todoList
    }

    fun add(todo: Todo) {
        val values = ContentValues().apply {
            put("title", todo.title)
            put("content", todo.content)
        }

        open()

        database!!.insert("todo", null, values)

        close()
    }

    fun update(todo: Todo) {
        val values = ContentValues().apply {
            put("title", todo.title)
            put("content", todo.content)
        }

        open()

        database!!.update(
            "todo",
            values,
            "id = ?",
            arrayOf(todo.id.toString())
        )

        close()
    }

    fun delete(id: Int) {
        open()

        database!!.delete("todo", "id = ?", arrayOf(id.toString()))

        close()
    }
}