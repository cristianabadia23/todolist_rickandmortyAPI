package com.example.myapplication

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.myapplication.persistence.dao.TodoDao
import com.example.myapplication.persistence.models.TodoModel
import com.example.myapplication.persistence.room.TodoDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class TodoDaoTest {
    private lateinit var database: TodoDatabase
    private lateinit var todoDao: TodoDao

    //@get:Rule
    //val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoDatabase::class.java
        ).allowMainThreadQueries().build()
        todoDao = database.todoTaskDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertTask_shouldInsertTask() = runBlocking {
        val task = TodoModel(
            title = "Test Task",
            description = "Test Description",
            state = "in progress",
            date = "2023-01-01"
        )

        todoDao.insertTask(task)
        val allTasks = todoDao.getAllTasks().first()

        assertEquals(1, allTasks.size)
        assertEquals("Test Task", allTasks[0].title)
    }

    @Test
    fun updateTask_shouldUpdateTask() = runBlocking {
        val task = TodoModel(
            title = "Test Task",
            description = "Test Description",
            state = "in progress",
            date = "2023-01-01"
        )
        todoDao.insertTask(task)

        val insertedTask = todoDao.getAllTasks().first()[0]
        val updatedTask = insertedTask.copy(title = "Updated Task")
        todoDao.updateTask(updatedTask)

        val allTasks = todoDao.getAllTasks().first()
        assertEquals("Updated Task", allTasks[0].title)
    }

    @Test
    fun deleteTask_shouldDeleteTask() = runBlocking {
        val task = TodoModel(
            title = "Test Task",
            description = "Test Description",
            state = "in progress",
            date = "2023-01-01"
        )
        todoDao.insertTask(task)

        val insertedTask = todoDao.getAllTasks().first()[0]
        todoDao.deleteTask(insertedTask)

        val allTasks = todoDao.getAllTasks().first()
        assertTrue(allTasks.isEmpty())
    }

    @Test
    fun getTaskById_shouldReturnCorrectTask() = runBlocking {
        todoDao.insertTask(
            TodoModel(
                title = "Task 1",
                description = "Desc 1",
                state = "in progress",
                date = "2023-01-01"
            )
        )

        todoDao.insertTask(
            TodoModel(
                title = "Task 2",
                description = "Desc 2",
                state = "in progress",
                date = "2023-01-02"
            )
        )

        val allTasks = todoDao.getAllTasks().first()
        val task2 = allTasks.find { it.title == "Task 2" }

        assertNotNull(task2)

        val retrievedTask = todoDao.getTaskById(task2!!.id)

        assertNotNull(retrievedTask)
        assertEquals("Task 2", retrievedTask?.title)
        assertEquals("Desc 2", retrievedTask?.description)
        assertEquals(task2.id, retrievedTask?.id)
    }
}