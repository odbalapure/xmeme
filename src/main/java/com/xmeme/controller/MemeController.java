package com.xmeme.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xmeme.dto.Meme;
import com.xmeme.exchange.GetMemeRequest;
import com.xmeme.service.MemeService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class MemeController {
	
    private final static Logger logger = Logger.getLogger(MemeController.class);
	
	private static final String XMEME_API = "/xmeme";
	private static final String GET_MEMES = "/memes";
	private static final String GET_MEME = "/meme";
	private static final String POST_MEME = "/post";
	private static final String EDIT_MEME = "/edit";
	private static final String DELETE_MEME = "/delete";
	private static final String DELETE_ALL_MEMES = "/deleteall";
		
	@Autowired
	private MemeService memeService;
	
	/**
	 * Print a welcome message
	 */
	@GetMapping(XMEME_API)
	public String welcomeToXMeme() {
		return "<html>\n" + "<header><title>XMEME</title></header>\n" + "<body>\n" + "<div>\n"
				+ "<h1>Hey there, Welcome to XMEME!</h1>\n" + "<ul>"
				+ "<li>Where you can see all sorts of memes...</li>"
				+ "<li>You need to register with a unique username before posting any memes.</li>"
				+ "<li>You can search memes using owner of the meme or captions.</li>"
				+ "<li>But remember! Only the admins have the right to delete and edit memes</li>"
				+ "<li>After user registration the admin(s) will activate a user.</li>"
				+ "<li>Once again, welcome to XMEM. ENJOY!</li>"
				+ "</ul>\n" + "</div>\n"
				+ "</body>\n" + "</html>";
	}
	
	
	@GetMapping(XMEME_API + GET_MEMES)
	public ResponseEntity<List<Meme>> getAllMemes(@Valid GetMemeRequest getMemeRequest) {
		List<Meme> memeList = memeService.getAllMemes();

		if (memeList.size() == 0 || memeList == null) {
			System.err.println("No memes present in the DB.");
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
		}
		
		if (getMemeRequest.getSearchFor() != null) {
			// return search results
			logger.info("Search parameter is not empty: " + getMemeRequest.getSearchFor());
			
			List<Meme> memeSearchList = memeService.getMemeBySearch(getMemeRequest.getSearchFor());
			return new ResponseEntity<>(memeSearchList, HttpStatus.OK);
		}
		
		// return latest 50 memes
		List<Meme> processedList = new ArrayList<>();
	    if (memeList.size() > 50) {

	      for (Meme meme : memeList) {
	        processedList.add(meme);

	      }
	      
	      logger.info("Returning latest 50 memes");
		  return new ResponseEntity<>(processedList.subList(memeList.size() - 50, memeList.size()), HttpStatus.OK);
	    } 

		// return entire all document as it is 
	    logger.info("Returning all memes");
	    logger.info(memeList);
		return new ResponseEntity<>(memeList, HttpStatus.OK);
	}

	@GetMapping(XMEME_API + GET_MEME + "/{memeId}")
	public ResponseEntity<Meme> getMeme(@PathVariable String memeId) {
		Meme meme = memeService.getMeme(memeId);
		logger.info("Meme fetched for Meme ID " +memeId + ": " +meme);
		
		if (meme == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(meme, HttpStatus.OK);
	}

	@PostMapping(XMEME_API + POST_MEME)
	public ResponseEntity<String> postMeme(@Valid @RequestBody GetMemeRequest getMemeResquest) {
		try {
			Meme meme = new Meme(getMemeResquest.getMemeId(), getMemeResquest.getOwner(), getMemeResquest.getCaption(),
					getMemeResquest.getUrl());
			
			// check for duplicate meme
			Meme memeResposne = memeService.getMeme(getMemeResquest.getMemeId());
			if (memeResposne.getMemeId() != null) {
				logger.warn("Meme cannot be posted. Meme with duplicate MEME ID found.");
				return new ResponseEntity<>("Sorry, your meme couldn't be posted.", HttpStatus.CONFLICT);
			}
			
			memeService.postMeme(meme);			
			logger.info("Meme posted, owner name: " +meme.getOwner() +".");
			return new ResponseEntity<>("Hey! Your Meme was posted.", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Something went wrong while this posting meme...");
			return new ResponseEntity<>("Meme couldn't be posted.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(XMEME_API + EDIT_MEME)
	public ResponseEntity<String> updateMeme(@RequestBody GetMemeRequest getMemeRequest) {
		try {
			Meme meme = new Meme(getMemeRequest.getMemeId(), getMemeRequest.getOwner(), getMemeRequest.getCaption(),
					getMemeRequest.getUrl());
	
			Meme memeResponse = memeService.getMeme(getMemeRequest.getMemeId());
			if (memeResponse.getMemeId() == null) {
				logger.warn("Meme cannot be edited. Meme does not exist.");
				return new ResponseEntity<>("Meme cannot be edited, meme does not exist", HttpStatus.NOT_FOUND);
			}
			
			memeService.editMeme(meme);
			logger.info("Meme edited successfully. Meme id: " +meme.getMemeId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Internal server error while editing meme...");
			return new ResponseEntity<>("Meme couldn't be edited!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
        return new ResponseEntity<>("Your meme was updated!", HttpStatus.OK);
	}

	@DeleteMapping(XMEME_API + DELETE_MEME + "/{memeId}")
	public ResponseEntity<String> deleteMeme(@PathVariable String memeId) {
		try {
			Meme meme = memeService.getMeme(memeId);
			
			if (meme == null || meme.getMemeId() == null) {
				logger.warn("Meme cannot be delete. Meme does not exist.");
				return new ResponseEntity<>("Deletion not possible, the user does not exist.", HttpStatus.NOT_FOUND);
			}
			
			memeService.deleteMeme(memeId);
			logger.info("Meme was delete. Meme id: " +memeId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Something went wrong while deleting meme...");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(XMEME_API + DELETE_ALL_MEMES)
	public ResponseEntity<String> deleteAllMemes() {	
		try {
			List<Meme> memes = memeService.getAllMemes();
			if (memes.size() == 0) {
				logger.warn("Database is empty!");
				return new ResponseEntity<>("Database is empty, nothing to delete!", HttpStatus.NO_CONTENT); 
			}
			
			memeService.deleteAllMemes();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Something went wrong while deleting all memes...");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
