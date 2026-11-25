package com.example.ui.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.R
import com.example.data.GalleryItem
import com.example.data.SpaceShowcaseRepository
import com.example.ui.components.SectionKicker
import com.example.ui.components.SpaceSectionContainer
import com.example.ui.theme.SpaceBlack
import com.example.ui.theme.SpaceBorderStrong
import com.example.ui.theme.SpaceBorderSubtle
import com.example.ui.theme.SpaceCarbon
import com.example.ui.theme.SpaceGraphite
import com.example.ui.theme.SpaceIvory
import com.example.ui.theme.SpaceTitanium
import com.example.ui.theme.SpaceWhite
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun InteriorSection(
    selectedMaterialIndex: Int,
    onSelectMaterial: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val materials = SpaceShowcaseRepository.interiorMaterials
    val activeMaterial = materials.getOrElse(selectedMaterialIndex) { materials.first() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SpaceCarbon)
            .padding(vertical = 64.dp)
            .testTag("section_interior")
    ) {
        SpaceSectionContainer {
            Column(verticalArrangement = Arrangement.spacedBy(22.dp)) {
                SectionKicker(text = stringResource(R.string.interior_kicker))

                Text(
                    text = stringResource(R.string.interior_headline),
                    style = MaterialTheme.typography.displayMedium,
                    color = SpaceWhite
                )

                Text(
                    text = "An architectural monocoque cockpit stripped of distraction. Tactile milled titanium switchgear meets bespoke Alcantara tailoring.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SpaceTitanium
                )

                Image(
                    painter = painterResource(id = R.drawable.img_interior_cockpit_1795538368482),
                    contentDescription = "SPACE ONE futuristic cockpit interior with ivory Alcantara and forged carbon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f)
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(4.dp))
                )

                Text(
                    text = "ATELIER MATERIAL SPECIFICATION",
                    style = MaterialTheme.typography.labelMedium,
                    color = SpaceTitanium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    materials.forEachIndexed { index, material ->
                        val selected = index == selectedMaterialIndex
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .border(
                                    1.dp,
                                    if (selected) SpaceIvory else SpaceBorderSubtle,
                                    RoundedCornerShape(4.dp)
                                )
                                .background(if (selected) SpaceGraphite else SpaceBlack)
                                .clickable { onSelectMaterial(index) }
                                .heightIn(min = 76.dp)
                                .padding(12.dp)
                                .testTag("interior_material_$index"),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .background(material.primarySwatch, RoundedCornerShape(2.dp))
                                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                                )
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .background(material.accentSwatch, RoundedCornerShape(2.dp))
                                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                                )
                            }
                            Text(
                                text = material.name.substringBefore(" "),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) SpaceWhite else SpaceTitanium,
                                maxLines = 1
                            )
                        }
                    }
                }

                AnimatedContent(
                    targetState = activeMaterial,
                    transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(180)) },
                    label = "material_detail"
                ) { material ->
                    Surface(
                        color = SpaceBlack,
                        border = BorderStroke(1.dp, SpaceBorderStrong),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = material.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = SpaceWhite
                                )
                                Text(
                                    text = material.weightAdvantage,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SpaceIvory
                                )
                            }
                            Text(
                                text = material.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = SpaceTitanium
                            )
                            Text(
                                text = "PROVENANCE: ${material.origin}",
                                style = MaterialTheme.typography.labelSmall,
                                color = SpaceTitanium.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExperienceSection(
    onOpenLightbox: (GalleryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val galleryItems = SpaceShowcaseRepository.galleryItems
    val pagerState = rememberPagerState(pageCount = { galleryItems.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SpaceBlack)
            .padding(vertical = 64.dp)
            .testTag("section_experience")
    ) {
        SpaceSectionContainer {
            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                SectionKicker(text = stringResource(R.string.experience_kicker))

                Text(
                    text = stringResource(R.string.experience_headline),
                    style = MaterialTheme.typography.displayMedium,
                    color = SpaceWhite
                )

                Text(
                    text = stringResource(R.string.experience_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = SpaceTitanium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = galleryItems[pagerState.currentPage].indexLabel,
                        style = MaterialTheme.typography.labelLarge,
                        color = SpaceIvory
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = {
                                val prev = (pagerState.currentPage - 1).coerceAtLeast(0)
                                scope.launch { pagerState.animateScrollToPage(prev) }
                            },
                            enabled = pagerState.currentPage > 0,
                            modifier = Modifier
                                .size(48.dp)
                                .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                                .testTag("gallery_prev_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous gallery study",
                                tint = if (pagerState.currentPage > 0) SpaceIvory else SpaceTitanium.copy(alpha = 0.3f)
                            )
                        }
                        IconButton(
                            onClick = {
                                val next = (pagerState.currentPage + 1).coerceAtMost(galleryItems.lastIndex)
                                scope.launch { pagerState.animateScrollToPage(next) }
                            },
                            enabled = pagerState.currentPage < galleryItems.lastIndex,
                            modifier = Modifier
                                .size(48.dp)
                                .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                                .testTag("gallery_next_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next gallery study",
                                tint = if (pagerState.currentPage < galleryItems.lastIndex) SpaceIvory else SpaceTitanium.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 14.dp,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("experience_gallery_pager")
        ) { page ->
            val item = galleryItems[page]
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            Surface(
                color = SpaceCarbon,
                border = BorderStroke(1.dp, SpaceBorderSubtle),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val scale = lerp(0.95f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                        scaleX = scale
                        scaleY = scale
                        alpha = lerp(0.65f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                    }
                    .clickable { onOpenLightbox(item) }
                    .testTag("gallery_card_$page")
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 10f)
                    ) {
                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        0.5f to Color.Transparent,
                                        1.0f to SpaceBlack.copy(alpha = 0.85f)
                                    )
                                )
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .background(SpaceBlack.copy(alpha = 0.72f), RoundedCornerShape(2.dp))
                                .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Fullscreen,
                                contentDescription = "Expand studio photo",
                                tint = SpaceIvory,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "INSPECT",
                                style = MaterialTheme.typography.labelSmall,
                                color = SpaceIvory
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = item.studioMetadata,
                            style = MaterialTheme.typography.labelSmall,
                            color = SpaceTitanium.copy(alpha = 0.75f)
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = SpaceWhite
                        )
                        Text(
                            text = item.caption,
                            style = MaterialTheme.typography.bodyMedium,
                            color = SpaceTitanium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SpaceSectionContainer {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                galleryItems.forEachIndexed { index, _ ->
                    val active = index == pagerState.currentPage
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(3.dp)
                            .background(if (active) SpaceIvory else SpaceGraphite)
                    )
                }
            }
        }
    }
}
