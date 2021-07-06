package eco;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOperations {
    private static String[] readTextFile (File textFile) {
        String[] credentialsTable = new String[5];
        try {
            Scanner txtReader = new Scanner(textFile);
            for(int i=0; i<5; ++i)
            {
                if(txtReader.hasNextLine())
                {
                    credentialsTable[i] = txtReader.nextLine();
                }
            }
            txtReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return credentialsTable;
    }

    public static String[] selectAndOpenTextFile() {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(".txt file with credentials", "txt");
        jfc.setFileFilter(extensionFilter);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setDialogTitle("Select text file with credentials");
        int returnVal = jfc.showOpenDialog(null);
        File file;
        String[] credentialsTable;

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
            credentialsTable = FileOperations.readTextFile(file);
        } else {
            credentialsTable = new String[5];
        }
        return credentialsTable;
    }
    public static String selectAndOpenExcelFile() {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(".xlsx file with product numbers", "xlsx");
        jfc.setFileFilter(extensionFilter);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setDialogTitle("Select Excel file with product numbers");
        int returnVal = jfc.showOpenDialog(null);
        File file;
        String path = "";

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
            path = file.getAbsolutePath();
        } else {
            //do nothing
        }

        return path;
    }

    public static List<String> getProductNumbersFromExcelFile(String filePath) throws InvalidFormatException, IOException {
        File file = new File(filePath);
        OPCPackage pck = OPCPackage.open(file);
        XSSFWorkbook wb = new XSSFWorkbook(pck);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;
        List<String> productList = new ArrayList<>();

        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; ++i) {
            row = sheet.getRow(i);
            if (row != null) {
                cell = row.getCell(0);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    double prodNum = cell.getNumericCellValue();
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setGroupingUsed(false);
                    nf.setMaximumIntegerDigits(999);
                    nf.setMaximumFractionDigits(0);
                    productList.add(nf.format(prodNum));
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return productList;
    }

    public static void writeStatusToProductDisplayedInExcel(String filePath, String product, Boolean status) throws InvalidFormatException, IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;
        String productNumber;

        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; ++i) {
            row = sheet.getRow(i);
            if (row != null) {
                cell = row.getCell(0);
            } else {
                System.out.println("No more rows exist.");
                break;
            }

            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                productNumber = processProductNumberFromDoubleToString(cell.getNumericCellValue());
            } else if (cell != null && cell.getCellType() == CellType.STRING) {
                productNumber = cell.getStringCellValue();
            } else {
                System.out.println("Cell contents are neither number nor string.");
                break;
            }

            if(productNumber.equals(product)) {
                cell = row.createCell(1, CellType.STRING);
                fillCellWithResult(wb, cell, status);
            }
        }
        fis.close();
        FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);
        wb.close();
        fos.close();
    }

    private static String processProductNumberFromDoubleToString(double productNumber){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumIntegerDigits(999);
        nf.setMaximumFractionDigits(0);
        return nf.format(productNumber);
    }

    private static void fillCellWithResult(XSSFWorkbook wb, XSSFCell cell, Boolean status) {
        CellStyle cs = wb.createCellStyle();
        if(status==true){
            cell.setCellValue("Product displayed properly");
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cs.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            cell.setCellStyle(cs);

        } else {
            cell.setCellValue("Page 404 displayed");
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cs.setFillForegroundColor(IndexedColors.RED.getIndex());
            cell.setCellStyle(cs);
        }
    }
}
