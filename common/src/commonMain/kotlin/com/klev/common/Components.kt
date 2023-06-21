import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klev.common.Square
import com.klev.common.Symbol

@Composable
fun SquareAnimation(square: Square, child: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = square.symbol != Symbol.EMPTY, exit = fadeOut(), enter = fadeIn(
            animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
        )
    ) {
        child()
    }
}

@Composable
fun ResultsBanner(bannerText: String) {
    AnimatedVisibility(
        visible = bannerText.isNotEmpty(),
        enter = slideInHorizontally() + fadeIn(),
        exit = slideOutHorizontally() + fadeOut()
    ) {
        Box(
            modifier = resultsModifier,
            contentAlignment = Alignment.Center
        ) {
            Text(bannerText, fontSize = 40.sp, fontStyle = FontStyle.Italic)
        }
    }
}

val appModifier = Modifier.fillMaxSize().background(Color(116, 115, 189, 255))
val boardModifier = Modifier.clip(RoundedCornerShape(10.dp)).background(Color(227, 227, 242, 255)).size(540.dp)
val squareModifier = Modifier.border(2.dp, Color(186, 185, 222, 255)).height(180.dp)
val resultsModifier = Modifier.fillMaxWidth().height(140.dp).background(Color(214, 216, 255, 255))