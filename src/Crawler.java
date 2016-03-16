import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Crawler {

    private int maxCrawlDepth;
    private String[] urls;

    private HashMap<String, WebPage> webPages;

    public Crawler(int maxCrawlDepth, String[] urls) {
        this.maxCrawlDepth = maxCrawlDepth;
        setURLs(urls);
    }

    public void setURLs(String[] urls) {
        this.urls = urls;
    }

    public void startCrawling() {
        if (urls == null || urls.length == 0) {
            return;
        }

        webPages = new HashMap<>();

        for (String url : urls) {
            crawlURL(url, 0);
        }
    }

    private void crawlURL(String url, int currentDepth) {
        // Go through all links
        Queue<WebPage> bfs = new LinkedList<>();

        WebPage rootPage = createWebPageFromURL(url, currentDepth);
        bfs.add(rootPage);

        // BFS Implementation to go through links
        while (!bfs.isEmpty()) {
            WebPage webPage = bfs.remove();

            if (webPages.containsKey(webPage.getUrl()))
                continue;

            webPages.put(webPage.getUrl(), webPage);

            if (webPage.getCurrentDepth() < maxCrawlDepth) {
                Elements links = webPage.getLinks();
                for (Element link : links) {
                    String href = link.attr("href");
                    if (href.length() > 0 && (href.charAt(0) == '/' || href.charAt(0) == '#')) {
                        href = webPage.getRootUrlName() + href;
                    }
                    if (webPages.containsKey(href))
                        continue;

                    WebPage newPage = createWebPageFromURL(href, webPage.getCurrentDepth() + 1);

                    if (newPage != null)
                        bfs.add(newPage);
                }
            }
            System.out.println("Queue Size = " + bfs.size());

        }

//            for (Element link : links) {
//                StringBuilder sb = new StringBuilder();
//                String href = link.attr("href");
//                if (href.length() > 0 && (href.charAt(0) == '/' || href.charAt(0) == '#')) {
//                    String rootUrl = webPage.getRootUrlName();
//                    href = rootUrl + href;
//                }
//                System.out.println(href + " " + (currentDepth + 1));
//                crawlURL(href, currentDepth + 1);
//            }

    }


    private WebPage createWebPageFromURL(String url, int currentDepth) {
        Document doc = null;
        WebPage webPage = null;
        // Get the html from site
        try {
            doc = Jsoup.connect(url).get();
        } catch (IllegalArgumentException m) {
            System.out.println("Url is malformed" + url);
            m.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Could not connect to " + url);
            e.printStackTrace();
        }

        // If we were successfully able to get the html
        if (doc != null && doc.body() != null) {
            // Get all the links on the page
            String title = doc.title();
            Elements links = doc.body().getElementsByTag("a");
            webPage = new WebPage(title, url, links, currentDepth);
        }

        return webPage;
    }

    public void printWebPages(String fileName) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileName);
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found. Printerwriter null");
        }

        if (printWriter != null) {
            for (String key : webPages.keySet()) {
                printWriter.println(webPages.get(key).toString());
            }
            printWriter.close();
        }
    }
}
