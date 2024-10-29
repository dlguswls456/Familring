package com.familring.presentation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.familring.domain.TimeCapsule
import com.familring.presentation.navigation.BottomNavigationBar
import com.familring.presentation.navigation.ScreenDestinations
import com.familring.presentation.screen.calendar.CalendarRoute
import com.familring.presentation.screen.chat.ChatRoute
import com.familring.presentation.screen.gallery.AlbumRoute
import com.familring.presentation.screen.gallery.GalleryRoute
import com.familring.presentation.screen.home.HomeRoute
import com.familring.presentation.screen.question.QuestionListScreen
import com.familring.presentation.screen.question.QuestionScreen
import com.familring.presentation.screen.signup.BirthRoute
import com.familring.presentation.screen.signup.DoneRoute
import com.familring.presentation.screen.signup.FamilyCountRoute
import com.familring.presentation.screen.signup.FirstRoute
import com.familring.presentation.screen.signup.NicknameRoute
import com.familring.presentation.screen.signup.PictureRoute
import com.familring.presentation.screen.signup.ProfileColorRoute
import com.familring.presentation.screen.timecapsule.TimeCapsuleCreateRoute
import com.familring.presentation.screen.timecapsule.TimeCapsuleListScreen
import com.familring.presentation.screen.timecapsule.TimeCapsuleRoute
import com.familring.presentation.screen.timecapsule.WritingTimeCapsule
import com.familring.presentation.screen.timecapsule.WritingTimeCapsuleScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val snackBarHostState = remember { SnackbarHostState() } // 스낵바 호스트
    val onShowSnackBar: (message: String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    // statusBar, navigationBar 색상 설정
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(color = Color.White)

    var showBottomBar by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val hideBottomBarScreen =
                listOf(
                    ScreenDestinations.Album.route,
                    ScreenDestinations.QuestionList.route,
                )
            showBottomBar = !hideBottomBarScreen.contains(backStackEntry.destination.route)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        bottomBar = {
            if(showBottomBar){
                BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute,
                )
            }
        },
    ) { _ ->
        MainNavHost(
            modifier = modifier,
            navController = navController,
            startDestination = ScreenDestinations.First.route,
            showSnackBar = onShowSnackBar,
        )
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    showSnackBar: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = ScreenDestinations.First.route,
        ) {
            FirstRoute(
                modifier = modifier,
                navigateToBirth = {
                    navController.navigate(ScreenDestinations.Birth.route)
                },
            )
        }

        composable(
            route = ScreenDestinations.Birth.route,
        ) {
            BirthRoute(
                modifier = modifier,
                popUpBackStack = navController::popBackStack,
                navigateToColor = {
                    navController.navigate(ScreenDestinations.ProfileColor.route)
                },
            )
        }

        composable(
            route = ScreenDestinations.ProfileColor.route,
        ) {
            ProfileColorRoute(
                modifier = modifier,
                popUpBackStack = navController::popBackStack,
                navigateToNickname = {
                    navController.navigate(ScreenDestinations.Nickname.route)
                },
            )
        }

        composable(
            route = ScreenDestinations.Nickname.route,
        ) {
            NicknameRoute(
                modifier = modifier,
                popUpBackStack = navController::popBackStack,
                navigateToPicture = {
                    navController.navigate(ScreenDestinations.Picture.route)
                },
            )
        }

        composable(
            route = ScreenDestinations.Picture.route,
        ) {
            PictureRoute(
                modifier = modifier,
                popUpBackStack = navController::popBackStack,
                navigateToCount = {
                    navController.navigate(ScreenDestinations.FamilyCount.route)
                },
            )
        }

        composable(
            route = ScreenDestinations.FamilyCount.route,
        ) {
            FamilyCountRoute(
                modifier = modifier,
                popUpBackStack = navController::popBackStack,
                navigateToDone = {
                    navController.navigate(ScreenDestinations.Done.route)
                },
            )
        }

        composable(
            route = ScreenDestinations.Done.route,
        ) {
            DoneRoute(
                modifier = modifier,
            )
        }

        composable(
            route = ScreenDestinations.Question.route,
        ) {
            QuestionScreen(
                navigateToQuestionList = {
                    navController.navigate(ScreenDestinations.QuestionList.route)
                },
            )
        }

        composable(
            route = ScreenDestinations.QuestionList.route,
        ) {
            QuestionListScreen(
                onNavigateBack = navController::popBackStack,
            )
        }

        // 타임캡슐
        composable(
            route = ScreenDestinations.TimeCapsule.route,
        ) {
            TimeCapsuleRoute(
                modifier = modifier,
                popUpBackStack = navController::popBackStack,
                navigateToCreate = {
                    navController.navigate(ScreenDestinations.TimeCapsuleCreate.route)
                },
            )
        }

        composable(
            route = ScreenDestinations.TimeCapsuleList.route,
        ) {
            TimeCapsuleListScreen(
                modifier = modifier,
                onShowSnackBar = { showSnackBar("아직 캡슐을 열 수 없어요!") },
                timeCapsules =
                    listOf(
                        TimeCapsule(0),
                        TimeCapsule(1),
                        TimeCapsule(2),
                        TimeCapsule(3),
                        TimeCapsule(4),
                        TimeCapsule(5),
                        TimeCapsule(6),
                        TimeCapsule(7, false),
                    ),
            )
        }

        composable(
            route = ScreenDestinations.WritingTimeCapsule.route,
        ) {
            WritingTimeCapsuleScreen(
                modifier = modifier,
                writingState = 0,
                navigateToCreate = { navController.navigate(ScreenDestinations.TimeCapsuleCreate.route) },
            )
        }

        composable(
            route = ScreenDestinations.TimeCapsuleCreate.route,
        ) {
            TimeCapsuleCreateRoute(
                modifier = modifier,
                popUpBackStack = navController::popBackStack,
            )
        }

        composable(
            route = ScreenDestinations.Home.route,
        ) {
            HomeRoute(modifier = modifier)
        }

        composable(
            route = ScreenDestinations.Chat.route,
        ) {
            ChatRoute(modifier = modifier)
        }

        composable(
            route = ScreenDestinations.Calendar.route,
        ) {
            CalendarRoute(modifier = modifier)
        }

        composable(
            route = ScreenDestinations.Gallery.route,
        ) {
            GalleryRoute(modifier = modifier, navigateToAlbum = {
                navController.navigate(ScreenDestinations.Album.route)
            })
        }

        composable(
            route = ScreenDestinations.Album.route,
        ) {
            AlbumRoute(modifier = modifier, onNavigateBack = navController::popBackStack)
        }
    }
}
