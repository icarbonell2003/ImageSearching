package part02;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import console.Console;

public class ImageManager {
	
	static ArrayList<ImageRecord> images = initializeData();
	static Console input = new Console(true);
	static Console display = new Console(true);
	public static void main(String[] args) {
	
		initializeConsoles();
		
		String options[]= {"Add Image", "Search Image", "Display All", "Exit"};
		Menu myMenu = new Menu("Menu Window:\nQUB Media Images", options);
		
		boolean finished = false;
		
		do {
			 
			input.setVisible(true);
			setInputWindow();
			int option = myMenu.getUserChoice();
			switch (option) {
			case 1:
				addImage();
				break;
			case 2: 
				searchImage();
				break;
			case 3: 
				displayAll();
				break;
			case 4:
				input.print("Are you sure you want to exit? (Type Y/y for yes, anything else for no): ");
				String decision = input.readLn();
				if (decision.equals("Y") || decision.equals("y")) {
					finished = true;
					input.println("Bye!");
					input.setVisible(false);
					display.setVisible(false);
					break;
				}
				else {
					break;
				}
			default:
				input.println("Not a valid option.");
			}
			
		} while (!finished);
		
	}
	
	
	public static void addImage() {
		setInputWindow();
		input.println("OK-------Adding new Image");
		
		ImageRecord image = new ImageRecord();
		
		String title = testTitle();
		image.setTitle(title);
		
		String description = testDescription();
		image.setDescription(description);
		
		ImageType genre = testGenreTitle();
		image.setImageType(genre);
		
		input.println("Input the date in which the Image was taken");
		LocalDate date = testDate();
		image.setDateTaken(date);
		
		
		String thumbnail = testThumbnail();
		ImageIcon newThumb = new ImageIcon (thumbnail);
		image.setThumbnail(newThumb);
		
		images.add(image); 
		
		setInputWindow();
		setDisplayWindow(); 
		display.setVisible(true);
		setDisplayWindow();
		display.println("\n\nImage added succesfully!\n\n");
		display.println(image.getDetails());
		display.println(image.getThumbnail());
		boolean keepConsole = true;
		do {
			display.println("Whenever you are ready to move on type ''y'' or ''Y'': ");
			String keep = display.readLn();
			if (keep.equals("y") || keep.equals("Y")) {
				keepConsole = false;
			}
		} while (keepConsole);
		display.setVisible(false);
		
	}
	public static String testTitle() {
		setInputWindow();
		String title = "";
		do {
			input.print("What is the title of the Image (First letter needs to be upper case): ");
			title = input.readLn();
		} while (title.isEmpty() || !isUpper(title.charAt(0)));
		return title;
	}
	
	public static String testDescription() {
		
		setInputWindow();
		String description = "";
		do {
			input.print("What is the description of the Image (First letter needs to be upper case): ");
			description = input.readLn();
		} while (description.isEmpty() || !isUpper(description.charAt(0)));
		return description;
	}
	
	public static ImageType testGenreTitle() {
		input.setVisible(false);
		String genreOptions[] = {"Astronomy", "Architecture", "Sport", "Landscape", "Portrait", "Nature", "Aerial", "Food", "Other"};
		Menu genreMenu = new Menu ("Menu Window:\nChoose a Genre:", genreOptions);
		int election = genreMenu.getUserChoice();
		
		input.setVisible(true);
		switch (election) {
		case 1:
			return ImageType.ASTRONOMY;
		case 2:
			return ImageType.ARCHITECTURE;
		case 3: 
			return ImageType.SPORT;
		case 4: 
			return ImageType.LANDSCAPE;
		case 5:
			return ImageType.PORTRAIT;
		case 6:
			return ImageType.NATURE;
		case 7: 
			return ImageType.AERIAL;
		case 8: 
			return ImageType.FOOD;
		default:
			return ImageType.OTHER;
		}
		
	}
	
	public static LocalDate testDate() {
		setInputWindow();
	    input.print("Year: ");
	    String conYear = input.readLn();
	    int year = Integer.parseInt(conYear);
	    int month, day;
	    do {
	        input.print("Month (1-12): ");
	        String conMonth = input.readLn();
		    month = Integer.parseInt(conMonth);
	    } while (month < 1 || month > 12); 
	    do {
	       input.print("Day (1-31): ");
	       String conDay = input.readLn();
	       day = Integer.parseInt(conDay);
	    } while (day < 1 || day > 31); 

	    LocalDate date = LocalDate.of(year, month, day); 
	    return date;
	}
	
	public static String testThumbnail() {
		setInputWindow();
		String thumbnail = "";
		String path = "Images/";
		do {
			input.print("What is the thumbnail of the Image (First letter needs to be upper case and needs to end in .png): ");
			thumbnail = input.readLn();
		} while (thumbnail.isEmpty() || (!isUpper(thumbnail.charAt(0)) || !hasPNG(thumbnail)));
		path += thumbnail;
		return path;
	}
	
	public static void displayAll() {
		setDisplayWindow(); 
		display.setVisible(true);
		setDisplayWindow();
		
		if (images.size() == 0) {
			display.println("\n There are no images to be displayed.\n");
		}
		else {
			for (int i = 0; i < images.size(); i++) {
				display.println(images.get(i).getDetails() + "\n");
				display.println(images.get(i).getThumbnail());
				display.println();
			}
		}
		
		boolean keepConsole = true;
		do {
			display.print("Whenever you are ready to move on type ''y'' or ''Y'': ");
			String keep = display.readLn();
			if (keep.equals("y") || keep.equals("Y")) {
				keepConsole = false;
			}
		} while (keepConsole);
		display.setVisible(false);
	}
	
	public static void searchImage() {
		input.setVisible(false);
		String searchOptions[] = {"Search by ID", "Search by Title", "Search by Description", "Search by Genre", "Search by Date Range", "Get all images in chronological order", "Return to main menu"};
		Menu search = new Menu ("Menu Window:\nHow would you like to search for your images?", searchOptions);
		int searchOption = search.getUserChoice();
		input.setVisible(true);
		setInputWindow();
		switch (searchOption) {
		case 1: 
			input.println("OK, searching by ID: ");
			input.println("Please type the ID of the image you are looking for: ");
			String conID = input.readLn();
			int id = Integer.parseInt(conID);
			ImageRecord result = searchId(id);
			if (result == null) {
				setDisplayWindow(); setDisplayWindow();
				input.println("Sorry, there is no Image with ID" + id + ".\n\n");
				break;
			} else {
				
				input.println("InputWindow");
				setDisplayWindow(); 
				display.setVisible(true);
				setDisplayWindow();
				display.println("The image with ID " + id + "is: \n");
				display.print(images.get(id-1).getDetails());
				display.println(images.get(id-1).getThumbnail());
				boolean keepConsole = true;
				do {
					display.println("Whenever you are ready to move on type ''y'' or ''Y'': ");
					String keep = display.readLn();
					if (keep.equals("y") || keep.equals("Y")) {
						keepConsole = false;
					}
				} while (keepConsole);
				display.setVisible(false);
				break;
			}
		case 2:
			 setInputWindow();
			input.println("OK, searching by  title! ");
			input.println("Type a String and the system will output in chronological order the first Image Record that contains those words in the title.");
			input.println("Beware this is a case sensitive system!");
			input.print("String: ");
			input.println();
			String searchedTitle = input.readLn();
			ImageAlbum possibleTitles = searchTitle(searchedTitle);
			display(possibleTitles);
			break;
		case 3:
			 setInputWindow();
			input.println("OK, searching by description! ");
			input.println("Type a String and the system will output in chronological order the first Image Record that contains those words in the description.");
			input.println("Beware this is a case sensitive system!");
			input.print("String: ");
			input.println();
			String searchedDescription = input.readLn();
			ImageAlbum possibleDescriptions = searchTitle(searchedDescription);
			display(possibleDescriptions);
			break;
		case 4:
			 setInputWindow();
			input.println("OK, searching by Genre! ");
			input.println("Select a Genre and the system will output in chronological order the first Image Record that belongs to that Genre.");
			input.println();
			ImageType searchedGenre = testGenreTitle();
			ImageAlbum possibleGenres = searchGenre(searchedGenre);
			display(possibleGenres);
			break;
		case 5: 
			 setInputWindow();
			input.println("OK, searching by date range! ");
			input.println("After receiving the start and end date for your search, the system will output in chronological order the first Image Record that belongs in that range.");
			input.println();
			input.println("Intput the start date");
			LocalDate start = testDate();
			LocalDate end = null;
			do{
				input.println("Intput the end date");
				end = testDate();
				if (start.compareTo(end) >= 0) {
					input.println("Can't have an end date before the start date");
				}
			} while (start.compareTo(end) >= 0);
			
			ImageAlbum possibleDates = searchDates(start, end);
			display(possibleDates);
			break;
		case 6:
			
			setInputWindow();
			input.println("OK, outputting all Image Records in chronological order");
			ImageAlbum sorted = getAllImages();
			display(sorted);
			break;
		default:
			input.println("Not a valid option");	
		}
	}
	
	
	public static ImageRecord searchId(int id) {
		for (int i = 0; i < images.size(); i++) {
			if (id == images.get(i).getId()) {
				return images.get(i);
			}
		}
		return null;
	}
	
	public static ImageAlbum searchTitle (String str) {
		ImageAlbum titles = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getTitle().contains(str)) {
				titles.addPossibleImage(images.get(i));
			}
		}
		return titles;
	}
	
	public static ImageAlbum searchDescription (String str) {
		ImageAlbum descriptions = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getDescription().contains(str)) {
				descriptions.addPossibleImage(images.get(i));
			}
		}
		return descriptions;
	}
	
	public static ImageAlbum searchGenre(ImageType genre) {
		ImageAlbum genres = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getGenre() == genre) {
				genres.addPossibleImage(images.get(i));
			}
		}
		return genres;
	}
	
	public static ImageAlbum searchDates(LocalDate start, LocalDate end) {
		ImageAlbum dates = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			LocalDate pointer = images.get(i).getDateTaken();
			if ((pointer.compareTo(start) >= 0) && (pointer.compareTo(end) <= 0)) {
				dates.addPossibleImage(images.get(i));
			}
		}
		return dates;
	}
	
	public static ImageAlbum getAllImages() {
		ImageAlbum all = new ImageAlbum();
		for (int i = 0; i < images.size(); i++) {
			all.addPossibleImage(images.get(i));
		}
		return all;
	}
	
	public static void display(ImageAlbum album) {
		setDisplayWindow(); 
		display.setVisible(true);
		setDisplayWindow();
		ImageRecord firstResult = album.getFirst();
		if (album == null || album.getFirst() == null) {
			display.println("Sorry, there are no Image Records that coincide with you search criteria.");
		} else {
			display.println("This album has " + album.getSize() +" Image Records.\n\n");
			display.println("This is the first Image Record that coincides with you search criteria: ");
			display.println(firstResult.getDetails() + "\n" );
			display.println(firstResult.getThumbnail());
			display.println("\n");
			input.setVisible(true);
			setInputWindow();
			String suboptions[] = {"Search next image", "Search previous image", "Return to main menu"};
			Menu sub = new Menu ("Menu Window:\nWhat would you like to do next? ", suboptions);
			boolean done = false;
			do {
				int userChoice = sub.getUserChoice();
				switch (userChoice) {
				case 1: 
					display.setVisible(true);
					ImageRecord next = album.getNextPointer();
					if (next == null) {
						display.println("Sorry, there are no more next Image Records in this Album that coincide with your search criteria.");
						break;
					} else {
						display.println(next.getDetails()+ "\n" );
						display.println(next.getThumbnail());
						display.println("\n");
						break;
					}
				case 2:
					display.setVisible(true);
					ImageRecord previous = album.getPreviousPointer();
					if (previous == null) {
						display.println("Sorry, there are no more previous Image Records in this Album that coincide with your search criteria.");
						break;
					} else {
						display.println(previous.getDetails() + "\n" );
						display.println(previous.getThumbnail());
						display.println("\n");
						break;
					}
				case 3: 
					display.setVisible(false);
					
					input.setVisible(true);
					setInputWindow();
					input.print("Are you sure you want to return to the main menu? (Type Y/y for yes, anything else for no): ");
					String decision = input.readLn();
					if (decision.equals("Y") || decision.equals("y")) {
						done = true;
						input.println("OK!\n");
						break;
					}
					else {
						break;
					}
				default:
					input.setVisible(true);
					input.println("Not a valid option.");
				}
			} while (!done);
		}
		
}
	
	public static boolean isUpper(char ch) {
		if (ch >= 'A' && ch <= 'Z') {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean hasPNG(String thumb) {
		int length = thumb.length();
		if ((thumb.charAt(length-1) == 'g') && (thumb.charAt(length-2) == 'n')  && (thumb.charAt(length-3) == 'p') && (thumb.charAt(length-4) == '.')) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ArrayList<ImageRecord> initializeData() {
		ArrayList<ImageRecord> images = new ArrayList<>();
		
		ImageIcon thumb1 = new ImageIcon("Images/Andromeda.png");
		ImageIcon thumb2 = new ImageIcon("Images/LanyonQUB.png");
		ImageIcon thumb3 = new ImageIcon("Images/KermitGolf.png");
		ImageIcon thumb4 = new ImageIcon("Images/Mournes.png");
		ImageIcon thumb5 = new ImageIcon("Images/Homer.png");
		ImageIcon thumb6 = new ImageIcon("Images/RedKite.png");
		ImageIcon thumb7 = new ImageIcon("Images/CentralPark.png");
		ImageIcon thumb8 = new ImageIcon("Images/Apples.png");
		ImageIcon thumb9 = new ImageIcon("Images/ChatGPT.png");
		ImageIcon thumb10 = new ImageIcon("Images/Meme.png");
		
		
		ImageRecord image1 = new ImageRecord ("Andromeda Galaxy", "Image of the Andromeda galaxy.", ImageType.ASTRONOMY, LocalDate.of(2023, 01, 01), thumb1);
		ImageRecord image2 = new ImageRecord ("Lanyon QUB", "An image of the QUB Lanyon building.", ImageType.ARCHITECTURE, LocalDate.of(2023, 02, 01), thumb2);
		ImageRecord image3 = new ImageRecord ("Kermit Plays Golf", "An image of Kermit the frog playing golf.", ImageType.SPORT, LocalDate.of(2023, 03, 01), thumb3);
		ImageRecord image4 = new ImageRecord ("Mourne Mountains", "A panoramic view of the Mourne mountains.", ImageType.LANDSCAPE, LocalDate.of(2023, 04, 01), thumb4);
		ImageRecord image5 = new ImageRecord ("Homer Simpson", "Homer Simpson - A portrait of the man.", ImageType.PORTRAIT, LocalDate.of(2023, 03, 01), thumb5);
		ImageRecord image6 = new ImageRecord ("Red Kite", "A Red Kite bird of prey in flight.", ImageType.NATURE, LocalDate.of(2023, 04, 01), thumb6);
		ImageRecord image7 = new ImageRecord ("Central Park", "An overhead view of Central Park New York USA.", ImageType.AERIAL, LocalDate.of(2023, 05, 01), thumb7);
		ImageRecord image8 = new ImageRecord ("Apples", "A bunch of apples.", ImageType.FOOD, LocalDate.of(2023, 06, 01), thumb8);
		ImageRecord image9 = new ImageRecord ("Programming Meme", "A Chat GPT programming meme.", ImageType.OTHER, LocalDate.of(2023, 07, 01), thumb9);
		ImageRecord image10 = new ImageRecord ("Meme", "Just a meme.", ImageType.OTHER, LocalDate.of(2024, 03, 03), thumb10);
		
		images.add(image1);
		images.add(image2);
		images.add(image3);
		images.add(image4);
		images.add(image5);
		images.add(image6);
		images.add(image7);
		images.add(image8);
		images.add(image9);
		images.add(image10);
		
		return images;	
	}
	
	public static void initializeConsoles(){
		input.setVisible(false);
		input.setLocation(0, 0);
		input.setSize(1600, 350);
		input.setFont( new Font("Courier", Font.BOLD, 20));
		input.setColour( Color.black );
		input.setBgColour(Color.white);
		input.println("Input Window");
		
		display.setVisible(false);
		display.setLocation(0, 400);
		display.setSize(1600, 400);
		display.setFont( new Font("Arial", Font.PLAIN, 20) );
		display.setColour( Color.red );
		display.setBgColour(Color.black);
		display.println("Output window: ");
	}
	
	public static void setInputWindow() {
		input.clear();
		input.println("Input Window: ");
	}
	
	public static void setDisplayWindow() {
		display.clear();
		display.println("Output Window: ");
	}
}
