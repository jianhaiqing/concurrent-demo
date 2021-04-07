package com.seewo.service;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// 测试方法
public class ForkJoinPoolLearn {
    public final static String CONTENT =
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？" +
            "哇，好帅哟！哇，是啊，我好喜欢呢！哇，习伯伯, 可否给个签名呢？";
    public static final int THRESHHOLD = 5;
    public static List<String> BLACK_WORDS = new ArrayList<>();

    static {
        BLACK_WORDS.add("哇");
        BLACK_WORDS.add("习伯伯");
    }

    public static void main(String[] args) {
        //使用ForkJoinPool来执行任务
        // 有返回值对象
        System.out.println("即将测试有返回值对象。。。");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyRecursiveTask myRecursiveTask = new MyRecursiveTask(0, ForkJoinPoolLearn.CONTENT.length(), Arrays.asList(ForkJoinPoolLearn.CONTENT.split("")));
        Integer value = forkJoinPool.invoke(myRecursiveTask);
        System.out.println(String.format("字符串：%s 中包含\n违禁词数量：%s,违禁词：%s", CONTENT, value, StringUtils.join(BLACK_WORDS, ",")));
    }
}

/**
 * 提交任务类
 */

class MyRecursiveTask extends RecursiveTask<Integer> {

    private int startIndex;
    private int endIndex;
    private List<String> words;

    public MyRecursiveTask(int startIndex, int endIndex, List<String> words) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.words = words;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((endIndex - startIndex) <= ForkJoinPoolLearn.THRESHHOLD) {
            // 如果长度不可再分割，则开始做过滤
            for (int i = startIndex; i < words.size() && i < endIndex; i++) {
                String word = words.get(i);
                if (ForkJoinPoolLearn.BLACK_WORDS.contains(word)) {
                    sum += 1;
                }
            }
        } else {// 如果长度过长，fork为两个任务来处理
            int middle = (startIndex + endIndex) / 2;
            MyRecursiveTask left = new MyRecursiveTask(startIndex, middle, words);
            MyRecursiveTask right = new MyRecursiveTask(middle, endIndex, words);
            left.fork();
            right.fork();
            Integer leftValue = left.join();
            Integer rightValue = right.join();
            sum = leftValue + rightValue;
        }
        // 返回计算后的值
        return sum;
    }
}
