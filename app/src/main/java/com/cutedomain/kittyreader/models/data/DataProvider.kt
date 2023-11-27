package com.cutedomain.kittyreader.models.data

import com.cutedomain.kittyreader.R

object DataProvider {
    /* Lista de libros temporal para hacer pruebas funcionales */
    //val bookList= listOf(
    //    EBook(isbn="1346786432456", title ="El libro de Enoc", author = "Anónimo",date="1992", category = "Religión", image = R.drawable.el_libro_de_enoc_anonimo_lg, format = "epub"),
    //    EBook(isbn="3454675768543", title ="The Godfather", author = "Mario Puzzo",date="1969", category = "Novela", image=R.drawable.thegodfather_llogo, format = "pdf")

    //)

    val bookFormats = listOf(
        Formats(name = "EPUB", description = "Lee tus libros en formatos electrónicos.", extension = ".epub", image = R.drawable.epublogo_removebg_preview),
        Formats(name = "PDF", description = "Abre tus libros en formato de documentos.", extension = ".pdf", image = R.drawable.pdf_format_file_template_your_design_97886_11001_removebg_preview)
    )

}