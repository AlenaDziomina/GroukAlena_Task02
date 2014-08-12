/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.logic;

import by.epam.task02.entity.*;
import by.epam.task02.exception.ProjectException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Helena.Grouk
 */
public class SearchTextEntity { //поиск элементов текста по их классу

    public enum ETYPE {WORD, STRING, BLOCK, PUNCT, NUM, MARK}
    
    public static Set findEntitys(TextEntity block, ETYPE type) 
            throws ProjectException { //поиск слов в тексте
        
        if (block == null) {
            throw new ProjectException("Can't find in block == null.");
        }
        
        Set set = new TreeSet<>();
        
        switch (type) {
            case WORD: wordSearching(set, block); break;
            case STRING: stringSearching(set, block); break;
            case PUNCT: punctSearching(set, block); break;
            case MARK: markSearching(set, block); break;
            case NUM: numSearching(set, block); break;
        }
        
        return set;
    }
    
    private static void wordSearching(Set set, TextEntity block) {
        //поиск слов в блоке
        
        if (WordEntity.class.isInstance(block)) {
            set.add(block);   
        } else if (BlockEntity.class.isInstance(block)) {
            List<TextEntity> entity = ((BlockEntity)block).getTextEntity();
            entity.stream().forEach((ent) -> {
                wordSearching(set, ent);
            });
        }
    }
    
    private static void stringSearching(Set set, TextEntity block) {
        //поиск предложений в блоке
        
         if (StringEntity.class.isInstance(block)) {
            set.add(block);
        } else if (BlockEntity.class.isInstance(block)) {
            List<TextEntity> entity = ((BlockEntity)block).getTextEntity();
            entity.stream().forEach((ent) -> {
                 stringSearching(set, ent);
             });
        }
    }
    
    private static void punctSearching(Set set, TextEntity block) {
        //поиск пунктуации в блоке
        
         if (PunctEntity.class.isInstance(block)) {
            set.add(block);
        } else if (BlockEntity.class.isInstance(block)) {
            List<TextEntity> entity = ((BlockEntity)block).getTextEntity();
            entity.stream().forEach((ent) -> {
                 punctSearching(set, ent);
             });
        }
    }

    private static void markSearching(Set set, TextEntity block) {
        //поиск пробельных символов в блоке
        
         if (MarkEntity.class.isInstance(block)) {
            set.add(block);
        } else if (BlockEntity.class.isInstance(block)) {
            List<TextEntity> entity = ((BlockEntity)block).getTextEntity();
            entity.stream().forEach((ent) -> {
                 markSearching(set, ent);
             });
        }
    }

    private static void numSearching(Set set, TextEntity block) {
        //поиск цифр в блоке
        
         if (NumEntity.class.isInstance(block)) {
            set.add(block);
        } else if (BlockEntity.class.isInstance(block)) {
            List<TextEntity> entity = ((BlockEntity)block).getTextEntity();
            entity.stream().forEach((ent) -> {
                 numSearching(set, ent);
             });
        }
    }
    
}
