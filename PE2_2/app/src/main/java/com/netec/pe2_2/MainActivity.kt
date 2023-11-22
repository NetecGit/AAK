package com.netec.pe2_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.netec.pe2_2.ui.theme.PE2_2Theme
import com.netec.pe2_2.view.MainScreen
import com.netec.pe2_2.viewmodel.MainScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PE2_2Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MVVMPracApp()
                }
            }
        }
    }
}


@Composable
private fun MVVMPracApp() {
    val viewModel: MainScreenViewModel = viewModel()

    MainScreen(
        viewModel = viewModel
    )
}