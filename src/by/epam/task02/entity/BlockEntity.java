/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.task02.entity;

import by.epam.task02.exception.NullInitException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Helena.Grouk
 */
public class BlockEntity extends TextEntity{
//блок текста (параграф, подпараграф, абзац, блок кода)

    protected List<TextEntity> textEntity = new ArrayList();  
    
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
        BlockEntity bl = (BlockEntity)obj;
        return this.textEntity.equals(bl.textEntity);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.textEntity);
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(getClass().getName());
        str.append('@');
        str.append("textEntity: ");
        str.append(textEntity.toString());  
        return str.toString();
    }
     
    public void setTextEntity(TextEntity textEntity) throws NullInitException{
        if (textEntity == null) {
            throw new NullInitException("textEntity is null.");
        }
        this.textEntity.add(textEntity);
    }
    
    public List<TextEntity> getTextEntity(){
        return Collections.unmodifiableList(textEntity);
    }
    
    public TextEntity getTextEntity(int i){
        return textEntity.get(i);
    }
}
