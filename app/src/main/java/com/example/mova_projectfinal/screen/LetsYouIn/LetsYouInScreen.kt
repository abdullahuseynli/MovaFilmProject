package com.example.mova_projectfinal.screen.LetsYouIn


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mova_projectfinal.R
import com.example.mova_projectfinal.screen.components.PrimaryButton
import com.example.mova_projectfinal.screen.components.SocialButton
import com.example.mova_projectfinal.ui.theme.MovaBackground
import com.example.mova_projectfinal.ui.theme.MovaRed
import com.example.mova_projectfinal.ui.theme.MovaTextSecondary

@Composable
fun LetsYouInScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LetsYouInViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LetsYouInContract.Effect.NavigateToLogin -> onNavigateToLogin()
                LetsYouInContract.Effect.NavigateToRegister -> onNavigateToRegister()
                is LetsYouInContract.Effect.ShowMessage ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MovaBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = maxHeight)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.img_lets_you_in),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Let's you in",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(28.dp))

            SocialButton(
                text = "Continue with Facebook",
                icon = R.drawable.facebook,
                onClick = { viewModel.onIntent(LetsYouInContract.Intent.FacebookClicked) }
            )
            Spacer(Modifier.height(12.dp))
            SocialButton(
                text = "Continue with Google",
                icon = R.drawable.google,
                onClick = { viewModel.onIntent(LetsYouInContract.Intent.GoogleClicked) }
            )
            Spacer(Modifier.height(12.dp))
            SocialButton(
                text = "Continue with Apple",
                icon = R.drawable.apple,
                onClick = { viewModel.onIntent(LetsYouInContract.Intent.AppleClicked) }
            )

            Spacer(Modifier.height(20.dp))

            OrDivider()

            Spacer(Modifier.height(20.dp))

            PrimaryButton(
                text = "Sign in with password",
                onClick = { viewModel.onIntent(LetsYouInContract.Intent.SignInWithPasswordClicked) }
            )

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Don't have an account?",
                    color = MovaTextSecondary,
                    fontSize = 12.sp
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "Sign up",
                    color = MovaRed,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        viewModel.onIntent(LetsYouInContract.Intent.SignUpClicked)
                    }
                )
            }
        }
    }
}

@Composable
private fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFF35383F))
        Text(
            text = "or",
            color = MovaTextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFF35383F))
    }
}