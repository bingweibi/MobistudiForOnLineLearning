package czd.mobistudi.md.model.manager;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import czd.mobistudi.md.model.entity.BlogListBean;

/**
 * Created by thugwar on 2018/3/17.
 */

public class BlogListDataManager {

    public static List<BlogListBean> getBlogListBeans(Document document) {
        List<BlogListBean> blogListBeans = new ArrayList<>();
        Elements blogElements = document.select("div#archive .floated-thumb");
        for (Element blog : blogElements) {
            Element tmp1 = blog.select("div.post-thumb a").first();
            String href = tmp1.attr("href");
            String title = tmp1.attr("title");
            String image = tmp1.select("img").first().attr("src");
            Element tmp2 = blog.select("div.post-meta").first();
            String summary = tmp2.select("span").text();
            String category = tmp2.select("a[rel]").text();
            String time = tmp2.select("p").html().split("<br>")[1].trim().substring(0, 10);

            blogListBeans.add(new BlogListBean(image,title,summary,time,category,href));
        }
        return  blogListBeans;
    }

}
