package io.gnosis.safe.ui.terms

import android.app.Application
import io.gnosis.safe.ui.terms.TermsChecker.Companion.TERMS_AGREED
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import pm.gnosis.svalinn.common.PreferencesManager
import pm.gnosis.tests.utils.TestPreferences

class TermsCheckerTest {
    private lateinit var preferences: TestPreferences
    private lateinit var preferencesManager: PreferencesManager

    @Before
    fun setup() {
        preferences = spyk()
        val application = mockk<Application>().apply {
            every { getSharedPreferences(any(), any()) } returns preferences
        }
        preferencesManager = PreferencesManager(application)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun `setTermsAgreed (true) should save true under TERMS_AGREED`() {
        val termsChecker = TermsChecker(preferencesManager)
        runBlocking {
            termsChecker.setTermsAgreed(true)

            assertTrue(preferences.getBoolean(TERMS_AGREED, false))
        }
    }

    @Test
    fun `getTermsAgreed (initially) should return false`() {
        val termsChecker = TermsChecker(preferencesManager)

        runBlocking {
            val result = termsChecker.getTermsAgreed()

            assertFalse(result)
        }
    }

    @Test
    fun `getTermsAgreed (terms have been agreed previously) should return true`() {
        preferences.edit().putBoolean(TERMS_AGREED, true)
        val termsChecker = TermsChecker(preferencesManager)
        runBlocking {
            val result = termsChecker.getTermsAgreed()

            assertTrue(result)
        }
    }
}
