package cn.yang.commons.excel;

import com.alibaba.excel.EasyExcel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author yang
 * <p>
 * excel读取工具
 */
public class ExcelReader<T> {
    private final Class<T> type;

    private Integer sheet;

    private InputStream inputStream;

    private int batchSize = 10;

    private ExcelReader(Class<T> type) {
        this.type = type;
    }

    public static <T> ExcelReader<T> create(Class<T> type) {
        return new ExcelReader<>(type);
    }

    public static <T> ExcelReader<T> on(InputStream inputStream, Class<T> type) {
        return new ExcelReader<>(type).input(inputStream);
    }

    public ExcelReader<T> input(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public ExcelReader<T> sheet(Integer index) {
        this.sheet = index;
        return this;
    }

    public ExcelReader<T> batchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public ExcelReader<T> read(Consumer<List<T>> action, boolean close) {
        EasyExcel.read(this.inputStream, this.type, new DataListener<>(this.batchSize, action))
                .autoCloseStream(close).sheet(this.sheet).doRead();
        return this;
    }

    public ExcelReader<T> read(Consumer<List<T>> action) {
        return read(action, true);
    }

    public void close() {
        try {
            if (!Objects.isNull(this.inputStream)) {
                this.inputStream.close();
            }
        } catch (IOException ignore) {
        }
    }

}
