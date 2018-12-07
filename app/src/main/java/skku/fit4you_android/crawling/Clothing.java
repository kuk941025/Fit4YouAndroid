package skku.fit4you_android.crawling;

import java.util.ArrayList;

public class Clothing {
	int clothType; //1 == 상의, 2 == 아우터, 3 == 하의
	String title;
	String cost;
	String url;
	ArrayList<TopSize> topSize;
	ArrayList<BottomSize> bottomSize;
	
	public Clothing(){
		topSize = new ArrayList<TopSize>();
		bottomSize = new ArrayList<BottomSize>();
	}
}
