package com.gcolina.rickandmorty.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gcolina.rickandmorty.presentation.detail.view.DetailScreen
import com.gcolina.rickandmorty.presentation.home.view.HomeScreen
import com.gcolina.rickandmorty.presentation.splash.view.SplashScreen

@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash_Screen,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(700)
            ) + fadeIn(animationSpec = tween(700))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it }, // Desplaza hacia la izquierda
                animationSpec = tween(700)
            ) + fadeOut(animationSpec = tween(700))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it }, animationSpec = tween(700)
            ) + fadeIn(animationSpec = tween(700))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(700)
            ) + fadeOut(animationSpec = tween(700))
        }) {
        composable<Splash_Screen>{
            SplashScreen(
                navigateToHome = {
                    navController.navigate(Home_Screen)
                }
            )
        }
        composable<Home_Screen>{
            HomeScreen(
                navigateToDetail = {
                    navController.navigate(Detail_Screen)
                }
            )
        }
        composable<Detail_Screen> {
            DetailScreen {
                navController.popBackStack()
            }
        }
    }

}