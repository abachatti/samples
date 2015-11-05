//Claudius Skiba
//11/5/2015
//I used jsoup as I believe that's best web scrapper.


import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiSampleTopic {
	//variables 
  private final String baseUrl; 
  static Scanner userTopic = new Scanner(System.in);

  
  //land string specifies language used.
  //this string could be just set to "http://en.wikipedia.org/wiki/" 
  //this ads functionality of changing languages or asking users for specific language
  public WikiSampleTopic(String lang) {
    this.baseUrl = String.format("http://%s.wikipedia.org/wiki/", lang);
  }

  
  //jsoup access web page that consist of base Url which with en language looks
  // like: "http://en.wikipedia.org/wiki/"
  //first paragraph is just under element ".mw-content-ltr" which I looked up by going to random wiki page
  //and inspecting element using opera browser
  //then it gets first paragraph and returns it
  public String getIntroParagraph(String Wtopic) throws IOException {
    String url = baseUrl + Wtopic;
    Document doc = Jsoup.connect(url).get();
    Elements paragraphs = doc.select(".mw-content-ltr p");

    Element firstParagraph = paragraphs.first();
    return firstParagraph.text();
  }

  
  //main function
  //first asks for topic to look up on wiki
  //then it creates empty string that will hold value of topic
  //as long as topic string will remain empty its going to prompt user for a topic
  public static void main(String[] args) throws IOException {
	  System.out.print("Plaese input topic for me to look up on wikipedia:  ");
	  String topic = null;
	  while((topic = userTopic.nextLine()).isEmpty())
	  {
		  System.out.print("You didn't enter anything. Please enter a topic: ");
				   
	  }
	  
	  //while topic string isn't empty it replaces all spaces with underscores
	  topic = topic.replaceAll(" ", "_");
	  
	 //calls WikiSampleTopic to get base address with language specified as en
	 // creates null string that will hold value of first paragrapg
	 WikiSampleTopic parser = new WikiSampleTopic("en");
	 String firstParagraph = null;
	 
	 //it is trying to run getIntroParagraph
	 //if firstParagraph gets new value it continues, otherwise it throws an exception
	 try 
	 {
		 	firstParagraph = parser.getIntroParagraph(topic);
	 }
	 //when it throws exception it prompts: "Not found" and terminates 
	catch(IOException e) {
         System.out.print("Not found.");
         Runtime.getRuntime().exit(0);
     }
	 
	 //I created temp string that holds value of "may refer to"
	 //I did that because sometimes wiki when it can't find topic it will present a list of topics
	 //In that case the first paragraph will contain phrase " may refer to" 
	 // so if returned paragraph consist of phrase "may refer to" prompts "Not found and terminates
	 
		String tempStr = "may refer to";
    	if (firstParagraph.toLowerCase().contains(tempStr.toLowerCase()))
    	{
    		System.out.print("Not found.");
            Runtime.getRuntime().exit(0);
    	}
    	else
    	{
    		//in other case it prints first paragraph
    		//thanks to jsoup no html
    		System.out.println(firstParagraph);	
    	}
   
}
}
