package com.example.ui.sections

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.EditorialReveal
import com.example.ui.components.HairlineDivider
import com.example.ui.components.SectionKicker
import com.example.ui.components.SpaceSectionContainer
import com.example.ui.theme.SpaceAmberAccent
import com.example.ui.theme.SpaceAutomotiveTheme
import com.example.ui.theme.SpaceBlack
import com.example.ui.theme.SpaceBorderStrong
import com.example.ui.theme.SpaceBorderSubtle
import com.example.ui.theme.SpaceIvory
import com.example.ui.theme.SpaceTitanium
import com.example.ui.theme.SpaceWhite
import kotlin.math.roundToInt

@Composable
fun HeroScreen(
    modifier: Modifier = Modifier,
    scrollOffsetPx: Float = 0f,
    onExploreClick: () -> Unit = {},
    onReserveClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "hero_scroll_cue")
    val scrollLineY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hero_scroll_line_y"
    )

    val spotlightParallaxY = (scrollOffsetPx * 0.14f).coerceIn(0f, 110f)
    val spotlightParallaxX = -(scrollOffsetPx * 0.04f).coerceIn(0f, 32f)
    val imageZoomBoost = (scrollOffsetPx / 4500f).coerceIn(0f, 0.08f)

    val headlineText = stringResource(R.string.hero_headline)
    val styledHeadline = buildAnnotatedString {
        val splitToken = "PERFORMANCE"
        val splitIndex = headlineText.indexOf(splitToken)
        if (splitIndex >= 0) {
            append(headlineText.substring(0, splitIndex))
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(splitToken)
            }
        } else {
            append(headlineText)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("section_hero"),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 700.dp)
        ) {
            val isWide = maxWidth >= 680.dp
            val heroHeight = if (isWide) 780.dp else 756.dp

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heroHeight)
                    .clipToBounds()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_hypercar_1795538366516),
                    contentDescription = "SPACE ONE matte black hypercar in dark studio",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            val baseScale = 1.14f + imageZoomBoost
                            val topOverscan = size.height * ((baseScale - 1f) * 0.5f)
                            val shift = (scrollOffsetPx * 0.28f).coerceIn(0f, topOverscan * 1.3f)
                            scaleX = baseScale
                            scaleY = baseScale
                            translationY = (-topOverscan * 0.35f) + shift
                        }
                )

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            translationX = spotlightParallaxX
                            translationY = spotlightParallaxY
                        }
                ) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                SpaceAmberAccent.copy(alpha = 0.13f),
                                SpaceIvory.copy(alpha = 0.05f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.56f, size.height * 0.46f),
                            radius = size.maxDimension * 0.54f
                        ),
                        center = Offset(size.width * 0.56f, size.height * 0.46f),
                        radius = size.maxDimension * 0.54f
                    )

                    drawLine(
                        color = SpaceIvory.copy(alpha = 0.06f),
                        start = Offset(0f, size.height * 0.62f),
                        end = Offset(size.width, size.height * 0.54f),
                        strokeWidth = 1f
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0.0f to SpaceBlack.copy(alpha = 0.88f),
                                0.24f to SpaceBlack.copy(alpha = 0.25f),
                                0.50f to SpaceBlack.copy(alpha = 0.32f),
                                0.74f to SpaceBlack.copy(alpha = 0.88f),
                                1.0f to SpaceBlack
                            )
                        )
                )

                SpaceSectionContainer(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(top = 74.dp, bottom = 28.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditorialReveal(delayMillis = 80) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    SectionKicker(text = stringResource(R.string.hero_kicker))
                                    Text(
                                        text = stringResource(R.string.hero_eyebrow),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.88f)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                                        .background(SpaceBlack.copy(alpha = 0.6f), RoundedCornerShape(2.dp))
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "1,040 HP // 2.5S",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        EditorialReveal(delayMillis = 160) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = styledHeadline,
                                    style = (if (isWide) {
                                        MaterialTheme.typography.displayLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 56.sp,
                                            lineHeight = 58.sp,
                                            letterSpacing = (-1.4).sp
                                        )
                                    } else {
                                        MaterialTheme.typography.displayLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 38.sp,
                                            lineHeight = 41.sp,
                                            letterSpacing = (-1.0).sp
                                        )
                                    }),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.testTag("hero_headline")
                                )

                                Text(
                                    text = stringResource(R.string.hero_subtitle),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = SpaceTitanium,
                                    modifier = Modifier
                                        .fillMaxWidth(if (isWide) 0.58f else 0.94f)
                                        .testTag("hero_subheadline")
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                OutlinedButton(
                                    onClick = onExploreClick,
                                    shape = RoundedCornerShape(2.dp),
                                    border = BorderStroke(1.2.dp, SpaceBorderStrong),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = SpaceBlack.copy(alpha = 0.55f),
                                        contentColor = MaterialTheme.colorScheme.primary
                                    ),
                                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                                    modifier = (if (isWide) Modifier else Modifier.fillMaxWidth())
                                        .heightIn(min = 52.dp)
                                        .testTag("hero_explore_button")
                                ) {
                                    Text(
                                        text = stringResource(R.string.hero_cta),
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.Medium,
                                            letterSpacing = 1.8.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                HairlineDivider()

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(1.dp)
                                                .height(26.dp)
                                                .background(SpaceBorderSubtle)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .offset { IntOffset(0, scrollLineY.roundToInt()) }
                                                    .width(1.dp)
                                                    .height(10.dp)
                                                    .background(SpaceIvory)
                                            )
                                        }
                                        Text(
                                            text = "SCROLL TO DISCOVER",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = SpaceTitanium
                                        )
                                    }

                                    Text(
                                        text = "PRIVATE CONCIERGE ↗",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = SpaceIvory,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(2.dp))
                                            .clickable(onClick = onReserveClick)
                                            .heightIn(min = 48.dp)
                                            .padding(horizontal = 8.dp, vertical = 14.dp)
                                            .testTag("hero_concierge_link")
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090909)
@Composable
private fun HeroScreenPreview() {
    SpaceAutomotiveTheme {
        HeroScreen()
    }
}
