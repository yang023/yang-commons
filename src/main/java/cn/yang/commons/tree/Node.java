package cn.yang.commons.tree;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author yang
 * <p>
 * 树形结构节点, 必须设置 key 及 parentKey
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Node<T> implements Serializable {

    private String key;
    private String parentKey;
    private T data;
    private boolean leaf;

    private T parent;
    private List<Node<T>> children;

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this != obj) {
            return false;
        }

        if (!(obj instanceof Node)) {
            return false;
        }

        Node node = (Node) obj;
        return node.hashCode() == this.hashCode();
    }
}
