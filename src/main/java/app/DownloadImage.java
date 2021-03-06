package main.java.app;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

import javafx.concurrent.Task;
import com.flickr4java.flickr.*;
import com.flickr4java.flickr.photos.*;

/**
 * This class is made to download images from Flickr API
 */
public class DownloadImage extends Task<Object> {
	private String _searchTerm;
	private int _numOfImage;


	public DownloadImage(String _searchTerm, int _numOfImage) {
		this._searchTerm = _searchTerm;
		this._numOfImage = _numOfImage;
	}

	@Override
	protected Object call() throws Exception {
		// delete previous images
		String removePreviousImagesCommands = "rm -f " + ApplicationFolder.TempImages.getPath() + File.separator + "*.jpg";
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", removePreviousImagesCommands);
		Process process = pb.start();
		process.waitFor();
		
		// get new images
		try {
			String apiKey = getAPIKey("apiKey");
			String sharedSecret = getAPIKey("sharedSecret");

			Flickr flickr = new Flickr(apiKey, sharedSecret, new REST());
			int page = 0;
			
	        PhotosInterface photos = flickr.getPhotosInterface();
	        SearchParameters params = new SearchParameters();
	        params.setSort(SearchParameters.RELEVANCE);
	        params.setMedia("photos"); 
	        params.setText(_searchTerm);
	        
	        PhotoList<Photo> results = photos.search(params, _numOfImage, page);
	        for (Photo photo: results) {
	        	try {
	        		// Get images
	        		Image image = photos.getImage(photo,Size.LARGE);
		        	String filename = _searchTerm.trim().replace(' ', '-')+"-"+System.currentTimeMillis()+"-"+photo.getId()+".jpg";

					// create folder to save images
		        	File outputFile = new File("VAR-Encyclopedia/.temp/Images",filename);
					outputFile.getParentFile().mkdir();
					outputFile.createNewFile();

					// Write image to image file
		        	BufferedImage resizedImage = resizeImage(image,200,200);// need to edit
		        	ImageIO.write(resizedImage, "jpg", outputFile);
	        	} catch (FlickrException fe) {
	        		System.err.println("Ignoring image " +photo.getId() +": "+ fe.getMessage());
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method is used to get API key from text file
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getAPIKey(String key) throws Exception {
		// Get path for config file
		String config = System.getProperty("user.dir") + System.getProperty("file.separator") + "flickr-api-keys.txt";

		// Read file
		File file = new File(config);

		// use BufferedReader
		BufferedReader br = new BufferedReader(new FileReader(file)); 

		// get key in config file
		String line;
		while ( (line = br.readLine()) != null ) {
			if (line.trim().startsWith(key)) {
				br.close();
				return line.substring(line.indexOf("=")+1).trim();
			}
		}
		br.close();
		throw new RuntimeException("Couldn't find " + key + " in config file "+ file.getName());
	}

	/**
	 * This method is used to resize the image downloaded from Flickr API
	 * @param image original image
	 * @param width width of the image
	 * @param height height of the image
	 * @return a resized BufferedImage
	 */
	public static BufferedImage resizeImage(final Image image, int width, int height) {
		// create a buffered image with given dimension
		final BufferedImage BI = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		final Graphics2D g = BI.createGraphics();
		g.setComposite(AlphaComposite.Src);
		// draw new image
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return BI;
	}
}