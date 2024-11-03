package part02;
import java.util.ArrayList;
import java.time.LocalDate;

public class ImageAlbum {

	private ArrayList<ImageRecord> possibleImages;
	private int pointer;
	
	public ImageAlbum() {
		possibleImages = new ArrayList<>();
		pointer = -1;
	}
	
	public void addPossibleImage(ImageRecord image) {
		if (possibleImages.isEmpty()) {
			possibleImages.add(image);
		}
		else {
			
			LocalDate newDate = image.getDateTaken();
			int index = 0;
			for (ImageRecord img : possibleImages) {
				LocalDate existingDate = img.getDateTaken();
				if (newDate.compareTo(existingDate)<0) {
					possibleImages.add(index, image);
					return;
				}
				index++;
			}
			possibleImages.add(image);	
		}
	}

	public ImageRecord getFirst() {
		pointer = 0;
		if (possibleImages.get(pointer) == null) {
			return null;
		} else {
			return possibleImages.get(pointer);
		}
	}
	
	public ImageRecord getNextPointer() {
	    if (pointer < (possibleImages.size() - 1)) { 
	    	pointer ++;
	        return possibleImages.get(pointer);
	    } else {
	        return null;
	    }
	}
	
	public ImageRecord getPreviousPointer() {
	    if ((pointer - 1) >= 0) {
	        pointer --;
	        return possibleImages.get(pointer);
	    } else {
	        return null;
	    }
	}
	
	public int getSize() {
		int albumSize = possibleImages.size();
		return albumSize;
	}
}
