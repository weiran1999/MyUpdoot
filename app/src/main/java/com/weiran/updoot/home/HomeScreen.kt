package com.weiran.updoot.home

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.weiran.updoot.puck.MyUtils
import com.weiran.updoot.puck.puck

@Composable
fun HomeScreen() {
    val parentSize = remember { mutableStateOf(Size.Zero) }
    val listState = rememberLazyListState()
    Scaffold(
        topBar = {
            MyTopBar(
                modifier = Modifier.fillMaxWidth(),
            )
        }) { scaffoldPaddingValues ->
        Box(
            Modifier
                .onGloballyPositioned {
                    it.parentCoordinates?.size
                    parentSize.value = it.size.toSize()
                }
                .padding(scaffoldPaddingValues)
        ) {
            DraggableFab(
                parentSize = parentSize,
            ) {
                listState.animateScrollBy(it)
            }
        }
    }

}

@Composable
private fun DraggableFab(
    parentSize: MutableState<Size>,
    scrollBy: suspend (Float) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                parentSize.value = Size(it.size.width.toFloat(), it.size.height.toFloat())
            }
    ) {
        val previousPointState = remember { mutableStateOf(Offset.Zero) }
        val latestPointState = remember { mutableStateOf(Offset.Zero) }
        LaunchedEffect(previousPointState.value, latestPointState.value) {
            scrollBy(latestPointState.value.y - previousPointState.value.y)
        }
        FloatingActionButton(
            modifier = Modifier.puck(
                parentSize = parentSize,
                behaviour = MyUtils.Behaviour.FreeForm,
                previousPointState = previousPointState,
                latestPointState = latestPointState,
            ),
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                // TODO add event
            }) {
            Icon(Icons.Outlined.Menu, Icons.Outlined.Menu.name)
        }
    }

}

@Composable
private fun MyTopBar(
    modifier: Modifier = Modifier,
    showName: String = "TopBar"
) {
    Surface(modifier = modifier, elevation = 8.dp) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) { Text(text = showName) }
    }
}