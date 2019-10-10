package com.company;

public class SearchTree implements NodeList {

    private ListItem root = null;

    public SearchTree(ListItem root) {
        this.root = root;
    }

    @Override
    public ListItem getRoot() {
        return this.root;
    }

    @Override
    public boolean addItem(ListItem newItem) {
        if (this.root == null) {
            //The tree was empty, so our item becomes the head of the tree
            this.root = newItem;
            return true;
        }

        //otherwise, start comparing from the head of the tree
        ListItem currentItem = this.root;
        while (currentItem != null) {
            int comparison = (currentItem.compareTo(newItem));
            if (comparison < 0) {
                //new item is greater, move right if possible
                if (currentItem.next() != null) {
                    currentItem = currentItem.next();
                } else {
                    // there is no node to the right, so add at this point
                    currentItem.setNext(newItem);
                    return true;
                }
            } else if (comparison > 0) {
                //new item is less, move left if possible
                if (currentItem.previous() != null) {
                    currentItem = currentItem.previous();
                } else {
                    //there is no node to the left, so add at this point
                    currentItem.setPrevious(newItem);
                    return true;
                }
            } else {
                //equal, so don't add
                System.out.println(newItem.getValue() + " is already present");
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean removeItem(ListItem item) {
        if (item != null) {
            System.out.println("Deleting item " + item.getValue());
        }
        ListItem currentItem = this.root;
        ListItem parentItem = currentItem;

        while (currentItem != null) {
            int comparison = (currentItem.compareTo(item));
            if (comparison < 0) {
                parentItem = currentItem;
                currentItem = currentItem.next();
            } else if (comparison > 0) {
                parentItem = currentItem;
                currentItem = currentItem.previous();
            } else {
                //equal: we've found the item, so remove it
                performRemoval(currentItem, parentItem);
                return true;
            }
        }
        return false;
    }

    private void performRemoval(ListItem item, ListItem parent) {
        //remove item from the tree
        if (item.next() == null) {
            //no right tree, so make parent point to left tree (which may be null)
            if (parent.next() == item) {
                //item is right child of its parent
                parent.setNext(item.previous());
            } else if (parent.previous() == item){
                //item is left child of its parent
                parent.setPrevious(item.previous());
            } else {
                //parent must be item, the root of the tree
                this.root = item.previous();
            }
        } else if (item.previous() == null) {
            //no left tree, so make parent point to the right tree (which may be null)
            if (parent.next() == item) {
                //item is right child of its parent
                parent.setNext(item.next());
            } else if (parent.previous() == item) {
                //item is left child of its parent
                parent.setPrevious(item.next());
            } else {
                //again, we are deleting the root
                this.root = item.next();
            }
        } else {
            //neither is null, from the right sub-tree, find smallest (leftmost) value
            ListItem current = item.next();
            ListItem leftmostParent = item;
            while (current.previous() != null) {
                leftmostParent = current;
                current = current.previous();
            }
            //put the smallest value into our node to be deleted
            item.setValue(current.getValue());
            //delete the smallest
            if (leftmostParent == item) {
                item.setNext(current.next());
            } else {
                /*set the smallest node's parent to point to the
                smallest node's right child (which may be null*/
                leftmostParent.setPrevious(current.next());
            }
        }
    }

    @Override
    public void traverse(ListItem root) {
        //recursive method
        if (root != null) {
            traverse(root.previous());
            System.out.println(root.getValue());
            traverse(root.next());
        }
    }
}
