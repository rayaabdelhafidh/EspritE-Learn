package tn.esprit.esprit.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.esprit.models.PlanDetude;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ExportToExcel {
    public static void exportToExcel(Set<PlanDetude> planDetudeList, File file){
        //na3ml instance  ml classe  XSSFWorkbook  tasna3li fichier exel
        XSSFWorkbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("Plan d'etude");
        int row=0;
        Row headerRow=sheet.createRow(row++);
        String[] headers={"Nom programme","Niveau","Durée Totale","Crédits requis total"};
        for(int i=0;i<headers.length;i++){
            Cell cell=headerRow.createCell(i, CellType.STRING);
            cell.setCellValue(headers[i]);
        }
        for(PlanDetude planDetude:planDetudeList){
            Row dataRow=sheet.createRow(row++);
            dataRow.createCell(0,CellType.STRING).setCellValue(planDetude.getNomProgramme());
            dataRow.createCell(1,CellType.STRING).setCellValue(planDetude.getNiveau().toString());
            dataRow.createCell(2,CellType.NUMERIC).setCellValue(planDetude.getDureeTotal());
            dataRow.createCell(3,CellType.NUMERIC).setCellValue(planDetude.getCreditsRequisTotal());
        }
        try {
            workbook.write(new FileOutputStream(file));
            workbook.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
