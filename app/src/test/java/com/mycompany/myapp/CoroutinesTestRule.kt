package com.mycompany.myapp


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutinesTestRule : TestWatcher() {


    val testDispatcher = TestCoroutineDispatcher()
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}
