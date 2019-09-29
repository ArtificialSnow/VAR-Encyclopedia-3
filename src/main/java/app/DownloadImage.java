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

public class DownloadImage extends Task<Object> {
	private String _searchTerm;
	private int _numOfImage;
	
	

	public DownloadImage(String _searchTerm, int _numOfImage) {
		this._searchTerm = _searchTerm;
		this._numOfImage = _numOfImage;
	}

	@Override
	protected Object call() throws Exception {
		// delete all previous image
		String cmd = "rm -f ./downloads/*.jpg";
		ProcessBuilder pb = new ProcessBuilder("bash","-c",cmd);
		Process process = pb.start();
		process.waitFor();
		
		// get new image..
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
	        System.out.println("Retrieving " + results.size()+ " results");
	        
	        for (Photo photo: results) {
	        	try {
	        		Image image = photos.getImage(photo,Size.LARGE);
		        	String filename = _searchTerm.trim().replace(' ', '-')+"-"+System.currentTimeMillis()+"-"+photo.getId()+".jpg";
		        	//
		        	File outputfile = new File("downloads",filename);
		        	outputfile.getParentFile().mkdir();
		        	outputfile.createNewFile();
		        	//
		        	BufferedImage resizedImage = resizeImage(image,800,600);// need to edit
		        	ImageIO.write(resizedImage, "jpg", outputfile);
		        	System.out.println("Downloaded "+filename);
	        	} catch (FlickrException fe) {
	        		System.err.println("Ignoring image " +photo.getId() +": "+ fe.getMessage());
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getAPIKey(String key) throws Exception {
		// TODO fix the following based on where you will have your config file stored

		String config = System.getProperty("user.dir") 
				+ System.getProperty("file.separator")+ "flickr-api-keys.txt"; 
		
		File file = new File(config); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		String line;
		while ( (line = br.readLine()) != null ) {
			if (line.trim().startsWith(key)) {
				br.close();
				return line.substring(line.indexOf("=")+1).trim();
			}
		}
		br.close();
		throw new RuntimeException("Couldn't find " + key +" in config file "+file.getName());
	}
	
	public static BufferedImage resizeImage(final Image image, int width, int height) {
		final BufferedImage BI = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		final Graphics2D g = BI.createGraphics();
		g.setComposite(AlphaComposite.Src);
		
		g.drawImage(image, 0, 0, width, height, null);//need to edit
		g.dispose();
		return BI;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DownloadImage image = new DownloadImage("bicycle", 5);
		try {
			image.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}