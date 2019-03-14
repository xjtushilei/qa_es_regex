package com.example.demo.service;


import com.example.demo.entity.Dependency;
import com.example.demo.repository.DependencyRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.max;

@Service
public class DependencyService {

    private final Logger logger = LoggerFactory.getLogger(DependencyService.class);

    private static ArrayList<String> topics = new ArrayList<>();

    static {
        // 用来去重
        HashSet<String> topicsSet = new HashSet<>();
        List res = new ArrayList<>();
        try {
            res = FileUtils.readLines(ResourceUtils.getFile("classpath:data/dependency.csv"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object re : res) {
            String[] t = re.toString().split(",");
            topicsSet.add(t[1].trim());
            topicsSet.add(t[2].trim());
        }
        topics.addAll(topicsSet);
        // 按照文字长度排序
        topics.sort(Comparator.comparingInt(String::length));
    }

    public static void main(String[] args) {
        new DependencyService().getAnswerWithDependency("学习二叉树之前需要学什么");
    }

    @Autowired
    DependencyRepository dependencyRepository;


    public String getAnswerWithDependency(String input) {
        HashMap<String, String> map = regex(input);
        if (map.get("topic") != null) {
            logger.debug("regex匹配成功：" + map);
            for (String topic : topics) {
                if (getLCS(topic, map.get("topic"))) {
                    logger.debug("LCS匹配成功，topic：" + topic);
                    //获取之后学习什么
                    if (map.get("direction").equals("after")) {
                        List<Dependency> dependencies = dependencyRepository.findByStartTopic(topic);
                        if (dependencies.size() == 0) {
                            return null;
                        }
                        ArrayList<String> topicList = new ArrayList<>();
                        for (Dependency dependency : dependencies) {
                            topicList.add(dependency.getEndTopic());
                        }
                        return topicList.toString();
                    } else {
                        List<Dependency> dependencies = dependencyRepository.findByEndTopic(topic);
                        ArrayList<String> topicList = new ArrayList<>();
                        for (Dependency dependency : dependencies) {
                            topicList.add(dependency.getStartTopic());
                        }
                        if (dependencies.size() == 0) {
                            return null;
                        }
                        return topicList.toString();
                    }
                }
            }
        }

        return null;
    }


    public static HashMap<String, String> regex(String input) {
        HashMap<String, String> res = new HashMap<>();

        String regEx = "学[了习]*(.*)(后|后面|然后|接着)+(需要)*(学习什么|学什么|学.*|怎么).*"; //学了二叉树后怎么学习
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(input);
        if (m.find()) {
            res.put("direction", "after");
            res.put("topic", m.group(1));
            res.put("regEx", regEx);
            return res;
        }

        regEx = "学[习]*(.*)(之前|前面|接着|前)+(需要)*(学习什么|学什么|学.*|怎么).*"; //学习二叉树之前学习什么
        p = Pattern.compile(regEx);
        m = p.matcher(input);
        if (m.find()) {
            res.put("direction", "before");
            res.put("topic", m.group(1));
            res.put("regEx", regEx);
            return res;
        }

        regEx = "学[习]*(完|结束)*(.*)(需要)*(学习什么|学什么|学啥|怎么.*).*"; //学完二叉树学什么
        p = Pattern.compile(regEx);
        m = p.matcher(input);
        if (m.find()) {
            res.put("direction", "after");
            res.put("topic", m.group(2));
            res.put("regEx", regEx);
            return res;
        }
        return res;
    }


    /**
     * 输入已知topic和正则表达式匹配到的topic
     * 获得最长公共子序列。返回bool时候，如果最长公共子序列和原来的值比较长度相似度，则粗略的认为认为是这个topic
     */
    public static boolean getLCS(String x, String y) {

        char[] s1 = x.toCharArray();
        char[] s2 = y.toCharArray();
        int[][] array = new int[x.length() + 1][y.length() + 1];//此处的棋盘长度要比字符串长度多加1，需要多存储一行0和一列0

        for (int j = 0; j < array[0].length; j++) {//第0行第j列全部赋值为0
            array[0][j] = 0;
        }
        for (int i = 0; i < array.length; i++) {//第i行，第0列全部为0
            array[i][0] = 0;
        }

        for (int m = 1; m < array.length; m++) {//利用动态规划将数组赋满值
            for (int n = 1; n < array[m].length; n++) {
                if (s1[m - 1] == s2[n - 1]) {
                    array[m][n] = array[m - 1][n - 1] + 1;//动态规划公式一
                } else {
                    array[m][n] = max(array[m - 1][n], array[m][n - 1]);//动态规划公式二
                }
            }
        }
        Stack stack = new Stack();
        int i = x.length() - 1;
        int j = y.length() - 1;

        while ((i >= 0) && (j >= 0)) {
            if (s1[i] == s2[j]) {//字符串从后开始遍历，如若相等，则存入栈中
                stack.push(s1[i]);
                i--;
                j--;
            } else {
                if (array[i + 1][j] > array[i][j + 1]) {//如果字符串的字符不同，则在数组中找相同的字符，注意：数组的行列要比字符串中字符的个数大1，因此i和j要各加1
                    j--;
                } else {
                    i--;
                }
            }
        }
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {//打印输出栈正好是正向输出最大的公共子序列
            res.append(stack.pop());
        }
        // 如果最长公共子序列和原来的值比较大于某个概率，则粗略的认为认为是这个topic
        return Math.abs((res.length() - x.length()) / (float) x.length()) < 0.2 || Math.abs((res.length() - y.length()) / (float) y.length()) < 0.2;
    }

}
