package cn.yang.commons.list;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yang
 */
@ToString
public class PageParam {

    private static final Converter<String, String> CONVERTER = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

    private Integer page;
    private Integer size;
    private List<String> desc;
    private List<String> asc;

    PageParam(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public PageParam() {
        this(0, 10);
    }

    public Long getOffset() {
        return (long) ((page - 1) * size);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    public void setAsc(List<String> asc) {
        this.asc = asc;
    }

    protected List<String> getAsc() {
        if (this.asc == null) {
            return Lists.newArrayListWithCapacity(0);
        }
        ArrayList<String> list = Lists.newArrayListWithCapacity(this.asc.size());
        this.asc.forEach(item -> list.add(CONVERTER.convert(item)));
        return list;
    }

    protected List<String> getDesc() {
        if (this.desc == null) {
            return Lists.newArrayListWithCapacity(0);
        }
        ArrayList<String> list = Lists.newArrayListWithCapacity(this.desc.size());
        this.desc.forEach(item -> list.add(CONVERTER.convert(item)));
        return list;
    }

    protected String[] getAscArray() {
        List<String> list = getAsc();
        return list.toArray(new String[0]);
    }

    protected String[] getDescArray() {
        List<String> list = getDesc();
        return list.toArray(new String[0]);
    }

}
