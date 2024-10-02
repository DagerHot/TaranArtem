package com.example.abstractfactory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Furniture interface
interface Furniture {
    fun getDescription(): String
}

// Implementations of various styles of chairs
class GothicChair : Furniture {
    override fun getDescription(): String = "Gothic Chair"
}

class ModernChair : Furniture {
    override fun getDescription(): String = "Modern Chair"
}

class VictorianChair : Furniture {
    override fun getDescription(): String = "Victorian Chair"
}

// Implementations of various styles of sofas
class GothicSofa : Furniture {
    override fun getDescription(): String = "Gothic Sofa"
}

class ModernSofa : Furniture {
    override fun getDescription(): String = "Modern Sofa"
}

class VictorianSofa : Furniture {
    override fun getDescription(): String = "Victorian Sofa"
}

// Implementations of various styles of tables
class GothicTable : Furniture {
    override fun getDescription(): String = "Gothic Table"
}

class ModernTable : Furniture {
    override fun getDescription(): String = "Modern Table"
}

class VictorianTable : Furniture {
    override fun getDescription(): String = "Victorian Table"
}

// Abstract Factory for creating furniture
interface FurnitureFactory {
    fun createChair(): Furniture
    fun createSofa(): Furniture
    fun createTable(): Furniture
}

// Implementations of furniture factories for each style
class GothicFactory : FurnitureFactory {
    override fun createChair(): Furniture = GothicChair()
    override fun createSofa(): Furniture = GothicSofa()
    override fun createTable(): Furniture = GothicTable()
}

class ModernFactory : FurnitureFactory {
    override fun createChair(): Furniture = ModernChair()
    override fun createSofa(): Furniture = ModernSofa()
    override fun createTable(): Furniture = ModernTable()
}

class VictorianFactory : FurnitureFactory {
    override fun createChair(): Furniture = VictorianChair()
    override fun createSofa(): Furniture = VictorianSofa()
    override fun createTable(): Furniture = VictorianTable()
}

// Factory manager to provide the right factory based on user input
object FurnitureFactoryManager {
    private val factories = mapOf(
        "gothic" to GothicFactory(),
        "modern" to ModernFactory(),
        "victorian" to VictorianFactory()
    )

    // Get factory by style
    fun getFactory(style: String): FurnitureFactory? {
        return factories[style.lowercase()]
    }

    /*// Get all available furniture styles
    fun getAllFurnitureStyles(): List<String> {
        return factories.keys.toList()
    }*/

    // Get all available furniture types
    fun getAllFurnitureTypes(): List<String> {
        return listOf("chair", "sofa", "table")
    }
}

// Main activity for the application
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FurnitureApp()
        }
    }
}

// Composable function for the UI
@Composable
fun FurnitureApp() {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = input,
            onValueChange = { input = it },
            placeholder = { Text("Enter furniture type and style") }
        )
        Button(onClick = {
            handleUserInput(input) { result -> output = result }
        }) {
            Text("Submit")
        }
        Text(text = output)
    }
}

// Function to handle user input and generate appropriate output
fun handleUserInput(input: String, onResult: (String) -> Unit) {
    val parts = input.split(" ")
    if (parts.size == 2) {
        val type = parts[0].lowercase()
        val style = parts[1].lowercase()

        // Check for the furniture factory based on style
        val factory = FurnitureFactoryManager.getFactory(style)
        if (factory != null) {
            val createdItem = when (type) {
                "chair" -> factory.createChair()
                "sofa" -> factory.createSofa()
                "table" -> factory.createTable()
                else -> null
            }

            if (createdItem != null) {
                onResult("Created: ${createdItem.getDescription()}")

                // List missing items for this style
                val missingItems = mutableListOf<String>()
                if (type != "chair") missingItems.add("$style chair")
                if (type != "sofa") missingItems.add("$style sofa")
                if (type != "table") missingItems.add("$style table")

                if (missingItems.isNotEmpty()) {
                    onResult("Created: ${createdItem.getDescription()}\nMissing: ${missingItems.joinToString(", ")}")
                }
            } else {
                val availableTypes = FurnitureFactoryManager.getAllFurnitureTypes()
                onResult("Missing: ${availableTypes.joinToString(", ")}")
            }
        } else {
            onResult("Invalid request")
        }
    } else {
        onResult("Invalid request")
    }
}

// Preview function for the UI
@Preview(showBackground = true)
@Composable
fun PreviewFurnitureApp() {
    FurnitureApp()
}
