package com.freeing.common.component.utils.tree;

import org.apache.commons.collections4.CollectionUtils;
import org.reflections.ReflectionsException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TreeUtils
 *
 * @author yanggy
 */
public class TreeUtils {
    /**
     *  构建一个树形结构
     *
     * @param dataList 所有的节点
     * @param rootPredicate 根节点断言
     * @param childPredicate 孩子节点断言 T = 可能的父节点, U = 可能的孩子节点
     * @param <T> 具有 children 属性的类
     * @return tree
     */
    public static <T> List<T> buildTree(List<T> dataList, Predicate<T> rootPredicate, BiPredicate<T, T> childPredicate,
            Comparator<T> comparator) {
        List<T> rootNodes = dataList.stream().filter(rootPredicate).sorted(comparator).collect(Collectors.toList());
        for (T data : rootNodes) {
            doBuildTreeChildren(data, dataList, childPredicate, comparator);
        }
        return rootNodes;
    }

    private static <T> void doBuildTreeChildren(T root, List<T> dataList, BiPredicate<T, T> childPredicate,
            Comparator<T> comparator) {
        List<T> childrenNodes = new ArrayList<>();

        for (T mightChildNode : dataList) {
            if (childPredicate.test(root, mightChildNode)) {
                childrenNodes.add(mightChildNode);
                doBuildTreeChildren(mightChildNode, dataList, childPredicate, comparator);
            }
        }
        if (CollectionUtils.isNotEmpty(childrenNodes)) {
            childrenNodes.sort(comparator);
            try {
                Class<?> clazz = root.getClass();
                Field children = clazz.getDeclaredField("children");
                children.setAccessible(true);
                children.set(root, childrenNodes);
                children.setAccessible(false);
            } catch (Exception e) {
                throw new ReflectionsException("构建该类型数据的树形机构时，此类必须包含一个List类型的children的成员属性");
            }
        }
    }
}
