package com.zulalnb.todolistdatabase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// İnternet olmayan durumlarda uygulamaların çalışması
// ya da verilerin cihaz üzerinde tutulması gereken
// durumlarda local veri tabanlarını kullanıyoruz.

class MainActivity : AppCompatActivity() {

    private lateinit var todoDAO: TodoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        todoDAO = TodoDAO(this)

        // CREATE
        addTodo()

        // READ
        getAllTodos()

        // UPDATE
        // updateTodo()

        // DELETE
        // deleteTodo()
    }

    // CREATE
    private fun addTodo() {
        val todo = Todo().apply {
            title = "title 1"
            content = "content 1"
        }

        todoDAO.add(todo)
    }

    // READ
    private fun getAllTodos() {
        val todoList = todoDAO.getAll()

        todoList.forEach { todo ->
            println("ID: ${todo.id}")
            println("Title: ${todo.title}")
            println("Content: ${todo.content}")
        }
    }

    // UPDATE
    private fun updateTodo() {
        val todo = Todo().apply {
            id = 1
            title = "title 1 updated"
            content = "content 1 updated"
        }

        todoDAO.update(todo)
    }

    // DELETE
    private fun deleteTodo() {
        todoDAO.delete(1)
    }
}