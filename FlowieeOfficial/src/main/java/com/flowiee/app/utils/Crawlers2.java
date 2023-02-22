package com.flowiee.app.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.flowiee.app.model.sales.Product;

import java.util.ArrayList;

public class Crawlers2 {
    private static final String URL_Juno_HomePage = "https://juno.vn/collections/quan-ao?itm_source=homepage&itm_medium=menu&itm_campaign=normal&itm_content=quanao";
    private static String URL_Juno_DetailPage = "";
    private static String Base_URL = "https://juno.vn";

    Document homePage;
    Document detailPage;

    ArrayList<String> list_URL_Detail = new ArrayList<>();
    ArrayList<Product> listFullInformation = new ArrayList<Product>();

    public ArrayList<String> getListCode() {
        ArrayList<String> listCode = new ArrayList<String>();
        //
        try {
            homePage = Jsoup.connect(URL_Juno_HomePage).get();
            Elements names = homePage.select("h3[class=pro-name]");
            // Lấy danh sách link trang chi tiết
            for (Element name : names) {
                Elements links = name.select("SecurityConfig");
                URL_Juno_DetailPage = Base_URL + links.attr("href");
                list_URL_Detail.add(Base_URL + links.attr("href"));
            }
            // Vào trang chi tiết lấy mã sản phẩm
            for (String link : list_URL_Detail) {
                detailPage = Jsoup.connect(link).get();
                Element code = detailPage.select("span[class=infoaf]").first();
                listCode.add(code.text());
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        return listCode;
    }

    public ArrayList<String> getListName() {
        ArrayList<String> list = new ArrayList<String>();
        //
        try {
            homePage = Jsoup.connect(URL_Juno_HomePage).get();
            org.jsoup.select.Elements names = homePage.select("h3[class=pro-name]");
            for (Element name : names) {
                list.add(name.text());
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        return list;
    }

    public ArrayList<String> getListPrice() {
        ArrayList<String> list = new ArrayList<String>();
        //
        try {
            Document doc = Jsoup.connect(URL_Juno_HomePage).get();
            Elements prices = doc.select("p[class=pro-price highlight]");
            for (Element price : prices) {
                list.add(price.text());
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        return list;
    }

    public ArrayList<String> getListImage() {
        ArrayList<String> list = new ArrayList<String>();
        //
        try {
            int i = 0;
            for (String link : list_URL_Detail) {
                detailPage = Jsoup.connect(link).get();
                Element code = detailPage.select("span[class=infoaf]").first();
                Elements images = detailPage.getElementsByClass("removeImgMobile hidden-xs img-fluid img-responsive");
                for (Element image : images) {

                    //char[] color = image.absUrl("src").substring(47).toCharArray();

                    //String colorFinal = "";

//                    for (int c = 0; c < color.length; c++){
//                        colorFinal += color[i];
//                        if (color[c] == '_'){
//                            continue;
//                        }
//                    }

                    //System.out.println("Color: " + colorFinal);

                    System.out.println(code.text() + "\t" + image.absUrl("src"));
                    //list.add(image.absUrl("src"));
                    i++;
                }
            }
            System.out.println(i);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        return list;
    }

    ArrayList<String> listCode = getListCode();
    ArrayList<String> listName = getListName();
    ArrayList<String> listPrice = getListPrice();
    //ArrayList<String> listImage = getListImage();

    public ArrayList<Product> getFullInformation() {
        //
        int sizeList = listCode.size();
        for (int i = 0; i < sizeList; i++) {
            Product product = new Product();
            product.setCode(listCode.get(i));
            product.setName(listName.get(i));
            product.setPrice(Double.parseDouble(listPrice.get(i)));
            listFullInformation.add(product);
        }
        //
        return listFullInformation;
    }
}
