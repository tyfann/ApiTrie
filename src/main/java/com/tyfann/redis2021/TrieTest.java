package com.tyfann.redis2021;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
        for (int i =0; i <20; i++){
            trie.insert(textArray.get(i));
        }

        HashSet<String> trieSet;

        trieSet = trie.getWordsForPrefix("/get");
        System.out.println("\n\n包含/get（包括本身）前缀的单词及出现次数：");

        for (String key : trieSet){
            System.out.println(key);
        }

        if (!trie.isExist("/getAllName")) {
            System.out.println("\n\n字典树中不存在：/getAllName ");
        }


    }
}
