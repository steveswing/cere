package com.briarshore.rule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.io.Files;

/**
 * Class: RuleExtractor
 */
public final class RuleExtractor {
    private RuleExtractor() {
    }

    public static Map<String, Table<String, String, String>> extractRules(final File ruleSource) throws IOException, InvalidFormatException {
        try (InputStream inputStream = Files.newInputStreamSupplier(ruleSource).getInput()) {
            return extractRules(inputStream);
        }
    }

    public static Map<String, Table<String, String, String>> extractRules(final InputStream ruleSource) throws IOException, InvalidFormatException {
        final Map<String, Table<String, String, String>> rules = new LinkedHashMap<>();
        try (Workbook workbook = WorkbookFactory.create(ruleSource)) {
            if (workbook instanceof XSSFWorkbook) {
                XSSFWorkbook xssfWorkbook = (XSSFWorkbook)workbook;
                final DataFormatter dataFormatter = new DataFormatter(true);
                for (final Sheet xssfSheet : xssfWorkbook) {
                    final String sheetName = xssfSheet.getSheetName();
                    final Table<String, String, String> ruleTable = Tables.newCustomTable(Maps.<String, Map<String, String>>newLinkedHashMap(), Maps::newLinkedHashMap);
                    rules.put(sheetName, ruleTable);
                    for (final Row row : xssfSheet) {
                        for (final Cell cell : row) {
                            final String rowKey = extractRowNbrString(cell.getRow());
                            final String columnKey = extractColumnIndexString(cell);
                            final String value = StringUtils.trimToEmpty(dataFormatter.formatCellValue(cell));
                            ruleTable.put(rowKey, columnKey, value);
                        }
                    }
                }
            }
        }
        return rules;
    }

    public static String extractRowNbrString(final Row row) {
        return null == row ? "undefined" : String.valueOf(row.getRowNum());
    }

    public static String extractColumnIndexString(final Cell cell) {
        return null == cell ? "undefined" : CellReference.convertNumToColString(cell.getColumnIndex());
    }
}
