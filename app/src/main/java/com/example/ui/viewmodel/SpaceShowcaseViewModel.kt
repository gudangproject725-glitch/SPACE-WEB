package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GalleryItem
import com.example.data.SpaceSection
import com.example.data.SpaceShowcaseRepository
import com.example.data.SpecCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InquiryConfirmation(
    val referenceCode: String,
    val guestName: String,
    val experienceType: String,
    val atelierCity: String
)

data class SpaceUiState(
    val isOpeningSequenceVisible: Boolean = true,
    val isMenuOpen: Boolean = false,
    val activeSection: SpaceSection = SpaceSection.HERO,
    val selectedVisionPillarIndex: Int = 0,
    val selectedAeroHotspotIndex: Int = 0,
    val isAirflowOverlayEnabled: Boolean = true,
    val selectedDriveModeIndex: Int = 2,
    val selectedMaterialIndex: Int = 0,
    val selectedSpecCategory: SpecCategory = SpecCategory.ALL,
    val lightboxGalleryItem: GalleryItem? = null,
    val isInquirySheetOpen: Boolean = false,
    val selectedExperienceType: String = SpaceShowcaseRepository.experienceTypes.first(),
    val selectedAtelierCity: String = SpaceShowcaseRepository.atelierLocations.first().city,
    val guestName: String = "",
    val guestEmail: String = "",
    val inquiryError: String? = null,
    val inquiryConfirmation: InquiryConfirmation? = null
)

class SpaceShowcaseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SpaceUiState())
    val uiState: StateFlow<SpaceUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1350L)
            _uiState.update { it.copy(isOpeningSequenceVisible = false) }
        }
    }

    fun dismissOpeningSequence() {
        _uiState.update { it.copy(isOpeningSequenceVisible = false) }
    }

    fun setMenuOpen(open: Boolean) {
        _uiState.update { it.copy(isMenuOpen = open) }
    }

    fun updateActiveSection(section: SpaceSection) {
        _uiState.update { current ->
            if (current.activeSection == section) current else current.copy(activeSection = section)
        }
    }

    fun selectVisionPillar(index: Int) {
        _uiState.update { it.copy(selectedVisionPillarIndex = index) }
    }

    fun selectAeroHotspot(index: Int) {
        _uiState.update { it.copy(selectedAeroHotspotIndex = index) }
    }

    fun toggleAirflowOverlay() {
        _uiState.update { it.copy(isAirflowOverlayEnabled = !it.isAirflowOverlayEnabled) }
    }

    fun selectDriveMode(index: Int) {
        _uiState.update { it.copy(selectedDriveModeIndex = index) }
    }

    fun selectInteriorMaterial(index: Int) {
        _uiState.update { it.copy(selectedMaterialIndex = index) }
    }

    fun selectSpecCategory(category: SpecCategory) {
        _uiState.update { it.copy(selectedSpecCategory = category) }
    }

    fun openGalleryLightbox(item: GalleryItem) {
        _uiState.update { it.copy(lightboxGalleryItem = item) }
    }

    fun closeGalleryLightbox() {
        _uiState.update { it.copy(lightboxGalleryItem = null) }
    }

    fun openInquirySheet(preferredAtelierCity: String? = null) {
        _uiState.update { current ->
            current.copy(
                isInquirySheetOpen = true,
                selectedAtelierCity = preferredAtelierCity ?: current.selectedAtelierCity,
                inquiryError = null
            )
        }
    }

    fun closeInquirySheet() {
        _uiState.update { it.copy(isInquirySheetOpen = false, inquiryError = null) }
    }

    fun selectExperienceType(type: String) {
        _uiState.update { it.copy(selectedExperienceType = type) }
    }

    fun selectAtelierCity(city: String) {
        _uiState.update { it.copy(selectedAtelierCity = city) }
    }

    fun updateGuestName(name: String) {
        _uiState.update { it.copy(guestName = name, inquiryError = null) }
    }

    fun updateGuestEmail(email: String) {
        _uiState.update { it.copy(guestEmail = email, inquiryError = null) }
    }

    fun submitInquiry() {
        val state = _uiState.value
        val trimmedName = state.guestName.trim()
        val trimmedEmail = state.guestEmail.trim()

        if (trimmedName.length < 2) {
            _uiState.update { it.copy(inquiryError = "Please enter your full name.") }
            return
        }
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        if (!emailRegex.matches(trimmedEmail)) {
            _uiState.update { it.copy(inquiryError = "Please enter a valid email address.") }
            return
        }

        val suffix = ((trimmedName.hashCode() and 0xFFFF) % 9000) + 1000
        _uiState.update {
            it.copy(
                inquiryError = null,
                inquiryConfirmation = InquiryConfirmation(
                    referenceCode = "SPC-2026-$suffix",
                    guestName = trimmedName,
                    experienceType = state.selectedExperienceType,
                    atelierCity = state.selectedAtelierCity
                )
            )
        }
    }

    fun resetInquiryConfirmation() {
        _uiState.update {
            it.copy(
                inquiryConfirmation = null,
                guestName = "",
                guestEmail = "",
                inquiryError = null
            )
        }
    }
}
