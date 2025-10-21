package com.tx.api.test.automate.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
public class ExcelUtil {

    public static Iterator<TestExecutionContext> excelDataReader(Integer sheetNumber, String filepath) {
      ArrayList<TestExecutionContext> apiData = new ArrayList<>();
      try{
          String[] queryParams = null;
          String[] queryValues = null;
          String []  headerNames = null;
          String [] headerValues = null;

          DataFormatter formatter = new DataFormatter();

          File f = new File(filepath);
          FileInputStream file = new FileInputStream(f);
          Workbook workbook = new XSSFWorkbook(file);
          Sheet firstSheet = workbook.getSheetAt(sheetNumber);
          TestExecutionContext testExecutionContext;
          for (Row row : firstSheet) {

              if(row.getRowNum() == 0)
                  continue;
             try{
                 Cell testName = row.getCell(1);
                 Cell baseURlCell = row.getCell(2);
                 Cell reqTypeCell = row.getCell(3);
                 Cell resourceUrlCell = row.getCell(4);
                 Cell contentType = row.getCell(5);
                 Cell queryParamCell = row.getCell(6);
                 Cell queryValueCell = row.getCell(7);
                 Cell headerNameCell = row.getCell(8);
                 Cell headerValueCell = row.getCell(9);
                 Cell requestBodyCell = row.getCell(10);
                 Cell statusCodeCell = row.getCell(11);
                 Cell resTypeCell = row.getCell(12);
                 Cell expectedResponseCell = row.getCell(13);

                 if (!queryParamCell.getStringCellValue().equals(ApiAutomationConstants.NA)
                         && !queryValueCell.getStringCellValue().equals(ApiAutomationConstants.NA)) {
                     queryParams = formatter.formatCellValue(queryParamCell).split(ApiAutomationConstants.SEPARATOR);
                     queryValues = formatter.formatCellValue(queryValueCell).split(ApiAutomationConstants.SEPARATOR);
                 }
                 if ((!headerNameCell.getStringCellValue().equals(ApiAutomationConstants.NA)
                         && !headerValueCell.getStringCellValue().equals(ApiAutomationConstants.NA))) {
                     headerNames = formatter.formatCellValue(headerNameCell).split(ApiAutomationConstants.SEPARATOR);
                     headerValues = formatter.formatCellValue(headerValueCell).split(ApiAutomationConstants.SEPARATOR);
                 }

                 Map<String,Object> queryParamMap = new HashMap<>();
                 if(queryParams != null && (!queryParams[0].equals(ApiAutomationConstants.NA) && !queryValues[0].equals(ApiAutomationConstants.NA))) {
                     for (int j = 0; j < queryParams.length; j++) {
                         queryParamMap.put(queryParams[j],queryValues[j]);
                     }
                 }

                 Map<String,String> headerMap = new HashMap<>();
                 if(headerNames != null && (!headerNames[0].equals(ApiAutomationConstants.NA) && !headerValues[0].equals(ApiAutomationConstants.NA))) {
                     for (int j = 0; j < headerNames.length; j++) {
                         headerMap.put(headerNames[j],headerValues[j]);
                     }
                 }

                 testExecutionContext = TestExecutionContext.builder().testName(testName.getStringCellValue())
                         .baseUrl(baseURlCell.getStringCellValue())
                         .resourceUrl(resourceUrlCell.getStringCellValue())
                         .httpMethod(reqTypeCell.getStringCellValue())
                         .contentType( contentType.getStringCellValue())
                         .headers(headerMap)
                         .queryParams(queryParamMap)
                         .requestBody(getData(requestBodyCell.getStringCellValue()))
                         .statusCode((int)statusCodeCell.getNumericCellValue())
                         .responseType(resTypeCell.getStringCellValue())
                         .expectedResponse(getData(expectedResponseCell.getStringCellValue())).build();

                 apiData.add(testExecutionContext);
                 if(log.isDebugEnabled())
                     log.debug(testExecutionContext.toString());
             } catch (Exception exception){
                 log.error("Exception reading data from excel row",exception);
             }
          }
      }catch(Exception exception){
         log.error("Exception loading data from exel",exception);
      }

        return apiData.iterator();
    }

    private static String getData(String content) throws IOException {
        if(!isNull(content)){
            if(content.startsWith("file:")){
                try {
                    return FileUtils.readFileToString(new File(content.substring(5)), StandardCharsets.UTF_8);
                } catch (IOException exception) {
                    log.error("Exception reading data from exel row",exception);
                    throw exception;
                }
            }
        }
        return content;
    }
}
