package com.example.mova_projectfinal.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mova_projectfinal.R
import com.example.mova_projectfinal.screen.components.PrimaryButton
import com.example.mova_projectfinal.ui.theme.MovaBackground
import com.example.mova_projectfinal.ui.theme.MovaRed

private data class OnboardingPage(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

private val pages = listOf(
    OnboardingPage(
        R.drawable.spiderman,
        "Welcome to Mova",
        "The best movie streaming app of the century to make your days great!"
    ),
    OnboardingPage(
        R.drawable.onboarding_4,
        "Watch Anywhere",
        "Stream your favorite movies and series on any device, anytime."
    ),
    OnboardingPage(
        R.drawable.odyssey,
        "Discover New Movies",
        "Explore thousands of titles across every genre and find your next favorite."
    )
)

@Composable
fun OnboardingScreen(
    onNavigateToLetsYouIn: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { pages.size }


    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.onIntent(OnboardingContract.Intent.PageChanged(page))
        }
    }


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnboardingContract.Effect.NavigateToLetsYouIn -> onNavigateToLetsYouIn()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MovaBackground)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            OnboardingPageContent(pages[index])
        }


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageIndicator(
                pageCount = pages.size,
                currentPage = state.currentPage
            )

            Spacer(Modifier.height(24.dp))

            PrimaryButton(
                text = "Get Started",
                onClick = {
                    viewModel.onIntent(OnboardingContract.Intent.GetStartedClicked)
                }
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.4f to Color.Transparent,
                        0.75f to MovaBackground.copy(alpha = 0.9f),
                        1f to MovaBackground
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 170.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = page.title,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = page.description,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PageIndicator(pageCount: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            val width by animateDpAsState(
                targetValue = if (isSelected) 28.dp else 8.dp,
                label = "indicatorWidth"
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(if (isSelected) MovaRed else Color(0xFF6B6B6B))
            )
        }
    }
}