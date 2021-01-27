package com.tyfann.redis2021;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tyfann
 * @date 2021/1/27 3:47 下午
 */
public class RedisTrie {
    private class TrieNode{
        private int prefix_num;

        private TrieNode childs[];
        private boolean isLeaf;
        public TrieNode(){
            prefix_num = 0;
            isLeaf = false;
            childs = new TrieNode[128];
        }
    }

    private TrieNode root;

    public RedisTrie() {
        root = new TrieNode();
    }

    /**
     * 插入字串，用循环取代迭代实现
     * @param words
     */
    public void insert(String words){
        insert(this.root,words);
    }


    /**
     * 插入字串，用循环取代迭代实现
     * @param root
     * @param words
     */
    private void insert(TrieNode root,String words){
        words=words.toLowerCase();
        char[] chrs=words.toCharArray();

        for(int i=0,length=chrs.length; i<length; i++){
            ///用ASCII码来作为下标索引
            int index = chrs[i];
            if(root.childs[index]!=null){
                //已经存在了，该子节点prefix_num++
            }else{
                ///如果不存在
                root.childs[index]=new TrieNode();
            }
            root.childs[index].prefix_num++;

            ///如果到了字串结尾，则做标记
            if(i == length-1){
                root.childs[index].isLeaf=true;
            }
            ///root指向子节点，继续处理
            root=root.childs[index];
        }

    }




    /**
     * 遍历Trie树，查找所有的words以及出现次数
     * @return HashMap<String, Integer> map
     */
    public HashSet<String> getAllWords(){
//		HashMap<String, Integer> map=new HashMap<String, Integer>();

        return preTraversal(this.root, "");
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

            if(root.isLeaf==true){
                //当前即为一个单词
                prefixSet.add(prefixs);
            }

            for(int i=0,length=root.childs.length; i<length;i++){
                if(root.childs[i]!=null){
                    char ch=(char) (i);
                    //递归调用前序遍历

                    String tempStr=prefixs+ch;
                    prefixSet.addAll(preTraversal(root.childs[i], tempStr));
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
        char[] chs=word.toLowerCase().toCharArray();
        for(int i=0,length=chs.length; i<length;i++){
            int index=chs[i];
            if(root.childs[index]==null){
                ///如果不存在，则查找失败
                return false;
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
        char[] chrs=prefix.toLowerCase().toCharArray();

        for(int i=0, length=chrs.length; i<length; i++){

            int index=chrs[i];
            if(root.childs[index]==null){
                return null;
            }

            root=root.childs[index];

        }
        ///结果包括该前缀本身
        ///此处利用之前的前序搜索方法进行搜索
        return preTraversal(root, prefix);
    }


}
