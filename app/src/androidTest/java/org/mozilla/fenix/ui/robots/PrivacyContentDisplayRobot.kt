package org.mozilla.fenix.ui.robots

import androidx.test.espresso.Espresso
import org.mozilla.fenix.helpers.TestHelper.packageName
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import androidx.test.uiautomator.Until.findObject
import org.junit.Assert
import org.mozilla.fenix.R
import org.mozilla.fenix.helpers.SessionLoadedIdlingResource
import org.mozilla.fenix.helpers.TestAssetHelper
import org.mozilla.fenix.helpers.ext.waitNotNull

class PrivacyContentDisplayRobot {
    private lateinit var sessionLoadedIdlingResource: SessionLoadedIdlingResource
    fun verifyPrivacyContentCloseButton() = assertPrivacyContentCloseButton()
    fun verifyPrivacyContentPage(expectedText: String) {
        sessionLoadedIdlingResource = SessionLoadedIdlingResource()

        mDevice.waitNotNull(
            Until.findObject(By.res("$packageName:id/privacyContentEngineView")),
            TestAssetHelper.waitingTime
        )

        runWithIdleRes(sessionLoadedIdlingResource) {
            Assert.assertTrue(
                mDevice.findObject(UiSelector().textContains(expectedText))
                    .waitForExists(TestAssetHelper.waitingTime)
            )
        }
    }
    class Transition {
        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        fun dismissPrivacyContentDisplay(interact: HomeScreenRobot.() -> Unit): HomeScreenRobot.Transition {
            mDevice.waitForIdle()
            mDevice.pressBack()
            HomeScreenRobot().interact()
            return HomeScreenRobot.Transition()
        }

        fun closePrivacyContentDisplay(interact: HomeScreenRobot.() -> Unit): HomeScreenRobot.Transition {
            mDevice.waitForIdle()
            privacyContentCloseButton().click()
            HomeScreenRobot().interact()
            return HomeScreenRobot.Transition()
        }
    }
}

private fun privacyContentCloseButton(): UiObject {
    mDevice.waitNotNull(Until.findObjects(By.res("org.mozilla.fenix.debug:id/privacyContentCloseButton")), TestAssetHelper.waitingTime)
    return mDevice.findObject(UiSelector().resourceId("$packageName:id/privacyContentCloseButton"))
}

private fun assertPrivacyContentCloseButton() {
    mDevice.findObject(UiSelector().resourceId("$packageName:id/privacyContentCloseButton")).waitForExists(TestAssetHelper.waitingTime)
    Espresso.onView(ViewMatchers.withId(R.id.privacyContentCloseButton))
        .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
}
