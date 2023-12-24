package com.hospital.validation;

public class EmployeeValidation {
    
    protected boolean isChar(char ch){
        return (ch>='A'&&ch<='Z') || (ch>='a' && ch<='z');
    }

    protected boolean isSpace(char c){
        return c==' ';
    }
/*
 * Validate name
 */
    protected boolean isName(String name){

        if(name.length()==0 || name==null){
            return false;
        }

        int c=0;
        for(int i=0; i<name.length(); i++){
            if(isChar(name.charAt(i))||isSpace(name.charAt(i))){
                c++;
            }
            if(!isChar(name.charAt(name.length()-1))){
                return false;
            }
            if(isSpace(name.charAt(i))&&isSpace(name.charAt(i+1))&&i!=name.length()-1){
                return false;
            }
        }

        return c==name.length();
    }

    public boolean validateName(String name){
        return isName(name);
    }

    /*
     * Validate email
     */
    public boolean validateEmail(String email){
        if(email.length()==0||email==null){
            return false;
        }
        String regex = "^(.+)@(.+)$";  
        return email.matches(regex);
    }
    /*
     * Validate phone number
     */
    private boolean isNumber(char c){
        return c>='0' && c<='9';
    }
    public boolean validatePhoneNumber(String phoneNumber){

        if(phoneNumber.length()==0 || phoneNumber==null){
            return false;
        }
        
        String firstChars="";
        if(phoneNumber.length()>3){
            for(int i=0; i<3; i++){
                firstChars += phoneNumber.charAt(i);
            }
            if(!(firstChars.equals("015")||firstChars.equals("012")
                ||firstChars.equals("010"))){
                    return false;
                }
            int c=0;
            for(int i=0; i<phoneNumber.length(); i++){
                if (isNumber(phoneNumber.charAt(i))) {
                    c++;
                }
            }
            return c==11;
        }
        return false;
    }

}
