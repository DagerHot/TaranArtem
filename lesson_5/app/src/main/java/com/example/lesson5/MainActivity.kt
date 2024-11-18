package com.example.lesson5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lesson5.ui.theme.Lesson5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lesson5Theme {
                val items = remember { mutableStateListOf(*List(20) { ItemData() }.toTypedArray()) }
                ItemList(items = items)
            }
        }
    }
}

@Composable
fun ItemList(items: MutableList<ItemData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            ItemRow(item)
        }
    }
}

@Composable
fun ItemRow(item: ItemData) {
    var isOn by remember { mutableStateOf(item.isOn) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isOn) Color.Green else Color.Red)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (isOn) "ON" else "OFF",
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isOn,
            onCheckedChange = {
                isOn = it
                item.isOn = it
                item.text = if (it) "ON" else "OFF"
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemList() {
    Lesson5Theme {
        val items = List(3) { ItemData() }
        ItemList(items = items.toMutableList())
    }
}
