package reschikov.test.test_uvenco.ui.item_screen



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import reschikov.test.test_uvenco.R
import reschikov.test.test_uvenco.data.constance.CURRENCY
import reschikov.test.test_uvenco.data.constance.EMPTY
import reschikov.test.test_uvenco.ui.base_view.Toolbar
import reschikov.test.test_uvenco.ui.theme.Accept
import reschikov.test.test_uvenco.ui.theme.Control
import reschikov.test.test_uvenco.ui.theme.FootNote
import reschikov.test.test_uvenco.ui.theme.TitleField
import reschikov.test.test_uvenco.ui.theme.styleControl
import reschikov.test.test_uvenco.ui.theme.styleFootnote
import reschikov.test.test_uvenco.ui.theme.styleHeaderH4

@Composable
fun DetailCoffeeScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailCoffeeViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    goto: () -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            Toolbar(
                modifier = Modifier.fillMaxWidth(),
                stateTime = viewModel.getTime(lifecycleOwner.lifecycle).collectAsState(initial = EMPTY),
                stateTem = viewModel.getTemperature(lifecycleOwner.lifecycle).collectAsState(initial = EMPTY),
                goto = goto
                )
        }
    ) { pv ->
        
        Column(modifier = Modifier
            .padding(pv)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center       
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {

                Spacer(modifier = Modifier.weight(0.2f))
                Column(modifier = Modifier.weight(0.33f)) {
                    Text(text = "Наименование", style = styleHeaderH4, color = TitleField)
                    InputText(
                        modifier = Modifier.fillMaxWidth(),
                        stateText = remember { viewModel.getStateName() },
                        onValueChange = viewModel::setName,
                        onError = remember {{ if (viewModel.getStateName().value.isEmpty()) "Ошибка, поле пустое" else null}})
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Цена", style = styleHeaderH4, color = TitleField)
                    InputText(
                        modifier = Modifier.fillMaxWidth(),
                        stateText = remember { viewModel.getStatePrice() },
                        onValueChange = viewModel::setPrice,
                        trailingIcon = { Text(text = CURRENCY, style = styleControl, color = Control) },
                        keyboardType = KeyboardType.Number
                    )
                    SwitchField(
                        modifier = Modifier.fillMaxWidth(),
                        stateChecked = remember { derivedStateOf {
                            viewModel.getStatePrice().value.isEmpty()
                        }},
                        onCheckedChange = viewModel::checkPrice
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Btn(stateEnabled = remember { derivedStateOf {
                            viewModel.getStateCoffee().value?.let {
                                it.name != viewModel.getStateName().value  ||
                                        it.price != viewModel.getStatePrice().value ||
                                        it.imageUrl != viewModel.getStateIcon().value
                            } ?: false
                        }},
                        onClick = viewModel::save
                    )
                }
                Spacer(modifier= Modifier.width(16.dp))
                SelectingPicture(modifier = Modifier.weight(0.33f),
                    selectState = remember { viewModel.getStateIcon() },
                    select = viewModel::setImage
                )
            }
        }
    }
}

@Composable
private fun InputText(
    modifier: Modifier = Modifier,
    stateText: State<String>,
    onValueChange: (String) -> Unit,
    onError: (() -> String?)? = null,
    trailingIcon: @Composable (() -> Unit)?= null,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusManager: FocusManager = LocalFocusManager.current,
){
    Column {
        TextField(
            value = stateText.value,
            onValueChange = onValueChange,
            modifier = modifier,
            trailingIcon = trailingIcon,
            singleLine = true,
            isError = onError?.invoke() != null,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        onError?.let {
            it()?.let { str -> Text(text = str) }
        }
    }
}

@Composable
private fun SwitchField (
    modifier: Modifier = Modifier,
    stateChecked: State<Boolean>,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Продавать бесплатно", style = styleFootnote, color = FootNote)
        Switch(checked = stateChecked.value,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Accept,
                uncheckedTrackColor = MaterialTheme.colorScheme.background,
                uncheckedBorderColor = FootNote,
                uncheckedThumbColor = FootNote
            )
        )
    }
}

@Composable
private fun SelectingPicture(
    modifier: Modifier = Modifier,
    pictures: List<String> = remember {
        listOf(
            "https://s3-alpha-sig.figma.com/img/a065/5b3f/94860c1bebf809ee8dba77436257bb09?Expires=1717372800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=plZ-y6Zrq0FdvLpDA6zkk4ul3D04XENPDuW6j8afXbNCMv0kvLSOdlkcnCiIBSKKdDDLcptANBRfHTamZ4QMwdR~dNZIY-6DcfPCG5x6c5vqpWNl3MQBsmMeVMSWGva8Yc7E4hbs0TsU5eKD1kQFMMJyu2tytMMISCxTIZ1kmLfrwvWRlM4vxX1rfEKEdLRT5KRTL-mpH1QIdNfOqr46uV41XXrJ1a5nELJG~TQUsUth77HmX9GdVOUP9CKs7h-e0bW2O9kDC4wkSOWGmVk0mGrtOQiDKPsE4iXuj1PWfjxhQHg754UPRHT7w7umr0PNacC9O84j2sfPhUNlOk6UkA__",
            "https://s3-alpha-sig.figma.com/img/d61d/4a5a/d70a7ddda8797832ecad93c9456747e0?Expires=1717977600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=BxDzU1bhltdYl1Y5sWfb1zbZ3~N7YvN8kaQRFQifzkqBgmqt5l9j5BNYKaEmeDqaIZhVU7-afR808cZnepfiTjN0A11x3fhiyrWe4j7cdtoeF9XYx076J53PIrF-iEmq3sjk4mJNEaMt7yTp6GgMc3ACWfPhHR7GWHA3y92q7GekAFy-Va-WU0MYCBTaYGpzkv7CQQ~E0VBQJz3~A1G78WQ5kbVUeQkhKgYqSIkwLNPaWPHCGfCuJ2pIbYj4SXPTeco2EmNxfu5~02~EuVwnXH~kHnhRwXk7kToo3HxvqgA9ycMHdba5ovUN-dphGuFlsBu2FgmNy9K9hMx7ASiFdw__"
        )
    },
    selectState: State<String>,
    select: (String) -> Unit
) {

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(pictures.size) { i ->
            ImageCoffee(
                modifier = Modifier.weight(1f),
                url = pictures[i],
                select = remember { derivedStateOf {
                    selectState.value == pictures[i]
                }},
                onClick = select
            )
        }
    }
}


@Composable
private fun ImageCoffee(
    modifier: Modifier = Modifier,
    url: String,
    select: State<Boolean>,
    onClick: (String) -> Unit
) {
    Box(modifier = modifier.clickable(enabled = select.value.not()) { onClick(url) },
        contentAlignment = Alignment.BottomCenter
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator()
            } else {
                Image(painter = painter, contentDescription = null)
            }
        }
        if (select.value) {
            Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_choice), contentDescription = null, modifier = Modifier.size(32.dp))
        } else {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun Btn(
    modifier: Modifier = Modifier,
    stateEnabled: State<Boolean>,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = stateEnabled.value,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = Accept,
            contentColor = Color.White,
            disabledContainerColor = Accept.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.5f)
        ),
        contentPadding = remember { PaddingValues(horizontal = 24.dp, vertical = 16.dp) }
    ) {
        Text(text = "Сохранить", style= styleControl)
    }
}
