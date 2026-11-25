package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SpaceSection
import com.example.ui.theme.SpaceBlack
import com.example.ui.theme.SpaceBorderSubtle
import com.example.ui.theme.SpaceCarbon
import com.example.ui.theme.SpaceIvory
import com.example.ui.theme.SpaceObsidianGlass
import com.example.ui.theme.SpaceTitanium
import com.example.ui.theme.SpaceWhite

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.theme.SpaceAutomotiveTheme

@Composable
fun NavigationBar(
    isScrolled: Boolean = false,
    activeSection: SpaceSection = SpaceSection.HERO,
    onBrandClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isScrolled) SpaceObsidianGlass else Color.Transparent,
        animationSpec = tween(durationMillis = 320),
        label = "navbar_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isScrolled) SpaceBorderSubtle else Color.Transparent,
        animationSpec = tween(durationMillis = 320),
        label = "navbar_border"
    )
    val sectionProgress by animateFloatAsState(
        targetValue = (activeSection.listIndex + 1f) / SpaceSection.entries.size.toFloat(),
        animationSpec = tween(durationMillis = 320),
        label = "navbar_section_progress"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .testTag("navigation_bar")
    ) {
        SpaceSectionContainer(
            modifier = Modifier
                .statusBarsPadding()
                .padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(onClick = onBrandClick)
                        .heightIn(min = 48.dp)
                        .padding(end = 8.dp)
                        .testTag("navbar_brand_button")
                ) {
                    Text(
                        text = stringResource(R.string.brand_mark),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.6.sp
                        ),
                        color = SpaceWhite
                    )
                    Box(
                        modifier = Modifier
                            .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                            .background(SpaceCarbon.copy(alpha = 0.72f), RoundedCornerShape(2.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${activeSection.indexLabel} / ${activeSection.navTitle}",
                            style = MaterialTheme.typography.labelSmall,
                            color = SpaceTitanium
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(2.dp))
                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                        .background(
                            if (isScrolled) SpaceCarbon.copy(alpha = 0.85f)
                            else SpaceBlack.copy(alpha = 0.45f),
                            RoundedCornerShape(2.dp)
                        )
                        .clickable(onClick = onMenuClick)
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp)
                        .testTag("menu_button")
                ) {
                    Text(
                        text = stringResource(R.string.menu_label),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.8.sp
                        ),
                        color = SpaceIvory
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Open navigation menu",
                        tint = SpaceIvory,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(borderColor)
        ) {
            if (isScrolled) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(sectionProgress)
                        .height(1.dp)
                        .background(SpaceIvory.copy(alpha = 0.75f))
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090909)
@Composable
private fun NavigationBarPreview() {
    SpaceAutomotiveTheme {
        NavigationBar(
            isScrolled = true,
            activeSection = SpaceSection.ENGINEERING
        )
    }
}

@Composable
fun SpaceMenuOverlay(
    visible: Boolean,
    activeSection: SpaceSection,
    onSelectSection: (SpaceSection) -> Unit,
    onRequestExperience: () -> Unit,
    onClose: () -> Unit
) {
    BackHandler(enabled = visible) {
        onClose()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(260)) + slideInVertically(
            animationSpec = tween(320, easing = FastOutSlowInEasing),
            initialOffsetY = { -it / 10 }
        ),
        exit = fadeOut(tween(220)) + slideOutVertically(
            animationSpec = tween(260, easing = FastOutSlowInEasing),
            targetOffsetY = { -it / 10 }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SpaceBlack)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .testTag("menu_overlay")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.brand_mark),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.5.sp
                    ),
                    color = SpaceWhite
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                        .testTag("close_menu_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close navigation menu",
                        tint = SpaceIvory
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HairlineDivider()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                SpaceSection.entries.forEach { section ->
                    val isActive = section == activeSection
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { onSelectSection(section) }
                            .heightIn(min = 56.dp)
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                            .testTag("menu_item_${section.navTitle.lowercase()}"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = section.indexLabel,
                                style = MaterialTheme.typography.labelLarge,
                                color = if (isActive) SpaceIvory else SpaceTitanium.copy(alpha = 0.6f)
                            )
                            Text(
                                text = section.navTitle,
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    letterSpacing = 1.sp
                                ),
                                color = if (isActive) SpaceWhite else SpaceTitanium
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            if (isActive) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(SpaceIvory, CircleShape)
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = if (isActive) SpaceIvory else SpaceTitanium.copy(alpha = 0.4f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    HairlineDivider()
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            SpacePrimaryButton(
                text = stringResource(R.string.contact_cta),
                onClick = {
                    onClose()
                    onRequestExperience()
                },
                testTag = "menu_request_experience_button",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SPACE ONE · CONCEPT SHOWCASE",
                    style = MaterialTheme.typography.labelSmall,
                    color = SpaceTitanium.copy(alpha = 0.7f)
                )
                Text(
                    text = stringResource(R.string.footer_copyright),
                    style = MaterialTheme.typography.labelSmall,
                    color = SpaceTitanium.copy(alpha = 0.7f)
                )
            }
        }
    }
}
