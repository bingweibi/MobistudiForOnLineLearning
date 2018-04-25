package czd.mobistudi.md.model.manager;

import org.jsoup.nodes.Document;

import czd.mobistudi.md.model.entity.BlogDetailBean;

/**
 * Created by thugwar on 2018/3/18.
 */

public class BlogDetailDataManager {

    public static BlogDetailBean getBlogDetailBean(Document document) {
        String title = document.select("h1").first().text();
        String content = document.select("div.entry").html();
        return new BlogDetailBean(title,content);
    }
}
