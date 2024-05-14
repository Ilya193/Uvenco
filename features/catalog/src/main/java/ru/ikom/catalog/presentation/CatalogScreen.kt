package ru.ikom.catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.ikom.catalog.R
import ru.ikom.common.theme.Gray
import ru.ikom.common.theme.LinearInfoDrink1
import ru.ikom.common.theme.LinearInfoDrink2
import ru.ikom.common.theme.LinearInfoDrink3
import ru.ikom.common.theme.Main
import ru.ikom.common.theme.Orange

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel = koinViewModel(),
    onNavigateToDetails: (Int, String, Int?) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 227.dp)
        ) {
            items(state.drinks, key = { item -> item.id }) {
                DrinkItem(item = it) {
                    onNavigateToDetails(it.id, it.name, it.price)
                }
            }
        }
    }
}

@Composable
fun DrinkItem(item: DrinkUi, onClick: () -> Unit) {
    val brush = Brush.horizontalGradient(listOf(LinearInfoDrink1, LinearInfoDrink2, LinearInfoDrink3))

    Column(
        modifier = Modifier
            .width(227.dp)
            .height(313.dp)
            .background(Main)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = null, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(166.dp),
            painter = painterResource(id = R.drawable.drink),
            contentDescription = null
        )
        Text(text = item.name)
        Spacer(modifier = Modifier.height(16.dp))
        if (item.price == null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(horizontal = 32.dp)
                    .clip(RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
                    .background(brush),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = item.percent.toString(),
                    color = Gray
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(horizontal = 32.dp)
                    .clip(RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
                    .background(brush),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = item.percent.toString(),
                    color = Gray
                )
                Text(
                    modifier = Modifier.padding(end = 12.dp),
                    text = item.price.toString() + " " + stringResource(ru.ikom.common.R.string.currency),
                    color = Orange,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}