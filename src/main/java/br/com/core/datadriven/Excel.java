package br.com.core.datadriven;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class Excel {

    private XSSFWorkbook excel;
    private XSSFSheet sheet;
    Workbook workbook;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public XSSFWorkbook getExcel() {
        return excel;
    }

    public void setExcel(XSSFWorkbook excel) {
        this.excel = excel;
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(XSSFSheet sheet) {
        this.sheet = sheet;
    }

    public Workbook openExcelFile(String path, String fileName, String sheetName)  {

        File file = new File(path+fileName);

        if(file.exists()){
            try {
                FileInputStream fis1 = new FileInputStream(file);
                excel = new XSSFWorkbook(fis1);
            } catch (IOException e) {
                System.out.println("Erro ao tentar criar arquivo excel!");
            }
        }else {
            excel = new XSSFWorkbook();
        }
        if(excel.getNumberOfSheets() == 0){
            setSheet(excel.createSheet(sheetName));
        }else {
            setSheet(excel.getSheet(sheetName));
        }
        return excel;
    }

    public Workbook createExcelFile(){
        excel = new XSSFWorkbook();
        return excel;
    }

    public void setHeader(XSSFSheet sheet, String[] headers){
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length ; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
    }

    public void saveFile(XSSFWorkbook workbook, String path, String fileName){
        try {
            FileOutputStream outputStream = new FileOutputStream(path + fileName);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeNextLineEmpty(XSSFSheet sheet, String[] value){
        Iterator<Row> linhas = sheet.iterator();
        int ultimaLinha = 0;
        while (linhas.hasNext()){
            linhas.next();
            ultimaLinha ++;
        }
        Row line = sheet.createRow(ultimaLinha);
        for (int i = 0; i < value.length ; i++) {
            line.createCell(i).setCellValue(value[i]);
        }
    }


    public void writeSpecificLine(XSSFSheet sheet,int row, String[] value){
        Row line = sheet.createRow(row - 1);
        for (int i = 0; i < value.length ; i++) {
            line.createCell(i).setCellValue(value[i]);
        }
    }

    public  static void saveDataTest(String path, String file, String sheetName, String[] headers, String[] data){
        Excel excel = new Excel();
        excel.openExcelFile(path, file, sheetName);
        excel.setHeader(excel.getSheet(),headers);
        excel.writeNextLineEmpty(excel.getSheet(),data);
        excel.saveFile(excel.getExcel(),path, file);
    }

}
