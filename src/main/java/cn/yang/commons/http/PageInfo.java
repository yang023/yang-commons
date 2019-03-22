package cn.yang.commons.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yang 2019/3/17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> {

    private Long page;

    private Long size;

    private Long total;

    private List<T> list;

    public static <T> PageInfo<T> empty() {
        return PageInfo.<T>builder()
                .page(0L)
                .size(0L)
                .total(0L)
                .list(new ArrayList<>(0))
                .build();
    }

}
