package cn.yang.commons.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
