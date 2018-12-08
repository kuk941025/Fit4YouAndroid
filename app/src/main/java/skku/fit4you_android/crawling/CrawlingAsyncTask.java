package skku.fit4you_android.crawling;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class CrawlingAsyncTask extends AsyncTask<String,Integer,Clothing> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Clothing doInBackground(String... voids) {
        ArrayList<TopSize> topSize;
        ArrayList<BottomSize> bottomSize;
        Clothing cloth =null;
        int length, shoulder, chest, sleeve, waist,thigh, rise, hem;
        String result="";//크롤링 결과 텍스트

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

            String str = type.get(1).attr("href");
            int clothType = (int)(str.charAt(str.length()-1)) - 48;
            cloth = new Clothing(clothType, title, cost, src);
            result += "title: "+cloth.title+"\nCost: "+cloth.cost+"\nClothType: "+cloth.clothType;
            if (clothType == 1 || clothType == 2) {
                result+="\nTop Cloth";
                cloth.topSize = new ArrayList<TopSize>();
                cloth.bottomSize = null;
                for(int i = 1; i < size.size(); i++) {
                    length = (int)Double.parseDouble(nsize.get(4*(i-1)).text());
                    shoulder = (int)Double.parseDouble(nsize.get(4*(i-1) + 1).text());
                    chest = (int)Double.parseDouble(nsize.get(4*(i-1) + 2).text());
                    sleeve = (int)Double.parseDouble(nsize.get(4*(i-1) + 3).text());
                    TopSize temp = new TopSize(size.get(i).text(),length,shoulder,chest,sleeve);
                    cloth.topSize.add(temp);
                }

                if (size.size() != 0) {
                    for (int i = 0; i < cloth.topSize.size(); i++) {
                        result += "\ntype: "+cloth.topSize.get(i).type+"\nlength: "+cloth.topSize.get(i).length+"\nshoulder: "+cloth.topSize.get(i).shoulder+
                                "\nchest: "+cloth.topSize.get(i).chest+"\nsleeve: "+cloth.topSize.get(i).sleeve;
                    }
                }
                else {
                    Log.d("crawling:","No Size Info.");
                    cloth.topSize = null;
                    cloth.bottomSize = null;
                }
            }
            else {
                cloth.topSize = null;
                cloth.bottomSize = new ArrayList<BottomSize>();
                result+="\nBottom Cloth";
                for(int i = 1; i < size.size(); i++) {
                    length = (int)Double.parseDouble(nsize.get(5*(i-1)).text());
                    waist = (int)Double.parseDouble(nsize.get(5*(i-1) + 1).text());
                    thigh = (int)Double.parseDouble(nsize.get(5*(i-1) + 2).text());
                    rise = (int)Double.parseDouble(nsize.get(5*(i-1) + 3).text());
                    hem = (int)Double.parseDouble(nsize.get(5*(i-1) + 4).text());
                    BottomSize temp = new BottomSize(size.get(i).text(),length,waist,thigh,rise,hem);
                    cloth.bottomSize.add(temp);
                }

                if (size.size() != 0) {

                    for (int i = 0; i < cloth.bottomSize.size(); i++) {
                        result += "\ntype: "+cloth.bottomSize.get(i).type+"\nlength: "+cloth.bottomSize.get(i).length+"\nwaist: "+cloth.bottomSize.get(i).waist+
                                "\nthigh: "+cloth.bottomSize.get(i).thigh+"\nrise: "+cloth.bottomSize.get(i).rise+"\nhem: "+cloth.bottomSize.get(i).hem;
                    }
                }
                else {
//                    Log.d("crawling:","No Size Info.");
                    cloth.topSize = null;
                    cloth.bottomSize = null;
                }
            }

//            Log.d("crawling:",cloth.url);
//            result += "\nURL: "+cloth.url;

        } catch (Exception e) {
            Log.d("crawling:","No Size Info.");
            e.printStackTrace();
        }
        Log.d("result: ",result);
        return cloth;
    }

    @Override
    protected void onPostExecute(Clothing cloth) {
        super.onPostExecute(cloth);
    }
}
