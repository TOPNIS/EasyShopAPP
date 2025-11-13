package com.easyshop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.easyshop.app.screens.*
import com.easyshop.app.ui.theme.EasyShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyShopTheme {
                EasyShopApp()
            }
        }
    }
}

enum class Pantalla {
    SPLASH,
    CATALOGO,
    DETALLE,
    FAVORITOS,
    BUSQUEDA,
    COMPARADOR,
    HISTORIAL
}

@Composable
fun EasyShopApp() {
    var pantallaActual by remember { mutableStateOf(Pantalla.SPLASH) }
    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }
    var favoritos by remember { mutableStateOf(setOf<String>()) }
    var historialVistos by remember { mutableStateOf(listOf<String>()) }

    // Función para agregar al historial
    fun agregarAlHistorial(productoId: String) {
        historialVistos = (listOf(productoId) + historialVistos)
            .distinct()
            .take(20) // Mantener solo los últimos 20
    }

    when (pantallaActual) {
        Pantalla.SPLASH -> {
            SplashScreen(
                onTimeout = {
                    pantallaActual = Pantalla.CATALOGO
                }
            )
        }

        Pantalla.CATALOGO -> {
            CatalogoScreen(
                cantidadFavoritos = favoritos.size,
                cantidadHistorial = historialVistos.size,
                onProductoClick = { producto ->
                    productoSeleccionado = producto
                    agregarAlHistorial(producto.id)
                    pantallaActual = Pantalla.DETALLE
                },
                onVerFavoritosClick = {
                    pantallaActual = Pantalla.FAVORITOS
                },
                onVerBusquedaClick = {
                    pantallaActual = Pantalla.BUSQUEDA
                },
                onVerComparadorClick = {
                    pantallaActual = Pantalla.COMPARADOR
                },
                onVerHistorialClick = {
                    pantallaActual = Pantalla.HISTORIAL
                }
            )
        }

        Pantalla.DETALLE -> {
            productoSeleccionado?.let { producto ->
                DetalleScreen(
                    producto = producto,
                    esFavorito = favoritos.contains(producto.id),
                    onVolverClick = {
                        pantallaActual = Pantalla.CATALOGO
                    },
                    onToggleFavorito = { prod ->
                        favoritos = if (favoritos.contains(prod.id)) {
                            favoritos - prod.id
                        } else {
                            favoritos + prod.id
                        }
                    }
                )
            }
        }

        Pantalla.FAVORITOS -> {
            FavoritosScreen(
                productosFavoritos = ProductosData.productos.filter {
                    favoritos.contains(it.id)
                },
                onVolverClick = {
                    pantallaActual = Pantalla.CATALOGO
                },
                onProductoClick = { producto ->
                    productoSeleccionado = producto
                    agregarAlHistorial(producto.id)
                    pantallaActual = Pantalla.DETALLE
                },
                onQuitarFavorito = { producto ->
                    favoritos = favoritos - producto.id
                }
            )
        }

        Pantalla.BUSQUEDA -> {
            BusquedaScreen(
                onVolverClick = {
                    pantallaActual = Pantalla.CATALOGO
                },
                onProductoClick = { producto ->
                    productoSeleccionado = producto
                    agregarAlHistorial(producto.id)
                    pantallaActual = Pantalla.DETALLE
                }
            )
        }

        Pantalla.COMPARADOR -> {
            ComparadorScreen(
                onVolverClick = {
                    pantallaActual = Pantalla.CATALOGO
                },
                onProductoClick = { producto ->
                    productoSeleccionado = producto
                    agregarAlHistorial(producto.id)
                    pantallaActual = Pantalla.DETALLE
                }
            )
        }

        Pantalla.HISTORIAL -> {
            HistorialScreen(
                productosVistos = ProductosData.productos.filter {
                    historialVistos.contains(it.id)
                }.sortedBy { producto ->
                    historialVistos.indexOf(producto.id)
                },
                onVolverClick = {
                    pantallaActual = Pantalla.CATALOGO
                },
                onProductoClick = { producto ->
                    productoSeleccionado = producto
                    agregarAlHistorial(producto.id)
                    pantallaActual = Pantalla.DETALLE
                },
                onLimpiarHistorial = {
                    historialVistos = emptyList()
                }
            )
        }
    }
}