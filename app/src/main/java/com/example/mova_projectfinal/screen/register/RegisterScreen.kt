package com.example.mova_projectfinal.screen.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mova_projectfinal.R
import com.example.mova_projectfinal.screen.components.MovaTextField
import com.example.mova_projectfinal.screen.components.OrDivider
import com.example.mova_projectfinal.screen.components.PrimaryButton
import com.example.mova_projectfinal.screen.components.SocialIconButton
import com.example.mova_projectfinal.ui.theme.MovaBackground
import com.example.mova_projectfinal.ui.theme.MovaRed
import com.example.mova_projectfinal.ui.theme.MovaTextSecondary

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                RegisterContract.Effect.NavigateBack -> onNavigateBack()
                RegisterContract.Effect.NavigateToLogin -> onNavigateToLogin()
                RegisterContract.Effect.NavigateToHome -> onNavigateToHome()
                is RegisterContract.Effect.ShowMessage ->
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
        IconButton(
            onClick = { viewModel.onIntent(RegisterContract.Intent.BackClicked) },
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            Image(
                painter = painterResource(R.drawable.mova_logo),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Create Your Account",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(32.dp))

            MovaTextField(
                value = state.email,
                onValueChange = { viewModel.onIntent(RegisterContract.Intent.EmailChanged(it)) },
                placeholder = "Email",
                leadingIcon = Icons.Default.Email,
                errorText = state.emailError,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            Spacer(Modifier.height(16.dp))

            MovaTextField(
                value = state.password,
                onValueChange = { viewModel.onIntent(RegisterContract.Intent.PasswordChanged(it)) },
                placeholder = "Password",
                leadingIcon = Icons.Default.Lock,
                errorText = state.passwordError,
                isPassword = true,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    viewModel.onIntent(RegisterContract.Intent.TogglePasswordVisibility)
                },
                imeAction = ImeAction.Done
            )

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state.rememberMe,
                    onCheckedChange = {
                        viewModel.onIntent(RegisterContract.Intent.RememberMeChanged(it))
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

            Spacer(Modifier.height(16.dp))

            PrimaryButton(
                text = "Sign up",
                isLoading = state.isLoading,
                onClick = { viewModel.onIntent(RegisterContract.Intent.SignUpClicked) }
            )

            Spacer(Modifier.height(28.dp))

            OrDivider(text = "or continue with")

            Spacer(Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SocialIconButton(
                    icon = R.drawable.facebook,
                    onClick = { viewModel.onIntent(RegisterContract.Intent.SocialClicked("Facebook")) }
                )
                SocialIconButton(
                    icon = R.drawable.google,
                    onClick = { viewModel.onIntent(RegisterContract.Intent.SocialClicked("Google")) }
                )
                SocialIconButton(
                    icon = R.drawable.apple,
                    onClick = { viewModel.onIntent(RegisterContract.Intent.SocialClicked("Apple")) }
                )
            }

            Spacer(Modifier.height(24.dp))
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?", color = MovaTextSecondary, fontSize = 12.sp)
            Spacer(Modifier.width(6.dp))
            Text(
                text = "Sign in",
                color = MovaRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    viewModel.onIntent(RegisterContract.Intent.SignInClicked)
                }
            )
        }
    }
}