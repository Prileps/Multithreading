import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private final static String SITE_ROOT = "https://skillbox.ru/";
    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();

        Node sitemapRoot = new Node(SITE_ROOT);

        new ForkJoinPool().invoke(new NodeRecursiveAction(sitemapRoot));

        FileOutputStream stream = new FileOutputStream("src/main/resources/sitemap.txt");
        String result = createStringForFile(sitemapRoot, 0);

        stream.write(result.getBytes());
        stream.flush();
        stream.close();

        System.out.println("Время записи: " + (System.currentTimeMillis() - start) / 60000 + " мин");
    }


    public static String createStringForFile(Node node, int depth) {
        String tabs = String.join("", Collections.nCopies(depth, "\t"));

        StringBuilder result = new StringBuilder(tabs + node.getUrl());
        node.getChildren().forEach(child -> result.append("\n").append(createStringForFile(child, depth + 1)));

        return result.toString();
    }
}