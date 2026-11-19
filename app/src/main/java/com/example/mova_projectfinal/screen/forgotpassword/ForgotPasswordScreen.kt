package com.example.mova_projectfinal.screen.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mova_projectfinal.R
import com.example.mova_projectfinal.screen.components.MovaTextField
import com.example.mova_projectfinal.screen.components.PrimaryButton
import com.example.mova_projectfinal.ui.theme.MovaBackground
import com.example.mova_projectfinal.ui.theme.MovaRed
import com.example.mova_projectfinal.ui.theme.MovaTextSecondary

@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ForgotPasswordContract.Effect.NavigateBack -> onNavigateBack()
                is ForgotPasswordContract.Effect.ShowMessage ->
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
                onClick = { viewModel.onIntent(ForgotPasswordContract.Intent.BackClicked) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Forgot Password",
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
            Spacer(Modifier.height(48.dp))

            Image(
                painter = painterResource(R.drawable.mova_logo),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.height(32.dp))

            if (state.isEmailSent) {
                SentContent(
                    email = state.email,
                    onResend = { viewModel.onIntent(ForgotPasswordContract.Intent.SendClicked) },
                    onBackToLogin = { viewModel.onIntent(ForgotPasswordContract.Intent.BackClicked) },
                    isLoading = state.isLoading
                )
            } else {
                Text(
                    text = "Enter your email and we'll send you a link to reset your password.",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(32.dp))

                MovaTextField(
                    value = state.email,
                    onValueChange = {
                        viewModel.onIntent(ForgotPasswordContract.Intent.EmailChanged(it))
                    },
                    placeholder = "Email",
                    leadingIcon = Icons.Default.Email,
                    errorText = state.emailError,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                )

                Spacer(Modifier.height(32.dp))

                PrimaryButton(
                    text = "Send Reset Link",
                    isLoading = state.isLoading,
                    onClick = { viewModel.onIntent(ForgotPasswordContract.Intent.SendClicked) }
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SentContent(
    email: String,
    onResend: () -> Unit,
    onBackToLogin: () -> Unit,
    isLoading: Boolean
) {
    Text(
        text = "Check your email",
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(12.dp))

    Text(
        text = "We sent a password reset link to",
        color = MovaTextSecondary,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )
    Text(
        text = email.trim(),
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.height(12.dp))

    Text(
        text = "If you don't see it, check your spam folder.",
        color = MovaTextSecondary,
        fontSize = 12.sp,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.height(32.dp))

    PrimaryButton(text = "Back to Login", onClick = onBackToLogin)

    Spacer(Modifier.height(20.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Didn't get the email?", color = MovaTextSecondary, fontSize = 12.sp)
        Spacer(Modifier.width(6.dp))
        Text(
            text = if (isLoading) "Sending..." else "Resend",
            color = MovaRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable(enabled = !isLoading, onClick = onResend)
        )
    }

    Spacer(Modifier.height(24.dp))
}