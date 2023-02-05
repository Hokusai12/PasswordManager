package password_manager;

import java.io.*;
import java.util.*;

public class PasswordManager {
	
	private HashMap<String, String> passwords;
	private String genEx = "`1234567890-=qwertyuiop[]\\\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP\\{\\}|ASDFGHJKL\\\"ZXCVBNM<>?";
	private String filepath = "C:/nothing/";
	
	public PasswordManager(String filename) {
		passwords = new HashMap<String, String>();
		
		try {
			passwordPropertySetup(new String(filepath + filename));
		} catch(IOException e) {
			System.err.println("An Input/Output error occurred: " + e.getMessage());
		}
	}
	
	private void passwordPropertySetup(String pwFilepath) throws IOException {
		FileReader input = null;
		String account = "", password = "";
		boolean isPassword = false;
		
		try {
			input = new FileReader(pwFilepath);
			
			int line;
			
			while((line = input.read()) != -1) {
				if(Character.toChars(line)[0] == '\n') {
					password = password.trim();
					this.passwords.put(account.toUpperCase(),  password);
					account = "";
					password = "";
					isPassword = false;
					continue;
				}
				if(Character.toChars(line)[0] == ':') {
					isPassword = true;
					continue;
				}
				
				if(isPassword) {
					password += Character.toChars(line)[0];
				} else {
					account += Character.toChars(line)[0];
				}
			}
		} finally {
			if(input != null) {
				input.close();
			}
		}
	}
	
	public Collection<String> getAllPasswords() {
		return this.passwords.values();
	}
	
	public String getPassword(String account) {
		return this.passwords.get(account);
	}
	
	public String generatePassword() { //Generates a new password of random chars
		Random rGen = new Random();
		String newPassword = "";
		
		for(int i = 0; i < 19; i++) {
			newPassword += genEx.charAt(rGen.nextInt(this.genEx.length()));
		}
		newPassword += genEx.charAt(rGen.nextInt(1, 11)); //Ensures the pw has at least one number
		
		return newPassword;
	}
	
	public void savePassword(String account, String newPassword) {
		FileWriter output = null;
		try {
			output = new FileWriter(filepath + "nothing.txt");
			output.write(new String(account + ": " + newPassword));
			if(output != null) {
				output.close();
			}
		}catch(IOException e) {
			System.err.println("An Input/Output Exception occurred: " + e.getMessage());
		}
	}
	
	public boolean hasAccount(String account) {
		if(this.passwords.containsKey(account)) {
			return true;
		}
		return false;
	}
	
	public void replacePassword(String account, String newPassword) {
		this.passwords.replace(account,  newPassword);
	}
}
