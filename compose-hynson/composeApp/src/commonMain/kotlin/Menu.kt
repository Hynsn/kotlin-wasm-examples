import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
internal fun MenuButton(screens: List<Screen>, select: (Int) -> (Unit)) {
    var menuExpanded by remember { mutableStateOf(false) }
    IconButton(
        onClick = { menuExpanded = true }
    ) {
        Icon(
            Icons.Default.Menu,
            contentDescription = "Select a seed color"
        )
    }

    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = { menuExpanded = false }
    ) {
        screens.forEachIndexed { index, item ->
            key(item.title) {
                DropdownMenuItem(
                    text = { Text(item.title) },
                    onClick = { select.invoke(index) },
                    leadingIcon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                    }
                )
            }
        }
    }
}