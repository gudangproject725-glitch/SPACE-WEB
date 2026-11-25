package com.example.ui.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SpaceShowcaseRepository
import com.example.data.VisionPillar
import com.example.ui.components.HairlineDivider
import com.example.ui.components.SectionKicker
import com.example.ui.components.SpaceSectionContainer
import com.example.ui.theme.SpaceAutomotiveTheme
import com.example.ui.theme.SpaceBorderStrong
import com.example.ui.theme.SpaceBorderSubtle
import com.example.ui.theme.SpaceCarbon
import com.example.ui.theme.SpaceGraphite
import com.example.ui.theme.SpaceIvory
import com.example.ui.theme.SpaceTitanium

@Composable
fun VisionSection(
    selectedPillarIndex: Int = 0,
    onSelectPillar: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val pillars = SpaceShowcaseRepository.visionPillars
    val activePillar = pillars.getOrElse(selectedPillarIndex) { pillars.first() }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("section_vision"),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 80.dp)
        ) {
            SpaceSectionContainer {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val isWide = maxWidth >= 680.dp

                    Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
                        SectionKicker(text = stringResource(R.string.vision_kicker))

                        if (isWide) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(40.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(
                                    modifier = Modifier.weight(0.46f),
                                    verticalArrangement = Arrangement.spacedBy(18.dp)
                                ) {
                                    VisionEditorialHeadline()
                                    VisionManifestoQuote()
                                }
                                Column(
                                    modifier = Modifier.weight(0.54f),
                                    verticalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    VisionPhilosophyParagraph()
                                    VisionPillarExplorer(
                                        pillars = pillars,
                                        selectedPillarIndex = selectedPillarIndex,
                                        activePillar = activePillar,
                                        onSelectPillar = onSelectPillar
                                    )
                                }
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(22.dp)) {
                                VisionEditorialHeadline()
                                VisionManifestoQuote()
                                HairlineDivider()
                                VisionPhilosophyParagraph()
                                Spacer(modifier = Modifier.height(4.dp))
                                VisionPillarExplorer(
                                    pillars = pillars,
                                    selectedPillarIndex = selectedPillarIndex,
                                    activePillar = activePillar,
                                    onSelectPillar = onSelectPillar
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VisionEditorialHeadline() {
    Text(
        text = stringResource(R.string.vision_headline),
        style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.9).sp
        ),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.testTag("vision_headline")
    )
}

@Composable
private fun VisionManifestoQuote() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
                .background(SpaceIvory)
        )
        Text(
            text = stringResource(R.string.vision_subtitle),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Normal,
                lineHeight = 26.sp
            ),
            color = SpaceIvory
        )
    }
}

@Composable
private fun VisionPhilosophyParagraph() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "PHILOSOPHY // FORM & SILENCE",
            style = MaterialTheme.typography.labelSmall,
            color = SpaceTitanium.copy(alpha = 0.75f)
        )
        Text(
            text = stringResource(R.string.vision_philosophy),
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 26.sp,
                letterSpacing = 0.2.sp
            ),
            color = SpaceTitanium,
            modifier = Modifier.testTag("vision_philosophy_body")
        )
    }
}

@Composable
private fun VisionPillarExplorer(
    pillars: List<VisionPillar>,
    selectedPillarIndex: Int,
    activePillar: VisionPillar,
    onSelectPillar: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pillars.forEachIndexed { index, pillar ->
                val selected = index == selectedPillarIndex
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(2.dp))
                        .border(
                            width = 1.dp,
                            color = if (selected) SpaceIvory else SpaceBorderSubtle,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .background(if (selected) SpaceCarbon else Color.Transparent)
                        .clickable { onSelectPillar(index) }
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .testTag("vision_pillar_$index"),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                            text = pillar.code,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected) SpaceIvory else SpaceTitanium.copy(alpha = 0.6f)
                        )
                        Text(
                            text = pillar.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.8.sp
                            ),
                            color = if (selected) MaterialTheme.colorScheme.onBackground else SpaceTitanium,
                            maxLines = 2
                        )
                    }
                }
            }
        }

        AnimatedContent(
            targetState = activePillar,
            transitionSpec = {
                fadeIn(tween(240)) togetherWith fadeOut(tween(180))
            },
            label = "vision_pillar_content"
        ) { pillar ->
            Surface(
                color = SpaceCarbon,
                border = BorderStroke(1.dp, SpaceBorderStrong),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${pillar.code} // ${pillar.title}",
                            style = MaterialTheme.typography.labelLarge,
                            color = SpaceIvory
                        )
                        Box(
                            modifier = Modifier
                                .background(SpaceGraphite, RoundedCornerShape(2.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = pillar.metricValue,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Text(
                        text = pillar.summary,
                        style = MaterialTheme.typography.bodyLarge,
                        color = SpaceTitanium
                    )
                    Text(
                        text = pillar.metricLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = SpaceTitanium.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090909)
@Composable
private fun VisionSectionPreview() {
    SpaceAutomotiveTheme {
        VisionSection()
    }
}
