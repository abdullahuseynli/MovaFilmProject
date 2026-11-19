package com.example.mova_projectfinal.screen.fillprofile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.mova_projectfinal.screen.components.MovaTextField
import com.example.mova_projectfinal.screen.components.PrimaryButton
import com.example.mova_projectfinal.ui.theme.MovaBackground
import com.example.mova_projectfinal.ui.theme.MovaRed
import com.example.mova_projectfinal.ui.theme.MovaSurface
import com.example.mova_projectfinal.ui.theme.MovaTextSecondary

@Composable
fun FillProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: FillProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val photoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.onIntent(FillProfileContract.Intent.PhotoSelected(uri))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                FillProfileContract.Effect.NavigateBack -> onNavigateBack()
                FillProfileContract.Effect.NavigateToHome -> onNavigateToHome()
                is FillProfileContract.Effect.ShowMessage ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MovaBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.onIntent(FillProfileContract.Intent.BackClicked) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Fill Your Profile",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Box(modifier = Modifier.size(120.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MovaSurface)
                        .clickable {
                            photoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (state.photoUri != null) {
                        AsyncImage(
                            model = state.photoUri,
                            contentDescription = "Profile photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MovaTextSecondary,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(32.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MovaRed)
                        .clickable {
                            photoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Change photo",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            MovaTextField(
                value = state.fullName,
                onValueChange = { viewModel.onIntent(FillProfileContract.Intent.FullNameChanged(it)) },
                placeholder = "Full Name",
                leadingIcon = Icons.Default.Person,
                errorText = state.fullNameError
            )

            Spacer(Modifier.height(16.dp))

            MovaTextField(
                value = state.nickname,
                onValueChange = { viewModel.onIntent(FillProfileContract.Intent.NicknameChanged(it)) },
                placeholder = "Nickname",
                leadingIcon = Icons.Default.Person,
                errorText = state.nicknameError
            )

            Spacer(Modifier.height(16.dp))

            ReadOnlyField(
                text = state.email,
                leadingIcon = Icons.Default.Email
            )

            Spacer(Modifier.height(16.dp))

            MovaTextField(
                value = state.phone,
                onValueChange = { viewModel.onIntent(FillProfileContract.Intent.PhoneChanged(it)) },
                placeholder = "Phone Number",
                leadingIcon = Icons.Default.Phone,
                errorText = state.phoneError,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            )

            Spacer(Modifier.height(16.dp))

            GenderDropdown(
                selected = state.gender,
                onSelected = { viewModel.onIntent(FillProfileContract.Intent.GenderSelected(it)) }
            )

            Spacer(Modifier.height(24.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.onIntent(FillProfileContract.Intent.SkipClicked) },
                enabled = !state.isLoading,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF35383F),
                    disabledContainerColor = Color(0xFF35383F).copy(alpha = 0.6f)
                )
            ) {
                Text(
                    text = "Skip",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            PrimaryButton(
                text = "Continue",
                isLoading = state.isLoading,
                onClick = { viewModel.onIntent(FillProfileContract.Intent.ContinueClicked) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ReadOnlyField(
    text: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MovaSurface)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(leadingIcon, contentDescription = null, tint = MovaTextSecondary)
        Spacer(Modifier.width(16.dp))
        Text(text = text, color = MovaTextSecondary, fontSize = 14.sp)
    }
}

@Composable
private fun GenderDropdown(
    selected: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MovaSurface)
                .clickable { expanded = true }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selected.ifEmpty { "Gender" },
                color = if (selected.isEmpty()) MovaTextSecondary else Color.White,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MovaTextSecondary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MovaSurface
        ) {
            FillProfileContract.State.genders.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender, color = Color.White) },
                    onClick = {
                        onSelected(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}