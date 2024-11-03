package part01;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ImageManager {
	
	static Scanner scanner = new Scanner (System.in);
	static ArrayList<ImageRecord> images = initialize();

	public static void main(String[] args) {
		
	
		String options[]= {"Add Image", "Search Image", "Display All", "Exit"};
		Menu myMenu = new Menu("QUB Images", options);

		boolean finished = false;
		
		do {
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
				System.out.print("Are you sure you want to exit? (Type Y/y for yes, anything else for no): ");
				String decision = scanner.next();
				if (decision.equals("Y") || decision.equals("y")) {
					finished = true;
					System.out.println("Bye!");
					break;
				}
				else {
					break;
				}
			default:
				System.out.println("Not a valid option.");
			}
			
		} while (!finished);
	}
	
	public static void addImage() {
		
		System.out.println("OK-------Adding new Image");
		ImageRecord image = new ImageRecord();
		
		String title = testTitle();
		image.setTitle(title);
		
		String description = testDescription();
		image.setDescription(description);
		
		ImageType genre = testGenreTitle();
		image.setImageType(genre);
		
		System.out.println("Input the date in which the Image was taken");
		LocalDate date = testDate();
		image.setDateTaken(date);
		
		
		String thumbnail = testThumbnail();
		image.setThumbnail(thumbnail);
		
		images.add(image); 
		System.out.println("\n\nImage added succesfully\n\n");
		System.out.println(image.getDetails());	
		
	}
	public static String testTitle() {
		String title = "";
		do {
			System.out.print("What is the title of the Image (First letter needs to be upper case): ");
			title = scanner.nextLine();
		} while (title.isEmpty() || !isUpper(title.charAt(0)));
		return title;
	}
	
	public static String testDescription() {
		String description = "";
		do {
			System.out.print("What is the description of the Image (First letter needs to be upper case): ");
			description = scanner.nextLine();
		} while (description.isEmpty() || !isUpper(description.charAt(0)));
		return description;
	}
	
	public static ImageType testGenreTitle() {
		String genreOptions[] = {"Astronomy", "Architecture", "Sport", "Landscape", "Portrait", "Nature", "Aerial", "Food", "Other"};
		Menu genreMenu = new Menu ("Choose a Genre:", genreOptions);
		int election = genreMenu.getUserChoice();
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
	    System.out.print("Year: ");
	    int year = scanner.nextInt();
	    int month, day;
	    do {
	        System.out.print("Month (1-12): ");
	        month = scanner.nextInt();
	    } while (month < 1 || month > 12); 
	    do {
	        System.out.print("Day (1-31): ");
	        day = scanner.nextInt();
	    } while (day < 1 || day > 31); 

	    LocalDate date = LocalDate.of(year, month, day); 
	    return date;
	}
	
	public static String testThumbnail() {
		scanner.nextLine();
		String thumbnail = "";
		do {
			System.out.print("What is the thumbnail of the Image (First letter needs to be upper case and needs to end in .png): ");
			thumbnail = scanner.nextLine();
		} while (thumbnail.isEmpty() || (!isUpper(thumbnail.charAt(0)) || !hasPNG(thumbnail)));
		return thumbnail;
	}
	
	public static void displayAll() {
		System.out.println();
		if (images.size() == 0) {
			System.out.println("\n There are no images to be displayed.\n");
		}
		else {
			for (int i = 0; i < images.size(); i++) {
				System.out.println(images.get(i).getDetails());
				System.out.println();
			}
		}
	}
	
	public static void searchImage() {
		String searchOptions[] = {"Search by ID", "Search by Title", "Search by Description", "Search by Genre", "Search by Date Range", "Get all images in chronological order", "Return to main menu"};
		Menu search = new Menu ("How would you like to search for your images?", searchOptions);
		int searchOption = search.getUserChoice();
		switch (searchOption) {
		case 1: 
			System.out.println("OK, searching by ID: ");
			System.out.printf("Please type the ID of the image you are looking for: ");
			int id = scanner.nextInt();
			ImageRecord result = searchId(id);
			if (result == null) {
				System.out.printf("Sorry, there is no Image with ID %s.\n\n", id);
				break;
			} else {
				System.out.printf("The image with ID %s is:\n\n", id);
				System.out.print(images.get(id-1).getDetails());
				System.out.println();
				break;
			}
		case 2:
			System.out.println("OK, searching by  title! ");
			System.out.println("Type a String and the system will output in chronological order the first Image Record that contains those words in the title.");
			System.out.println("Beware this is a case sensitive system!");
			System.out.print("String: ");
			System.out.println();
			String searchedTitle = scanner.nextLine();
			ImageAlbum possibleTitles = searchTitle(searchedTitle);
			System.out.println();
			display(possibleTitles);
			break;
		case 3:
			System.out.println("OK, searching by description! ");
			System.out.println("Type a String and the system will output in chronological order the first Image Record that contains those words in the description.");
			System.out.println("Beware this is a case sensitive system!");
			System.out.print("String: ");
			System.out.println();
			String searchedDescription = scanner.nextLine();
			ImageAlbum possibleDescriptions = searchTitle(searchedDescription);
			System.out.println();
			display(possibleDescriptions);
			break;
		case 4:
			System.out.println("OK, searching by Genre! ");
			System.out.println("Select a Genre and the system will output in chronological order the first Image Record that belongs to that Genre.");
			System.out.println();
			ImageType searchedGenre = testGenreTitle();
			ImageAlbum possibleGenres = searchGenre(searchedGenre);
			System.out.println();
			display(possibleGenres);
			break;
		case 5: 
			System.out.println("OK, searching by date range! ");
			System.out.println("After receiving the start and end date for your search, the system will output in chronological order the first Image Record that belongs in that range.");
			System.out.println();
			System.out.println("Intput the start date");
			LocalDate start = testDate();
			LocalDate end = null;
			do{
				System.out.println("Intput the end date");
				end = testDate();
				if (start.compareTo(end) >= 0) {
					System.out.println("Cant have an end date before the start date");
				}
			} while (start.compareTo(end) >= 0);
			
			ImageAlbum possibleDates = searchDates(start, end);
			System.out.println();
			display(possibleDates);
			break;
		case 6:
			System.out.println("OK, outputting all Image Records in chronological order");
			ImageAlbum sorted = getAllImages();
			System.out.println();
			display(sorted);
			break;
		case 7:
			break;
		default:
			System.out.println("Not a valid option");	
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
		ImageRecord firstResult = album.getFirst();
		if (album == null || album.getFirst() == null) {
			System.out.println("Sorry, there are no Image Records that coincide with you search criteria.");
		} else {
			System.out.println("This album has " + album.getAlbumSize() + " Image Records.\n\n");
			System.out.println("This is the first Image Record that coincides with you search criteria: ");
			System.out.println(firstResult.getDetails());
			String suboptions[] = {"Search next image", "Search previous image", "Return to main menu"};
			Menu sub = new Menu ("What would you like to do next? ", suboptions);
			boolean done = false;
			do {
				int userChoice = sub.getUserChoice();
				switch (userChoice) {
				case 1: 
					ImageRecord next = album.getNextPointer();
					if (next == null) {
						System.out.println("Sorry, there are no more next Image Records in this Album that coincide with your search criteria.");
						break;
					} else {
						System.out.println(next.getDetails());
						break;
					}
				case 2:
					ImageRecord previous = album.getPreviousPointer();
					if (previous == null) {
						System.out.println("Sorry, there are no more previous Image Records in this Album that coincide with your search criteria.");
						break;
					} else {
						System.out.println(previous.getDetails());
						break;
					}
				case 3: 
					System.out.print("Are you sure you want to return to the main menu? (Type Y/y for yes, anything else for no): ");
					String decision = scanner.next();
					if (decision.equals("Y") || decision.equals("y")) {
						done = true;
						System.out.println("OK!\n");
						break;
					}
					else {
						break;
					}
				default:
					System.out.println("Not a valid option.");
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
	
	public static ArrayList<ImageRecord> initialize() {
		ArrayList<ImageRecord> images = new ArrayList<>();
		
		ImageRecord image1 = new ImageRecord ("Andromeda Galaxy", "Image of the Andromeda galaxy.", ImageType.ASTRONOMY, LocalDate.of(2023, 01, 01), "Andromeda.png");
		ImageRecord image2 = new ImageRecord ("Lanyon QUB", "An image of the QUB Lanyon building.", ImageType.ARCHITECTURE, LocalDate.of(2023, 02, 01), "LanyonQUB.png");
		ImageRecord image3 = new ImageRecord ("Kermit Plays Golf", "An image of Kermit the frog playing golf.", ImageType.SPORT, LocalDate.of(2023, 03, 01), "KermitGolf.png");
		ImageRecord image4 = new ImageRecord ("Mourne Mountains", "A panoramic view of the Mourne mountains.", ImageType.LANDSCAPE, LocalDate.of(2023, 04, 01), "Mournes.png");
		ImageRecord image5 = new ImageRecord ("Homer Simpson", "Homer Simpson - A portrait of the man.", ImageType.PORTRAIT, LocalDate.of(2023, 03, 01), "Homer.png");
		ImageRecord image6 = new ImageRecord ("Red Kite", "A Red Kite bird of prey in flight.", ImageType.NATURE, LocalDate.of(2023, 04, 01), "RedKite.png");
		ImageRecord image7 = new ImageRecord ("Central Park", "An overhead view of Central Park New York USA.", ImageType.AERIAL, LocalDate.of(2023, 05, 01), "CentralPark.png");
		ImageRecord image8 = new ImageRecord ("Apples", "A bunch of apples.", ImageType.FOOD, LocalDate.of(2023, 06, 01), "Apples.png");
		ImageRecord image9 = new ImageRecord ("Programming Meme", "A Chat GPT programming meme.", ImageType.OTHER, LocalDate.of(2023, 07, 01), "ChatGPT.png");
		ImageRecord image10 = new ImageRecord ("Meme", "Just a meme.", ImageType.OTHER, LocalDate.of(2024, 03, 03), "Meme.png");
		
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
}
