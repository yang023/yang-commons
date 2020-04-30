package cn.yang.commons.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Consumer;

public class DataListener<T> extends AnalysisEventListener<T> {

    private List<T> list;

    private int batchSize;
    private Consumer<List<T>> action;


    DataListener(int batchSize, Consumer<List<T>> action) {
        this.action = action;
        this.batchSize = batchSize;
        this. list = Lists.newArrayListWithCapacity(batchSize);
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
        if (list.size() >= batchSize) {
            saveData();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        this.action.accept(this.list);
        list.clear();
    }
}