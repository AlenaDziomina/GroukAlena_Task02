/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.entity;

/**
 *
 * @author Helena.Grouk
 */
public final class MarkEntity extends TextEntity { //текстовый элемент символ
    //(пробельный и др. кроме пунктуационных, алфавитных и цифр)

    private char mark;
    
    public MarkEntity(){}
    
    public MarkEntity(char mark) {
        setMark(mark);
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
        MarkEntity mk = (MarkEntity)obj;
        return (this.mark == mk.mark);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.mark;
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(getClass().getName());
        str.append('@');
        str.append("mark: ");
        str.append(mark);
        return str.toString();
    }

    public void setMark(char mark) {
        this.mark = mark;
    }

    public char getMark() {
        return this.mark;
    } 
}
