package cn.yang.commons.tree;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author yang
 * <p>
 * 树形结构生成器
 */
public class TreeBuilder<T> {

    private String root;
    private boolean setParent = true;

    private List<Node<T>> tree;

    private TreeBuilder(String root) {
        this.root = root;
        this.tree = Lists.newArrayList();
    }

    /**
     * 创建树构造器
     *
     * @param <T> Node节点包含数据的实体类
     * @return 返回构造器实例
     */
    public static <T> TreeBuilder<T> create() {
        return new TreeBuilder<>("root");
    }

    /**
     * 创建树构造器, 并指定 root 节点 key 标识
     *
     * @param <T>  Node节点包含数据的实体类
     * @param root root 节点 key 标识, key = ${root} 时, 该节点为根节点
     * @return 返回构造器实例
     */
    public static <T> TreeBuilder<T> create(String root) {
        return new TreeBuilder<>(root);
    }

    /**
     * 指定 root 节点 key 标识
     *
     * @param root root 节点 key 标识, key = ${root} 时, 该节点为根节点
     * @return 返回构造器实例
     */
    public TreeBuilder<T> root(String root) {
        this.root = root;
        return this;
    }

    /**
     * 生成节点时是否设置 parent 节点
     *
     * @param setParent 是否设置
     * @return 返回构造器实例
     */
    public TreeBuilder<T> setParent(boolean setParent) {
        this.setParent = setParent;
        return this;
    }

    /**
     * list 转 tree
     *
     * @param nodes 待转换为 tree 的原始节点列表集合
     * @return 转换后的 tree 对象
     */
    public List<Node<T>> parse(List<Node<T>> nodes) {
        this.tree = this.getRootNodes(nodes);
        List<Node<T>> childNodes = nodes.stream().filter(item -> !this.tree.contains(item)).collect(Collectors.toList());
        this.tree.forEach(item -> {
            List<Node<T>> itemChildNodes = this.getChildNodes(item, childNodes);
            if (itemChildNodes.isEmpty()) {
                item.setLeaf(true);
            }
            item.setChildren(itemChildNodes);
        });
        return this.tree;
    }

    /**
     * list 转 tree
     *
     * @param list         待转换为 tree 的原始数据列表集合
     * @param getKey       将原始数据转换成 Node 对象的转换时设置的 key 数据
     * @param getParentKey 将原始数据转换成 Node 对象的转换时设置的 parentKey 数据
     * @return 转换后的 tree 对象
     */
    public List<Node<T>> parse(List<T> list, Function<T, String> getKey, Function<T, String> getParentKey) {
        List<Node<T>> nodes = list.stream()
                .map(item -> new Node<T>()
                        .setData(item).setKey(getKey.apply(item)).setParentKey(getParentKey.apply(item)))
                .collect(toList());
        return parse(nodes);
    }

    private List<Node<T>> getRootNodes(List<Node<T>> nodes) {
        return nodes.stream()
                .filter(this::isRoot)
                .map(item -> item.setParent(null))
                .collect(toList());
    }

    private List<Node<T>> getChildNodes(Node<T> current, List<Node<T>> nodes) {
        List<Node<T>> children = nodes.stream()
                .filter(item -> item.getParentKey().equals(current.getKey()))
                .map(item -> {
                    if (this.setParent) {
                        return item.setParent(current.getData());
                    }
                    return item.setParentKey(current.getKey());
                })
                .collect(toList());
        List<Node<T>> others = nodes.stream().filter(item -> !children.contains(item)).collect(toList());
        children.forEach(item -> {
            List<Node<T>> itemChildNodes = this.getChildNodes(item, others);
            if (itemChildNodes.isEmpty()) {
                item.setLeaf(true);
            }
            item.setChildren(itemChildNodes);
        });
        return children;
    }

    private boolean isRoot(Node<T> node) {
        return node.getParentKey().equals(this.root);
    }

}
