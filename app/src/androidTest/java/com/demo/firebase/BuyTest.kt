package com.demo.firebase

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.demo.firebase.view.MainActivity
import com.demo.firebase.view.ProductListAdapter
import com.demo.firebase.view.ProductListAdapter.ProductHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BuyTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    fun log(msg: String) {
        android.util.Log.i("TEST", msg)
    }

    @Test
    fun listGoesOverTheFold() {
        log("New test")
        onView(withId(randomOf(R.id.seeall_just, R.id.seeall_new)))
                .perform(click())
        if (Math.random() > 0.9) return

        val itemToClick = (Math.random() * 4.0).toInt()
        log("Clicking item $itemToClick")
        onView(withId(R.id.section_product_list))
                .perform(actionOnItemAtPosition<ProductHolder>(itemToClick, click()))
        if (Math.random() > 0.7) return

//        Espresso.pressBack()
        onView(withText("ADD TO CART")).perform(click())
        log("Added to cart")
        onView(withText("View Cart")).perform(click())
        if (Math.random() > 0.7) return

        onView(withText("PROCEED TO CHECKOUT")).perform(click())
        onView(withText("CONTINUE SHOPPING")).check(matches(isDisplayed()))

    }

    private fun randomOf(id1: Int, id2: Int) = if (Math.random() > 0.5) id1 else id2
}