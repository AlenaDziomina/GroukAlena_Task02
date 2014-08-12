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
public final class NumEntity extends TextEntity{ //текстовый элемент цифра
    
    private char num;
    
    public NumEntity(){}
    
    public NumEntity(char num) {
        setNum(num);
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
        NumEntity nm = (NumEntity)obj;
        return (this.num == nm.num);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.num;
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(getClass().getName());
        str.append('@');
        str.append("num: ");
        str.append(num);   
        return str.toString();
    }

    public void setNum(char num) {
        this.num = num;
    }

    public char getNum() {
        return this.num;
    }
}
