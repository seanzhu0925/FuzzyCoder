package cse308;

public class Question {
	public String courseName;
	public String type;
	public String title;
	public String answer;
	public String score;
	public String contentA;
	public String contentB;
	public String contentC;
	public String contentD;
	public String contentE;
	public Question(String type, String title, String answer, String score, String contentA, String contentB, String contentC, String contentD, String contentE){
		this.type = type;
		this.title = title;
		this.answer = answer;
		this.score = score;
		this.contentA = contentA;
		this.contentB = contentB;
		this.contentC = contentC;
		this.contentD = contentD;
		this.contentE = contentE;
	}
	public Question(String type, String title, String answer, String score){
		this.type = type;
		this.title = title;
		this.answer = answer;
		this.score = score;
	}
	public String getTitle(){
		return title;
	}
	public String getType(){
		return type;
	}
	public String getAnswer(){
		return answer;
	}
	public String getScore(){
		return score;
	}
	public String getContentA(){
		return contentA;
	}
	public String getContentB(){
		return contentB;
	}
	public String getContentC(){
		return contentC;
	}
	public String getContentD(){
		return contentD;
	}
	public String getContentE(){
		return contentE;
	}
}
