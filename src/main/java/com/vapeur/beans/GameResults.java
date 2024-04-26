package com.vapeur.beans;

import java.util.List;

public class GameResults {
	
	private List<Game> games;
	private int totalResults;
	
	public GameResults() {
		
	}
	
	public GameResults(List<Game> games, int totalResults) {
		super();
		setGames(games);
		setTotalResults(totalResults);
	}
	

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	@Override
	public String toString() {
		return "GameResults [games=" + games + ", totalResults=" + totalResults + "]";
	}

}
