/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.entity;

import by.epam.task02.exception.InitException;
import java.util.Objects;

/**
 *
 * @author Helena.Grouk
 */
public final class WordEntity extends TextEntity 
    implements Comparable<WordEntity> { //текстовый элемент слово
    
    private String word;
    
    public WordEntity(){}
    
    public WordEntity(String word) throws InitException {
        setWord(word);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WordEntity w = (WordEntity)obj;
        return this.word.equals(w.word);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.word);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(getClass().getName());
        str.append('@');
        str.append("word: ");
        str.append(word);
        return str.toString();
    }
    
    @Override
    public int compareTo(WordEntity obj) {
        if (obj == null ) {
            throw new NullPointerException();
        }
        String word1 = obj.getWord().toLowerCase();
        String word2 = this.getWord().toLowerCase();
        return word2.compareTo(word1);
    }
    
    public void setWord(String word) throws InitException {
            if (word == null || word.isEmpty()) {
                throw new InitException("word is null or empty.");
            }
            this.word = word;
    }
    
    public String getWord() {
        return this.word;
    }
}
