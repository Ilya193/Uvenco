package ru.ikom.details.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.ikom.common.theme.LightGray
import ru.ikom.common.theme.Main
import ru.ikom.common.theme.Orange
import ru.ikom.details.R

@Composable
fun DetailsScreen(
    drinkId: Int,
    drinkName: String,
    drinkPrice: String?,
    viewModel: DetailsViewModel = koinViewModel { parametersOf(drinkId) },
    pop: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var inputName by remember { mutableStateOf(drinkName) }
    var inputPrice by remember { mutableStateOf(drinkPrice ?: "") }

    if (state.isCompleted != null) {
        LaunchedEffect(Unit) {
            pop()
        }
    }

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT ->
            PortraitOrientation(
                drinkName = inputName,
                drinkPrice = inputPrice,
                isEnabled = state.isChanges,
                onChangeName = {
                    inputName = it
                    viewModel.action(Event.ChangeName(it))
                },
                onChangePrice = {
                    inputPrice = it
                    if (it.isNotEmpty()) viewModel.action(Event.ChangePrice(it.toInt()))
                    else viewModel.action(Event.ChangePrice(0))
                },
                onSave = { name, price ->
                    viewModel.action(Event.Save(name, price.toInt()))
                },
            )

        else ->
            LandscapeOrientation(
                drinkName = inputName,
                drinkPrice = inputPrice,
                isEnabled = state.isChanges,
                onChangeName = {
                    inputName = it
                    viewModel.action(Event.ChangeName(it))
                },
                onChangePrice = {
                    inputPrice = it
                    if (it.isNotEmpty()) viewModel.action(Event.ChangePrice(it.toInt()))
                    else viewModel.action(Event.ChangePrice(0))
                },
                onSave = { name, price ->
                    viewModel.action(Event.Save(name, price.toInt()))
                },
            )
    }
}

@Composable
private fun PortraitOrientation(
    drinkName: String,
    drinkPrice: String,
    isEnabled: Boolean,
    onChangeName: (String) -> Unit,
    onChangePrice: (String) -> Unit,
    onSave: (String, String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(287.dp),
            painter = painterResource(id = R.drawable.drink),
            contentDescription = null
        )

        Text(text = stringResource(R.string.name), color = LightGray)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = drinkName, onValueChange = {
                onChangeName(it)
            }, colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Main,
                focusedContainerColor = Main,
                focusedIndicatorColor = Color.Transparent,
            )
        )
        Text(text = stringResource(R.string.price), color = LightGray)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = drinkPrice,
            onValueChange = {
                onChangePrice(it)
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Main,
                focusedContainerColor = Main,
                focusedIndicatorColor = Color.Transparent,
            ),
            trailingIcon = { Text(text = stringResource(ru.ikom.common.R.string.currency)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Card(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = CardDefaults.outlinedCardColors(containerColor = Orange),
            shape = RoundedCornerShape(8.dp),
            onClick = { onSave(drinkName, drinkPrice) },
            enabled = isEnabled,
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(R.string.save), color = Color.White
            )
        }
    }
}

@Composable
private fun LandscapeOrientation(
    drinkName: String,
    drinkPrice: String,
    isEnabled: Boolean,
    onChangeName: (String) -> Unit,
    onChangePrice: (String) -> Unit,
    onSave: (String, String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Text(text = stringResource(R.string.name), color = LightGray)
            TextField(
                value = drinkName, onValueChange = {
                    onChangeName(it)
                }, colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Main,
                    focusedContainerColor = Main,
                    focusedIndicatorColor = Color.Transparent,
                )
            )
            Text(text = stringResource(R.string.price), color = LightGray)
            TextField(value = drinkPrice,
                onValueChange = {
                    onChangePrice(it)
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Main,
                    focusedContainerColor = Main,
                    focusedIndicatorColor = Color.Transparent,
                ),
                trailingIcon = { Text(text = stringResource(ru.ikom.common.R.string.currency)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Card(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = CardDefaults.outlinedCardColors(containerColor = Orange),
                shape = RoundedCornerShape(8.dp),
                onClick = { onSave(drinkName, drinkPrice) },
                enabled = isEnabled
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.save), color = Color.White
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Image(
                modifier = Modifier
                    .height(287.dp),
                painter = painterResource(id = R.drawable.drink),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .height(287.dp),
                painter = painterResource(id = R.drawable.drink2),
                contentDescription = null
            )
        }
    }
}