package reschikov.test.test_uvenco.ui.base_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reschikov.test.test_uvenco.R
import reschikov.test.test_uvenco.ui.theme.FootNote
import reschikov.test.test_uvenco.ui.theme.ToolbarColor
import reschikov.test.test_uvenco.ui.theme.styleBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    stateTime: State<String>,
    stateTem: State<String>,
    goto: () -> Unit
) {
    Column {
        TopAppBar(
            title = {},
            modifier = modifier,
            navigationIcon = {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { goto() }
                ) {
                    Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_union),
                        contentDescription = null, tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "RUNERO Touch", style = styleBody, color = ToolbarColor)
                }
            },
            actions = {
                ShowData(state = stateTime)
                ShowData(state = stateTem, icon = {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_drop),
                            contentDescription = null, tint = Color.Unspecified )
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.mask_group),
                        contentDescription = null, modifier = Modifier.size(11.dp))
                    Text(text = "RU", style = styleBody, color = FootNote)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
        )
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = ToolbarColor)
    }
}

@Composable
private fun <T> ShowData(
    modifier: Modifier = Modifier,
    state: State<T>,
    icon: @Composable (() -> Unit)? = null
) {

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.width(80.dp)
    ) {
        Text(text = "${state.value}", style = styleBody, color = FootNote,
            textAlign = TextAlign.End, modifier = Modifier.weight(1f) )
        icon?.invoke()
    }
}


@Preview(showBackground = true,
    device = "spec:width=1280px,height=800px,dpi=440"
)
@Composable
fun PreviewToolbar() {
    Toolbar(
        modifier = Modifier.fillMaxWidth(),
        stateTime = remember { mutableStateOf("99:99")},
        stateTem = remember { mutableStateOf("90.0") }) {

    }
}