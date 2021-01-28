package com.tyfann.redis2021;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * @author tyfann
 * @date 2021/1/27 4:52 下午
 */
public class TrieTest {
    public static void main(String[] args) {
        File file = new File("nodelist.txt");
        StringArray textArray = new StringArray();
        if (file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    textArray.add(text);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        RedisTrie trie = new RedisTrie();
        for (int i =0; i <50; i++){
            trie.insert(textArray.get(i));
        }


//        trie.insert("/home/get/father/name");
//        trie.insert("/home/get/mother/name");
//        trie.insert("/home/get/child/name");
//        trie.insert("/class/get/student/name");
//        trie.insert("/class/add/student/name");
//        trie.insert("/home/get/{id}");

        HashSet<String> trieSet;

        // 对于不存在的语句，返回null
        trieSet = trie.getWordsForPrefix("/home/add");
        System.out.println("包含/home/add（包括本身）前缀的单词：");

        if (trieSet == null){
            System.out.println("无字符匹配");
        }else {
            for (String key : trieSet){
                System.out.println(key);
            }
        }

        // 处理 /yljgs/{jgbh} 语句
        boolean getOrNot = trie.isExist("/yljgs/10086");
        System.out.println("\n是否包含/yljgs/10086语句：");

        if (!getOrNot){
            System.out.println("否");
        }else {
            System.out.println("是");
        }


        // 处理 /sign/** 语句
        trieSet = trie.getWordsForPrefix("/sign");
        System.out.println("\n包含/sign（包括本身）前缀的单词：");

        if (trieSet == null){
            System.out.println("无字符匹配");
        }else {
            for (String key : trieSet){
                System.out.println(key);
            }
        }


    }
}
