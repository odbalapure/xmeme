package com.xmeme.exchange;

import javax.validation.constraints.NotNull;

public class GetMemeRequest {

	@NotNull
	private String memeId;

	@NotNull
	private String owner;

	@NotNull
	private String caption;

	@NotNull
	private String url;

	private String searchFor;

	public String getMemeId() {
		return memeId;
	}

	public void setMemeId(String memeId) {
		this.memeId = memeId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

}
