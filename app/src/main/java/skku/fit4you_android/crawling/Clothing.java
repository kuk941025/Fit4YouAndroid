package skku.fit4you_android.crawling;

import java.util.ArrayList;

public class Clothing {
	public int clothType; //1 == 상의, 2 == 아우터, 3 == 하의
	public String title, cost, url;
	public ArrayList<TopSize> topSize;
	public ArrayList<BottomSize> bottomSize;

	public Clothing(int clothType, String title, String cost, String url) {
		this.clothType = clothType;
		this.title = title;
		this.cost = cost;
		this.url = url;
	}

}
