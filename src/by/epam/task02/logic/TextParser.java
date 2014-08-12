/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.logic;

import by.epam.task02.entity.*;
import by.epam.task02.exception.*;
import static by.epam.task02.main.Task02.localLog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Helena.Grouk
 */
public class TextParser {
    
    private static final String[] REGULEX = {"^\\d+\\.[\\t ]+[A-Z][\\S ]+\\n*$",
                                        "^\\d\\.\\d[\\t ]+[A-Z][\\S ]+\\n*$",
                                        "^[A-Z].+\\n*$"
    };

    public static TextEntity textParsing(String fileName)
            throws LogicException, ProjectException { //анализ текста
        
        StringBuilder sb;
        sb = readFileToString(fileName);
        try {
            return parseText(sb.toString(), 0);
        } catch (NullInitException ex) {
            throw new LogicException("Error in parsing text "
                    + "recursion.", ex);
        }
    }
    
    private static TextEntity parseText(String str, int i)
            throws LogicException, NullInitException {
        //разбиение текста по параграфам и блокам текста/листинга
        
        if (i >= REGULEX.length) {
            return reorgBlock(str); //парсим блоки кода
        }
        BlockEntity block = new BlockEntity(); //текст, параграф, подпараграф
        if (str == null || str.isEmpty())
        {
            throw new NullInitException("ReorgText: string is null or empty.");
        }
        Pattern pat = Pattern.compile(REGULEX[i], Pattern.MULTILINE);
        Matcher mat = pat.matcher(str);
        
        int st = 0;
        while (mat.find()) {        
            if (st < mat.start()) {
                //парсим содержимое параграфов, подпараграфов
                block.setTextEntity(parseText(str.substring(st, mat.start()),
                        i+1));
            }
            try {
                //парсим названия параграфов и подпараграфов, , абзацы текста
                block.setTextEntity(parseParagraph(mat.group()));
            } catch (NullInitException ex) {
                localLog.error("Parsing paragraph string was interrupted.");
            }
            st = mat.end();
        }
        if (st < str.length())
        {
            block.setTextEntity(parseText(str.substring(st, str.length()),
                    i+1));
        }
        return block;
    }

    private static TextEntity parseParagraph(String str) {
        //разбиение абзаца текста на предложения
        
        String reg = "[.?!]+\\n?";
        Matcher mat = Pattern.compile(reg).matcher(str);
        BlockEntity block = new BlockEntity(); //абзац
        int st = 0; //начало предложения
        while (mat.find()) {
            //преверяем, является ли знак препинания концом предложения
            if (checkParagraph(mat.start(), mat.end(), str)) {
                try {
                    block.setTextEntity(parseSentence(
                            str.substring(st, mat.end())));
                } catch (NullInitException ex) {
                    localLog.error("Parsing paragraph string was interrupted.");
                }
                st = mat.end();
            } 
        }
        //оставшиеся части абзаца также парсятся на слова и препинание
        if (st < str.length()) {
            try {
                block.setTextEntity(parseSentence(
                        str.substring(st, str.length())));
            } catch (NullInitException ex) {
                localLog.error("Parsing paragraph string was interrupted.");
            }
        }
        return block;
    }
    
    private static StringEntity parseSentence(String str){
        //выделяем блок как предложение 
        StringEntity stringEntity = new StringEntity();
        try {
            //парсим предложение по пунктуации и др.
            parsePunct(str, stringEntity); 
        } catch (LogicException ex) {
            localLog.error("Parsing sentence was interrupted.");
        }
        return stringEntity;
    }
    
    private static void parsePunct(String str, BlockEntity block) 
            throws LogicException {
        //выделяем знаки препинания и матем. символы
        
        String reg = "[\\p{Punct}]+";
        Matcher mat = Pattern.compile(reg).matcher(str);
        int st = 0;
        while (mat.find()){
            if (st < mat.start()) {
                String str1 = str.substring(st, mat.start());
                //подстроку между пунктуацией парсим на пробельные символы и др.
                parseSpaceMark(str1, block); 
            }
            //пунктуацию посимвольно заносим в структуру
            char[] punct = mat.group().toCharArray();
            for (char p : punct) {
                try {
                    block.setTextEntity(new PunctEntity(p));    
                } catch (NullInitException ex) {
                    throw new LogicException("Can't set punctEntity == "
                            + "null", ex);
                }
            }
            st = mat.end();
        }
        if (st < str.length()) {
            parseSpaceMark(str.substring(st, str.length()), block);
        }
    }
    
    private static void parseSpaceMark(String str, BlockEntity block) 
            throws LogicException {
        //парсим по пробельным символам
        
        String reg = "\\s+";
        Matcher mat = Pattern.compile(reg).matcher(str); 
        int st = 0;
        while (mat.find()){
            if (st < mat.start()) {
                String str1 = str.substring(st, mat.start());
                //подстроку между пробельными символами парсим на цифры
                parseNum(str1, block); 
            }
            //пробельные символы заносим в структуру
            char[] mark = mat.group().toCharArray();
            for (char m : mark) {
                try {    
                    block.setTextEntity(new MarkEntity(m));
                } catch (NullInitException ex) {
                    throw new LogicException("Can't set markEntity == "
                            + "null", ex);
                }
            }
            st = mat.end();
        }
        if (st < str.length()) {
            parseNum(str.substring(st, str.length()), block);
        }
    }
    
    private static void parseNum(String str, BlockEntity block) 
            throws LogicException {
        //парсим по цифрам
        
        String reg = "\\d+";
        Matcher mat = Pattern.compile(reg).matcher(str);
        int st = 0;
        while (mat.find()){         
            if (st < mat.start()) {
                String str1 = str.substring(st, mat.start());
                try {
                    //оставшуюся подстроку как слово заносим в структуру
                    block.setTextEntity(new WordEntity(str1)); 
                } catch (NullInitException ex) {
                    throw new LogicException("Can't set wordEntity == null or "
                            + "empty", ex);
                }
            }     
            //цифры заносим в структуру
            char[] num = mat.group().toCharArray();
            for (char n : num) {
                try {    
                    block.setTextEntity(new NumEntity(n));
                } catch (NullInitException ex) {
                    throw new LogicException("Can't set numEntity == null", ex);
                }
            }
            st = mat.end();
        }
        if (st < str.length()) {
            String str1 = str.substring(st, str.length());
            try {
                block.setTextEntity(new WordEntity(str1));
            } catch (NullInitException ex) {
                throw new LogicException("Can't set wordEntity == null or "
                        + "empty", ex);
            }
        }
    }
    
    private static boolean checkParagraph(int start, int end, String str) {
        //проверка точки, является ли она концом предложения
        
        if (end + 1 < str.length()) {
            String str2 = str.substring(end, end + 2);
            if (! str2.matches(" [A-Z]")) {
                return false;
            }
        }
        return true;
    }
    
     private static TextEntity reorgBlock(String str) throws LogicException {
        //разбиение блока листинга на компоненты
        
        String reg = "^.*\\n";
        Matcher mat = Pattern.compile(reg, Pattern.MULTILINE).matcher(str);
        List<String> list = new ArrayList<>();
        //перерабатываем листинг в массив строк
        while (mat.find()) {
            list.add(mat.group());
        }
        //парсим массив строк с учетом кодовой пунктуации
        BlockEntity bl = parseCode(list);
        return bl;
    }
     
    private static BlockEntity parseCode(List<String> list) 
            throws LogicException {
        //парсим код с учетом пунктуации (фигурные скобки)
        
        BlockEntity block = new BlockEntity();
        Iterator it = list.iterator();
        boolean returned = false; //флаг возврата из рекурсии
        while (it.hasNext()) {
            String str = (String)it.next();
            //если только что не было возврата из рекурсии и находим символ }
            if (!returned && str.contains("}")) { 
                return block;
            }
            returned = false; //обнуляем флаг возврата
            try {
                block.setTextEntity(parseCodeString(str)); //парсим строку кода текущего уровня по пунктуации, словам и др.
            } catch (NullInitException ex) {
                localLog.error("Parsing code string was interrupted.");
            }
            it.remove();
            if (str.contains("{")) { 
                
                try {
                    //если строка содержит символ {, то рекурсивно обрабатываем следующие строки
                    block.setTextEntity(parseCode(list));
                } catch (NullInitException ex) {
                    throw new LogicException("Error in parsing code recursion.", ex);
                }
                it = list.iterator(); //переинициализация итератора по возвращению из рекурсии
                returned = true; //выставляем флаг возврата
            }
        }
        return block;       
    }
    
    private static BlockEntity parseCodeString(String str) {
        //парсим строку кода как блок по пунктуации, словам и др.
        
        BlockEntity block = new BlockEntity();
        try {
            parsePunct(str, block); //парсим по пунктуации и др.
        } catch (LogicException ex) {
            localLog.error("Parsing code string was interrupted.");
        }
        return block; 
    }
    
    public static String compileText(TextEntity block){
        //обратно компануем текст
        
        StringBuilder str = new StringBuilder();
        
        if (BlockEntity.class.isInstance(block)) {
            List<TextEntity> entity = ((BlockEntity)block).getTextEntity();
            for (TextEntity ent : entity) {
                str.append(compileText(ent));
            }
        } else if (WordEntity.class.isInstance(block)) {
            str.append(((WordEntity)block).getWord());
        } else if (PunctEntity.class.isInstance(block)) {
            str.append(((PunctEntity)block).getPunct());
        } else if (MarkEntity.class.isInstance(block)) {
            str.append(((MarkEntity)block).getMark());
        } else if (NumEntity.class.isInstance(block)) {
            str.append(((NumEntity)block).getNum());
        }
        return str.toString();
    }
    
    private static StringBuilder readFileToString(String fileName) 
            throws ProjectException {
        //чтение текста из файла
        
        BufferedReader br;
        StringBuilder sb;
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName)));
            sb = new StringBuilder();
            String s;
            try {
                while((s = br.readLine()) != null) {
                    sb.append(s).append('\n');
                }
            } catch (IOException ex) {
                throw new ProjectException("Can't read file " + fileName);
            }
            finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    localLog.error("Can't close file " + fileName);
                }
            }   
        } catch (FileNotFoundException ex) {
            throw new ProjectException("File " + fileName + " not found.");
        }
        return sb;
    }

}
