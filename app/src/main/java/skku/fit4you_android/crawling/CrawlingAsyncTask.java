package skku.fit4you_android.crawling;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CrawlingAsyncTask extends AsyncTask<String,Integer,String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... voids) {
        String result="";//크롤링 결과 텍스트
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
            result += "title: "+cloth.title+"\nCost: "+cloth.cost+"\nClothType: "+cloth.clothType;
            if (clothType == 1 || clothType == 2) {
                result+="\nTop Cloth";
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
//                    Log.d("crawling:","Name: " + cloth.title);
//                    Log.d("crawling:","Cost: " + cloth.cost);
//                    Log.d("crawling:",cloth.clothType+"");
//
//                    Log.d("crawling:","type\t");
//                    Log.d("crawling:","length\t");
//                    Log.d("crawling:","shoulder\t");
//                    Log.d("crawling:","chest\t");
//                    Log.d("crawling:","sleeve");

                    for (int i = 0; i < cloth.topSize.size(); i++) {
//                        Log.d("crawling:",cloth.topSize.get(i).type + "\t");
//                        Log.d("crawling:",cloth.topSize.get(i).length + "\t");
//                        Log.d("crawling:",cloth.topSize.get(i).shoulder + "\t\t");
//                        Log.d("crawling:",cloth.topSize.get(i).chest + "\t");
//                        Log.d("crawling:",cloth.topSize.get(i).sleeve+"");
                        result += "\ntype: "+cloth.topSize.get(i).type+"\nlength: "+cloth.topSize.get(i).length+"\nshoulder: "+cloth.topSize.get(i).shoulder+
                                "\nchest: "+cloth.topSize.get(i).chest+"\nsleeve: "+cloth.topSize.get(i).sleeve;
                    }
                }
                else {
//                    Log.d("crawling:","No Size Info.");
                    cloth.topSize = null;
                    cloth.bottomSize = null;
                }
            }
            else {
                cloth.topSize = null;
                result+="\nBottom Cloth";
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
//                    Log.d("crawling:","Name: " + cloth.title);
//                    Log.d("crawling:","Cost: " + cloth.cost);
//                    Log.d("crawling:","Cloth Type: " + cloth.clothType);
//
//                    Log.d("crawling:","type\t");
//                    Log.d("crawling:","length\t");
//                    Log.d("crawling:","waist\t");
//                    Log.d("crawling:","thigh\t");
//                    Log.d("crawling:","rise\t");
//                    Log.d("crawling:","hem");

                    for (int i = 0; i < cloth.bottomSize.size(); i++) {
//                        Log.d("crawling:",cloth.bottomSize.get(i).type + "\t");
//                        Log.d("crawling:",cloth.bottomSize.get(i).length + "\t");
//                        Log.d("crawling:",cloth.bottomSize.get(i).waist + "\t");
//                        Log.d("crawling:",cloth.bottomSize.get(i).thigh + "\t");
//                        Log.d("crawling:",cloth.bottomSize.get(i).rise + "\t");
//                        Log.d("crawling:",cloth.bottomSize.get(i).hem+"");
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
            result += "\nURL: "+cloth.url;

        } catch (Exception e) {
//            Log.d("crawling:","No Size Info.");
            cloth.topSize = null;
            cloth.bottomSize = null;
            // e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
