
package moses;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.Credentials;

//Purpose of this class is to get data/interact with websites as Generically as possible
public final class WebCrawler 
{
    private String link =  "";
    public String contents;
    private WebClient webClient;
    private HtmlPage page;
    
    WebCrawler(String link) throws IOException
    {
        this.link = link;
        setupPage(link);
        
    }
    
    public void printHtml()
    {
        System.out.println("---------");
        System.out.println("html");
        System.out.println("---------");
        System.out.println(this.page.asXml());
        System.out.println("---------");
    }
    
    public String pirateBayMagnetTest() throws MalformedURLException
    {
        HtmlAnchor magnetLink = this.page.getFirstByXPath("//table[@id='searchResult']/tbody/tr/td/a");
        //System.out.println(magnetLink.asXml());
       // magnetLink.openLinkInNewWindow();
       return magnetXMLtoString(magnetLink.asXml());
    }
    
    public String magnetXMLtoString(String xml)
    {
        int start = xml.indexOf("magnet");
        int end = xml.indexOf("\"", start);
        return xml.substring(start, end);
    }
    
    public String pirateBayMagnet(int index)
    {
        
        String indexPart ="[" + index + "]";
        HtmlAnchor magnetLink = this.page.getFirstByXPath("//table[@id='searchResult']/tbody/tr"+indexPart+"/td/a");
        return magnetXMLtoString(magnetLink.asXml());
    }
    
    public String wikiSearch(String search) throws IOException
    {
        HtmlInput inputField = this.page.getFirstByXPath("//input[@id='searchInput']");
        inputField.setValueAttribute(search);
        HtmlButton searchBtn = this.page.getFirstByXPath("//button[@class='pure-button pure-button-primary-progressive']");
        HtmlPage page2 = searchBtn.click();
        return page2.asText();
    }
    
    public String googleSearch(String search) throws IOException, InterruptedException, IllegalMonitorStateException
    {
        HtmlInput inputField = this.page.getFirstByXPath("//input[@id='lst-ib']");
        inputField.setValueAttribute(search);
        System.out.println(this.page.getBaseURI());
        System.out.println("Searching google for " + search + "...");
        HtmlInput searchBtn = this.page.getFirstByXPath("//input[@name='btnK']");
        HtmlPage page2 = searchBtn.click();
        String results = page2.asText();
        
        //updatePage();
        return results;
    }
    
    public int googleNumberOfSeasons(String series) throws IOException, InterruptedException
    {
        String output = googleSearch(series + " number of seasons");
        System.out.println(output);
        return 0;
    }
    
    public void searchTest() throws AWTException, IOException
    {
        HtmlInput inputField = this.page.getFirstByXPath("//p[@id='inp']/input");
        //printHtml();
        inputField.setTextContent("Game of Thrones");
        HtmlInput sbmtBtn = this.page.getFirstByXPath("//p[@id='subm']/input"); ////input[@accesskey='s']
        //JOptionPane.showInputDialog(null, inputField.getAccessKeyAttribute(), inputField.getTextContent(), 0);
        System.out.println(sbmtBtn.toString());
        //printPage();
        //printPage();
        sbmtBtn.click();
        updatePage();
     
    }

    public final void setupPage(String webpage) throws IOException
    {
      this.webClient = new WebClient(BrowserVersion.CHROME);
      faster();
      stopWithTheCssErrors();
      this.page = webClient.getPage(webpage);
      this.contents = this.page.asText();
    }
    
    public void faster()
    {
       // this.webClient.getOptions().setJavaScriptEnabled(false);
        this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
        this.webClient.getOptions().setCssEnabled(false);
    }
    
    public void stopWithTheCssErrors()
    {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF); 
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
    }
    
    public void printPage()
    {
        System.out.println(this.contents);
    }
    
    public void updatePage()
    {
        getContents();
    }
    
    public void getContents()
    {

        WebResponse response = this.page.getWebResponse();
        this.contents = response.getContentAsString();
    }

    
    public void connect(String webPage) throws MalformedURLException, IOException
    {
        URL url = new URL(webPage);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();  // ** WRONG: should use "con.getContentType()" instead but it returns something like "text/html; charset=UTF-8" so this value must be parsed to extract the actual encoding
        encoding = encoding == null ? "UTF-8" : encoding;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        contents = new String(baos.toByteArray(), encoding);
    }
    
    
    public void setContents(String contents)
    {
        this.contents = contents;
    }
    
    public String contents()
    {
        return this.contents;
    }
    
    public void setWebClient(WebClient webClient)
    {
        this.webClient = webClient;
    }
    
    public WebClient webClient()
    {
        return this.webClient;
    }
    
    public void setPage(HtmlPage page)
    {
        this.page = page;
    }
    
    public HtmlPage page()
    {
        return this.page;
    }
}
