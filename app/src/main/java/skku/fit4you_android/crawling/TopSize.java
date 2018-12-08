package skku.fit4you_android.crawling;

public class TopSize {
	public String type;
	public int length, shoulder, chest,sleeve;

	public TopSize(String type, int length, int shoulder, int chest, int sleeve) {
		this.type = type;
		this.length = length;
		this.shoulder = shoulder;
		this.chest = chest;
		this.sleeve = sleeve;
	}
}
