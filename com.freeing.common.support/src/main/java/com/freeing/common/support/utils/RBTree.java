package com.freeing.common.support.utils;

import com.freeing.common.support.module.RBNode;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 红黑树
 *
 * @author yanggy
 */
public class RBTree<K extends Comparable<K>, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private RBNode<K, V> root;

    private int size;

    /**
     * 操作版本号
     */
    private int version;

    public RBNode<K, V> getRoot() {
        return root;
    }

    public int size() {
        return size;
    }

    /**
     * 添加一个节点，如果权值相同则覆盖旧的value
     *
     * @param key 节点权值
     * @param value 节点value
     */
    public void put(K key, V value) {
        if (root == null) {
            root = new RBNode<>(BLACK, key, value);
            size++;
            version++;
            return;
        }
        RBNode<K, V> point = root;
        // point 的 parent
        RBNode<K, V> parent;
        int cmp;
        do {
            parent = point;
            cmp = key.compareTo(point.getKey());
            if (cmp < 0) {
                point = point.getLeft();
            } else if (cmp > 0) {
                point = point.getRight();
            } else {
                // key 相同则覆盖并结束
                point.setValue(value);
                return;
            }
        } while (point != null);
        RBNode<K, V> newNode = new RBNode<>(RED, key, value, parent);
        if (cmp < 0) {
            parent.setLeft(newNode);
        } else  {
            parent.setRight(newNode);
        }
        size++;
        version++;
        adjustAfterPut(newNode);
    }

    /**
     * 删除节点
     *
     * @param key key
     */
    public void remove(K key) {
        RBNode<K, V> rmNode = search(node -> node.getKey().equals(key));
        if (rmNode == null) {
            return;
        }
        RBNode<K, V> parent = parentOf(rmNode);
        if (rmNode.getLeft() == null && rmNode.getRight() == null) {
            if (parent.getLeft() == rmNode) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if (rmNode.getLeft() != null && rmNode.getRight() != null) {

        } else {

        }
    }

    /**
     * 获取前驱节点
     *
     * @param node RBNode
     * @return RBNode
     */
    private RBNode<K, V> predecessor(RBNode<K, V> node) {
        if (node == null) {
            return null;
        } else if (node.getLeft() != null) {
            RBNode<K, V> p = node.getLeft();
            while (p.getRight() != null) {
                p = p.getRight();
            }
            return p;
        } else {
            RBNode<K, V> p = node.getParent();
            RBNode<K, V> childOfP = node;
            while (p != null && childOfP !=  p.getLeft()) {
                childOfP = p;
                p = p.getParent();
            }
            return p;
        }
    }

    /**
     * 获取后继节点
     *
     * @param node RBNode
     * @return RBNode
     */
    private RBNode<K, V> sucessor(RBNode<K, V> node) {
        if (node == null) {
            return null;
        } else if (node.getRight() != null) {
            RBNode<K, V> p = node.getRight();
            while (p.getLeft() != null) {
                p = p.getLeft();
            }
            return p;
        } else {
            RBNode<K, V> p = node.getParent();
            RBNode<K, V> childOfP = node;
            while (p != null && childOfP !=  p.getRight()) {
                childOfP = p;
                p = p.getParent();
            }
            return p;
        }
    }

    /**
     * 1、2-3-4树：新增元素与2节点合并（节点中原有1个元素） -> 3节点（节点中2个元素）
     *      红黑树：新增一个红色节点+黑色父节点（上黑下红）
     * 2、2-3-4树：新增元素与3节点合并（节点中原有2个元素）-> 4节点(节中有3个元素）
     *      红黑树：3个节点有4中情况(分别是：全左，全又，还有2个左中右)，全左，全右需要调整
     *      调整：新增红色节点+上黑夏红（中间节点是黑色，两边节点是红色）
     * 3、2-3-4树：新增一个节点与4节点合并 -> 原来的4节点分裂，中间节点升级为父节点，新增节点
     *            剩余节点中的其中一个合并红黑树
     *      红黑树：新增红色节点 + 爷爷节点（黑），父亲和叔叔节点都是红色 -> 爷爷变红，父亲
     *             叔叔变黑，如果爷爷是根则需要再次变色
     *
     * @param node 新增节点（叶子节点）
     */
    private void adjustAfterPut(RBNode<K, V> node) {
        RBNode<K, V> uncle;
        // node 的 parent 是红色，则爷爷节点是黑色，叔叔节点为空则是黑色否则是红色
        while (node != null && node != root && node.getParent().color() == RED) {
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                // 左中右 + 新增最左节点：变色爷爷变红色，父亲和叔叔变黑色，自己红色
                uncle = rightOf(parentOf(parentOf(node)));
                if (colorOf(uncle) == RED) { // 叔叔非空则是红色
                    /*
                     *     a
                     *    /  \
                     *   b    d
                     *     \
                     *       c
                     */
                    setColor(parentOf(node), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    // 递归往上判断
                    node = parentOf(parentOf(node));
                } else {
                    if (node == rightOf(parentOf(node))) {
                        /*
                         *     a                a
                         *    /                /
                         *   b       ==>      c
                         *     \             /
                         *       c          b
                         */
                        node = parentOf(node);
                        leftRotate(node);
                    }
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    rightRotate(parentOf(parentOf(node)));
                }
            } else {
                uncle = leftOf(parentOf(parentOf(node)));
                if (colorOf(uncle) == RED) {
                    setColor(parentOf(node), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                }
                else {
                    if (node == leftOf(parentOf(node))) {
                        node = parentOf(node);
                        rightRotate(node);
                    }
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    leftRotate(parentOf(parentOf(node)));
                }
            }
        }
        root.setColor(BLACK);
    }

    private void setColor(RBNode<K, V> node, boolean color) {
        if (node != null) {
            node.setColor(color);
        }
    }

    private boolean colorOf(RBNode<K, V> node) {
        // 空节点（叶子）是黑色
        return node == null ? BLACK : node.color();
    }

    private RBNode<K, V> parentOf(RBNode<K, V> node) {
        return node != null ? node.getParent() : null;
    }

    private RBNode<K, V> leftOf(RBNode<K, V> node) {
        return node != null ? node.getLeft() : null;
    }

    private RBNode<K, V> rightOf(RBNode<K, V> node) {
        return node != null ? node.getRight() : null;
    }

    /**
     * 遍历红黑树
     *
     * @param action {@code RBNode}的消费者
     */
    public void visit(Consumer<RBNode<K, V>> action) {
        if (root != null) {
            doInfixOrder(root, action);
        }
    }

    /**
     * 中序遍历红黑树
     *
     * @param node {@code RBNode}
     * @param action {@code RBNode}的消费者
     */
    final void doInfixOrder(RBNode<K, V> node, Consumer<RBNode<K, V>> action) {
        if (node.getLeft() != null) {
            doInfixOrder(node.getLeft(), action);
        }
        action.accept(node);
        if (node.getRight() != null) {
            doInfixOrder(node.getRight(), action);
        }
    }

    /**
     * 中序遍历查找二叉树的节点，若不存在返回null
     *
     * @param predicate 断言，判断是否为目标节点
     * @return RBNode<K, V> 返回目标节点
     */
    private RBNode<K, V> search(Predicate<RBNode<K, V>> predicate) {
       if (root != null) {
           return doSearch(root, predicate);
       }
       return null;
    }

    /**
     * 中序遍历查找二叉树的节点，若不存在返回null
     *
     * @param node RBNode<K, V>
     * @param predicate 断言，判断是否为目标节点
     * @return RBNode<K, V> 返回目标节点
     */
    private RBNode<K, V> doSearch(RBNode<K, V> node, Predicate<RBNode<K, V>> predicate) {
        RBNode<K, V> resultNode = null;
        if (node.getLeft() != null) {
            resultNode = doSearch(node.getLeft(), predicate);
        }
        if (resultNode != null) {
            return resultNode;
        }
        if (predicate.test(node)) {
            return node;
        }
        if (node.getRight() != null) {
            resultNode = doSearch(node.getRight(), predicate);
        }
        return resultNode;
    }

    /**
     * 左旋（围绕旋转点左旋）
     *           4                              6
     *         /  \                            / \
     *        3    6            ==>           4   7
     *            /  \                       / \
     *          5      7                    3   5
     * @param pivot 旋转点
     */
    private void leftRotate(RBNode<K, V> pivot) {
        if (pivot == null) {
            return;
        }
        /* *************** 旋转上移 ***************/
        // 旋转点右指针指向旋转点的右子树的左子树，然后旋转点的右子树左旋转为根
        RBNode<K, V> prNode = pivot.getRight();
        pivot.setRight(prNode.getLeft());
        // 修改parent指向
        if (prNode.getLeft() != null) {
            prNode.getLeft().setParent(pivot);
        }
        // 旋转为根
        prNode.setParent(pivot.getParent());
        if (pivot.getParent() == null) {
            root = prNode;
        } else if (pivot.getParent().getLeft() == pivot) {
            pivot.getParent().setLeft(prNode);
        } else {
            pivot.getParent().setRight(prNode);
        }

        /* *************** 旋转下移 ***************/
        prNode.setLeft(pivot);

        pivot.setParent(prNode);
    }

    /**
     * 右旋（围绕旋转点右旋）
     *
     * @param pivot 旋转点
     */
    private void rightRotate(RBNode<K, V> pivot) {
        if (pivot == null) {
            return;
        }
        /* *************** 旋转上移 ***************/
        RBNode<K, V> plNode = pivot.getLeft();
        pivot.setLeft(plNode.getRight());
        // 修改parent指向
        if (plNode.getRight() != null) {
            plNode.getRight().setParent(pivot);
        }
        // 旋转为根
        plNode.setParent(pivot.getParent());
        if (pivot.getParent() == null) {
            root = plNode;
        } else if (pivot.getParent().getRight() == pivot) {
            pivot.getParent().setRight(plNode);
        } else {
            pivot.getParent().setLeft(plNode);
        }

        /* *************** 旋转下移 ***************/
        plNode.setLeft(pivot);
        pivot.setParent(plNode);
    }
}
