package com.freeing.common.component.utils.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yanggy
 */
public class TestTNode {
    public static void main(String[] args) {
        TNode<Long, String> n1 = new TNode<>();
        n1.setNodeId(1L);
        n1.setParentId(-1L);
        n1.setData("根节点1");
        TNode<Long, String> n2 = new TNode<>();
        n2.setNodeId(2L);
        n2.setParentId(-1L);
        n2.setData("根节点2");

        TNode<Long, String> n3 = new TNode<>();
        n3.setNodeId(3L);
        n3.setParentId(1L);
        n3.setData("二层节点1");
        TNode<Long, String> n4 = new TNode<>();
        n4.setNodeId(4L);
        n4.setParentId(1L);
        n4.setData("二层节点2");
        TNode<Long, String> n5 = new TNode<>();
        n5.setNodeId(5L);
        n5.setParentId(2L);
        n5.setData("二层节点3");

        TNode<Long, String> n6 = new TNode<>();
        n6.setNodeId(6L);
        n6.setParentId(3L);
        n6.setData("三层节点1");
        TNode<Long, String> n7 = new TNode<>();
        n7.setNodeId(7L);
        n7.setParentId(3L);
        n7.setData("三层节点2");
        TNode<Long, String> n8 = new TNode<>();
        n8.setNodeId(8L);
        n8.setParentId(5L);
        n8.setData("三层节点3");

        ArrayList<TNode<Long, String>> list = new ArrayList<>();
        list.add(n1);
        list.add(n2);
        list.add(n3);
        list.add(n4);
        list.add(n5);
        list.add(n6);
        list.add(n7);
        list.add(n8);

        List<TNode<Long, String>> tNodes = TNodeHelper.buildTree(list,
            node -> Objects.equals(node.getParentId(), -1L),
            (root, child) -> child.getParentId().equals(root.getNodeId()));
        System.out.println(tNodes);
    }
}
