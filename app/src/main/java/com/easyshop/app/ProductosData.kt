package com.easyshop.app

object ProductosData {
    val productos = listOf(
        Producto(
            id = "1",
            nombre = "Audífonos Bluetooth XZ100",
            categoria = "Audífonos",
            precio = 59.99,
            descripcion = "Audífonos inalámbricos con cancelación de ruido activa",
            especificaciones = "Batería: 30h | Bluetooth 5.0 | Cancelación activa de ruido | Micrófono integrado"
        ),
        Producto(
            id = "2",
            nombre = "Teclado Mecánico RGB K95",
            categoria = "Teclados",
            precio = 149.99,
            descripcion = "Teclado mecánico gaming con iluminación RGB personalizable",
            especificaciones = "Switches Cherry MX | RGB por tecla | Anti-ghosting | Reposamuñecas magnético"
        ),
        Producto(
            id = "3",
            nombre = "Laptop Dell XPS 15",
            categoria = "Laptops",
            precio = 1299.99,
            descripcion = "Laptop profesional de alto rendimiento",
            especificaciones = "Intel i7 11th Gen | 16GB RAM | 512GB SSD | Pantalla 15.6' FHD | GeForce GTX 1650"
        ),
        Producto(
            id = "4",
            nombre = "Mouse Logitech MX Master 3",
            categoria = "Mouse",
            precio = 99.99,
            descripcion = "Mouse ergonómico inalámbrico de precisión",
            especificaciones = "Sensor 4000 DPI | 7 botones programables | Batería recargable | Bluetooth + USB"
        ),
        Producto(
            id = "5",
            nombre = "Monitor Samsung 27' 4K",
            categoria = "Monitores",
            precio = 399.99,
            descripcion = "Monitor 4K UHD con tecnología IPS",
            especificaciones = "3840x2160 | 60Hz | HDR10 | Panel IPS | HDMI + DisplayPort"
        ),
        Producto(
            id = "6",
            nombre = "Webcam Logitech C920",
            categoria = "Accesorios",
            precio = 79.99,
            descripcion = "Webcam Full HD 1080p con micrófono estéreo",
            especificaciones = "1080p 30fps | Enfoque automático | Corrección de luz | Micrófono dual"
        )
    )
}