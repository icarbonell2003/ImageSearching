package part02;
import java.awt.Color;
import java.awt.Font;
import console.Console;

public class Menu {
	private String items[];
	private String title;
	private Console con;
	
	public Menu(String title, String data[]) {
		this.title = title;
		this.items = data;
		this.con = new Console(true);

	
		con.setVisible(false);
		con.setLocation(0, 0);
		con.setSize(1600, 350);
		con.setFont( new Font("Times New Roman", Font.BOLD, 20) );
		con.setColour( Color.blue );
		con.setBgColour(Color.white);
	
	}
	
	private void display() {
		con.setVisible(true);
		con.println(title);
		for(int count=0;count<title.length();count++) {
			con.print("+");
		}
		con.println();
		for(int option=1; option<=items.length; option++) {
			con.println(option + ". " + items[option-1] );
		}
		con.println();
	}
	
	public int getUserChoice() {
		String userInput;
		int value;
		do{
			display();
			con.print("Enter Selection: ");
			
			userInput = con.readLn();
			value = Integer.parseInt(userInput);
			if (value < 1 || value > items.length) {
				con.println("Please enter a number between 1 and " + items.length + ".");
			}
		} while (value < 1 || value > items.length);
		con.clear();
		con.setVisible(false);
		return value;
	}
}

