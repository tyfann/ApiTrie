package com.tyfann.redis2021;

import java.util.HashSet;

/**
 * @author tyfann
 * @date 2021/1/27 3:47 下午
 */
public class RedisTrie {



    private class TrieNode{

        private TrieNode[] childs;
        // 是否为叶子节点
        private boolean isLeaf;
        // 是否是类似 {id} 这样的表达式
        private boolean isRegular;
        // 是否为模糊匹配
        private boolean isWild;

        String part;
        String path;

        public TrieNode(String part,String path) {
            this.part = part;
            this.path = path;
            this.isLeaf = false;
            this.isWild = false;
            this.isRegular = false;
            this.childs = null;
        }

        public int query(String childNode) {
            if (this.childs == null){
                return -1;
            }
            for (int i =0;i< this.childs.length;i++){
                if (this.childs[i].part.equals(childNode)){
                    return i;
                }
            }
            return -1;
        }

        public boolean containRegular(){
            for (int i =0;i< this.childs.length;i++){
                if (this.childs[i].isRegular){
                    return true;
                }
            }
            return false;
        }
    }

    private TrieNode root;

    public RedisTrie() {
        root = new TrieNode(null,null);
    }

    /**
     * 插入字串，用循环取代迭代实现
     * @param path
     */
    public void insert(String path){
        insert(this.root,path);
    }


    /**
     * 插入字串，用循环取代迭代实现
     * @param root
     * @param path
     */
    private void insert(TrieNode root,String path){
        String[] wordsArr = path.split("\\/");
        String[] notNullArr = new String[wordsArr.length-1];
        for (int i =1;i<wordsArr.length;i++){
            notNullArr[i-1] = wordsArr[i];
        }

        for(int i=0,length=notNullArr.length; i<length; i++){

            int index = root.query(notNullArr[i]);
            if(index>=0){
            }else{
                ///如果不存在
                if (root.childs == null) {
                    root.childs = new TrieNode[1];
                }else {
                    TrieNode[] temp = root.childs;
                    root.childs = new TrieNode[root.childs.length+1];
                    for (int j =0;j<temp.length;j++){
                        root.childs[j] = temp[j];
                    }
                }

                String localPath = null;
                for (int j =0;j<=i;j++){
                    if (localPath == null) {
                        localPath = "/";
                        localPath = localPath + notNullArr[j];
                    }else {
                        localPath = localPath + "/";
                        localPath = localPath + notNullArr[j];
                    }
                }

                root.childs[root.childs.length-1] = new TrieNode(notNullArr[i],localPath);
                if (notNullArr[i].contains("{") && notNullArr[i].contains("}")){
                    root.childs[root.childs.length-1].isRegular = true;
                }
            }

            ///如果到了字串结尾，则做标记
            if(i == length-1){
                int tailIndex = root.query(notNullArr[i]);
                root.childs[tailIndex].isLeaf=true;
            }
            // root指向子节点，继续处理
            if (index>=0){
                root = root.childs[index];
            } else {
                root = root.childs[root.childs.length-1];
            }
        }

    }




    /**
     * 前序遍历
     * @param root		子树根节点
     * @param prefixs	查询到该节点前所遍历过的前缀
     * @return
     */
    private  HashSet<String> preTraversal(TrieNode root,String prefixs){
        HashSet<String> prefixSet=new HashSet<String>();
        if(root!=null){

            if(root.isLeaf == true){
                //当前即为一个单词
                prefixSet.add(root.path);
            }

            if (root.childs != null){
                for(int i=0,length=root.childs.length; i<length;i++){
                    String prefixPath = prefixs + "/" + root.childs[i].part;
                    prefixSet.addAll(preTraversal(root.childs[i],prefixPath));

                }
            }
        }

        return prefixSet;
    }


    /**
     * 判断某字串是否在字典树中
     * @param word
     * @return true if exists ,otherwise  false
     */
    public boolean isExist(String word){
        return search(this.root, word);
    }
    /**
     * 查询某字串是否在字典树中
     * @param word
     * @return true if exists ,otherwise  false
     */
    private boolean search(TrieNode root,String word){
        String[] wordsArr = word.split("\\/");
        String[] notNullArr = new String[wordsArr.length-1];
        for (int i =1;i<wordsArr.length;i++){
            notNullArr[i-1] = wordsArr[i];
        }

        for(int i=0,length=notNullArr.length; i<length;i++){

            int index = root.query(notNullArr[i]);
            if(root.query(notNullArr[i]) < 0){
                if (!root.containRegular()){
                    return false;
                } else {
                    return true;
                }
            }
            root=root.childs[index];
        }

        return true;
    }


    /**
     * 得到以某字串为前缀的字串集，包括字串本身！ 类似单词输入法的联想功能
     * @param prefix 字串前缀
     * @return 字串集以及出现次数，如果不存在则返回null
     */
    public HashSet<String> getWordsForPrefix(String prefix){
        return getWordsForPrefix(this.root, prefix);
    }
    /**
     * 得到以某字串为前缀的字串集，包括字串本身！
     * @param root
     * @param prefix
     * @return 字串集以及出现次数
     */
    private HashSet<String> getWordsForPrefix(TrieNode root, String prefix){
        String[] wordsArr = prefix.split("\\/");
        String[] notNullArr = new String[wordsArr.length-1];
        for (int i =1;i<wordsArr.length;i++){
            notNullArr[i-1] = wordsArr[i];
        }


        for(int i=0, length=notNullArr.length; i<length; i++){

            int index = root.query(notNullArr[i]);
            if (index < 0){
                return null;
            }
            root=root.childs[index];

        }
        ///结果包括该前缀本身
        ///此处利用之前的前序搜索方法进行搜索
        return preTraversal(root, prefix);
    }


}
