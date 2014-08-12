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
public final class PunctEntity extends TextEntity{ //текстовый элемент 
    //пунктуации (включая матем. и спец.символы)
    
    private char punct;
    
    public PunctEntity(){}
    
    public PunctEntity(char punct) {
        setPunct(punct);
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
        PunctEntity pc = (PunctEntity)obj;
        return (this.punct == pc.punct);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.punct;
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(getClass().getName());
        str.append('@');
        str.append("punct: ");
        str.append(punct);
        return str.toString();
    }

    public void setPunct(char punct) {
        this.punct = punct;
    }

    public char getPunct() {
        return this.punct;
    }
}
