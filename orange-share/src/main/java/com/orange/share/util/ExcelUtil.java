package com.orange.share.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public  class ExcelUtil {
    //默认单元格内容为数字时格式
    private static DecimalFormat df = new DecimalFormat("0");
    // 默认单元格格式化日期字符串
    private static SimpleDateFormat sdf = new SimpleDateFormat(  "yyyy-MM-dd HH:mm:ss");
    // 格式化数字
    private static DecimalFormat nf = new DecimalFormat("0.00");
    public static ArrayList<ArrayList<Object>> readExcel(File file) throws IOException {
        if(file == null){
            return null;
        }
        if(file.getName().endsWith("xlsx")){
            //处理ecxel2007
            return readExcel2007(file);
        }else{
            //处理ecxel2003
            return readExcel2003(file);
        }
    }
    /*
     * @return 将返回结果存储在ArrayList内，存储结构与二位数组类似
     * lists.get(0).get(0)表示过去Excel中0行0列单元格
     */
    public static ArrayList<ArrayList<Object>> readExcel2003(File file){
        try{
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;
            Object value;
            for(int i = sheet.getFirstRowNum() , rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows() ; i++ ){
                row = sheet.getRow(i);
                colList = new ArrayList<Object>();
                if(row == null){
                    //当读取行为空时
                    if(i != sheet.getPhysicalNumberOfRows()){//判断是否是最后一行
                        rowList.add(colList);
                    }
                    continue;
                }else{
                    rowCount++;
                }
                for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){
                    if(j==-1){
                        break;
                    }
                    cell = row.getCell(j);
                    if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
                        //当该单元格为空
                        if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格
                            colList.add("");
                        }
                        continue;
                    }
                    switch(cell.getCellType()){
                        case XSSFCell.CELL_TYPE_STRING:
                            //System.out.println(i + "行" + j + " 列 is String type");
                            value = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else if ("General".equals(cell.getCellStyle()
                                    .getDataFormatString())) {
                                value = nf.format(cell.getNumericCellValue());
                            } else {
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                        .getNumericCellValue()));
                            }
                            //System.out.println(i + "行" + j+ " 列 is Number type ; DateFormt:"+ value.toString());
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            //System.out.println(i + "行" + j + " 列 is Boolean type");
                            value = Boolean.valueOf(cell.getBooleanCellValue());
                            break;
                        case XSSFCell.CELL_TYPE_BLANK:
                            //System.out.println(i + "行" + j + " 列 is Blank type");
                            value = "";
                            break;
                        default:
                            //System.out.println(i + "行" + j + " 列 is default type");
                            value = cell.toString();
                    }// end switch
                    colList.add(value);
                }//end for j
                rowList.add(colList);
            }//end for i

            return rowList;
        }catch(Exception e){
            return null;
        }
    }

    public static ArrayList<ArrayList<Object>> readExcel2007(File file) throws IOException {

            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;

            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));


            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            Object value;
            //System.out.println("开始"+sheet.getPhysicalNumberOfRows());
            for(int i = sheet.getFirstRowNum() , rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows() ; i++ ){

                row = sheet.getRow(i);
                colList = new ArrayList<Object>();
                if(row == null){
                    //当读取行为空时
                    if(i != sheet.getPhysicalNumberOfRows()){//判断是否是最后一行
                        rowList.add(colList);
                    }
                    continue;
                }else{
                    rowCount++;
                }
               // System.out.println("运行"+row.getFirstCellNum());
                for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){
                    if(j==-1){
                        break;
                    }
                    cell = row.getCell(j);
                    if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
                        //当该单元格为空
                        if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格
                            colList.add("");
                        }
                        continue;
                    }
                    //System.out.println("结束");
                    switch(cell.getCellType()){
                        case XSSFCell.CELL_TYPE_STRING:
                           // System.out.println(i + "行" + j + " 列 is String type");
                            value = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else if ("General".equals(cell.getCellStyle()
                                    .getDataFormatString())) {
                                value = nf.format(cell.getNumericCellValue());
                            } else {
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                        .getNumericCellValue()));
                            }
                            //System.out.println(i + "行" + j + " 列 is Number type ; DateFormt:" + value.toString());
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            //System.out.println(i + "行" + j + " 列 is Boolean type");
                            value = Boolean.valueOf(cell.getBooleanCellValue());
                            break;
                        case XSSFCell.CELL_TYPE_BLANK:
                            //System.out.println(i + "行" + j + " 列 is Blank type");
                            value = "";
                            break;
                        default:
                            //System.out.println(i + "行" + j + " 列 is default type");
                            value = cell.toString();
                    }// end switch
                    colList.add(value);
                }//end for j
                rowList.add(colList);
            }//end for i
            return rowList;
    }

    public static void writeExcel(ArrayList<ArrayList<Object>> result,String path){
        if(result == null){
            return;
        }
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for(int i = 0 ;i < result.size() ; i++){
            Row row = sheet.createRow(i);
            if(result.get(i) != null){
                for(int j = 0; j < result.get(i).size() ; j ++){
                    Cell cell = row.createCell(j);
                    cell.setCellValue(result.get(i).get(j).toString());
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            wb.write(os);
        } catch (IOException e){
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        File file = new File(path);//Excel文件生成后存储的位置。
        OutputStream fos  = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(content);
            os.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DecimalFormat getDf() {
        return df;
    }
    public static void setDf(DecimalFormat df) {
        ExcelUtil.df = df;
    }
    public static SimpleDateFormat getSdf() {
        return sdf;
    }
    public static void setSdf(SimpleDateFormat sdf) {
        ExcelUtil.sdf = sdf;
    }
    public static DecimalFormat getNf() {
        return nf;
    }
    public static void setNf(DecimalFormat nf) {
        ExcelUtil.nf = nf;
    }

    public static boolean writeModelExcel(ArrayList<ArrayList<Object>> result,String path,int modelLength){
        if(result == null){
            return false;
        }
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for(int i = 0 ;i < result.size() ; i++){
            Row row = sheet.createRow(i);

            int n=0;
            int nl=0;
            if(result.get(i) != null){
                for(int j = 0; j < result.get(i).size() ; j ++){
                    String res;
                    Cell cell = row.createCell(j);
                    if(result.get(i).get(j)!=null){
                        res= result.get(i).get(j).toString();
                    }else {
                        res="";
                    }
                    //单元格处理
                    if (i<modelLength){
                        if (StringUtils.isBlank(res)){
                            n++;

                        }else {
                            nl++;
                            for (int q=j-1;q>=1;q--){
                                if (StringUtils.isBlank(result.get(i).get(q).toString())){
                                    //result.get(i).set(q,"--");
                                    continue;
                                }else {
                                    if (q==j-1){
                                        break;
                                    }
                                    System.out.println("excel"+i+","+q+","+(j-1));
                                    CellRangeAddress cra =new CellRangeAddress(i, i, q, j-1); // 起始行, 终止行, 起始列, 终止列
                                    sheet.addMergedRegion(cra);
                                    break;
                                }
                            }
                            for (int q=i+1;q<modelLength;q++){
                                if (j>=result.get(q).size()){
                                    continue;
                                }
                                String str=result.get(q).get(j).toString();
                                if (StringUtils.isBlank(str)){
                                    if (q==modelLength-1){
                                        System.out.println("excel--纵向"+i+","+q+","+(j));
                                        CellRangeAddress cra =new CellRangeAddress(i,q , j, j); // 起始行, 终止行, 起始列, 终止列
                                        sheet.addMergedRegion(cra);
                                    }else {
                                        continue;
                                    }

                                }else {

                                    break;
                                }
                            }
                        }
                    }

                    cell.setCellValue(res);
                    cell.setCellStyle(setStyle(wb));
                    sheet.setColumnWidth(j,6*512);
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            wb.write(os);
        } catch (IOException e){
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        File file = new File(path);//Excel文件生成后存储的位置。
        OutputStream fos  = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(content);
            os.close();
            fos.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static CellStyle setStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle(); // 单元格样式
        Font fontStyle = workbook.createFont(); // 字体样式
        fontStyle.setBold(false); // 加粗
        fontStyle.setFontName("宋体"); // 字体
        fontStyle.setFontHeightInPoints((short) 11); // 大小
        // 将字体样式添加到单元格样式中
        cellStyle.setFont(fontStyle);
        // 边框，居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }
}