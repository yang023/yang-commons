package cn.yang.commons.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author yang
 * <p>
 * excel 生成共工具
 */
public class ExcelFiller {

    private int sheet = 0;

    private WriteDirectionEnum directionEnum = WriteDirectionEnum.VERTICAL;

    private InputStream template;
    private ExcelWriter excelWriter;

    private ByteArrayOutputStream outputStream;
    private WriteSheet writeSheet;

    private ExcelFiller() {
    }

    public static ExcelFiller create() {
        return new ExcelFiller();
    }

    public ExcelFiller template(InputStream template) {
        this.template = template;
        return this;
    }

    public ExcelFiller build() {
        this.outputStream = new ByteArrayOutputStream();
        this.excelWriter = EasyExcel.write(outputStream)
                .excelType(ExcelTypeEnum.XLSX).withTemplate(this.template)
                .autoCloseStream(false)
                .build();
        return this;
    }

    public byte[] getBytes() {
        this.excelWriter.finish();
        return this.outputStream.toByteArray();
    }

    public ExcelFiller fill(Object data) {
        if (Objects.isNull(this.outputStream)) {
            this.build();
        }
        if (Objects.isNull(this.writeSheet)) {
            this.writeSheet = EasyExcel.writerSheet(this.sheet).build();
        }
        FillConfig build = FillConfig.builder().direction(this.directionEnum).build();
        this.excelWriter.fill(data, build, this.writeSheet);
        return this;
    }

    public ExcelFiller fillNewSheet(Object data) {
        this.sheet++;
        this.writeSheet = EasyExcel.writerSheet(this.sheet).build();
        return this.fill(data);
    }

    public ExcelFiller vertical() {
        this.directionEnum = WriteDirectionEnum.VERTICAL;
        return this;
    }

    public ExcelFiller horizontal() {
        this.directionEnum = WriteDirectionEnum.HORIZONTAL;
        return this;
    }

}