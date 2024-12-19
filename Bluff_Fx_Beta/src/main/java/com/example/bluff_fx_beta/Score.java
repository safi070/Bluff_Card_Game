package com.example.bluff_fx_beta;

import javafx.beans.property.SimpleStringProperty;

public class Score {
    private final SimpleStringProperty nameColumn;
    private final SimpleStringProperty wonColumn;
    private final SimpleStringProperty lostColumn;

    Score(String name,String won,String lost){
        this.wonColumn=new SimpleStringProperty(name);
        this.lostColumn=new SimpleStringProperty(won);
        this.nameColumn=new SimpleStringProperty(lost);
    }

    public String getNameColumn() {
        return nameColumn.get();
    }

    public SimpleStringProperty nameColumnProperty() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn.set(nameColumn);
    }

    public String getWonColumn() {
        return wonColumn.get();
    }

    public SimpleStringProperty wonColumnProperty() {
        return wonColumn;
    }

    public void setWonColumn(String wonColumn) {
        this.wonColumn.set(wonColumn);
    }

    public String getLostColumn() {
        return lostColumn.get();
    }

    public SimpleStringProperty lostColumnProperty() {
        return lostColumn;
    }

    public void setLostColumn(String lostColumn) {
        this.lostColumn.set(lostColumn);
    }
}//CT
