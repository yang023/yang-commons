package cn.yang.commons.select;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yang 2019/1/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {

    private String value;

    private String text;

}
