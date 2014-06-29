package com.spxc.stockpile.model;

public class NavDrawerItem {
    
	private String title;
	private String email;
    private int icon;
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;
    private boolean isEmailVisible = false;
     
    public NavDrawerItem(){}
 
    public NavDrawerItem(String title, int icon, String email){
        this.title = title;
        this.icon = icon;
        this.email = email;
    }
     
    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count, boolean isEmailVisible, String email){
        this.title = title;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
        this.isEmailVisible = isEmailVisible;
        this.email = email;
    }
     
    public String getTitle(){
        return this.title;
    }
     
    public int getIcon(){
        return this.icon;
    }
     
    public String getCount(){
        return this.count;
    }
    
    public String getEmail(){
        return this.email;
    }
     
    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }
    
    public boolean getEmailVisibility(){
        return this.isEmailVisible;
    }
     
    public void setTitle(String title){
        this.title = title;
    }
     
    public void setIcon(int icon){
        this.icon = icon;
    }
     
    public void setCount(String count){
        this.count = count;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
     
    public void setCounterVisibility(boolean isCounterVisible){
        this.isCounterVisible = isCounterVisible;
    }
    
    public void setEmailVisibility(boolean isEmailVisible){
        this.isEmailVisible = isEmailVisible;
    }
}