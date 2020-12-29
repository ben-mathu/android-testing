package com.example.android.architecture.blueprints.todoapp.tasks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import androidx.test.runner.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.util.ServiceLocator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * @time 30/12/20
 */
@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class TasksFragmentTest {

    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest {
        repository.saveTask(Task("TITLE1", "DESCRIPTION1", false, "id1"))
        repository.saveTask(Task("TITLE2", "DESCRIPTION2", true, "id2"))

        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)

        // Use Mockito to create a NavController mock
        val navController = mock(NavController::class.java)
        // Attach the mock NavController to a fragment
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the first item in list
        onView(withId(R.id.tasks_list))
                .perform(
                        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                            hasDescendant(withText("TITLE1")), click()
                        )
                )

        // Verify that navigate was called with correct action and parameters
        verify(navController).navigate(
                TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment( "id1")
        )
    }

    @Test
    fun clickAddTaskButton_navigateToAddEditFragment() {
        // GIVEN - home screen
        val scenario =  launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the FAB
        onView(withId(R.id.add_task_fab))
                .perform(click())

        // THEN - Navigate to add task screen
        verify(navController).navigate(
                TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                        null,
                        getApplicationContext<Context>().getString(R.string.add_task)
                )
        )
    }
}