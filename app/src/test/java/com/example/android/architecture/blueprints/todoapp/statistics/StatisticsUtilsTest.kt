package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

/**
 * @time 21/12/20
 */
class StatisticsUtilsTest {
    /**
     * Test Names
     * Naming convention: subjectUnderTest_actionOrInput_resultState
     */
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // Create an active task: Given
        val tasks = listOf<Task>(
                Task("title", "desc", isCompleted = false)
        )

        // Call your function: When
        val result = getActiveAndCompletedStats(tasks)

        // Check the result: Then
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(100f))
    }
}