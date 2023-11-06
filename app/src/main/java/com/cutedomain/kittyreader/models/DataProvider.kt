package com.cutedomain.kittyreader.models

import com.cutedomain.kittyreader.R

object DataProvider {

    /* Lista de libros temporal para hacer pruebas funcionales */
    val bookList= listOf(
        EBook(isbn="1346786432456", title ="Hola mundo", author = "Hola mundo",date="1992", category = "Política", image = R.drawable.froyo),
        EBook(isbn="3454675768543", title ="Libro numero 2", author = "El anticristo",date="1956", category = "Filosofía", image=R.drawable.jellybean)

    )

    val bookFormats = listOf(
        Formats(name = "EPUB", description = "Lee tus libros en formatos electrónicos.", extension = ".epub", image = R.drawable.epublogo_removebg_preview),
        Formats(name = "PDF", description = "Abre tus libros en formato de documentos.", extension = ".pdf", image = R.drawable.pdf_format_file_template_your_design_97886_11001_removebg_preview)
    )

}