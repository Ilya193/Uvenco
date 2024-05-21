package ru.ikom.uvenco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import ru.ikom.catalog.presentation.CatalogScreen
import ru.ikom.common.theme.Gray
import ru.ikom.common.theme.Main
import ru.ikom.common.theme.UvencoTheme
import ru.ikom.details.presentation.DetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            UvencoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(viewModel: MainViewModel = koinViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.start()
            }
            if (event == Lifecycle.Event.ON_STOP) {
                viewModel.stop()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Main)
    ) {
        TopContent(date = state.date, temperature = state.temperature) {
            val start = navController.graph.findStartDestination().route
            val current = navBackStackEntry?.destination?.route
            if (start != current) navController.popBackStack()
        }
        NavHost(
            modifier = Modifier,
            navController = navController,
            startDestination = Catalog,
            enterTransition = { slideInHorizontally(animationSpec = tween(500)) { it } },
            exitTransition = { slideOutHorizontally(animationSpec = tween(500)) { it } }
        ) {
            composable<Catalog> {
                CatalogScreen { id ->
                    navController.navigate(Settings(id))
                }
            }

            composable<Settings> {
                val settings: Settings = it.toRoute()
                DetailsScreen(settings.id) {
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun TopContent(date: String, temperature: String, onClickLogo: () -> Unit) {
    Box(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .height(54.dp)
            .padding(horizontal = 12.dp)
            .background(Main),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.clickable(interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null, onClick = onClickLogo)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.union),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp), text = stringResource(R.string.title), color = Gray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(end = 4.dp),
                    text = date,
                    color = Gray
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(end = 4.dp),
                    text = "${temperature}°",
                    color = Gray
                )
                Image(
                    modifier = Modifier
                        .size(11.dp)
                        .padding(end = 2.dp),
                    painter = painterResource(id = R.drawable.mask),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(end = 4.dp),
                    text = stringResource(R.string.mask),
                    color = Gray
                )
            }
        }
    }
}