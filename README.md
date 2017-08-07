# Low Level Search Engine 
Hi, this is a low level search engine that uses java as its practiced language implementing HashMaps and Linked links to secure links related to the website we are using. Using these datastructures, the engine transverses the links one by one and optimizes the best possible outcome to display to the user while transversing throw each link. 

### Function for optimization named computePageRanks(): 
Initially all the pages are given the same rank number of 1.0: 
```internet.pageRank.put(webs, 1.0)``` 

This rank number changes as the pages are transversed one after another using the formula : 
```no += 0.5*(internet.getPageRank(connects)/internet.getOutDegree(connects));```
