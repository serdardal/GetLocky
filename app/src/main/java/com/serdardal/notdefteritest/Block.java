package com.serdardal.notdefteritest;

/**
 * Created by serdar on 15.03.2018.
 */

public class Block{
    private String statement;
    private String id;
    private String password;
    private String notes;

    public Block(String statement,String id, String password, String notes){
        this.statement=statement;
        this.id=id;
        this.password=password;
        this.notes=notes;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return statement;
    }


    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Block) || other == null) return false;
        return statement == ((Block)other).statement &&
                id == ((Block)other).id &&
                password == ((Block)other).password&&
                notes == ((Block)other).notes;
    }


}
