package com.easyshop.app.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.easyshop.app.Producto
import com.easyshop.app.ProductosData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    cantidadFavoritos: Int,
    cantidadHistorial: Int,
    onProductoClick: (Producto) -> Unit,
    onVerFavoritosClick: () -> Unit,
    onVerBusquedaClick: () -> Unit,
    onVerComparadorClick: () -> Unit,
    onVerHistorialClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EasyShop - Catálogo") },
                actions = {
                    // Botón de Búsqueda
                    IconButton(onClick = onVerBusquedaClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Botón de Comparador - USA Info
                    IconButton(onClick = onVerComparadorClick) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Comparar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Botón de Historial - USA Star (estrella como marcador)
                    IconButton(onClick = onVerHistorialClick) {
                        BadgedBox(
                            badge = {
                                if (cantidadHistorial > 0) {
                                    Badge { Text("$cantidadHistorial") }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Historial",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // Botón de Favoritos con badge
                    IconButton(onClick = onVerFavoritosClick) {
                        BadgedBox(
                            badge = {
                                if (cantidadFavoritos > 0) {
                                    Badge { Text("$cantidadFavoritos") }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favoritos",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ProductosData.productos) { producto ->
                ProductoCard(
                    producto = producto,
                    onClick = { onProductoClick(producto) }
                )
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = producto.categoria,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "$${producto.precio}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}