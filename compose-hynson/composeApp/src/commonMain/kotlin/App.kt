import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import compose_hynson.composeapp.generated.resources.Res
import compose_hynson.composeapp.generated.resources.app_title
import org.jetbrains.compose.resources.stringResource
import screen.AboutScreen
import screen.HomeScreen
import screen.ProjectScreen
import theme.AppTheme
import theme.LocalThemeIsDark

const val narrowScreenWidthThreshold = 1300

// 吐司 Snackbar
val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> { error("SnackbarHostState is not found") }

// 底部modal navigation drawer
val LocalDrawerState =
    compositionLocalOf<DrawerState> { error("DrawerState is not found") }

// bottomSheet
@OptIn(ExperimentalMaterial3Api::class)
val LocalBottomSheetScaffoldState =
    compositionLocalOf<BottomSheetScaffoldState> { error("BottomSheetScaffoldState is not found") }

data class Screen(
    val title: String,
    val icon: ImageVector,
    val content: @Composable () -> Unit
)

val screens = listOf(
    Screen("Home", Icons.Filled.Home) { HomeScreen() },
    Screen("Project", Icons.Filled.Approval) { ProjectScreen() },
    Screen("About", Icons.Filled.Person) { AboutScreen() },
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App(typography: Typography? = null) = AppTheme(typography) {
    var screenWidth by remember { mutableStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    var selectedScreen by remember { mutableStateOf(screens[0]) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val scaffoldState = rememberBottomSheetScaffoldState(
        rememberStandardBottomSheetState(SheetValue.Hidden, skipHiddenState = false)
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.background(color = Color.Blue),
        drawerContent = {
            ModalDrawerSheet { //NavigationDrawerContent()
            }
        },
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .onGloballyPositioned {
                    screenWidth = it.size.width
                },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                @Composable
                fun RowScope.actions() {
                    var isDark by LocalThemeIsDark.current
                    IconButton(
                        onClick = { isDark = !isDark }
                    ) {
                        Icon(
                            if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle brightness"
                        )
                    }
                    if (screenWidth <= narrowScreenWidthThreshold) {
                        MenuButton(screens) {
                            selectedScreen = screens[it]
                        }
                    }
                }

                val title = stringResource(Res.string.app_title)
                if (screenWidth <= narrowScreenWidthThreshold) {
                    TopAppBar(
                        title = { androidx.compose.material3.Text(text = title) },
                        scrollBehavior = scrollBehavior,
                        actions = { actions() }
                    )
                } else {
                    CenterAlignedTopAppBar(
                        title = { androidx.compose.material3.Text(text = title) },
                        scrollBehavior = scrollBehavior,
                        actions = { actions() }
                    )
                }
            },
            content = {
                Row(
                    modifier = Modifier.padding(it).consumeWindowInsets(WindowInsets.systemBars)
                ) {
                    if (screenWidth > narrowScreenWidthThreshold) {
                        NavigationRail(
                            modifier = Modifier.padding(6.dp)
                        ) {
                            screens.forEach { screen ->
                                NavigationRailItem(
                                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                                    label = { androidx.compose.material3.Text(screen.title) },
                                    selected = selectedScreen == screen,
                                    onClick = {
                                        scrollBehavior.state.contentOffset = 0f
                                        selectedScreen = screen
                                    }
                                )
                            }
                        }
                    }
                    CompositionLocalProvider(
                        LocalSnackbarHostState provides snackbarHostState,
                        LocalBottomSheetScaffoldState provides scaffoldState,
                        LocalDrawerState provides drawerState
                    ) {
                        selectedScreen.content()
                    }
                }
            }
        )
    }
}