package cs445.a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cs445.StackAndQueuePackage.*; // Needed by tree iterators


/**
 * A class that implements the ADT binary tree.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author William C. Garrison
 * @version 4.5
 */
public class TernaryTree<E> implements TernaryTreeInterface<E> {

    private TernaryNode<E> root;

    public TernaryTree() {
        root = null;
    }

    public TernaryTree(E rootData) {
        root = new TernaryNode(root);

    }

    public TernaryTree(E rootData, TernaryTree<E> leftTree, TernaryTree<E> middleTree, TernaryTree<E> rightTree) {

        privateSetTree(rootData, leftTree, middleTree, rightTree);
    }

    @Override
    public E getRootData() {
        if (isEmpty())
            throw new EmptyTreeException();
        else
            return root.getData();

    }

    @Override
    public int getHeight() {

        int height = 0;
        if (!isEmpty())
            height = root.getHeight();

        return height;
    }

    @Override
    public int getNumberOfNodes() {
        int numberOfNodes = 0;
        if (!isEmpty()) {
            numberOfNodes = root.getNumberOfNodes();
        }
        return numberOfNodes;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = null;

    }

    public Iterator<E> getPreorderIterator() {
        return new PreorderIterator();
    }

    public Iterator<E> getInorderIterator() {
        throw new UnsupportedOperationException();
        /*
         * In order traversal is when you visit the left subtree, the root, and then the
         * right subtree. In this tree, there is a also a middle subtree. There is no
         * place for it.
         */
    }

    public Iterator<E> getPostorderIterator() {
        return new PostorderIterator();
    }

    public Iterator<E> getLevelOrderIterator() {
        return new LevelOrderIterator();
    }


    public void setTree(E rootData) {
        root = new TernaryNode<>(rootData);
    }

    
    public void setTree(E rootData, TernaryTreeInterface<E> leftTree, TernaryTreeInterface<E> middleTree,
            TernaryTreeInterface<E> rightTree) {
        privateSetTree(rootData, (TernaryTree<E>) leftTree, (TernaryTree<E>) middleTree, (TernaryTree<E>) rightTree);
    }

    private void privateSetTree(E rootData, TernaryTree<E> leftTree, TernaryTree<E> middleTree,TernaryTree<E> rightTree) {
        TernaryNode<E> root = new TernaryNode<>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
        }

        if ((middleTree != null) && !middleTree.isEmpty()) {
            root.setMiddleChild(middleTree.root);

             if (middleTree != leftTree) {
                root.setMiddleChild(middleTree.root);
            } else {
                root.setMiddleChild(middleTree.root.copy());
            }
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if ((rightTree != leftTree) && (rightTree != middleTree)) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }
        }

        this.root = root;

        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }

        if ((middleTree != null) && (middleTree != this)) {
            middleTree.clear();
        }

        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
    }

    private class PreorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;

        public PreorderIterator() {
            nodeStack = new LinkedStack<>();
            if (root != null) {
                nodeStack.push(root);
            }
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<E> leftChild = nextNode.getLeftChild();
                TernaryNode<E> middleChild = nextNode.getMiddleChild();
                TernaryNode<E> rightChild = nextNode.getRightChild();

// Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }

                if (middleChild != null) {
                    nodeStack.push(middleChild);
                }

                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class PostorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;
        private TernaryNode<E> currentNode;

        public PostorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }

        public E next() {
                boolean foundNext = false;
                TernaryNode<E> leftChild, middleChild, rightChild, nextNode = null;

                // Find leftmost leaf
                while (currentNode != null) 
                {
                    nodeStack.push(currentNode);
                    leftChild = currentNode.getLeftChild(); //get the left of the current 
                    if (leftChild == null)
                    {	
                    	
                        int placing = currentNode.getNextChild();//doesnt have a left child, ok make the root the nudd 
                        if(placing == 2 )
                        	currentNode = currentNode.getMiddleChild();
                        if(placing == 3)
                        	currentNode = currentNode.getRightChild();
                        if(placing == 5)
                        	currentNode = null;
                    }
                    else
                     {
                        currentNode = leftChild;
                    }
                } 

                //we have found the left most child
                //current node is null



                // Stack is not empty either because we just pushed a node, or
                // it wasn'E empty to begin with since hasNext() is true.
                // But Iterator specifies an exception for next() in case
                // hasNext() is false.

                if (!nodeStack.isEmpty()) 
                {
                    nextNode = nodeStack.pop();
                    // nextNode != null since stack was not empty before pop

                    TernaryNode<E> parent = null;
                    if (!nodeStack.isEmpty()) 
                    {
                        parent = nodeStack.peek();

                        if (nextNode == parent.getLeftChild()) //check if the left most node is the left child
                        {
                            currentNode = parent.getMiddleChild();
                        } 
                        else if(nextNode == parent.getMiddleChild());
                        {
                            currentNode = parent.getRightChild();
                        }
                   
                    }
                        
                    else 
                    {
                        currentNode = null;
                    }
                } 
                else 
                {
                    throw new NoSuchElementException();
                }

                return nextNode.getData();
            }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

//////////////////////////////////////////////////////////////////////////////
    private class LevelOrderIterator implements Iterator<E> {
        private QueueInterface<TernaryNode<E>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            if (root != null) {
                nodeQueue.enqueue(root);
            }
        }

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        }

        public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.dequeue();
                TernaryNode<E> leftChild = nextNode.getLeftChild();
                TernaryNode<E> middleChild = nextNode.getMiddleChild();
                TernaryNode<E> rightChild = nextNode.getRightChild();

// Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.enqueue(leftChild);
                }

                if (middleChild != null) {
                    nodeQueue.enqueue(middleChild);
                }

                if (rightChild != null) {
                    nodeQueue.enqueue(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }



    public static void main(String[] args) {
    	
    	   TernaryNode<Integer> root = new TernaryNode<Integer>(100);
    	TernaryNode<Integer> leaf4 = new TernaryNode<Integer>(4);   
    	TernaryNode<Integer> leaf5 = new TernaryNode<Integer>(5);   
    	TernaryNode<Integer> leaf6 = new TernaryNode<Integer>(6);   
    	TernaryNode<Integer> leaf7 = new TernaryNode<Integer>(7);   
    	TernaryNode<Integer> leaf8 = new TernaryNode<Integer>(8);   
    	TernaryNode<Integer> leaf9 = new TernaryNode<Integer>(9);   

    		TernaryNode<Integer> par2 = new TernaryNode<Integer>(11);   
    	TernaryNode<Integer> par3 = new TernaryNode<Integer>(12); 
    	
    	
    	
    	
   
    	TernaryTree<Integer> leaf1 = new TernaryTree<Integer>(1);   
    	//TernaryTree<Integer> leaf2 = new TernaryTree<Integer>(2);   
    	TernaryTree<Integer> leaf3 = new TernaryTree<Integer>(3);
    	
    	TernaryTree<Integer> par1 = new TernaryTree<Integer>(10, leaf1, null,leaf3);   
      
    	System.out.println(par1.getNumberOfNodes());
    	
    	  
    	
    	
    	
    }



}
