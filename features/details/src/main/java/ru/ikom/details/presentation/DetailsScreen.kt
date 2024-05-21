package ru.ikom.details.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import ru.ikom.common.theme.Gray
import ru.ikom.common.theme.LightGray
import ru.ikom.common.theme.Main
import ru.ikom.common.theme.Orange
import ru.ikom.details.R

@Composable
fun DetailsScreen(
    drinkId: Int,
    viewModel: DetailsViewModel = koinViewModel { parametersOf(drinkId) },
    pop: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) pop()
    }

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT ->
            PortraitOrientation(
                drinkName = state.inputName,
                drinkPrice = state.inputPrice,
                isEnabled = state.isChanges,
                switchIsFree = state.switchIsFree,
                onChangeName = {
                    viewModel.action(Event.InputName(it))
                    viewModel.action(Event.ChangeName(it))
                },
                onChangePrice = {
                    if (!it.contains(COMMA) && !it.contains(DOT)) {
                        viewModel.action(Event.InputPrice(it))
                        if (it.isNotEmpty()) viewModel.action(Event.ChangePrice(it.toInt()))
                        else viewModel.action(Event.ChangePrice(null))
                    }
                },
                onSave = { name, price ->
                    if (price.isNotEmpty()) viewModel.action(Event.Save(name, price.toInt()))
                    else viewModel.action(Event.Save(name, null))
                },
                onSwitch = {
                    viewModel.action(Event.SellForFree(it))
                }
            )

        else ->
            LandscapeOrientation(
                drinkName = state.inputName,
                drinkPrice = state.inputPrice,
                isEnabled = state.isChanges,
                switchIsFree = state.switchIsFree,
                onChangeName = {
                    viewModel.action(Event.InputName(it))
                    viewModel.action(Event.ChangeName(it))
                },
                onChangePrice = {
                    if (!it.contains(COMMA) && !it.contains(DOT)) {
                        viewModel.action(Event.InputPrice(it))
                        if (it.isNotEmpty()) viewModel.action(Event.ChangePrice(it.toInt()))
                        else viewModel.action(Event.ChangePrice(null))
                    }
                },
                onSave = { name, price ->
                    if (price.isNotEmpty()) viewModel.action(Event.Save(name, price.toInt()))
                    else viewModel.action(Event.Save(name, null))
                },
                onSwitch = {
                    viewModel.action(Event.SellForFree(it))
                }
            )
    }
}

@Composable
private fun PortraitOrientation(
    drinkName: String,
    drinkPrice: String,
    isEnabled: Boolean,
    switchIsFree: Boolean,
    onChangeName: (String) -> Unit,
    onChangePrice: (String) -> Unit,
    onSave: (String, String) -> Unit,
    onSwitch: (Boolean) -> Unit
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
        SingleLineTextField(value = drinkName, onValueChange = onChangeName)
        Text(text = stringResource(R.string.price), color = LightGray)
        SingleLineTextField(
            value = drinkPrice,
            onValueChange = onChangePrice,
            trailingIcon = { Text(text = stringResource(ru.ikom.common.R.string.currency)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Main),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.sell_for_free), color = Gray)
            Switch(
                checked = switchIsFree,
                onCheckedChange = {
                    onSwitch(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Orange,
                )
            )
        }
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
    switchIsFree: Boolean,
    onChangeName: (String) -> Unit,
    onChangePrice: (String) -> Unit,
    onSave: (String, String) -> Unit,
    onSwitch: (Boolean) -> Unit
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
            SingleLineTextField(value = drinkName, onValueChange = onChangeName)
            Text(text = stringResource(R.string.price), color = LightGray)
            SingleLineTextField(
                value = drinkPrice,
                onValueChange = onChangePrice,
                trailingIcon = { Text(text = stringResource(ru.ikom.common.R.string.currency)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .background(Main),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.sell_for_free), color = Gray)
                Switch(
                    checked = switchIsFree,
                    onCheckedChange = {
                        onSwitch(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Orange,
                    )
                )
            }
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

@Composable
private fun SingleLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int = MAX_LENGTH
) {
    TextField(
        value = value, onValueChange = {
            if (it.length <= maxLength)
                onValueChange(it)
        }, colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Main,
            focusedContainerColor = Main,
            focusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        maxLines = 1,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions
    )
}