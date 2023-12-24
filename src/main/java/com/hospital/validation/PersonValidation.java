package com.hospital.validation;

public class PersonValidation extends EmployeeValidation {
	
	private boolean isUsernameChar(char ch) {
		return (ch>='A'&&ch<='Z') || (ch>='a' && ch<='z') || (ch>='0'&&ch<='9') 
				|| (ch=='_');
	}
	
	public boolean validateUsername(String username) {
		if(username.length()==0||username==null){
			return false;
		}
		
		if(!isChar(username.charAt(0))) {
			return false;
		}
		int c=0;
		for(int i=0; i<username.length(); i++) {
			if(isUsernameChar(username.charAt(i))) {
				c++;
			}
		}
		return c==username.length();
	}
	
	public boolean isPasswordEncrypted(String password) {
		String regx="^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$";
		return password.matches(regx);
	}
	
}
