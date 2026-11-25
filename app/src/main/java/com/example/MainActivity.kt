package com.example

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.SpaceSection
import com.example.ui.components.GalleryLightboxDialog
import com.example.ui.components.NavigationBar
import com.example.ui.components.PageLoaderOverlay
import com.example.ui.components.PrivateInquirySheet
import com.example.ui.components.SpaceMenuOverlay
import com.example.ui.components.SpaceVerticalScrollContainer
import com.example.ui.theme.SpaceAutomotiveTheme
import com.example.ui.theme.SpaceBlack
import com.example.ui.viewmodel.SpaceShowcaseViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        )
        setContent {
            SpaceAutomotiveTheme {
                SpaceShowcaseApp()
            }
        }
    }
}

@Composable
fun SpaceShowcaseApp(
    viewModel: SpaceShowcaseViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 48
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SpaceBlack)
    ) {
        SpaceVerticalScrollContainer(
            listState = listState,
            uiState = uiState,
            onActiveSectionChanged = viewModel::updateActiveSection,
            onExploreClick = {
                scope.launch { listState.animateScrollToItem(SpaceSection.VISION.listIndex) }
            },
            onReserveClick = { city ->
                viewModel.openInquirySheet(city)
            },
            onSelectVisionPillar = viewModel::selectVisionPillar,
            onSelectAeroHotspot = viewModel::selectAeroHotspot,
            onToggleAirflow = viewModel::toggleAirflowOverlay,
            onSelectDriveMode = viewModel::selectDriveMode,
            onSelectInteriorMaterial = viewModel::selectInteriorMaterial,
            onOpenLightbox = viewModel::openGalleryLightbox,
            onSelectSpecCategory = viewModel::selectSpecCategory,
            onBackToTop = {
                scope.launch { listState.animateScrollToItem(SpaceSection.HERO.listIndex) }
            }
        )

        NavigationBar(
            isScrolled = isScrolled,
            activeSection = uiState.activeSection,
            onBrandClick = {
                scope.launch { listState.animateScrollToItem(SpaceSection.HERO.listIndex) }
            },
            onMenuClick = { viewModel.setMenuOpen(true) },
            modifier = Modifier.align(Alignment.TopCenter)
        )

        SpaceMenuOverlay(
            visible = uiState.isMenuOpen,
            activeSection = uiState.activeSection,
            onSelectSection = { section ->
                viewModel.setMenuOpen(false)
                scope.launch {
                    listState.animateScrollToItem(section.listIndex)
                }
            },
            onRequestExperience = { viewModel.openInquirySheet() },
            onClose = { viewModel.setMenuOpen(false) }
        )

        GalleryLightboxDialog(
            item = uiState.lightboxGalleryItem,
            onDismiss = viewModel::closeGalleryLightbox
        )

        PrivateInquirySheet(
            uiState = uiState,
            onDismiss = viewModel::closeInquirySheet,
            onSelectExperienceType = viewModel::selectExperienceType,
            onSelectAtelierCity = viewModel::selectAtelierCity,
            onNameChange = viewModel::updateGuestName,
            onEmailChange = viewModel::updateGuestEmail,
            onSubmit = viewModel::submitInquiry,
            onResetConfirmation = viewModel::resetInquiryConfirmation
        )

        PageLoaderOverlay(
            visible = uiState.isOpeningSequenceVisible,
            onSkip = viewModel::dismissOpeningSequence
        )
    }
}
