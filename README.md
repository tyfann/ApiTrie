# Trie进阶（应用于API路由）

字符种类：26个大写字母, 26个小写字母, "/", "*", '{', "}"
共计：26+26+4 = 56

对应ASCII码（十进制）：
"/" : 47
"*": 42
"{": 123
"}": 125
"A"-"Z": 65-90
"a"-"z": 97-122

## version 1.0

Trie树构造方案：用ASCII码区分不同的字符，同时不考虑空间开销，每一个Trie树节点都有128个（有可能）的子节点

### 目前进度：

完成基础的类似 "/mock/getNewsList" 这样的字符串匹配

尚未完成类似 "/api/jmZjjcsjs/{id}/info" 这样带有"/{id}/" 的字符串的匹配处理

getWordsForPrefix()函数通过输入的一个前缀字符串，返回一个HashSet包含了所有包含前缀字符串的子串

## Version 1.1

Trie树构造方案：将诸如"/mock/getNewsList/info"分割为mock $\rightarrow$ getNewsList $\rightarrow$ info的形式，不对两个'/'之间的字符做分割

难点：如何将子节点（即具体的字符串而非char）进行编码

### 目前进度：

完成基础的类似 "/mock/getNewsList" 这样的字符串匹配

完成类似 "/api/jmZjjcsjs/{id}/info" 这样带有"/{id}/" 的字符串的匹配处理

getWordsForPrefix()函数通过输入的一个前缀字符串，返回一个HashSet包含了所有包含前缀字符串的子串（类比模糊匹配）
