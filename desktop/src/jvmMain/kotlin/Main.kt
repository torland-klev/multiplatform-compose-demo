import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.klev.common.App


fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Pretend like you read something funny") {
        App()
    }
}
