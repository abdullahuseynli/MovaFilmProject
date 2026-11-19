package com.example.mova_projectfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mova_projectfinal.navigation.NavGraph
import com.example.mova_projectfinal.ui.theme.Mova_ProjectFinalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mova_ProjectFinalTheme {
                NavGraph()

            }
        }
    }
}

