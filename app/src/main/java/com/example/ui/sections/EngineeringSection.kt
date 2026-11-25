package com.example.ui.sections

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SpaceShowcaseRepository
import com.example.ui.components.HairlineDivider
import com.example.ui.components.SectionKicker
import com.example.ui.components.SpaceSectionContainer
import com.example.ui.theme.SpaceAutomotiveTheme
import com.example.ui.theme.SpaceBlack
import com.example.ui.theme.SpaceBorderStrong
import com.example.ui.theme.SpaceBorderSubtle
import com.example.ui.theme.SpaceCarbon
import com.example.ui.theme.SpaceIvory
import com.example.ui.theme.SpaceTitanium
import com.example.ui.theme.SpaceWhite
import java.util.Locale

@Composable
fun EngineeringSection(
    selectedDriveModeIndex: Int = 2,
    onSelectDriveMode: (Int) -> Unit = {},
    parallaxProgress: Float = 0f,
    modifier: Modifier = Modifier
) {
    val clampedParallax = parallaxProgress.coerceIn(-1f, 1f)
    val driveModes = SpaceShowcaseRepository.driveModes
    val activeMode = driveModes.getOrElse(selectedDriveModeIndex) { driveModes.last() }

    var metricsStarted by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        metricsStarted = true
    }

    val animatedHp by animateIntAsState(
        targetValue = if (metricsStarted) 1000 else 0,
        animationSpec = tween(durationMillis = 1200),
        label = "hp_counter"
    )
    val animatedAccel by animateFloatAsState(
        targetValue = if (metricsStarted) 2.5f else 0.0f,
        animationSpec = tween(durationMillis = 1200),
        label = "accel_counter"
    )
    val animatedSpeed by animateIntAsState(
        targetValue = if (metricsStarted) 350 else 0,
        animationSpec = tween(durationMillis = 1200),
        label = "speed_counter"
    )

    val animatedModeOutput by animateIntAsState(
        targetValue = activeMode.powerOutputHp,
        animationSpec = tween(durationMillis = 500),
        label = "mode_output"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("section_engineering"),
        color = SpaceBlack,
        contentColor = SpaceWhite
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clipToBounds()
        ) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        scaleX = 1.08f
                        scaleY = 1.08f
                        translationY = clampedParallax * 54f
                    }
            ) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            SpaceIvory.copy(alpha = 0.06f),
                            SpaceCarbon.copy(alpha = 0.32f),
                            Color.Transparent
                        ),
                        center = Offset(size.width * 0.78f, size.height * 0.28f),
                        radius = size.maxDimension * 0.56f
                    ),
                    center = Offset(size.width * 0.78f, size.height * 0.28f),
                    radius = size.maxDimension * 0.56f
                )

                drawLine(
                    color = SpaceBorderSubtle.copy(alpha = 0.45f),
                    start = Offset(0f, size.height * 0.22f),
                    end = Offset(size.width, size.height * 0.14f),
                    strokeWidth = 1f
                )
                drawLine(
                    color = SpaceBorderSubtle.copy(alpha = 0.35f),
                    start = Offset(0f, size.height * 0.74f),
                    end = Offset(size.width, size.height * 0.66f),
                    strokeWidth = 1f
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 72.dp)
            ) {
                SpaceSectionContainer {
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    SectionKicker(text = stringResource(R.string.engineering_kicker))

                    Text(
                        text = stringResource(R.string.engineering_headline),
                        style = MaterialTheme.typography.displayMedium.copy(
                            letterSpacing = (-0.9).sp
                        ),
                        color = SpaceWhite
                    )

                    Text(
                        text = "Quad-motor torque vectoring coupled to an autoclave carbon monocoque delivers instant linear thrust and razor-sharp chassis composure.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = SpaceTitanium
                    )

                    // Minimalist Hairline Specification Grid
                    Surface(
                        color = SpaceCarbon,
                        border = BorderStroke(1.dp, SpaceBorderSubtle),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("engineering_metrics_grid")
                    ) {
                        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                            val isWideGrid = maxWidth >= 560.dp

                            if (isWideGrid) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min)
                                ) {
                                    EngineeringGridCell(
                                        indexTag = "01 // OUTPUT",
                                        value = "${String.format(Locale.US, "%,d", animatedHp)}+ HP",
                                        label = "PEAK POWER",
                                        subLabel = "QUAD-MOTOR AWD",
                                        modifier = Modifier.weight(1f)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(SpaceBorderSubtle)
                                    )
                                    EngineeringGridCell(
                                        indexTag = "02 // SPRINT",
                                        value = String.format(Locale.US, "%.1fs", animatedAccel),
                                        label = "0–100 KM/H",
                                        subLabel = "LAUNCH CONTROL",
                                        modifier = Modifier.weight(1f)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(SpaceBorderSubtle)
                                    )
                                    EngineeringGridCell(
                                        indexTag = "03 // VELOCITY",
                                        value = "$animatedSpeed+ KM/H",
                                        label = "TOP SPEED",
                                        subLabel = "ACTIVE AERO DRS",
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            } else {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    EngineeringGridCell(
                                        indexTag = "01 // OUTPUT",
                                        value = "${String.format(Locale.US, "%,d", animatedHp)}+ HP",
                                        label = "PEAK HORSEPOWER",
                                        subLabel = "QUAD-MOTOR TRI-AXIAL POWERTRAIN",
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    HairlineDivider()
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(IntrinsicSize.Min)
                                    ) {
                                        EngineeringGridCell(
                                            indexTag = "02 // SPRINT",
                                            value = String.format(Locale.US, "%.1fs", animatedAccel),
                                            label = "0–100 KM/H",
                                            subLabel = "LAUNCH TELEMETRY",
                                            modifier = Modifier.weight(1f)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .width(1.dp)
                                                .fillMaxHeight()
                                                .background(SpaceBorderSubtle)
                                        )
                                        EngineeringGridCell(
                                            indexTag = "03 // VELOCITY",
                                            value = "$animatedSpeed+",
                                            label = "KM/H TOP SPEED",
                                            subLabel = "ACTIVE AERO",
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(4f / 3f)
                            .clip(RoundedCornerShape(4.dp))
                            .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(4.dp))
                            .testTag("engineering_powertrain_parallax_frame")
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_engineering_powertrain_1795538367934),
                            contentDescription = "SPACE ONE exposed carbon monocoque and titanium pushrod suspension",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    val baseScale = 1.14f + (1f - kotlin.math.abs(clampedParallax)) * 0.03f
                                    scaleX = baseScale
                                    scaleY = baseScale
                                    translationY = -clampedParallax * (size.height * 0.055f)
                                }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        0.0f to SpaceBlack.copy(alpha = 0.22f),
                                        0.45f to Color.Transparent,
                                        1.0f to SpaceBlack.copy(alpha = 0.38f)
                                    )
                                )
                        )
                    }

                    // Drive Mode Telemetry Switcher
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "TELEMETRY CALIBRATION MODE",
                            style = MaterialTheme.typography.labelMedium,
                            color = SpaceTitanium
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            driveModes.forEachIndexed { index, mode ->
                                val selected = index == selectedDriveModeIndex
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(2.dp))
                                        .border(
                                            1.dp,
                                            if (selected) SpaceIvory else SpaceBorderSubtle,
                                            RoundedCornerShape(2.dp)
                                        )
                                        .background(if (selected) SpaceIvory else SpaceCarbon)
                                        .clickable { onSelectDriveMode(index) }
                                        .heightIn(min = 48.dp)
                                        .padding(vertical = 12.dp)
                                        .testTag("drive_mode_$index"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = mode.name,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = if (selected) SpaceBlack else SpaceTitanium
                                    )
                                }
                            }
                        }

                        Surface(
                            color = SpaceCarbon,
                            border = BorderStroke(1.dp, SpaceBorderStrong),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Column {
                                        Text(
                                            text = "${activeMode.name} TELEMETRY",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = SpaceTitanium
                                        )
                                        Text(
                                            text = "$animatedModeOutput HP",
                                            style = MaterialTheme.typography.headlineLarge,
                                            color = SpaceWhite
                                        )
                                    }
                                    Text(
                                        text = activeMode.aeroProfile,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SpaceIvory
                                    )
                                }

                                Text(
                                    text = activeMode.subtitle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = SpaceTitanium
                                )

                                PowerDeliveryCurveCanvas(
                                    points = activeMode.curveControlPoints,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                )

                                HairlineDivider()

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    TelemetryChip("RIDE HEIGHT", activeMode.rideHeightMm)
                                    TelemetryChip("TORQUE SPLIT", activeMode.torqueSplit)
                                    TelemetryChip("RESPONSE", "0.8 MS")
                                }
                            }
                        }
                    }
                }
            }
        }
        }
    }
}

@Composable
private fun EngineeringGridCell(
    indexTag: String,
    value: String,
    label: String,
    subLabel: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 18.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = indexTag,
            style = MaterialTheme.typography.labelSmall,
            color = SpaceTitanium.copy(alpha = 0.65f),
            maxLines = 1
        )
        Text(
            text = value,
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 36.sp,
                letterSpacing = (-0.8).sp
            ),
            color = SpaceIvory,
            maxLines = 1
        )
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = SpaceWhite,
                maxLines = 1
            )
            Text(
                text = subLabel,
                style = MaterialTheme.typography.labelSmall,
                color = SpaceTitanium.copy(alpha = 0.72f),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun TelemetryChip(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = SpaceTitanium.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = SpaceWhite
        )
    }
}

@Composable
private fun PowerDeliveryCurveCanvas(
    points: List<Float>,
    modifier: Modifier = Modifier
) {
    val animatedPoints = points.mapIndexed { idx, target ->
        val animated by animateFloatAsState(
            targetValue = target,
            animationSpec = tween(durationMillis = 420 + idx * 45),
            label = "curve_pt_$idx"
        )
        animated
    }

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(2.dp))
            .background(SpaceBlack)
            .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
            .padding(12.dp)
    ) {
        val w = size.width
        val h = size.height
        val gridRows = 3
        for (i in 1 until gridRows) {
            val y = h * i / gridRows
            drawLine(
                color = SpaceBorderSubtle,
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1f
            )
        }

        if (animatedPoints.size >= 2) {
            val stepX = w / (animatedPoints.size - 1)
            val curvePath = Path()
            val fillPath = Path()

            animatedPoints.forEachIndexed { index, value ->
                val x = index * stepX
                val y = h - (value.coerceIn(0f, 1f) * h * 0.88f) - h * 0.06f
                if (index == 0) {
                    curvePath.moveTo(x, y)
                    fillPath.moveTo(x, h)
                    fillPath.lineTo(x, y)
                } else {
                    val prevX = (index - 1) * stepX
                    val prevY = h - (animatedPoints[index - 1].coerceIn(0f, 1f) * h * 0.88f) - h * 0.06f
                    val midX = (prevX + x) / 2f
                    curvePath.cubicTo(midX, prevY, midX, y, x, y)
                    fillPath.cubicTo(midX, prevY, midX, y, x, y)
                }
                if (index == animatedPoints.lastIndex) {
                    fillPath.lineTo(x, h)
                    fillPath.close()
                }
            }

            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(SpaceIvory.copy(alpha = 0.25f), Color.Transparent)
                )
            )
            drawPath(
                path = curvePath,
                color = SpaceIvory,
                style = Stroke(width = 3f, cap = StrokeCap.Round)
            )

            animatedPoints.forEachIndexed { index, value ->
                val x = index * stepX
                val y = h - (value.coerceIn(0f, 1f) * h * 0.88f) - h * 0.06f
                drawCircle(
                    color = SpaceIvory,
                    radius = 4.5f,
                    center = Offset(x, y)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090909)
@Composable
private fun EngineeringSectionPreview() {
    SpaceAutomotiveTheme {
        EngineeringSection()
    }
}
