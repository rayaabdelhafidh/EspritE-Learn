package tn.esprit.Controllers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.Models.Presence;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {
    public void generatePDFFromListView(List<Presence> presences, String outputPath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Presence Data");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 680);

                for (Presence presence : presences) {
                    // Format the data as needed
                    String data = String.format("Date: %s, Time: %s", presence.getDate(), presence.getDate().getTime());
                    contentStream.showText(data);
                    contentStream.newLineAtOffset(0, -15); // Move to the next line
                }

                contentStream.endText();
            }

            document.save(new File(outputPath));
            System.out.println("PDF saved  !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
