package kiet.imam.mymovies.core.presentation1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kiet.imam.mymovies.details.presentation.DetailsScreen
import kiet.imam.mymovies.movieList.presentation.MovieListVIewModel
import kiet.imam.mymovies.movieList.utils.Screen
import kiet.imam.mymovies.ui.theme.MyMoviesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMoviesTheme {
                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                val navHostController = rememberNavController()
                NavHost(navController = navHostController, startDestination = Screen.Home.rout) {
                    composable(Screen.Home.rout) {
                        HomeScreen(navHostController)
                    }
                    composable(Screen.Details.rout + "/{movieId}",
                        arguments = listOf(
                            navArgument("movieId") { type = NavType.IntType}
                        )
                    ) {
                       DetailsScreen()
                    }

                }
            }
        }
    }
    @Composable
    private  fun SetBarColor(color : Color){
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            systemUiController.setSystemBarsColor(color)
            
        }

    }
}

