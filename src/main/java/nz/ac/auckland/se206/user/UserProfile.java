package nz.ac.auckland.se206.user;

import java.util.ArrayList;

public class UserProfile {
	/**
	 * This Class will store user statistics and allow user statistics to be changed
	 * and updated
	 */

	private String accountName;
	private Integer numOfWin;
	private Integer numOfLoss;
	private ArrayList<String> wordsHistory;
	private String bestRecord;
	private Integer score;

	public UserProfile(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * This method will update the record automatically based on the value of the
	 * records
	 * 
	 * @param record String which should be inputed each time the game is run
	 * 
	 */
	public void updateRecord(String record) {
		// automatically update the record based on its value
		if (this.bestRecord == null) {
			this.bestRecord = record;
		} else {
			if (Integer.parseInt(this.bestRecord) > Integer.parseInt(record)) {
				this.bestRecord = record;
			}
		}
	}

	public void updateWordsHistory(String currentWord) {
		wordsHistory.add(currentWord);
	}

	public void wonTheGame() {
		this.numOfWin = this.numOfWin + 1;
	}

	public void lostTheGame() {
		this.numOfLoss = this.numOfLoss + 1;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getNumOfWin() {
		return numOfWin;
	}

	public void setNumOfWin(Integer numOfWin) {
		this.numOfWin = numOfWin;
	}

	public Integer getNumOfLost() {
		return numOfLoss;
	}

	public void setNumOfLost(Integer numOfLost) {
		this.numOfLoss = numOfLost;
	}

	public ArrayList<String> getWordsHistory() {
		return wordsHistory;
	}

	public void setWordsHistory(ArrayList<String> wordsHistory) {
		this.wordsHistory = wordsHistory;
	}

	public String getBestRecord() {
		return bestRecord;
	}

	public void setBestRecord(String bestRecord) {
		this.bestRecord = bestRecord;
	}

	public Integer getScore() {
		this.score = numOfWin - numOfLoss;
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
