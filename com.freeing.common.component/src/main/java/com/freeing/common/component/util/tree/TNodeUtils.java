package com.freeing.common.component.util.tree;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TNode 的工具类
 *
 * @author yanggy
 */
public class TNodeUtils {

    /**
     *  构建一个树
     *
     * @param nodes 所有树的节点
     * @param rootPredicate 根节点断言
     * @param childPredicate 孩子节点断言 T = 可能的父节点, U = 可能的孩子节点
     * @return tree
     */
    public static <K, V> List<TNode<K, V>> buildTree(List<TNode<K, V>> nodes, Predicate<TNode<K, V>> rootPredicate,
            BiPredicate<TNode<K, V>, TNode<K, V>> childPredicate, Comparator<TNode<K, V>> comparator) {
        List<TNode<K, V>> rootNodes = nodes.stream()
            .filter(rootPredicate)
            .sorted(comparator).collect(Collectors.toList());
        for (TNode<K, V> node : rootNodes) {
            node.setLevel(1);
            doBuildTreeChildren(node, nodes, childPredicate, comparator, 1);
        }
        return rootNodes;
    }

    private static <K, V> void doBuildTreeChildren(TNode<K, V> root, List<TNode<K, V>> nodes,
            BiPredicate<TNode<K, V>, TNode<K, V>> childPredicate, Comparator<TNode<K, V>> comparator, int level) {
        List<TNode<K, V>> childrenNodes = new ArrayList<>();
        for (TNode<K, V> mightChildNode : nodes) {
            if (childPredicate.test(root, mightChildNode)) {
                mightChildNode.setLevel(level + 1);
                childrenNodes.add(mightChildNode);
                doBuildTreeChildren(mightChildNode, nodes, childPredicate, comparator, level + 1);
            }
        }
        if (CollectionUtils.isNotEmpty(childrenNodes)) {
            childrenNodes.sort(comparator);
            root.setChildren(childrenNodes);
        }
    }
}
