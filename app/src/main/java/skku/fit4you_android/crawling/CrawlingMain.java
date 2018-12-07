package skku.fit4you_android.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CrawlingMain {

	public static void main(String[] args) {
		Clothing cloth = new Clothing();
		
		try {
			// String url1 = "https://store.musinsa.com/app/product/detail/312174/0"; // S, M, L, XL
			// String url2 = "https://store.musinsa.com/app/product/detail/407201/0"; // 85 ~ 130
			// String url3 = "https://store.musinsa.com/app/product/detail/836498/0"; // No Size Info.
			// String url4 = "https://store.musinsa.com/app/product/detail/834307/0"; // Different Format
			// String url5 = "https://store.musinsa.com/app/product/detail/900297/0"; // Outer
			String url6 = "https://store.musinsa.com/app/product/detail/67485/0"; // bottom
			Document doc = Jsoup.connect(url6).get();
			Elements eTitle = doc.select("span.product_title span");
			Elements eCost = doc.select("span.product_article_price");
			Elements type = doc.select("p.item_categories a[href]");
			Elements size = doc.select("table.table_th_grey tbody th");
			Elements nsize = doc.select("table.table_th_grey tbody td.goods_size_val");
			Elements img = doc.select("div.product_img_basic img");
			
			String title = eTitle.get(0).text();
			String cost = eCost.get(eCost.size()-1).text();
			String src = "https://" + img.attr("src");
			cloth.title = title;
			cloth.cost = cost;
			cloth.url = src;
			
			String str = type.get(1).attr("href");
			int clothType = (int)(str.charAt(str.length()-1)) - 48;
			cloth.clothType = clothType;
			
			if (clothType == 1 || clothType == 2) {
				cloth.bottomSize = null;
				for(int i = 1; i < size.size(); i++) {
					TopSize temp = new TopSize();
					temp.type = size.get(i).text();
					temp.length = (int)Double.parseDouble(nsize.get(4*(i-1)).text());
					temp.shoulder = (int)Double.parseDouble(nsize.get(4*(i-1) + 1).text());
					temp.chest = (int)Double.parseDouble(nsize.get(4*(i-1) + 2).text());
					temp.sleeve = (int)Double.parseDouble(nsize.get(4*(i-1) + 3).text());
				
					cloth.topSize.add(temp);
				}
			
				if (size.size() != 0) {
					System.out.println(cloth.clothType);
			
					System.out.print("type\t");
					System.out.print("length\t");
					System.out.print("shoulder\t");
					System.out.print("chest\t");
					System.out.println("sleeve");
			
					for (int i = 0; i < cloth.topSize.size(); i++) {
						System.out.print(cloth.topSize.get(i).type + "\t");
						System.out.print(cloth.topSize.get(i).length + "\t");
						System.out.print(cloth.topSize.get(i).shoulder + "\t\t");
						System.out.print(cloth.topSize.get(i).chest + "\t");
						System.out.println(cloth.topSize.get(i).sleeve);
					}
				}
				else {
					System.out.println("No Size Info.");
					cloth.topSize = null;
					cloth.bottomSize = null;
				}
			}
			else {
				cloth.topSize = null;
				for(int i = 1; i < size.size(); i++) {
					BottomSize temp = new BottomSize();
					temp.type = size.get(i).text();
					temp.length = (int)Double.parseDouble(nsize.get(5*(i-1)).text());
					temp.waist = (int)Double.parseDouble(nsize.get(5*(i-1) + 1).text());
					temp.thigh = (int)Double.parseDouble(nsize.get(5*(i-1) + 2).text());
					temp.rise = (int)Double.parseDouble(nsize.get(5*(i-1) + 3).text());
					temp.hem = (int)Double.parseDouble(nsize.get(5*(i-1) + 4).text());
				
					cloth.bottomSize.add(temp);
				}
			
				if (size.size() != 0) {
					System.out.println("Name: " + cloth.title);
					System.out.println("Cost: " + cloth.cost);
					System.out.println("Cloth Type: " + cloth.clothType);
			
					System.out.print("type\t");
					System.out.print("length\t");
					System.out.print("waist\t");
					System.out.print("thigh\t");
					System.out.print("rise\t");
					System.out.println("hem");
			
					for (int i = 0; i < cloth.bottomSize.size(); i++) {
						System.out.print(cloth.bottomSize.get(i).type + "\t");
						System.out.print(cloth.bottomSize.get(i).length + "\t");
						System.out.print(cloth.bottomSize.get(i).waist + "\t");
						System.out.print(cloth.bottomSize.get(i).thigh + "\t");
						System.out.print(cloth.bottomSize.get(i).rise + "\t");
						System.out.println(cloth.bottomSize.get(i).hem);
					}
				}
				else {
					System.out.println("No Size Info.");
					cloth.topSize = null;
					cloth.bottomSize = null;
				}
			}
			
			System.out.println(cloth.url);
			
		} catch (Exception e) {
			System.out.println("No Size Info.");
			cloth.topSize = null;
			cloth.bottomSize = null;
			// e.printStackTrace();
		}
	}

}
