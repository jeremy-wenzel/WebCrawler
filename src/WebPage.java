import org.jsoup.select.Elements;

public class WebPage {

    private String title;
    private String url;
    private Elements links;
    private int currentDepth;

    public WebPage (String title, String url, Elements links, int currentDepth) {
        this.title = title;
        this.url = url;
        this.links = links;
        this.currentDepth = currentDepth;
    }

    public String getTitle() {return title;}

    public String getUrl() {return url;}

    public Elements getLinks() {return links;}

    public int getCurrentDepth() {return currentDepth;}

    /**
     * This method extracts the root URL from any correctly input URL. A sample input URL could be
     * "http://www.jwenzel.me/projects". This method would extract the root URL, including the http protocol
     * which would result in "http://www.jwenzel.me"
     *
     * The url must contain the "http" protocol in the first 4 or 5 (for https) spaces
     *
     * @return The root URL of the full URL
     */
    public String getRootUrlName() {
        // URL is too short
        if (url == null || url.length() < 6) {
            throw new IllegalArgumentException("URL null or too short");
        }
        // URL is not a valid one
        if (!url.substring(0, 4).equals("http") && !url.substring(0, 5).equals("https")) {
            throw new IllegalArgumentException("Invalid Url. Does not contain http protocol: " + url);
        }

        StringBuilder sb = new StringBuilder();

        int i = 0;
        int numSlashes = 0;
        // Go through the URL only to the end of the Top Level Domain (TLD)
        while (i < url.length()) {
            if (url.charAt(i) == '/') {
                ++numSlashes;
                if (numSlashes > 2)
                    break;
            }
            sb.append(url.charAt(i));
            ++i;
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(title + " ");
        sb.append(url);

        return sb.toString();
    }
}
