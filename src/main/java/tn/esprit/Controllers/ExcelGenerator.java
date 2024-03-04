package tn.esprit.Controllers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.Models.Presence;
import tn.esprit.Models.PresenceData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    public static void exportToExcel(List<Presence> presenceList, File file){
        //na3ml instance  ml classe  XSSFWorkbook  tasna3li fichier excel
        XSSFWorkbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("Presence");
        int row=0;
        Row headerRow=sheet.createRow(row++);
        String[] headers={"date","nomClasse","seance"};
        for(int i=0;i<headers.length;i++){
            Cell cell=headerRow.createCell(i, CellType.STRING);
            cell.setCellValue(headers[i]);
        }
        for(Presence presence:presenceList){
            Row dataRow=sheet.createRow(row++);
            dataRow.createCell(0,CellType.STRING).setCellValue(presence.getDate());
            dataRow.createCell(1,CellType.STRING).setCellValue(presence.getNomClasse());
            dataRow.createCell(2,CellType.NUMERIC).setCellValue(presence.getSeance().toString());
        }
        try {
            workbook.write(new FileOutputStream(file));
            workbook.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

}
