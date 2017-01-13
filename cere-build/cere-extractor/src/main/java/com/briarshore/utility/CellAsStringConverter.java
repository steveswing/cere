package com.briarshore.utility;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.dozer.DozerConverter;

import com.briarshore.util.Strings;

/**
 * Class: CellAsStringConverter
 */
public class CellAsStringConverter extends DozerConverter<XSSFCell, String> {
    private final DataFormatter dataFormatter = new DataFormatter(true);

    public CellAsStringConverter() {
        this(XSSFCell.class, String.class);
    }

    /**
     * Defines two types, which will take part transformation.
     * As Dozer supports bi-directional mapping it is not known which of the classes is source and
     * which is destination. It will be decided in runtime.
     *
     * @param prototypeA type one
     * @param prototypeB type two
     */
    public CellAsStringConverter(final Class<XSSFCell> prototypeA, final Class<String> prototypeB) {
        super(prototypeA, prototypeB);
    }

    @Override
    public String convertTo(final XSSFCell source, final String destination) {
        if (null == source) {
            return destination;
        }
        return dataFormatter.formatCellValue(source);
    }

    @Override
    public XSSFCell convertFrom(final String source, final XSSFCell destination) {
        if (null != destination) {
            if (Strings.isBlank(source)) {
                destination.setCellType(XSSFCell.CELL_TYPE_BLANK);
            } else {
                destination.setCellValue(source);
            }
        }

        return destination;
    }
}
