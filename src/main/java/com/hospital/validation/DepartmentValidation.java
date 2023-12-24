package com.hospital.validation;

public class DepartmentValidation extends EmployeeValidation {
 
	@Override
    public boolean validateName(String name){
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
	
	@Override
	protected boolean isChar(char ch){
        return (ch>='A'&&ch<='Z') || (ch>='a' && ch<='z') || (ch>='0' && ch<='9');
    }
	
}
