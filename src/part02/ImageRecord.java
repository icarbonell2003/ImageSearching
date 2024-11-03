package part02;
import java.time.LocalDate;
import javax.swing.ImageIcon;

public class ImageRecord {
	
	private int id;
	private String title;
	private String description;
	private ImageType genre;
	private LocalDate dateTaken;
	private ImageIcon thumbnail;
	private static int nextId = 1;
	
	public void setTitle (String aTitle) {
		if (aTitle != null && isUpper(aTitle.charAt(0))) {
			this.title = aTitle;
		} else {
			if (this.title == null || !isUpper(aTitle.charAt(0))) {
				this.title = "UNKNOWN";
			}
		}
	}
	
	public void setDescription (String aDescription) {
		if (aDescription != null && isUpper(aDescription.charAt(0))) {
			this.description = aDescription;
		} else {
			if (this.description == null || !isUpper(aDescription.charAt(0))) {
				this.description = "UNKNOWN";
			}
		}
	}
	
	public void setImageType(ImageType aGenre) {
		this.genre = aGenre;
	}
	
	public void setDateTaken(LocalDate aDate) {
		LocalDate unknown = LocalDate.of(1, 1, 1);
		if (aDate != null) {
			this.dateTaken = aDate;
		}
		else {
			this.dateTaken = unknown;
		}
	}
	
	public void setThumbnail (ImageIcon aThumbnail) {
		if (aThumbnail != null) {
			this.thumbnail = aThumbnail;
		} else {
			if(this.thumbnail == null) {
				this.thumbnail = null;
			}
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getImageType() {
		return this.genre.toString();
	}
	
	public ImageType getGenre() {
		return this.genre;
	}
	
	public LocalDate getDateTaken() {
		return this.dateTaken;
	}
	
	public ImageIcon getThumbnail() {
		return this.thumbnail;
	}
	
	public ImageRecord(String title, String description, ImageType genre, LocalDate date, ImageIcon thumbnail){
		this.id = nextId;
		setTitle(title);
		setDescription(description);
		setImageType(genre);
		setDateTaken(date);
		setThumbnail(thumbnail);
		nextId++;
	}
	
	public ImageRecord() {
		this(null, null, ImageType.OTHER, null, null);
	}

	public String getDetails() {
		String details = "ID: " + getId() + "\nTitle: " + getTitle() + "\nImage Description: " + getDescription() + "\nGenre Description: " + getImageType() + "\nDate Taken: " + getDateTaken() + "\n";
		return details;
	}
	
	public String toString() {
		return getDetails();
	}

	private boolean isUpper(char ch) {
		if (ch >= 'A' && ch <= 'Z') {
			return true;
		} else {
			return false;
		}
	}
	
}
