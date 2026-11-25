package com.example.ui.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SpaceAutomotiveTheme
import com.example.R
import com.example.data.SpaceShowcaseRepository
import com.example.data.SpecCategory
import com.example.ui.components.HairlineDivider
import com.example.ui.components.SectionKicker
import com.example.ui.components.SpacePrimaryButton
import com.example.ui.components.SpaceSectionContainer
import com.example.ui.theme.SpaceBlack
import com.example.ui.theme.SpaceBorderSubtle
import com.example.ui.theme.SpaceCarbon
import com.example.ui.theme.SpaceGraphite
import com.example.ui.theme.SpaceIvory
import com.example.ui.theme.SpaceTitanium
import com.example.ui.theme.SpaceWhite

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SpecificationsSection(
    selectedCategory: SpecCategory,
    onSelectCategory: (SpecCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredSpecs = if (selectedCategory == SpecCategory.ALL) {
        SpaceShowcaseRepository.specifications
    } else {
        SpaceShowcaseRepository.specifications.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SpaceCarbon)
            .padding(vertical = 64.dp)
            .testTag("section_specifications")
    ) {
        SpaceSectionContainer {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                SectionKicker(text = stringResource(R.string.specs_kicker))

                Text(
                    text = stringResource(R.string.specs_headline),
                    style = MaterialTheme.typography.displayMedium,
                    color = SpaceWhite
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpecCategory.entries.forEach { category ->
                        val selected = category == selectedCategory
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(2.dp))
                                .border(
                                    1.dp,
                                    if (selected) SpaceIvory else SpaceBorderSubtle,
                                    RoundedCornerShape(2.dp)
                                )
                                .background(if (selected) SpaceIvory else SpaceBlack)
                                .clickable { onSelectCategory(category) }
                                .heightIn(min = 48.dp)
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                                .testTag("spec_filter_${category.name.lowercase()}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category.label,
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.4.sp),
                                color = if (selected) SpaceBlack else SpaceTitanium
                            )
                        }
                    }
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    HairlineDivider()
                    filteredSpecs.forEach { spec ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = spec.label,
                                style = MaterialTheme.typography.labelMedium,
                                color = SpaceTitanium,
                                modifier = Modifier.weight(0.45f)
                            )
                            Text(
                                text = spec.value,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = SpaceWhite,
                                textAlign = TextAlign.End,
                                modifier = Modifier.weight(0.55f)
                            )
                        }
                        HairlineDivider()
                    }
                }

                Text(
                    text = "*Conceptual demonstration specifications for the SPACE ONE showcase.",
                    style = MaterialTheme.typography.labelSmall,
                    color = SpaceTitanium.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun ContactFooterSection(
    onRequestExperience: (String?) -> Unit,
    onBackToTop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SpaceBlack)
            .padding(top = 64.dp)
            .testTag("section_contact")
    ) {
        SpaceSectionContainer {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                SectionKicker(text = stringResource(R.string.contact_kicker))

                Text(
                    text = stringResource(R.string.contact_headline),
                    style = MaterialTheme.typography.displayLarge,
                    color = SpaceWhite
                )

                Text(
                    text = stringResource(R.string.contact_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = SpaceTitanium
                )

                SpacePrimaryButton(
                    text = stringResource(R.string.contact_cta),
                    onClick = { onRequestExperience(null) },
                    testTag = "request_experience_button",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "GLOBAL ATELIER STUDIOS",
                    style = MaterialTheme.typography.labelMedium,
                    color = SpaceTitanium
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SpaceShowcaseRepository.atelierLocations.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowItems.forEach { atelier ->
                                Surface(
                                    color = SpaceCarbon,
                                    border = BorderStroke(1.dp, SpaceBorderSubtle),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { onRequestExperience(atelier.city) }
                                        .testTag("atelier_card_${atelier.code.lowercase()}")
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = atelier.city,
                                                style = MaterialTheme.typography.labelLarge,
                                                color = SpaceWhite
                                            )
                                            Icon(
                                                imageVector = Icons.Filled.LocationOn,
                                                contentDescription = null,
                                                tint = SpaceIvory,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                        Text(
                                            text = atelier.address,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = SpaceTitanium
                                        )
                                        Text(
                                            text = atelier.status,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = SpaceIvory.copy(alpha = 0.8f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HairlineDivider()

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

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(2.dp))
                            .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                            .background(SpaceGraphite.copy(alpha = 0.5f))
                            .clickable(onClick = onBackToTop)
                            .heightIn(min = 48.dp)
                            .padding(horizontal = 14.dp)
                            .testTag("back_to_top_button")
                    ) {
                        Text(
                            text = "TOP",
                            style = MaterialTheme.typography.labelSmall,
                            color = SpaceIvory
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowUpward,
                            contentDescription = "Scroll back to top",
                            tint = SpaceIvory,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        MinimalFooter()
    }
}

@Composable
fun MinimalFooter(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SpaceBlack)
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(top = 28.dp, bottom = 40.dp)
            .testTag("minimal_footer"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HairlineDivider(modifier = Modifier.fillMaxWidth(0.32f))
        Text(
            text = stringResource(R.string.footer_copyright),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Light,
                letterSpacing = 2.2.sp
            ),
            color = SpaceTitanium.copy(alpha = 0.72f),
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("footer_copyright_text")
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090909)
@Composable
private fun MinimalFooterPreview() {
    SpaceAutomotiveTheme {
        MinimalFooter()
    }
}
