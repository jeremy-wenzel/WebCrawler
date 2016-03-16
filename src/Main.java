/**
 * Created by Jeremy on 3/15/2016.
 */
public class Main {

    private static final int MAX_CRAWL_DEPTH = 2;

    public static void main(String[] args) {
//        String[] urls = {"http://www.jwenzel.me"};
        String[] urls = {
//                "http://www.jwenzel.me"
                "https://blog.logos.com/?utm_source=logos.com&utm_medium=website&utm_content=logosfooterlink&utm_campaign=logos"
//                "https://www.akingump.com/",
//                "http://www.rakuten.co.jp/",
//                "https://www.logos.com/",
//                "https://www.4moms.com",
//                "https://www.2valor.com",
//                "https://www.appdynamics.com/",
//                "https://homesteadfunding.com/"
        };
        Crawler crawler = new Crawler(MAX_CRAWL_DEPTH, urls);
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.startTimer();
        crawler.startCrawling();
        String fileName = System.getProperty("user.home")+"/student.csv";
        crawler.printWebPages(fileName);
        stopwatch.stopTimer();
        System.out.println("Time = " + stopwatch.toString());
    }
}
