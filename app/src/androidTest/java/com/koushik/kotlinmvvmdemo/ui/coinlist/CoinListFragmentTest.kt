package com.koushik.kotlinmvvmdemo.ui.coinlist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.koushik.kotlinmvvmdemo.R
import com.koushik.kotlinmvvmdemo.data.model.FakeCoin
import com.koushik.kotlinmvvmdemo.ui.MainActivity
import com.koushik.kotlinmvvmdemo.ui.coinlist.adapter.CoinListAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CoinListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val LIST_ITEM_IN_TEST = 4
    val COIN_IN_TEST = FakeCoin.coins[LIST_ITEM_IN_TEST]

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.rvCoinList)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        // Click list item #LIST_ITEM_IN_TEST
        onView(withId(R.id.rvCoinList))
            .perform(actionOnItemAtPosition<CoinListAdapter.ViewHolder>(LIST_ITEM_IN_TEST, click()))

        // Confirm nav to DetailFragment and display title
        onView(withId(R.id.tvName)).check(matches(withText(COIN_IN_TEST.name)))
    }
}