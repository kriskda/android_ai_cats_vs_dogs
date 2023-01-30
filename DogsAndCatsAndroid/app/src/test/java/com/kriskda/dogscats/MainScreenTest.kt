package com.kriskda.dogscats

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.kriskda.dogscats.model.MainScreenState
import com.kriskda.dogscats.model.Prediction
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class MainScreenTest {

    private val mockMainScreenViewModel: MainScreenViewModel = mock()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `shows Clear result button when PredictionResult state`() {
        assumeUiState(MainScreenState.PredictionResult(prediction()))

        composeTestRule.setContent { MainScreen(mockMainScreenViewModel) }

        composeTestRule.onNodeWithText("Clear result").assertIsDisplayed()
    }

    @Test
    fun `shows prediction results when PredictionResult state`() {
        assumeUiState(MainScreenState.PredictionResult(prediction()))

        composeTestRule.setContent { MainScreen(mockMainScreenViewModel) }

        composeTestRule.onNodeWithText("Dog").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cat").assertIsDisplayed()
    }

    private fun assumeUiState(uiState: MainScreenState) {
        whenever(mockMainScreenViewModel.uiState).thenReturn(uiState)
    }

    private fun prediction() =
        Prediction(
            dogProbability = 0.45f,
            catProbability = 0.55f
        )
}
