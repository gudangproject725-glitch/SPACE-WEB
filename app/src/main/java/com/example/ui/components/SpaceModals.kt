package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.GalleryItem
import com.example.data.SpaceShowcaseRepository
import com.example.ui.theme.SpaceBlack
import com.example.ui.theme.SpaceBorderStrong
import com.example.ui.theme.SpaceBorderSubtle
import com.example.ui.theme.SpaceCarbon
import com.example.ui.theme.SpaceError
import com.example.ui.theme.SpaceGraphite
import com.example.ui.theme.SpaceIvory
import com.example.ui.theme.SpaceTitanium
import com.example.ui.theme.SpaceWhite
import com.example.ui.viewmodel.SpaceUiState

@Composable
fun GalleryLightboxDialog(
    item: GalleryItem?,
    onDismiss: () -> Unit
) {
    if (item == null) return

    var scale by remember(item.id) { mutableFloatStateOf(1f) }
    var offset by remember(item.id) { mutableStateOf(Offset.Zero) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BackHandler(onBack = onDismiss)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SpaceBlack)
                .statusBarsPadding()
                .navigationBarsPadding()
                .testTag("gallery_lightbox_dialog")
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(item.id) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            val newScale = (scale * zoom).coerceIn(1f, 3.2f)
                            scale = newScale
                            offset = if (newScale > 1.02f) {
                                Offset(
                                    x = (offset.x + pan.x).coerceIn(-500f, 500f),
                                    y = (offset.y + pan.y).coerceIn(-500f, 500f)
                                )
                            } else {
                                Offset.Zero
                            }
                        }
                    }
                    .pointerInput(item.id) {
                        detectTapGestures(
                            onDoubleTap = {
                                if (scale > 1.2f) {
                                    scale = 1f
                                    offset = Offset.Zero
                                } else {
                                    scale = 2.2f
                                }
                            }
                        )
                    }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = item.indexLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = SpaceIvory
                    )
                    Text(
                        text = item.studioMetadata,
                        style = MaterialTheme.typography.labelSmall,
                        color = SpaceTitanium
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(48.dp)
                        .background(SpaceCarbon.copy(alpha = 0.85f), RoundedCornerShape(2.dp))
                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                        .testTag("close_lightbox_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close detail view",
                        tint = SpaceIvory
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(SpaceBlack.copy(alpha = 0.88f))
                    .border(1.dp, SpaceBorderSubtle)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ZoomIn,
                        contentDescription = null,
                        tint = SpaceTitanium,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "PINCH OR DOUBLE-TAP TO INSPECT",
                        style = MaterialTheme.typography.labelSmall,
                        color = SpaceTitanium
                    )
                }
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PrivateInquirySheet(
    uiState: SpaceUiState,
    onDismiss: () -> Unit,
    onSelectExperienceType: (String) -> Unit,
    onSelectAtelierCity: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onResetConfirmation: () -> Unit
) {
    if (!uiState.isInquirySheetOpen) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SpaceCarbon,
        contentColor = SpaceWhite,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        dragHandle = null
    ) {
        BackHandler(onBack = onDismiss)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .testTag("private_inquiry_sheet"),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionKicker(text = "PRIVATE ATELIER CONCIERGE")
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, SpaceBorderSubtle, RoundedCornerShape(2.dp))
                        .testTag("close_inquiry_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close concierge inquiry",
                        tint = SpaceIvory
                    )
                }
            }

            val confirmation = uiState.inquiryConfirmation
            if (confirmation != null) {
                Surface(
                    color = SpaceBlack,
                    border = BorderStroke(1.dp, SpaceBorderStrong),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("inquiry_confirmation_card")
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Inquiry registered",
                            tint = SpaceIvory,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = "DOSSIER REGISTERED.",
                            style = MaterialTheme.typography.headlineLarge,
                            color = SpaceWhite
                        )
                        Text(
                            text = "Your private concierge request for ${confirmation.experienceType} at our ${confirmation.atelierCity} Atelier has been logged for ${confirmation.guestName}.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = SpaceTitanium
                        )
                        HairlineDivider()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "REFERENCE ID",
                                style = MaterialTheme.typography.labelMedium,
                                color = SpaceTitanium
                            )
                            Text(
                                text = confirmation.referenceCode,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = SpaceIvory
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            SpaceOutlineButton(
                                text = "NEW REQUEST",
                                onClick = onResetConfirmation,
                                testTag = "new_inquiry_button",
                                modifier = Modifier.weight(1f)
                            )
                            SpacePrimaryButton(
                                text = "DONE",
                                onClick = onDismiss,
                                testTag = "done_inquiry_button",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "REQUEST AN EXCLUSIVE EXPERIENCE.",
                    style = MaterialTheme.typography.headlineLarge,
                    color = SpaceWhite
                )
                Text(
                    text = "Arrange a private studio viewing, track telemetry session, or bespoke SPACE ONE allocation dossier.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SpaceTitanium
                )

                Text(
                    text = "01 / EXPERIENCE FORMAT",
                    style = MaterialTheme.typography.labelMedium,
                    color = SpaceTitanium
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpaceShowcaseRepository.experienceTypes.forEach { type ->
                        val selected = uiState.selectedExperienceType == type
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(2.dp))
                                .border(
                                    width = 1.dp,
                                    color = if (selected) SpaceIvory else SpaceBorderSubtle,
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .background(if (selected) SpaceIvory else SpaceBlack)
                                .clickable { onSelectExperienceType(type) }
                                .heightIn(min = 48.dp)
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = type,
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.2.sp),
                                color = if (selected) SpaceBlack else SpaceTitanium
                            )
                        }
                    }
                }

                Text(
                    text = "02 / ATELIER LOCATION",
                    style = MaterialTheme.typography.labelMedium,
                    color = SpaceTitanium
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpaceShowcaseRepository.atelierLocations.forEach { atelier ->
                        val selected = uiState.selectedAtelierCity == atelier.city
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(2.dp))
                                .border(
                                    width = 1.dp,
                                    color = if (selected) SpaceIvory else SpaceBorderSubtle,
                                    shape = RoundedCornerShape(2.dp)
                                )
                                .background(if (selected) SpaceGraphite else SpaceBlack)
                                .clickable { onSelectAtelierCity(atelier.city) }
                                .heightIn(min = 48.dp)
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${atelier.code} · ${atelier.city}",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (selected) SpaceWhite else SpaceTitanium
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = uiState.guestName,
                    onValueChange = onNameChange,
                    label = {
                        Text(
                            text = "FULL NAME",
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SpaceIvory,
                        unfocusedBorderColor = SpaceBorderSubtle,
                        focusedLabelColor = SpaceIvory,
                        unfocusedLabelColor = SpaceTitanium,
                        focusedTextColor = SpaceWhite,
                        unfocusedTextColor = SpaceWhite,
                        cursorColor = SpaceIvory
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("inquiry_name_input")
                )

                OutlinedTextField(
                    value = uiState.guestEmail,
                    onValueChange = onEmailChange,
                    label = {
                        Text(
                            text = "PRIVATE EMAIL",
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SpaceIvory,
                        unfocusedBorderColor = SpaceBorderSubtle,
                        focusedLabelColor = SpaceIvory,
                        unfocusedLabelColor = SpaceTitanium,
                        focusedTextColor = SpaceWhite,
                        unfocusedTextColor = SpaceWhite,
                        cursorColor = SpaceIvory
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("inquiry_email_input")
                )

                if (uiState.inquiryError != null) {
                    Text(
                        text = uiState.inquiryError,
                        style = MaterialTheme.typography.bodyMedium,
                        color = SpaceError,
                        modifier = Modifier.testTag("inquiry_error_text")
                    )
                }

                SpacePrimaryButton(
                    text = "SUBMIT CONCIERGE REQUEST",
                    onClick = onSubmit,
                    testTag = "submit_inquiry_button",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
