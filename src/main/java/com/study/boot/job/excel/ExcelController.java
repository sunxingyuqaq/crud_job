package com.study.boot.job.excel;

import cn.hutool.core.lang.Dict;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Xingyu Sun
 * @date 2019/12/24 9:24
 */
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @SneakyThrows
    @GetMapping("/test")
    public Dict generate() {
        Path path = Paths.get("d:/jar/test.xlsx");
        @Cleanup
        OutputStream outputStream = new FileOutputStream(path.toFile());
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet("test");
        CellStyle cellStyle = getCommonStyle(sheet);
        for (int i = 0; i < 5; i++) {
            XSSFRow row = sheet.createRow(i);
            row.setHeight((short) 1000);
            for (int j = 0; j < 10; j++) {
                XSSFCell cell = row.createCell(j);
                sheet.setColumnWidth(j, 7000);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(i + ".春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。");
            }
        }
        xssfWorkbook.write(outputStream);
        return Dict.create().set("code", "200").set("msg", "ok");
    }

    private CellStyle getCommonStyle(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        return cellStyle;
    }
}
