package flappyBird;

import java.awt.Rectangle;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

final class ScoreArchive {
	private List<Integer> scoreList;
	private static ScoreArchive s = new ScoreArchive();
	private String source;
	private String maxVal;
	private ScoreArchive(){
		scoreList = new ArrayList<Integer>();
	}
	
	public static ScoreArchive getReference() {
		return s;
	}
	public void addScore(int sc){
		scoreList.add(sc);
	}
	
	/**Return the Max Score in the game**/
	public int getMax(){ //find the max Score
		int max = Integer.MIN_VALUE;
	    for(int i=0; i<scoreList.size(); i++){
	        if(scoreList.get(i) > max){
	            max = scoreList.get(i);
	        }
	    }
	    return max;
	}
	
	public String toString(){ //Print the all Resule
		for (int i = 0; i < scoreList.size(); i++) {
			source += " " + scoreList.get(i);
	    }
	    return source;
	}
}
