package com.easyshop.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.easyshop.app.Producto
import com.easyshop.app.ProductosData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparadorScreen(
    onVolverClick: () -> Unit,
    onProductoClick: (Producto) -> Unit
) {
    var producto1Seleccionado by remember { mutableStateOf<Producto?>(null) }
    var producto2Seleccionado by remember { mutableStateOf<Producto?>(null) }
    var mostrarSelector1 by remember { mutableStateOf(false) }
    var mostrarSelector2 by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comparar Productos") },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = "Selecciona dos productos para comparar sus características y precios",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = "Producto 1",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { mostrarSelector1 = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(producto1Seleccionado?.nombre ?: "Seleccionar Producto 1")
            }

            if (mostrarSelector1) {
                SelectorProductoDialog(
                    productos = ProductosData.productos,
                    onProductoSeleccionado = {
                        producto1Seleccionado = it
                        mostrarSelector1 = false
                    },
                    onDismiss = { mostrarSelector1 = false }
                )
            }

            HorizontalDivider()

            Text(
                text = "Producto 2",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { mostrarSelector2 = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(producto2Seleccionado?.nombre ?: "Seleccionar Producto 2")
            }

            if (mostrarSelector2) {
                SelectorProductoDialog(
                    productos = ProductosData.productos,
                    onProductoSeleccionado = {
                        producto2Seleccionado = it
                        mostrarSelector2 = false
                    },
                    onDismiss = { mostrarSelector2 = false }
                )
            }

            Spacer(Modifier.height(8.dp))

            if (producto1Seleccionado != null && producto2Seleccionado != null) {
                TablaComparacion(
                    producto1 = producto1Seleccionado!!,
                    producto2 = producto2Seleccionado!!,
                    onProductoClick = onProductoClick
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Selecciona dos productos para ver la comparación",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectorProductoDialog(
    productos: List<Producto>,
    onProductoSeleccionado: (Producto) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar Producto") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                productos.forEach { producto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onProductoSeleccionado(producto) }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = producto.nombre,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = producto.categoria,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = "$${producto.precio}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun TablaComparacion(
    producto1: Producto,
    producto2: Producto,
    onProductoClick: (Producto) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Comparación",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FilaComparacion(
                campo = "Nombre",
                valor1 = producto1.nombre,
                valor2 = producto2.nombre
            )

            FilaComparacion(
                campo = "Categoría",
                valor1 = producto1.categoria,
                valor2 = producto2.categoria,
                destacar = producto1.categoria != producto2.categoria
            )

            FilaComparacion(
                campo = "Precio",
                valor1 = "$${producto1.precio}",
                valor2 = "$${producto2.precio}",
                destacar = true,
                colorDestacado1 = if (producto1.precio < producto2.precio)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error,
                colorDestacado2 = if (producto2.precio < producto1.precio)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error
            )

            FilaComparacion(
                campo = "Descripción",
                valor1 = producto1.descripcion,
                valor2 = producto2.descripcion
            )

            FilaComparacion(
                campo = "Especificaciones",
                valor1 = producto1.especificaciones,
                valor2 = producto2.especificaciones
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onProductoClick(producto1) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver Detalle 1")
                }
                OutlinedButton(
                    onClick = { onProductoClick(producto2) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver Detalle 2")
                }
            }
        }
    }
}

@Composable
fun FilaComparacion(
    campo: String,
    valor1: String,
    valor2: String,
    destacar: Boolean = false,
    colorDestacado1: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    colorDestacado2: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = campo,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = if (destacar)
                        colorDestacado1.copy(alpha = 0.1f)
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = valor1,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (destacar) colorDestacado1 else MaterialTheme.colorScheme.onSurface
                )
            }
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = if (destacar)
                        colorDestacado2.copy(alpha = 0.1f)
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = valor2,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (destacar) colorDestacado2 else MaterialTheme.colorScheme.onSurface
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}