package cn.yang.commons.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yang 2019/2/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {

    private Integer page;
    private Integer size;

    public Integer getIndex() {
        return (page - 1) / size;
    }

}
