package cn.yang.commons.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yang 2019/2/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageQuery {

    private Integer page;
    private Integer size;

    public Integer getIndex() {
        if (size == 0) {
            return 0;
        }
        return (page - 1) / size;
    }

}
