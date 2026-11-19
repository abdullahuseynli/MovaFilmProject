package com.example.mova_projectfinal.screen.newpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mova_projectfinal.R
import com.example.mova_projectfinal.screen.components.MovaTextField
import com.example.mova_projectfinal.screen.components.PrimaryButton
import com.example.mova_projectfinal.ui.theme.MovaBackground
import com.example.mova_projectfinal.ui.theme.MovaRed
import com.example.mova_projectfinal.ui.theme.MovaSurface
import com.example.mova_projectfinal.ui.theme.MovaTextSecondary

@Composable
fun CreateNewPasswordScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: CreateNewPasswordViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateNewPasswordContract.Effect.NavigateBack -> onNavigateBack()
                CreateNewPasswordContract.Effect.NavigateToLogin -> onNavigateToLogin()
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
                onClick = { viewModel.onIntent(CreateNewPasswordContract.Intent.BackClicked) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Create New Password",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(24.dp))

            // Dizayndakı illüstrasiya
            Image(
                painter = painterResource(R.drawable.editpassword),
                contentDescription = null,
                modifier = Modifier
                    .size(220.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Create Your New Password",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(20.dp))

            MovaTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onIntent(CreateNewPasswordContract.Intent.PasswordChanged(it))
                },
                placeholder = "New Password",
                leadingIcon = Icons.Default.Lock,
                errorText = state.passwordError,
                isPassword = true,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    viewModel.onIntent(CreateNewPasswordContract.Intent.TogglePasswordVisibility)
                }
            )

            Spacer(Modifier.height(16.dp))

            MovaTextField(
                value = state.confirmPassword,
                onValueChange = {
                    viewModel.onIntent(CreateNewPasswordContract.Intent.ConfirmChanged(it))
                },
                placeholder = "Confirm Password",
                leadingIcon = Icons.Default.Lock,
                errorText = state.confirmError,
                isPassword = true,
                isPasswordVisible = state.isConfirmVisible,
                onTogglePasswordVisibility = {
                    viewModel.onIntent(CreateNewPasswordContract.Intent.ToggleConfirmVisibility)
                },
                imeAction = ImeAction.Done
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.rememberMe,
                    onCheckedChange = {
                        viewModel.onIntent(CreateNewPasswordContract.Intent.RememberMeChanged(it))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MovaRed,
                        uncheckedColor = MovaTextSecondary,
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = "Remember me",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(24.dp))
        }

        PrimaryButton(
            text = "Continue",
            isLoading = state.isLoading,
            onClick = { viewModel.onIntent(CreateNewPasswordContract.Intent.ContinueClicked) },
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )
    }

    if (state.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onIntent(CreateNewPasswordContract.Intent.DialogDismissed)
            },
            containerColor = MovaSurface,
            title = {
                Text("Password Updated", color = Color.White, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Your password has been changed. Please sign in again.",
                    color = MovaTextSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onIntent(CreateNewPasswordContract.Intent.DialogDismissed)
                    }
                ) {
                    Text("OK", color = MovaRed, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}