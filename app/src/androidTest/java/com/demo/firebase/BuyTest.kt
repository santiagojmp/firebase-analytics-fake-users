package com.demo.firebase

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.demo.firebase.view.MainActivity
import com.demo.firebase.view.ProductListAdapter.ProductHolder
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


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

        onView(withId(randomOf(R.id.seeall_just, R.id.seeall_new))).perform(click())
        if (Math.random() > 0.9) return

        addRandomProductToCart()
        if (Math.random() > 0.7) return

        if (Math.random() > 0.7) { // add another product to the cart
            Espresso.pressBack()
            Espresso.pressBack()
            addRandomProductToCart()
            sleep(2000)
        }

        if (Math.random() > 0.7) return
        onView(withText("View Cart")).perform(click())

        sleep(100) // so RemoteConfig is fetched

        val limit = when (getText(withId(R.id.checkout_btn))) {
            "PAY PLEASE"            -> 0.3
            "PROCEED TO CHECKOUT"   -> 0.4
            "BUY"                   -> 0.5
            else                    -> 0.2
        }

        if (Math.random() > limit) return

        onView(withId(R.id.checkout_btn)).perform(click())
        onView(withText("CONTINUE SHOPPING")).check(matches(isDisplayed()))

    }

    private fun addRandomProductToCart() {
        val itemToClick = (Math.random() * 4.0).toInt()
        onView(withId(R.id.section_product_list))
                .perform(actionOnItemAtPosition<ProductHolder>(itemToClick, click()))
        onView(withText("ADD TO CART")).perform(click())
        log("Added to cart")
    }

    private fun getText(matcher: Matcher<View>): String {
        var text = "EMPTY"
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(TextView::class.java)

            override fun getDescription() = "getting text from a TextView"

            override fun perform(uiController: UiController?, view: View) {
                val tv = view as TextView //Save, because of check in getConstraints()
                text = tv.text.toString()
            }
        })
        return text
    }

    private fun randomOf(id1: Int, id2: Int) = if (Math.random() > 0.5) id1 else id2
}