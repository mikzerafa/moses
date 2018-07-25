package moses;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Moses {
    /* on PC we have...
       1) Web Crawler
       2) Torrent Handler
       3) Storage Manager
       4) phonePC Manager 
       5) Folder Manager
       6) Folder
       7) Video Manager
            7.2) Video Viewer
            7.3) Video Controller
       8) Video   
       9) Series Manager
           9.2) seriesFinder
           9.3) seriesInfoFinder
           9.4) seriesImageFinder
       10) series //no. of seasons, finished or not, episode length, list of episodes, episode names, episode description, Series Image
    `      10.1) Season // no. , image, episode list
        `  10.2) Episode // no., name, info, rating, isAvailable, releaseDate, isDownloaded, pathtoFile 
       11) Film Manager
       12) Film
            12.2) FilmFinder
            12.3) FilmInfoFinder
            12.4) FilmImageFinder
           
        
        
     
    */
    
    /*
        On Mobile We Need To Send This Data To Phone
    
    */
    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        //Template : rush%20hour/0/99/0/
        /*
        System.out.println("What do you wanna watch?");  
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String film = reader.readLine();
        String search = film.replaceAll(" ", "%20");
        search = search + "/0/99/0"
        
        System.out.println("Entering this in search " + search);
        //Remove the last %20
        WebCrawler spider = new WebCrawler("https://pirateproxy.mx/search/" + search);
        //spider.connect("https://pirateproxy.mx");
        //spider.printHtml();
        //spider.searchTest();
        //spider.printHtml();
        
        System.out.println("Getting first Magnet :" );
        String magnet[] = new String[5];
        for(int index = 0; index < magnet.length; index++)
        {
            magnet[index] = spider.pirateBayMagnet(index + 1);
            System.out.println("magnet #" + (index+ 1) + "  " + magnet[index]);
        }
        
        //how toWebCrawler download = new WebCrawler(magnet);
        
        //WebCrawler spider2 = new WebCrawler("https://pirateproxy.mx/search/rush%20hour/0/99/0/"+magnet);
        //spider2.printPage();
        */
        

        String google = "https://www.google.com";
        String wiki = "https://www.wikipedia.org";
        String series[] = {"game of thrones", "House", "scrubs", "westworld", "prison break"};
        WebCrawler spider = new WebCrawler(wiki);
        String results = "";
        for(int num = 4; num < series.length; num++)
        {
            results = spider.wikiSearch("list of "+ series[num] + " episodes");
            System.out.println("searching for : " + series[num]);
            results = results.substring(results.indexOf("overall	No. in"));
            System.out.println(results);
        }
        
        String season[] = results.split("code");
        
        System.out.println("\n\n\nSeason 1 Extraction: \n");
        System.out.println(season[1]);
        
        //look for lines beginning with numbers in sequence, see what number they have, and complete the sequence.

        //WebCrawler spider2 = new WebCrawler(newUrl);
        //spider2.printPage();




    }
}
