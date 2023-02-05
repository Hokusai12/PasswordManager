package password_manager;

import java.util.Scanner;

public class Application {
	
	public static PasswordManager manager;
	public static Scanner scanner;
	
	public static void main(String args[]) {
		manager = new PasswordManager("nothing.txt");
		scanner = new Scanner(System.in);
		
		String input, password, account;

		while(true) {
				System.out.print("What account are you accessing(0 to exit/'Replace' to start replacing passwords): ");
				input = scanner.nextLine().toUpperCase();
				
				if(input.equals("0")) {
					System.out.println("Goodbye");
					break;
				}
				
				if(input.equals("REPLACE")) {
					System.out.print("Which account would you like to replace: ");
					account = scanner.nextLine().toUpperCase();
					
					if(!manager.hasAccount(account)) {
						System.out.println("That account isn't associated with a password!");
						continue;
					}
					
					while(true) {
						password = manager.generatePassword();
						System.out.print("Your new password for " + account + " is " + password + ". Do you want to keep it?(Y/N/0 to exit): ");
						
						input = scanner.nextLine().toUpperCase();
						
						if(input.equals("Y")) {
							manager.replacePassword(account,  password);
							break;
						} else if(input.equals("0")) {
							break;
						}
						
					}
					
					continue;
				}
				
				while(true) {
					if((password = manager.getPassword(input)) != null) {
						System.out.println(password);
						break;
					} else {
						account = input;
						
						System.out.println("Looks like there isn't a password associated with that account");
						System.out.println("Check your spelling or generate a password(G for generation/0 to exit): ");
						
						input = scanner.nextLine().toUpperCase();
						
						if(input.equals("G")) {
							while(true) {
								String newPassword = manager.generatePassword();
								System.out.println(newPassword + ": Want to save this one(Y/N/0 to exit): ");
								
								input = scanner.nextLine().toUpperCase();
								
								if(input.equals("Y")) {
									manager.savePassword(account, newPassword);
									break;
								} else if(input.equals("0")) {
									break;
								}
							}
						} else if(input.equals("0")) {
							break;
						}
					}
				}
		}
	}
}
