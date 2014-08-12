/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.logic;

import by.epam.task02.entity.WordEntity;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Helena.Grouk
 */
public abstract class WordEntityComparator implements Comparator<WordEntity>{
    
    public static Comparator<WordEntity> firstCharComparator  
            = (WordEntity word1, WordEntity word2) -> {
        Character c1 = word1.getWord().charAt(0);
        Character c2 = word2.getWord().charAt(0);
        return c1.compareTo(c2);
    };
    
    public static Comparator<WordEntity> stringCharsComparator  
            = (WordEntity word1, WordEntity word2) -> {
        int l1 = word1.getWord().length();
        int l2 = word2.getWord().length();
        int v1 = searchVowels(word1.getWord());
        int v2 = searchVowels(word2.getWord());
        Float f1 = (float)v1/l1;
        Float f2 = (float)v2/l2;
        return f1.compareTo(f2);
    };

    private static int searchVowels(String str) {
        String reg1 = "[aeyuio]";
        Matcher mat = Pattern.compile(reg1).matcher(str);
        int n = 0;
        while (mat.find()) {
            n++;
        }
        return n;
    }
    
}
