package com.innovator.innovator;

import com.innovator.innovator.models.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {
//    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"client_id", "email", "full_name", "donate", "photo_url"};
    static String SHEET = "Пользователи";

    public static ByteArrayInputStream usersToExcel(List<User> users) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            sheet.setColumnWidth(0, 14 * 256);
            sheet.setColumnWidth(1, 50 * 256);
            sheet.setColumnWidth(2, 25 * 256);
            sheet.setColumnWidth(3, 20 * 256);
            sheet.setColumnWidth(4, 80 * 256);

            Font fontHeader = workbook.createFont();
            fontHeader.setFontHeightInPoints((short) 20);
            fontHeader.setFontName("Calibri");
            fontHeader.setBold(true);

            // Header
            Row headerRow = sheet.createRow(0);

            CellStyle cellStyleHeader = workbook.createCellStyle();
            cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
            cellStyleHeader.setFillForegroundColor(IndexedColors.ORANGE.index);
            cellStyleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleHeader.setFont(fontHeader);

            for (int i = 0; i < HEADERs.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellStyle(cellStyleHeader);
                cell.setCellValue(HEADERs[i]);
            }

            Font font = workbook.createFont();
            font.setFontName("Calibri");
            font.setFontHeightInPoints((short) 20);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyle.setFont(font);

            int rowIdx = 1;
            for (User user : users) {
                Row row = sheet.createRow(rowIdx++);

                Cell cell0 = row.createCell(0);
                cell0.setCellStyle(cellStyle);
                cell0.setCellValue(user.getClientId());

                Cell cell1 = row.createCell(1);
                cell1.setCellStyle(cellStyle);
                cell1.setCellValue(user.getEmail());

                Cell cell2 = row.createCell(2);
                cell2.setCellStyle(cellStyle);
                cell2.setCellValue(user.getFullName());

                Cell cell3 = row.createCell(3);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue(user.getDonate() == null ? 0 : user.getDonate());

                Cell cell4 = row.createCell(4);
                cell4.setCellStyle(cellStyle);
                cell4.setCellValue(user.getPhotoUrl());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
