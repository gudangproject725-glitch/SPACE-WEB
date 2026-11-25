package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.GalleryItem
import com.example.data.SpaceSection
import com.example.data.SpecCategory
import com.example.ui.sections.ContactFooterSection
import com.example.ui.sections.EngineeringSection
import com.example.ui.sections.ExperienceSection
import com.example.ui.sections.ExteriorSection
import com.example.ui.sections.HeroScreen
import com.example.ui.sections.InteriorSection
import com.example.ui.sections.SpecificationsSection
import com.example.ui.sections.VisionSection
import com.example.ui.theme.SpaceBlack
import com.example.ui.viewmodel.SpaceUiState

@Composable
fun SpaceVerticalScrollContainer(
    listState: LazyListState,
    uiState: SpaceUiState,
    onActiveSectionChanged: (SpaceSection) -> Unit,
    onExploreClick: () -> Unit,
    onReserveClick: (String?) -> Unit,
    onSelectVisionPillar: (Int) -> Unit,
    onSelectAeroHotspot: (Int) -> Unit,
    onToggleAirflow: () -> Unit,
    onSelectDriveMode: (Int) -> Unit,
    onSelectInteriorMaterial: (Int) -> Unit,
    onOpenLightbox: (GalleryItem) -> Unit,
    onSelectSpecCategory: (SpecCategory) -> Unit,
    onBackToTop: () -> Unit,
    modifier: Modifier = Modifier
) {
    val heroScrollOffsetPx by remember(listState) {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0) {
                listState.firstVisibleItemScrollOffset.toFloat()
            } else {
                800f
            }
        }
    }

    val engineeringParallaxProgress by remember(listState) {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val engineeringIndex = SpaceSection.ENGINEERING.listIndex
            val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == engineeringIndex }
            if (itemInfo != null) {
                val viewportHeight = (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset)
                    .coerceAtLeast(1)
                    .toFloat()
                val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2f
                val itemCenter = itemInfo.offset + itemInfo.size / 2f
                ((itemCenter - viewportCenter) / viewportHeight).coerceIn(-1f, 1f)
            } else if (listState.firstVisibleItemIndex > engineeringIndex) {
                -1f
            } else {
                1f
            }
        }
    }

    val currentVisibleSection by remember(listState) {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
            val centeredItem = layoutInfo.visibleItemsInfo.firstOrNull { item ->
                item.offset <= viewportCenter && (item.offset + item.size) >= viewportCenter
            }
            val index = centeredItem?.index ?: listState.firstVisibleItemIndex
            SpaceSection.entries.getOrElse(index) { SpaceSection.HERO }
        }
    }

    LaunchedEffect(currentVisibleSection) {
        onActiveSectionChanged(currentVisibleSection)
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .fillMaxSize()
            .background(SpaceBlack)
            .testTag("space_showcase_feed")
    ) {
        item(key = SpaceSection.HERO.name) {
            HeroScreen(
                scrollOffsetPx = heroScrollOffsetPx,
                onExploreClick = onExploreClick,
                onReserveClick = { onReserveClick(null) }
            )
        }

        item(key = SpaceSection.VISION.name) {
            SectionScrollTransition(
                listState = listState,
                sectionIndex = SpaceSection.VISION.listIndex
            ) {
                VisionSection(
                    selectedPillarIndex = uiState.selectedVisionPillarIndex,
                    onSelectPillar = onSelectVisionPillar
                )
            }
        }

        item(key = SpaceSection.EXTERIOR.name) {
            SectionScrollTransition(
                listState = listState,
                sectionIndex = SpaceSection.EXTERIOR.listIndex
            ) {
                ExteriorSection(
                    selectedHotspotIndex = uiState.selectedAeroHotspotIndex,
                    isAirflowOverlayEnabled = uiState.isAirflowOverlayEnabled,
                    onSelectHotspot = onSelectAeroHotspot,
                    onToggleAirflow = onToggleAirflow
                )
            }
        }

        item(key = SpaceSection.ENGINEERING.name) {
            SectionScrollTransition(
                listState = listState,
                sectionIndex = SpaceSection.ENGINEERING.listIndex
            ) {
                EngineeringSection(
                    selectedDriveModeIndex = uiState.selectedDriveModeIndex,
                    onSelectDriveMode = onSelectDriveMode,
                    parallaxProgress = engineeringParallaxProgress
                )
            }
        }

        item(key = SpaceSection.INTERIOR.name) {
            SectionScrollTransition(
                listState = listState,
                sectionIndex = SpaceSection.INTERIOR.listIndex
            ) {
                InteriorSection(
                    selectedMaterialIndex = uiState.selectedMaterialIndex,
                    onSelectMaterial = onSelectInteriorMaterial
                )
            }
        }

        item(key = SpaceSection.EXPERIENCE.name) {
            SectionScrollTransition(
                listState = listState,
                sectionIndex = SpaceSection.EXPERIENCE.listIndex
            ) {
                ExperienceSection(
                    onOpenLightbox = onOpenLightbox
                )
            }
        }

        item(key = SpaceSection.SPECIFICATIONS.name) {
            SectionScrollTransition(
                listState = listState,
                sectionIndex = SpaceSection.SPECIFICATIONS.listIndex
            ) {
                SpecificationsSection(
                    selectedCategory = uiState.selectedSpecCategory,
                    onSelectCategory = onSelectSpecCategory
                )
            }
        }

        item(key = SpaceSection.CONTACT.name) {
            SectionScrollTransition(
                listState = listState,
                sectionIndex = SpaceSection.CONTACT.listIndex
            ) {
                ContactFooterSection(
                    onRequestExperience = onReserveClick,
                    onBackToTop = onBackToTop
                )
            }
        }
    }
}

@Composable
private fun SectionScrollTransition(
    listState: LazyListState,
    sectionIndex: Int,
    content: @Composable () -> Unit
) {
    var hasEnteredViewport by rememberSaveable(sectionIndex) { mutableStateOf(false) }

    val isInViewport by remember(listState, sectionIndex) {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == sectionIndex }
            if (itemInfo == null) {
                false
            } else {
                val viewportHeight = (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset)
                    .coerceAtLeast(1)
                itemInfo.offset < (layoutInfo.viewportEndOffset - viewportHeight * 0.08f)
            }
        }
    }

    LaunchedEffect(isInViewport) {
        if (isInViewport && !hasEnteredViewport) {
            hasEnteredViewport = true
        }
    }

    val entranceProgress by animateFloatAsState(
        targetValue = if (hasEnteredViewport) 1f else 0f,
        animationSpec = tween(
            durationMillis = 650,
            easing = FastOutSlowInEasing
        ),
        label = "section_entrance_$sectionIndex"
    )

    val motionBlurStrength = (1f - entranceProgress).coerceIn(0f, 1f)
    val motionBlurModifier = if (motionBlurStrength > 0.01f) {
        Modifier.blur(
            radiusX = (motionBlurStrength * 1.5f).dp,
            radiusY = (motionBlurStrength * 10f).dp,
            edgeTreatment = BlurredEdgeTreatment.Unbounded
        )
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = 0.15f + 0.85f * entranceProgress
                translationY = (1f - entranceProgress) * 44f
                val settleScale = 0.985f + 0.015f * entranceProgress
                scaleX = settleScale
                scaleY = settleScale
            }
            .then(motionBlurModifier)
    ) {
        content()
    }
}
