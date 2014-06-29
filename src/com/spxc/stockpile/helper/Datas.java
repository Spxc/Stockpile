package com.spxc.stockpile.helper;

public class Datas {

    //private variables
    int _id;
    String _name;
    String _icon;
    String _developer;
    String _description;
    String _version;
    String _download;
    String _updated;
    String _size;
    String _category;
    String _statistics;
    String _compackage;

    // Empty constructor
    public Datas(){

    }
    // constructor
    public Datas(int id, String name, String icon, String developer, String description, String version, String download, String updated, String size, String category, String statistics, String compackage){
        this._id = id;
        this._name = name;
        this._icon = icon;
        this._developer = developer;
        this._description = description;
        this._version = version;
        this._download = download;
        this._updated = updated;
        this._size = size;
        this._category = category;
        this._statistics = statistics;
        this._compackage = compackage;
    }

    // constructor
    public Datas(String name, String icon, String developer, String description, String version, String download, String updated, String size, String category, String statistics, String compackage){
    	this._name = name;
        this._icon = icon;
        this._developer = developer;
        this._description = description;
        this._version = version;
        this._download = download;
        this._updated = updated;
        this._size = size;
        this._category = category;
        this._statistics = statistics;
        this._compackage = compackage;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getIcon(){
        return this._icon;
    }

    public void setIcon(String icon){
        this._icon = icon;
    }

    public String getDeveloper(){
        return this._developer;
    }

    public void setDeveloper(String developer){
        this._developer = developer;
    }

    public String getDescription(){
        return this._description;
    }

    public void setDescription(String description){
        this._description = description;
    }
    //-------------------------------//
    public String getVersion(){
        return this._version;
    }

    public void setVersion(String version){
        this._version = version;
    }
    //
    public String getDownload(){
        return this._download;
    }

    public void setDownload(String download){
        this._download = download;
    }
    //
    public String getUpdated(){
        return this._updated;
    }

    public void setUpdated(String updated){
        this._updated = updated;
    }
    //
    public String getSize(){
        return this._size;
    }

    public void setSize(String size){
        this._size = size;
    }
    //
    public String getCategory(){
        return this._category;
    }

    public void setCategory(String category){
        this._category = category;
    }
    //
    public String getStatistics(){
        return this._statistics;
    }

    public void setStatistics(String statistics){
        this._statistics = statistics;
    }
    //
    public String getPackage(){
        return this._compackage;
    }

    public void setPackage(String compackage){
        this._compackage = compackage;
    }
}