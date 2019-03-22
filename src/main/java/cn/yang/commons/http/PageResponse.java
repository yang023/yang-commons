package cn.yang.commons.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * @author yang 2019/1/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private Integer code;
    private String message;
    private List<T> data;
    @Builder.Default
    private Long total = 0L;
    @Builder.Default
    private Long size = 0L;
    @Builder.Default
    private Long page = 0L;

    public Long getTotalPage() {
        if (Objects.isNull(size) || size == 0) {
            return 0L;
        }
        return Math.abs(total / size) + (total % size == 0 ? 0 : 1);
    }

    public Long getSize() {
        if (size < 0L) {
            return 0L;
        }
        return size;
    }

    public Long getPage() {
        if (size < 0L) {
            return 0L;
        }
        return page;
    }

    public boolean isHasPrePage() {
        return page > 1;
    }

    public boolean isHasNextPage() {
        return page < getTotalPage();
    }

    public PageResponse<T> pageInfo(PageInfo<T> pageInfo) {
        size = pageInfo.getSize();
        page = pageInfo.getPage();
        total = pageInfo.getTotal();
        data = pageInfo.getList();
        return this;
    }
}
