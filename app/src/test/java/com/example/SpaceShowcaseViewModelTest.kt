package com.example

import com.example.data.SpaceSection
import com.example.data.SpaceShowcaseRepository
import com.example.data.SpecCategory
import com.example.ui.viewmodel.SpaceShowcaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SpaceShowcaseViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `opening sequence dismisses automatically after delay`() = runTest(testDispatcher) {
        val viewModel = SpaceShowcaseViewModel()
        assertTrue(viewModel.uiState.value.isOpeningSequenceVisible)

        advanceTimeBy(1400L)
        assertFalse(viewModel.uiState.value.isOpeningSequenceVisible)
    }

    @Test
    fun `inquiry validation rejects invalid name and email then succeeds`() = runTest(testDispatcher) {
        val viewModel = SpaceShowcaseViewModel()
        viewModel.openInquirySheet("MONACO")
        assertTrue(viewModel.uiState.value.isInquirySheetOpen)
        assertEquals("MONACO", viewModel.uiState.value.selectedAtelierCity)

        viewModel.updateGuestName("A")
        viewModel.submitInquiry()
        assertNotNull(viewModel.uiState.value.inquiryError)
        assertNull(viewModel.uiState.value.inquiryConfirmation)

        viewModel.updateGuestName("Elena Vance")
        viewModel.updateGuestEmail("invalid-email")
        viewModel.submitInquiry()
        assertNotNull(viewModel.uiState.value.inquiryError)
        assertNull(viewModel.uiState.value.inquiryConfirmation)

        viewModel.updateGuestEmail("elena@vance.ch")
        viewModel.submitInquiry()
        assertNull(viewModel.uiState.value.inquiryError)
        val confirmation = viewModel.uiState.value.inquiryConfirmation
        assertNotNull(confirmation)
        assertTrue(confirmation!!.referenceCode.startsWith("SPC-2026-"))
        assertEquals("MONACO", confirmation.atelierCity)
    }

    @Test
    fun `section navigation and interactive selectors update state`() = runTest(testDispatcher) {
        val viewModel = SpaceShowcaseViewModel()
        viewModel.updateActiveSection(SpaceSection.ENGINEERING)
        viewModel.selectAeroHotspot(2)
        viewModel.toggleAirflowOverlay()
        viewModel.selectDriveMode(1)
        viewModel.selectSpecCategory(SpecCategory.CHASSIS)
        viewModel.openGalleryLightbox(SpaceShowcaseRepository.galleryItems.first())

        val state = viewModel.uiState.value
        assertEquals(SpaceSection.ENGINEERING, state.activeSection)
        assertEquals(2, state.selectedAeroHotspotIndex)
        assertFalse(state.isAirflowOverlayEnabled)
        assertEquals(1, state.selectedDriveModeIndex)
        assertEquals(SpecCategory.CHASSIS, state.selectedSpecCategory)
        assertNotNull(state.lightboxGalleryItem)
    }
}
