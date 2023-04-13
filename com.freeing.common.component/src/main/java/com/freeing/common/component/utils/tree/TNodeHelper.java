package com.freeing.common.component.utils.tree;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 构建树的工具类
 *
 * @author yanggy
 */
public class TNodeHelper {

    /**
     *  构建一个树
     *
     * @param nodes 所有树的节点
     * @param rootPredicate 根节点断言
     * @param childPredicate 孩子节点断言 T = 可能的父节点, U = 可能的孩子节点
     * @return tree
     */
    public static <K, V> List<TNode<K, V>> buildTree(List<TNode<K, V>> nodes,
            Predicate<TNode<K, V>> rootPredicate, BiPredicate<TNode<K, V>, TNode<K, V>> childPredicate) {
        List<TNode<K, V>> rootNodes = nodes.stream().filter(rootPredicate).collect(Collectors.toList());
        for (TNode<K, V> node : rootNodes) {
            doBuildTreeChildren(node, nodes, childPredicate);
        }
        return rootNodes;
    }

    private static <K, V> void doBuildTreeChildren(TNode<K, V> root,
            List<TNode<K, V>> nodes, BiPredicate<TNode<K, V>, TNode<K, V>> childPredicate) {
        List<TNode<K, V>> childrenNodes = new ArrayList<>();
        for (TNode<K, V> mightChildNode : nodes) {
            if (childPredicate.test(root, mightChildNode)) {
                childrenNodes.add(mightChildNode);
                doBuildTreeChildren(mightChildNode, nodes, childPredicate);
            }
        }
        if (CollectionUtils.isNotEmpty(childrenNodes)) {
            root.setChildren(childrenNodes);
        }
    }
}
