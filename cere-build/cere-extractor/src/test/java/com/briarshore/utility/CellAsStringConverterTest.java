package com.briarshore.utility;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class: CellAsStringConverterTest
 */
public class CellAsStringConverterTest {
    private CellAsStringConverter defaultConverter;
    private CellAsStringConverter converter;

    @Before
    public void setUp() throws Exception {
        defaultConverter = new CellAsStringConverter();
        converter = new CellAsStringConverter(XSSFCell.class, String.class);
    }

    @Test
    public void testConvertToWithNulls() throws Exception {
        final String defaultActual = defaultConverter.convertTo(null, null);
        Assert.assertNull("expected null", defaultActual);

        final String actual = converter.convertTo(null, null);
        Assert.assertNull("expected null", actual);
    }

    @Test
    public void testConvertToWithNullAndDefault() throws Exception {
        final String defaultExpected = "default";
        final String defaultActual = converter.convertTo(null, defaultExpected);
        Assert.assertNotNull("expected non-null", defaultActual);
        Assert.assertEquals("expected non-null", defaultExpected, defaultActual);

        final String expected = "default";
        final String actual = converter.convertTo(null, expected);
        Assert.assertNotNull("expected non-null", actual);
        Assert.assertEquals("expected non-null", expected, actual);
    }

    @Test
    public void testConvertFromNulls() throws Exception {
        final XSSFCell xssfCell = converter.convertFrom(null, null);
        Assert.assertNull("expected null", xssfCell);
    }

    @Test
    public void testConvertFromStringAndNull() throws Exception {
        final XSSFCell xssfCell = converter.convertFrom("expected", null);
        Assert.assertNull("expected null", xssfCell);
    }

    @Test
    public void testConvertToBlankCellToString() throws Exception {
        final SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        final XSSFWorkbook xssfWorkbook = sxssfWorkbook.getXSSFWorkbook();
        final XSSFSheet sheet = xssfWorkbook.createSheet();
        final XSSFRow row = sheet.createRow(1);
        final XSSFCell cell = row.createCell(1);
        cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
        final String defaultActual = defaultConverter.convertTo(cell);
        sxssfWorkbook.close();
        Assert.assertNotNull("expected non-null value", defaultActual);
        Assert.assertEquals("expected match", StringUtils.EMPTY, defaultActual);
    }

    @Test
    public void testConvertFromEmptyStringToBlankCell() throws Exception {
        final SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        final XSSFWorkbook xssfWorkbook = sxssfWorkbook.getXSSFWorkbook();
        final XSSFSheet sheet = xssfWorkbook.createSheet();
        final XSSFRow row = sheet.createRow(1);
        final XSSFCell cell = row.createCell(1);
        cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
        final XSSFCell defaultActual = defaultConverter.convertFrom(StringUtils.EMPTY, cell);
        sxssfWorkbook.close();
        Assert.assertNotNull("expected non-null value", defaultActual);
        Assert.assertEquals("expected match", XSSFCell.CELL_TYPE_BLANK, defaultActual.getCellType());
    }

    @Test
    public void testConvertFromNonEmptyStringToStringCell() throws Exception {
        final SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        final XSSFWorkbook xssfWorkbook = sxssfWorkbook.getXSSFWorkbook();
        final XSSFSheet sheet = xssfWorkbook.createSheet();
        final XSSFRow row = sheet.createRow(1);
        final XSSFCell cell = row.createCell(1);
        cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
        final XSSFCell defaultActual = defaultConverter.convertFrom("true", cell);
        sxssfWorkbook.close();
        Assert.assertNotNull("expected non-null value", defaultActual);
        Assert.assertEquals("expected match", XSSFCell.CELL_TYPE_STRING, defaultActual.getCellType());
    }
}
