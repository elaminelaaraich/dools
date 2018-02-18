package com.dools;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DoolsReader {
    private static final long ROW_TO_START = 9;

    public static List<Data> readSourceData(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .skip(1)
                .map(line -> new Data.Builder(line).build())
                .collect(Collectors.toList());
    }

    public static List<Condition> readDoolsDecisionTable(String fileDoolsDecisionTable) throws IOException {
        List<Condition> conditions = new ArrayList<>();
        InputStream ExcelFileToRead = new FileInputStream(fileDoolsDecisionTable);
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;

        Iterator rows = sheet.rowIterator();
        long currentRowIndex = 0;
        while (rows.hasNext()) {
            row = (HSSFRow) rows.next();
            if (currentRowIndex++ < ROW_TO_START - 1) continue;
            Iterator cells = row.cellIterator();
            if (!cells.hasNext()) continue;
            Condition condition = new Condition();
            cell = (HSSFCell) cells.next();
            condition.setName(cell.getStringCellValue());
            cell = (HSSFCell) cells.next();
            condition.setEmetteurContrePartie(cell.getStringCellValue().replace("emetteur_contrepartie = ", "").trim());
            cell = (HSSFCell) cells.next();
            condition.setNote(cell.getStringCellValue().replace("note = ", "").trim());
            cell = (HSSFCell) cells.next();
            condition.setPosition(cell.getStringCellValue().toLowerCase().trim());
            cell = (HSSFCell) cells.next();
            condition.setDureeResiduelle(cell.getStringCellValue().trim());
            cell = (HSSFCell) cells.next();
            condition.setFODEP(cell.getStringCellValue().trim());

            conditions.add(condition);
        }
        return conditions;
    }
}
