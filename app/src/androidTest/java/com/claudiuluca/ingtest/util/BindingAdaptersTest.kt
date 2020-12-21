package com.claudiuluca.ingtest.util

import android.content.Context
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalToIgnoringCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@SmallTest
class BindingAdaptersTest {

    private var applicationContext: Context? = null

    @Before
    fun setupContext() {
        applicationContext = getApplicationContext()
    }

    @Test
    fun set_correct_channels_names_to_text_view() {
        val textView = TextView(applicationContext)

        val channelsNames = listOf("Facebook", "Instagram")

        channelsNames(textView, channelsNames)

        assertThat(textView.text.toString(), equalToIgnoringCase("Facebook, Instagram"))
    }
}