import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.CanvasBasedWindow
import compose_hynson.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") { AppWithFont() }
}

@Composable
fun AppWithFont() {
    var typography by remember { mutableStateOf<Typography?>(null) }
    LaunchedEffect(Unit) {
        val fontFamily = getNotoSansSCFontFamily()
        typography = Typography().run {
            val displayLarge = displayLarge.copy(fontFamily = fontFamily)
            val displayMedium = displayMedium.copy(fontFamily = fontFamily)
            val displaySmall = displaySmall.copy(fontFamily = fontFamily)
            val headlineLarge = headlineLarge.copy(fontFamily = fontFamily)
            val headlineMedium = headlineMedium.copy(fontFamily = fontFamily)
            val headlineSmall = headlineSmall.copy(fontFamily = fontFamily)
            val titleLarge = titleLarge.copy(fontFamily = fontFamily)
            val titleMedium = titleMedium.copy(fontFamily = fontFamily)
            val titleSmall = titleSmall.copy(fontFamily = fontFamily)
            val bodyLarge = bodyLarge.copy(fontFamily = fontFamily)
            val bodyMedium = bodyMedium.copy(fontFamily = fontFamily)
            val bodySmall = bodySmall.copy(fontFamily = fontFamily)
            val labelLarge = labelLarge.copy(fontFamily = fontFamily)
            val labelMedium = labelMedium.copy(fontFamily = fontFamily)
            val labelSmall = labelSmall.copy(fontFamily = fontFamily)
            copy(
                displayLarge,
                displayMedium,
                displaySmall,
                headlineLarge,
                headlineMedium,
                headlineSmall,
                titleLarge,
                titleMedium,
                titleSmall,
                bodyLarge,
                bodyMedium,
                bodySmall,
                labelLarge,
                labelMedium,
                labelSmall
            )
        }
    }
    App(typography)
}

@OptIn(ExperimentalResourceApi::class)
suspend fun loadFont(
    path: String, identity: String,
    weight: FontWeight, style: FontStyle
): Font {
    val data = Res.readBytes(path)
    return Font(identity, data, weight, style)
}

suspend fun getNotoSansSCFontFamily(): FontFamily {
    val black =
        loadFont("fonts/NotoSansSC-Black.ttf", "regular", FontWeight.Black, FontStyle.Normal)
    val bold =
        loadFont("fonts/NotoSansSC-Bold.ttf", "bold", FontWeight.Bold, FontStyle.Normal)
    val extraBold =
        loadFont(
            "fonts/NotoSansSC-ExtraBold.ttf",
            "extraBold",
            FontWeight.ExtraBold,
            FontStyle.Normal
        )
    val extraLight =
        loadFont(
            "fonts/NotoSansSC-ExtraLight.ttf",
            "extraLight",
            FontWeight.ExtraLight,
            FontStyle.Normal
        )
    val light = loadFont("fonts/NotoSansSC-Light.ttf", "light", FontWeight.Light, FontStyle.Normal)
    val medium =
        loadFont("fonts/NotoSansSC-Medium.ttf", "medium", FontWeight.Medium, FontStyle.Normal)
    val regular =
        loadFont("fonts/NotoSansSC-Regular.ttf", "regular", FontWeight.Normal, FontStyle.Normal)
    val semiBold =
        loadFont("fonts/NotoSansSC-SemiBold.ttf", "semibold", FontWeight.SemiBold, FontStyle.Normal)
    val thin =
        loadFont("fonts/NotoSansSC-Thin.ttf", "thin", FontWeight.Thin, FontStyle.Normal)

    return FontFamily(
        listOf(black, bold, extraBold, extraLight, light, medium, regular, semiBold, thin)
    )
}