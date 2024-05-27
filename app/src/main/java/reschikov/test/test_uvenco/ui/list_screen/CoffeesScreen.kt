package reschikov.test.test_uvenco.ui.list_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import reschikov.test.test_uvenco.data.constance.CURRENCY
import reschikov.test.test_uvenco.data.constance.EMPTY
import reschikov.test.test_uvenco.data.models.Coffee
import reschikov.test.test_uvenco.ui.base_view.Toolbar
import reschikov.test.test_uvenco.ui.theme.Description
import reschikov.test.test_uvenco.ui.theme.HeaderH3
import reschikov.test.test_uvenco.ui.theme.Accept
import reschikov.test.test_uvenco.ui.theme.styleDescription
import reschikov.test.test_uvenco.ui.theme.styleHeaderH3
import reschikov.test.test_uvenco.ui.theme.styleValue

const val MIN_WIDTH_ITEM = 220

@Composable
fun CoffeesScreen(
    modifier: Modifier = Modifier,
    viewModel: CoffeesViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    select: (Long) -> Unit
) {

    val stateCoffees = viewModel.getCoffees(lifecycleOwner.lifecycle).collectAsState(initial = emptyList())

    Scaffold(
        modifier = modifier,
        topBar = {
            Toolbar(
                modifier = Modifier.fillMaxWidth(),
                stateTime = viewModel.getTime(lifecycleOwner.lifecycle).collectAsState(initial = EMPTY),
                stateTem = viewModel.getTemperature(lifecycleOwner.lifecycle).collectAsState(initial = EMPTY),
                goto= { if (stateCoffees.value.isNotEmpty()) select(stateCoffees.value[0].id)  }
            )
        }
    ) { pv ->

        LazyVerticalGrid(
            columns = GridCells.Adaptive(MIN_WIDTH_ITEM.dp),
            modifier = Modifier.padding(pv),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = stateCoffees.value, key = { c -> c.id }) {
                Item(coffee = it) { long -> select(long) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Item(
    modifier: Modifier = Modifier,
    coffee: Coffee,
    onClick: (Long) -> Unit
) {

    val topMod = remember {
        Modifier
            .background(
                Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF19110E),
                        0.5f to Color(0xFF100909)
                    )
                )
            )
            .padding(16.dp)
    }

    val bottomMod = remember {
        Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF1D1412),
                        0.3f to Color(0xFF160E0C),
                        1f to Color(0XFF231917)
                    )
                )
            )
            .padding(16.dp)
    }

    Card(onClick = { onClick(coffee.id) },
        modifier = modifier,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = topMod,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = coffee.imageUrl,
                contentDescription = null
            ) {
                if (painter.state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator()
                } else {
                    Image(painter = painter, contentDescription = null)
                }
            }
            Text(
                text = coffee.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = styleHeaderH3,
                color = HeaderH3
            )
        }
        Row(modifier = bottomMod,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0.33", style = styleDescription, color = Description,
                modifier = Modifier.weight(1f),
                textAlign = if (coffee.price.isNotEmpty()) TextAlign.Start else TextAlign.Center)
            if (coffee.price.isNotEmpty()) {
                Text(text = "${coffee.price} $CURRENCY" , style = styleValue, color = Accept)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItem() {
    Item(coffee = Coffee(
        id = 0,
        name = "Капучино эконом",
        price = "199",
        imageUrl = "https://s3-alpha-sig.figma.com/img/a065/5b3f/94860c1bebf809ee8dba77436257bb09?Expires=1717372800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=plZ-y6Zrq0FdvLpDA6zkk4ul3D04XENPDuW6j8afXbNCMv0kvLSOdlkcnCiIBSKKdDDLcptANBRfHTamZ4QMwdR~dNZIY-6DcfPCG5x6c5vqpWNl3MQBsmMeVMSWGva8Yc7E4hbs0TsU5eKD1kQFMMJyu2tytMMISCxTIZ1kmLfrwvWRlM4vxX1rfEKEdLRT5KRTL-mpH1QIdNfOqr46uV41XXrJ1a5nELJG~TQUsUth77HmX9GdVOUP9CKs7h-e0bW2O9kDC4wkSOWGmVk0mGrtOQiDKPsE4iXuj1PWfjxhQHg754UPRHT7w7umr0PNacC9O84j2sfPhUNlOk6UkA__"
    )) {

    }
}