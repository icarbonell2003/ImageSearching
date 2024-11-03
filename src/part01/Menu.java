package part01;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	private String items[];
	private String title;
	private Scanner input;
	
	public Menu(String title, String data[]) {
		this.title = title;
		this.items = data;
		this.input = new Scanner(System.in);
	}
	
	private void display() {
		System.out.println(title);
		for(int count=0;count<title.length();count++) {
			System.out.print("+");
		}
		System.out.println();
		for(int option=1; option<=items.length; option++) {
			System.out.println(option + ". " + items[option-1] );
		}
		System.out.println();
	}
	
	public int getUserChoice() {
		int value;
		do{
			display();
			System.out.print("Enter Selection: ");
			try {
				value = input.nextInt();
				if (value < 1 || value > items.length) {
					System.out.printf("Please enter a number between 1 and %s", items.length);
				}
			}catch (InputMismatchException e) {
				System.out.printf("Please enter a number between 1 and %s", items.length);
				input.next();
				value = -1;
			}
		} while (value < 1 && value > items.length);
		
		
		return value;
	}
}

