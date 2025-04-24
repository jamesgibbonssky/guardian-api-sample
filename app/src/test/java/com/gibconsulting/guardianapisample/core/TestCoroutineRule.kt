package com.gibconsulting.guardianapisample.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Test rule to avoid injecting a TestCoroutineDispatcher in classes being tested.
 * This avoid blocking scenarios from never failing the unit test.
 *
 * Just apply the below code in your tests:
 *      @get:Rule
 *      val rule = TestCoroutineRule()
 *
 */
@ExperimentalCoroutinesApi
class TestCoroutineRule : TestWatcher() {
    val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
