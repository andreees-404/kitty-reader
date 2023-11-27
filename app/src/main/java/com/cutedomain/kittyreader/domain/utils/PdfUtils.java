package com.cutedomain.kittyreader.domain.utils;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDDocumentInformation;

import java.io.InputStream;

public class PdfUtils {

    public static PdfInfo extractInfo(InputStream pdfStream) {
        PdfInfo pdfInfo = new PdfInfo();

        try(PDDocument document = PDDocument.load(pdfStream)) {
            PDDocumentInformation info = document.getDocumentInformation();
            pdfInfo.setAuthor(info.getAuthor());
            pdfInfo.setTitle(info.getTitle());

        }catch (Exception e){
            e.getMessage();
        }

        finally {
            return pdfInfo;
        }
    }

    public static class PdfInfo {
        private String title;
        private String author;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
