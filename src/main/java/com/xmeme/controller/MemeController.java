package com.xmeme.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import com.xmeme.repositoryservice.MemeRepositoryService;
import com.xmeme.service.MemeService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class MemeController {
	
	@Autowired 
	private MemeRepositoryService memeRepositoryService;
	
	@Autowired
	private MemeService memeService;
	
	/**
	 * Print a welcome message and list of API end points
	 */
	@GetMapping("")
	public String welcomeToXMeme() {
		return "<html>\n" + "<header><title>XMEME</title></header>\n" + "<body>\n" + "<div>\n"
				+ "<h1>Hi there, Welcome to XMEME!</h1>\n" + "<ul>"
				+ "<li>List all the memes - localhost:8081/xmeme/memes</li>"
				+ "<li>Search meme using owner - localhost:8081/xmeme/memes?owner=Om</li>"
				+ "<li>Search meme using caption - localhost:8081/xmeme/memes?caption=COVID+19</li>"
				+ "<li>Search meme using meme id - localhost:8081/xmeme/meme/007</li>" + "</ul>\n" + "</div>\n"
				+ "</body>\n" + "</html>";
	}
	
	
	@GetMapping("/memes")
	public ResponseEntity<List<Meme>> getAllMemes(@Valid GetMemeRequest getMemeRequest) {
		List<Meme> memeList = memeRepositoryService.findAllMemes();

		if (memeList.size() == 0 || memeList == null) {
			System.err.println("No memes present in the DB.");
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
		}
		
		if (getMemeRequest.getSearchFor() != null) {
			// return search results
			System.out.println("Search parameter is not empty: " + getMemeRequest.getSearchFor());

			List<Meme> memeSearchList = memeService.getMemeBySearch(getMemeRequest.getSearchFor());
			return new ResponseEntity<>(memeSearchList, HttpStatus.OK);
		}

		// return entire all document as it is 
		return new ResponseEntity<>(memeList, HttpStatus.OK);
	}

	@GetMapping("/meme/{memeId}")
	public ResponseEntity<Meme> getMeme(@PathVariable String memeId) {
		Meme meme = memeRepositoryService.findMeme(memeId);

		if (meme == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(meme, HttpStatus.OK);
	}

	@PostMapping("/post")
	public ResponseEntity<String> postMeme(@Valid @RequestBody GetMemeRequest getMemeResquest) {
		try {
			Meme meme = new Meme(getMemeResquest.getMemeId(), getMemeResquest.getOwner(), getMemeResquest.getCaption(),
					getMemeResquest.getUrl());
			
			// check for duplicate meme
			Meme memeResposne = memeRepositoryService.findMeme(getMemeResquest.getMemeId());
			if (memeResposne.getMemeId() != null) {
				System.err.println("Meme cannot be posted. Meme with duplicate MEME ID found.");
				return new ResponseEntity<>("Sorry, your meme cannot be posted.", HttpStatus.CONFLICT);
			}
			
			memeRepositoryService.postMeme(meme);				
			return new ResponseEntity<>("Hey! Your Meme was posted.", HttpStatus.CREATED);
		} catch (Exception e) {
			System.err.println("Internal server error while posting meme...");
			return new ResponseEntity<>("Meme couldn't be posted.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/edit")
	public ResponseEntity<String> updateMeme(@RequestBody GetMemeRequest getMemeRequest) {
		try {
			Meme meme = new Meme(getMemeRequest.getMemeId(), getMemeRequest.getOwner(), getMemeRequest.getCaption(),
					getMemeRequest.getUrl());
	
			Meme memeResponse = memeRepositoryService.findMeme(getMemeRequest.getMemeId());
			if (memeResponse.getMemeId() == null) {
				return new ResponseEntity<>("Meme cannot be edited, meme does not exist", HttpStatus.NOT_FOUND);
			}
			
			memeRepositoryService.editMeme(meme);
		} catch (Exception e) {
			System.err.println("Internal server error while editing meme...");
			return new ResponseEntity<>("Meme couldn't be edited!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
        return new ResponseEntity<>("Your meme was updated!", HttpStatus.OK);
	}

	@DeleteMapping("/delete/{memeId}")
	public ResponseEntity<String> deleteMeme(@PathVariable String memeId) {
		try {
			Meme meme = memeRepositoryService.findMeme(memeId);
			
			if (meme == null || meme.getMemeId() == null) {
				return new ResponseEntity<>("Deletion not possible, the user does not exist.", HttpStatus.NOT_FOUND);
			}
			
			memeRepositoryService.deleteMeme(memeId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.err.println("Internal server error while deleting meme...");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteall")
	public ResponseEntity<String> deleteAllTutorials() {	
		try {
			List<Meme> memes = memeRepositoryService.findAllMemes();
			if (memes.size() == 0) {
				return new ResponseEntity<>("Database is empty, nothing to delete!", HttpStatus.NO_CONTENT); 
			}
			
			memeRepositoryService.deleteAllMemes();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.err.println("Internal server error while deleting all memes...");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
