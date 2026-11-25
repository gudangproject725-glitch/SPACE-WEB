package com.example.ui.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.SpaceShowcaseRepository
import com.example.ui.components.SectionKicker
import com.example.ui.components.SpaceSectionContainer
import com.example.ui.theme.SpaceAmberAccent
import com.example.ui.theme.SpaceBlack
import com.example.ui.theme.SpaceBorderStrong
import com.example.ui.theme.SpaceBorderSubtle
import com.example.ui.theme.SpaceCarbon
import com.example.ui.theme.SpaceGraphite
import com.example.ui.theme.SpaceIvory
import com.example.ui.theme.SpaceTitanium
import com.example.ui.theme.SpaceWhite
import kotlin.math.roundToInt

@Composable
fun ExteriorSection(
    selectedHotspotIndex: Int,
    isAirflowOverlayEnabled: Boolean,
    onSelectHotspot: (Int) -> Unit,
    onToggleAirflow: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hotspots = SpaceShowcaseRepository.aeroHotspots
    val activeHotspot = hotspots.getOrElse(selectedHotspotIndex) { hotspots.first() }

    val airflowTransition = rememberInfiniteTransition(label = "airflow_stream")
    val phaseShift by airflowTransition.animateFloat(
        initialValue = 0f,
        targetValue = 120f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "airflow_phase"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SpaceCarbon)
            .padding(vertical = 64.dp)
            .testTag("section_exterior")
    ) {
        SpaceSectionContainer {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SectionKicker(text = stringResource(R.string.exterior_kicker))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(2.dp))
                            .border(
                                1.dp,
                                if (isAirflowOverlayEnabled) SpaceIvory else SpaceBorderSubtle,
                                RoundedCornerShape(2.dp)
                            )
                            .background(
                                if (isAirflowOverlayEnabled) SpaceIvory else SpaceBlack
                            )
                            .clickable(onClick = onToggleAirflow)
                            .heightIn(min = 48.dp)
                            .padding(horizontal = 12.dp)
                            .testTag("toggle_airflow_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Air,
                            contentDescription = "Toggle aerodynamic airflow streamlines",
                            tint = if (isAirflowOverlayEnabled) SpaceBlack else SpaceTitanium,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isAirflowOverlayEnabled) "AERO FLOW ON" else "AERO FLOW",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isAirflowOverlayEnabled) SpaceBlack else SpaceTitanium
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.exterior_headline),
                    style = MaterialTheme.typography.displayMedium,
                    color = SpaceWhite
                )

                Text(
                    text = "Tap the aerodynamic telemetry nodes below or directly on the side silhouette to inspect active carbon bodywork.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SpaceTitanium
                )

                // Interactive Side Profile with Aero Streamlines & Hotspots
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(4.dp))
                        .background(SpaceBlack)
                ) {
                    val density = LocalDensity.current
                    val widthPx = with(density) { maxWidth.toPx() }
                    val heightPx = with(density) { maxHeight.toPx() }
                    val touchHalfPx = with(density) { 24.dp.toPx() }

                    Image(
                        painter = painterResource(id = R.drawable.img_exterior_profile_1795538367658),
                        contentDescription = "SPACE ONE full side aerodynamic profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    if (isAirflowOverlayEnabled) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            val dashEffect = PathEffect.dashPathEffect(floatArrayOf(28f, 18f), -phaseShift)

                            val upperStream = Path().apply {
                                moveTo(0f, h * 0.62f)
                                cubicTo(w * 0.25f, h * 0.50f, w * 0.42f, h * 0.30f, w * 0.62f, h * 0.33f)
                                cubicTo(w * 0.78f, h * 0.35f, w * 0.90f, h * 0.39f, w, h * 0.36f)
                            }
                            drawPath(
                                path = upperStream,
                                color = SpaceIvory.copy(alpha = 0.55f),
                                style = Stroke(width = 2.2f, pathEffect = dashEffect, cap = StrokeCap.Round)
                            )

                            val midStream = Path().apply {
                                moveTo(0f, h * 0.72f)
                                cubicTo(w * 0.24f, h * 0.67f, w * 0.48f, h * 0.56f, w * 0.74f, h * 0.52f)
                                cubicTo(w * 0.86f, h * 0.50f, w * 0.94f, h * 0.46f, w, h * 0.44f)
                            }
                            drawPath(
                                path = midStream,
                                color = SpaceAmberAccent.copy(alpha = 0.50f),
                                style = Stroke(width = 1.8f, pathEffect = dashEffect, cap = StrokeCap.Round)
                            )
                        }
                    }

                    hotspots.forEachIndexed { index, hotspot ->
                        val isSelected = index == selectedHotspotIndex
                        Box(
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        x = (widthPx * hotspot.xFraction - touchHalfPx).roundToInt(),
                                        y = (heightPx * hotspot.yFraction - touchHalfPx).roundToInt()
                                    )
                                }
                                .size(48.dp)
                                .clip(CircleShape)
                                .clickable { onSelectHotspot(index) }
                                .testTag("aero_hotspot_$index"),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(if (isSelected) 28.dp else 22.dp)
                                    .border(
                                        width = 1.5.dp,
                                        color = if (isSelected) SpaceIvory else SpaceWhite.copy(alpha = 0.65f),
                                        shape = CircleShape
                                    )
                                    .background(
                                        color = if (isSelected) SpaceIvory.copy(alpha = 0.28f) else SpaceBlack.copy(alpha = 0.55f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            if (isSelected) SpaceIvory else SpaceWhite,
                                            CircleShape
                                        )
                                )
                            }
                        }
                    }
                }

                // Hotspot selector row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    hotspots.forEachIndexed { index, hotspot ->
                        val selected = index == selectedHotspotIndex
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(2.dp))
                                .border(
                                    1.dp,
                                    if (selected) SpaceIvory else SpaceBorderSubtle,
                                    RoundedCornerShape(2.dp)
                                )
                                .background(if (selected) SpaceGraphite else SpaceBlack)
                                .clickable { onSelectHotspot(index) }
                                .heightIn(min = 48.dp)
                                .padding(horizontal = 8.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = hotspot.code,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (selected) SpaceWhite else SpaceTitanium
                            )
                        }
                    }
                }

                AnimatedContent(
                    targetState = activeHotspot,
                    transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(180)) },
                    label = "hotspot_detail"
                ) { hotspot ->
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
                                    text = hotspot.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = SpaceWhite
                                )
                                Text(
                                    text = hotspot.metric,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = SpaceIvory
                                )
                            }
                            Text(
                                text = hotspot.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = SpaceTitanium
                            )
                        }
                    }
                }
            }
        }
    }
}


