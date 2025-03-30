package com.example.myapplication

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.persistence.models.TodoModel
import com.example.myapplication.ui.recycler.holder.TodoViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoViewHolderEspressoTest {
    private lateinit var viewHolder: TodoViewHolder
    private val context = ApplicationProvider.getApplicationContext<android.content.Context>()

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        activityRule.scenario.onActivity { activity ->
            val view = activity.layoutInflater.inflate(R.layout.todo_holder_layout, null)
            viewHolder = TodoViewHolder(view)

            val testTask = TodoModel(
                id = 1,
                title = "Test Task",
                description = "Test Description",
                state = "in progress",
                date = "2023-01-01"
            )

            viewHolder.bind(testTask) { updatedTask ->
            }

            activity.setContentView(view)
        }
    }


    @Test
    fun checkboxClick_shouldUpdateState() {
        onView(withId(R.id.checkBox)).perform(click())
        onView(withId(R.id.stateText)).check(matches(withText("Completada")))
    }
}
