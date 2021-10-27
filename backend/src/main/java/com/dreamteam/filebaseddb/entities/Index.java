//package com.dreamteam.filebaseddb.entities;
//
//import java.io.Serializable;
//
//public class Index implements Serializable {
//    private Index left;
//    private Index right;
//    private Index parent;
//
//    private boolean color; // red-black
//
//    private Node node;
//
//    public Index() {
//        color = true;
//    }
//
//    private static class Node {
//        Long id;
//        Long line;
//    }
//
//    public void put(Long id, Long line) {
//        Node currentNode = node;
//
//    }
//
//    public long get(Long id) {
//
//    }
//
//    private void rotateRight(Index index) {
//        Index parent = index.parent;
//        Index leftChild = index.left;
//
//        index.left = leftChild.right;
//
//        if (leftChild.right != null) {
//            leftChild.right.parent = index;
//        }
//
//        leftChild.right = index;
//        index.parent = leftChild;
//
//        replaceParentsChild(parent, index, leftChild);
//    }
//
//    private void replaceParentsChild(Index parent, Index oldChild, Index newChild) {
//        if (parent == null) {
//            root = newChild;
//        } else if (parent.left == oldChild) {
//            parent.left = newChild;
//        } else if (parent.right == oldChild) {
//            parent.right = newChild;
//        } else {
//            throw new IllegalStateException("Node is not a child of its parent");
//        }
//
//        if (newChild != null) {
//            newChild.parent = parent;
//        }
//    }
//}
