import java.util.*;
import java.util.Map.Entry;
import java.io.*;

// This class implements a google-like search engine
public class searchEngine {

    public HashMap<String, LinkedList<String> > wordIndex;                  // this will contain a set of pairs (String, LinkedList of Strings)	
    public directedGraph internet;             // this is our internet graph
    
    
    
    // Constructor initializes everything to empty data structures
    // It also sets the location of the internet files
    searchEngine() {
	// Below is the directory that contains all the internet files
	htmlParsing.internetFilesLocation = "internetFiles";
	wordIndex = new HashMap<String, LinkedList<String> > ();		
	internet = new directedGraph();				
    } // end of constructor2015
    
    
    // Returns a String description of a searchEngine
    public String toString () {
	return "wordIndex:\n" + wordIndex + "\ninternet:\n" + internet;
    }
    
    
    // This does a graph traversal of the internet, starting at the given url.
    // For each new vertex seen, it updates the wordIndex, the internet graph,
    // and the set of visited vertices.
    
    void traverseInternet(String url) throws Exception {
    	
   
    internet.addVertex(url);
    internet.visited.put(url, false);
    
    
    
    System.out.print("internet visited is  --> " + internet.visited +"\n \n \n ");
    
    //intialize a q
    Queue<String> q = new LinkedList<String>();
    // add the current url as first queue.

    
    /*            gets all the links and saves them as a links as follows.
     * 			  The link list will only show the links in front that are left that have not been visited. 	
     */
    
    
    LinkedList<String> links= htmlParsing.getLinks(url);
    //  System.out.print("internet visited is  --> " + links +"\n \n \n ");
    
    Iterator<String> i = links.iterator();  // <--- |  Start of an iterator for the links linked List
    while ( i.hasNext() ) {
    	String s = i.next(); 
    	 q.add(s); // add to the queue 
    	}
   
    
    /*           set all the links available into the hashmap of strings and boolean keeping track if visited or not.  	
     */
    
    
    
    while ( i.hasNext() ) {
    	String s = i.next(); 
    	internet.setVisited(s, false); // setting everything at start as false 
    	}
    
    System.out.println("Hashmap for all the links available -->> "+internet.visited);
    internet.vertices.put(url, links);
    internet.visited.put(url, true); // as we are at the first website hence we change it as visited 
    
    //Added
    
    LinkedList<String> b = htmlParsing.getContent(url); // get content of the url 
	wordIndex.put(url, b);
	System.out.println("\n This is me getting all the words -->"+url+"-->"+wordIndex.get(url));
	
   // /**   Added after first proper run */
    
    while (!q.isEmpty()) {
    	    
        	String check=q.remove();// removes the url and sees that as check.
        	System.out.print("internet visited is  --> " + check +"\n \n \n ");
        	
        	if(!internet.visited.containsKey(check)){
        	
        	// check in hashmap if true or not.
        	LinkedList<String> a = htmlParsing.getContent(check);
        	wordIndex.put(check, a);
        	
        	//System.out.println( "Website Name: ["+check+"] ---> " +htmlParsing.getContent(check)+"\n");
            LinkedList<String> sublinks= htmlParsing.getLinks(check);
            internet.visited.put(check, true);
        	internet.vertices.put(check, sublinks);
        	internet.visited.put(check, true);
        	
        	
        	
        	//------ The following line added any website that wasn't orignially linked to the main website ---// 
        	System.out.println(sublinks);
        	Iterator<String> k = sublinks.iterator();
        	while ( k.hasNext() ) {
            	String s = k.next(); 
            	if (!q.contains(s) && !internet.vertices.containsKey(s)){
            		q.add(s);
            	}}
            
        	//debugging.  
        	//System.out.println("This is me getting all the vertices -->"+check+"-->"+internet.vertices.get(check)+"\n");
        	//System.out.println("\n This is me getting all the words -->"+check+"-->"+wordIndex.get(check));
            }
        	
       
        	
        	     
    }
     
   
//System.out.print("This is me getting all the vertices --> "+internet.vertices.get(url));
    	
	/* Hints
	   0) This should take about 50-70 lines of code (or less)
	   1) To parse the content of the url, call
	   htmlParsing.getContent(url), which returns a LinkedList of Strings 
	   containing all the words at the given url. Also call htmlParsing.getLinks(url).
	   and assign their results to a LinkedList of Strings.
	   2) To iterate over all elements of a LinkedList, use an Iterator,
	   as described in the text of the assignment
	   3) Refer to the description of the LinkedList methods at
	   http://docs.oracle.com/javase/6/docs/api/ .
	   You will most likely need to use the methods contains(String s), 
	   addLast(String s), iterator()
	   4) Refer to the description of the HashMap methods at
	   http://docs.oracle.com/javase/6/docs/api/ .
	   You will most likely need to use the methods containsKey(String s), 
	   get(String s), put(String s, LinkedList l).  
	*/
	
	
	
    } // end of traverseInternet
    
    
    /* This computes the pageRanks for every vertex in the internet graph.
       It will only be called after the internet graph has been constructed using 
       traverseInternet.
       Use the iterative procedure described in the text of the assignment to
       compute the pageRanks for every vertices in the graph. 
       
       This method will probably fit in about 30 lines.
    */
    void computePageRanks() {

    	/** This gets all the pages that are available in the graph transversal ie all the websites */
    	
    	LinkedList<String> pages = internet.getVertices();
    	//System.out.println("This is from compute \n\n\n"+pages);
    	
    	/** ---------------------------------------------------------------------------------------*/
    	
    	
    	
    	
    	/** This will incoperate the pageranks from directed graph and set all the rank into 1 intially */
    	
    	 Iterator<String> i = pages.iterator();  // <--- |  Start of an iterator for the pages linked List
    	    while ( i.hasNext() ) {
    	    	String webs = i.next(); 
    	    	 internet.pageRank.put(webs, 1.0); // <--- intialize all the pageranks intially as 1.0
    	    	}
    	 	//System.out.println("\n What in the world is this what? : \n \n"+internet.pageRank);	
    	
    
    	    
       /** -------------------------------------This is a repeat procedure used for PR-------------------------------------------------*/
    	 
    	 	for (int repeats = 0; repeats<100; repeats ++) {
    	 		i=pages.iterator();
    	 		while (i.hasNext()) {
    	 			String vertices = i.next(); // for a repeat 
    	 		    double no=0.5;
    	 		   Iterator<String> nEdges = internet.getEdgesInto(vertices).iterator(); // <-- start of an nedge  where nedge is all the edges
    	 		    while ( nEdges.hasNext() ) {
    	 			  String connects= nEdges.next();
    	 			  no += 0.5*(internet.getPageRank(connects)/internet.getOutDegree(connects)); //<--formula for calculating PR 
    	 			 }
    	 		   internet.setPageRank(vertices, no); // adds to the page rank 
    	 		  // Debugging 
    	 		  // System.out.println("Website --> "+vertices+" has Pr --> "+no+ "out degree of "+internet.getEdgesInto(vertices).size());

    	 		}
    	 	}
    	 
    	 
    	 
    	 
    	 System.out.println(internet.pageRank); // printing all the page rank 
    	 /** ---------------------------------------------------------------------------------------*/

    	
    	
   
    } // end of computePageRanks
    
	
    /* Returns the URL of the page with the high page-rank containing the query word
       Returns the String "" if no web site contains the query.
       This method can only be called after the computePageRanks method has been executed.
       Start by obtaining the list of URLs containing the query word. Then return the URL 
       with the highest pageRank.
       This method should take about 25 lines of code.
    */
    String getBestURL(String query) {
    	
    	/**----------------------------------------------------------------------------------*/
    	// debugging 
    	//System.out.println("Word Index Print :  --> "+wordIndex);
    	
    	//boolean has= false;  // seeing if the 
    	
    	String stud=""; // for adding in string later
    	query = query.toLowerCase(); // < --- Have to turn into lower key.
    	 for (String key:internet.visited.keySet()) // iteration for all the links in the link provided
    	{	
    		 System.out.println(key);
    		 
    		 double no=-1;
    		 LinkedList<String> words=wordIndex.get(key);
    		 //System.out.println(words);
    		 Iterator<String> i = words.iterator(); // <-- start of the iterator for the arraylist. 
    		 while (i.hasNext()){
    			 String wordIn=i.next();
    			 if (wordIn.equals(query)) { // <-- checks if the word exists in the list of words of the website
    				//System.out.println("\n \n \n hey");
    				 
    				 // ------------This checks the rank and orders it------// 
    				 
    				double check=internet.getPageRank(key); 
    				if (check>no)no=check;stud=key; 
    				 	
    			 }
    			 
    		 }
    		 
    		 
    		 
    	}
   //     String website= 
    	//LinkedList<String> words=wordIndex.get(website)
    	// Iterator<String> i = websites.iterator();
    	 
    	
    		
   
    
    	
    	/**----------------------------------------------------------------------------------*/
   
    	
    	
    	
    	/**----------------------------------------------------------------------------------*/
	return stud; // remove this
    } // end of getBestURL
    
    
	
    public static void main(String args[]) throws Exception{		
	searchEngine mySearchEngine = new searchEngine();
	// to debug your program, start with.
	mySearchEngine.traverseInternet("http://www.cs.mcgill.ca/~blanchem/250/a.html");
	
	// When your program is working on the small example, move on to
	//mySearchEngine.traverseInternet("http://www.cs.mcgill.ca");
	
	// this is just for debugging purposes. REMOVE THIS BEFORE SUBMITTING
	System.out.println(mySearchEngine);
	
	mySearchEngine.computePageRanks();
	
	BufferedReader stndin = new BufferedReader(new InputStreamReader(System.in));
	String query;
	do {
	    System.out.print("Enter query: ");
	    query = stndin.readLine();
	    if ( query != null && query.length() > 0 ) {
		System.out.println("Best site = " + mySearchEngine.getBestURL(query));
	    }
	} while (query!=null && query.length()>0);				
    } // end of main
}



///** This gets all the pages that are available in the graph transversal ie all the websites */
//
//LinkedList<String> pages = internet.getVertices();
//System.out.println("This is from compute \n\n\n"+pages);
//
///** ---------------------------------------------------------------------------------------*/
//
//
//
//
///** This will incoperate the pageranks from directed graph and set all the rank into 1 intially */
//
// Iterator<String> i = pages.iterator();  // <--- |  Start of an iterator for the pages linked List
//    while ( i.hasNext() ) {
//    	String webs = i.next(); 
//    	 internet.pageRank.put(webs, 1.0);
//    	}
//
//    
// System.out.print("The following is a hashtable for intial pagesranks \n\n\n["+internet.pageRank+ "]\n\n");
// 
///** ---------------------------------------------------------------------------------------*/
//  //Gets me all websites and links to that website. 
// 
// for (Entry<String, LinkedList<String>> entry : internet.vertices.entrySet()) // iteration for all the links in the link provided
// {
//	
//	 
//	 
//
//     System.out.println(entry.getKey() + " ---- > " + entry.getValue());
//     String verticecheck=entry.getKey(); //<---the vertice being checked. 
//     
//     // intialize a linked list
//     LinkedList<String> allpages = internet.vertices.get(verticecheck);
//		 Iterator<String> a = allpages.iterator(); // <--- Iterator for all linked websites
//    
//		 System.out.println(internet.getOutDegree(verticecheck)); // <-Shows all the out degrees or the links for the vertice.
//		 double nValue=0.5;
//		 System.out.println("here we goo ===["+internet.vertices.get(verticecheck));
//     while ( a.hasNext() ) {
//      //String v= i.next(); 
//    // nValue=nValue+0.5*(internet.pageRank.get(v)/internet.getOutDegree(v));
//    
//     }
//    // internet.pageRank.put(verticecheck, nValue);
// }

