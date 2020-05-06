package com.mycompany.myapp

import android.os.Bundle
import io.mockk.every
import io.mockk.mockkClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class SimpleTests {
    @Test
    fun testTrueIsTrue() {
        assertTrue(true)
    }

    @Test
    fun testMocking() {
        val mockBundle = mockkClass(Bundle::class)
        every { mockBundle.getString("key") } returns ("value")

        val value = mockBundle.getString("key")
        assertEquals("value", value)
    }
}
