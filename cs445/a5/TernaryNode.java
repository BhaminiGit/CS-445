package cs445.a5;

public class TernaryNode<T> {
  
/*
   private T data;
    private TernaryNode <T>[] children = (TernaryNode <T>[])new TernaryNode <?>[3];
    private int placement; //1.2,3 , 0(root)

    children[0].placement = 1;
    children[1].placement = 2;
    children[2].placement = 3;

 */
    
    private T data;
    private TernaryNode<T>[] children = (TernaryNode<T>[]) new TernaryNode <?>[3];
    private int placement;      
    
    
    
    
    
    public TernaryNode() {
        this(null); // Call next constructor
    }
    public TernaryNode(T dataPortion) {

        this(dataPortion, null, null, null); // Call next constructor

     
    }

    public TernaryNode(T dataPortion, TernaryNode<T> newLeftChild, TernaryNode<T> newMiddleChild,
                      TernaryNode<T> newRightChild) {

        data = dataPortion;
        children[0] = newLeftChild;
        children[1] = newMiddleChild;
        children[2] = newRightChild;
        
       if(dataPortion != null)
    	   this.placement = 0;
        
        if(children[0] != null)
        	 children[0].placement = 1;
        
        if(children[1] != null)
        	children[1].placement = 2;
        
        if(children[2] != null)
        	children[2].placement = 3;
        
        
        
        

    }


    /** Retrieves the data portion of this node.
     *  @return  The object in the data portion of the node. */
    public T getData() {
        return data;
    }

    /** Sets the data portion of this node.
     *  @param newData  The data object. */
    public void setData(T newData) {
        data = newData;
    }
///////////////////////////////////////////////////////////////////////////////////
     /** Retrieves the left child of this node.
     *  @return  The nodeâ€™s left child. */
    public TernaryNode<T> getLeftChild() {
        return children[0];
    }

    /** Sets this nodeâ€™s left child to a given node.
     *  @param newLeftChild  A node that will be the left child. */
    public void setLeftChild(TernaryNode<T> newLeftChild) {
        children[0] = newLeftChild;
    }

    /** Detects whether this node has a left child.
     *  @return  True if the node has a left child. */
    public boolean hasLeftChild() {
        return children[0]!= null;
    }
//////////////////////////////////////////////////////////////////////

     public TernaryNode<T> getMiddleChild() {
        return children[1];
    }

    /** Sets this nodeâ€™s middle child to a given node.
     *  @param newMiddleChild  A node that will be the middle child. */
    public void setMiddleChild(TernaryNode<T> newMiddleChild) {
        children[1] = newMiddleChild;
    }

    /** Detects whether this node has a middle child.
     *  @return  True if the node has a middle child. */
    public boolean hasMiddleChild() {
        return children[1]!= null;
    }


    //////////////////////////////////////////////////////////////////////

     /** Retrieves the right child of this node.
     *  @return  The nodeâ€™s right child. */
    public TernaryNode<T> getRightChild() {
        return children[2];
    }

    /** Sets this nodeâ€™s right child to a given node.
     *  @param newRightChild  A node that will be the right child. */
    public void setRightChild(TernaryNode<T> newRightChild) {
       children[2] = newRightChild;
    }

    /** Detects whether this node has a right child.
     *  @return  True if the node has a right child. */
    public boolean hasRightChild() {
        return children[2] != null;
        //hello how are you 
    }



//////////////////////////////////////////////////////////////////////



    /** Detects whether this node is a leaf.
     *  @return  True if the node is a leaf. */
    public boolean isLeaf() {
        return (children[0] == null) && (children[1] == null) &&(children[2]== null);
    }


  public int getNumberOfNodes() {
        int leftNumber = 0;
        int middleNumber = 0;
        int rightNumber = 0;


        if (children[0] != null) {
            leftNumber = children[0].getNumberOfNodes();
        }

        if(children[1] != null)
            middleNumber = children[1].getNumberOfNodes();

        if (children[2] != null) {
            rightNumber = children[2].getNumberOfNodes();
        }

        return 1 + leftNumber + middleNumber + rightNumber;
    }

    /** Computes the height of the subtree rooted at this node.
     *  @return  The height of the subtree rooted at this node. */
    public int getHeight() {
        return getHeight(this); // Call private getHeight
    }

    private int getHeight(TernaryNode<T> node) {
        int height = 0;
        int max = 0;

        if (node != null)
        {
            int tempMax = Math.max(getHeight(node.getLeftChild()) , getHeight(node.getMiddleChild()));
            max = Math.max(tempMax, getHeight(node.getRightChild()));
            height = 1+ max;
        }
        return height;
    }

    public TernaryNode<T> copy() {
        TernaryNode<T> newRoot = new TernaryNode<>(data);

        if (children[0] != null) {
            newRoot.setLeftChild(children[0].copy());
        }

        if(children[1] !=null){
            newRoot.setMiddleChild((children[1]).copy());
        }

        if (children[2] != null) {
            newRoot.setRightChild(children[2].copy());
        }

        return newRoot;
    }

      public int getNextChild(){

        if(this.placement == 3 || this.placement == 0)
            return 5;
        else
        {
            return this.placement +1;
        }

    }


}