<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="eng">
<!-- InstanceBegin template="/Templates/new_template_leftmenu_max.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta name="author" content="James Malone" />
<meta name="Description" content="Bubastis is an ontology diff tool which can be used to extract logical differences between 2 ontologies"/>
<meta http-equiv="Content-Language" content="en-GB" />
<meta http-equiv="Window-target" content="_top" />
<meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/" />
<meta
	name="Keywords"
	content="Bubastis,
Ontology,
Diff,
Experimental, 
Factor, 
Array Express,
arrayexpress,
EFO, 
Experimental factor ontology"
/>
<!-- InstanceBeginEditable name="doctitle" -->
<title>Bubastis Results</title>
<!-- InstanceEndEditable -->
<link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/contents.css"  type="text/css" />
<link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/userstyles.css"  type="text/css" />
<script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
<link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/sidebars.css"   type="text/css" />
<link rel="SHORTCUT ICON" href="http://www.ebi.ac.uk/bookmark.ico" />
<style type="text/css">
@media print { 
	body, .contents, .header, .contentsarea, .head { 
		position: relative;
	}  
}  

<!-- InstanceBeginEditable name="head" -->
<!--  start meta tags, css , javascript here   -->


p.main {text-align:justify;}
h1{
text-align:left;
font-family:Arial, Helvetica, sans-serif;
font-size:1.6em;
color:#404040;
}
h2{
font-family:Arial, Helvetica, sans-serif;
font-size:1.2em;
color:#404040;
}
p{
font-size:0.9em;
color:#404040;
}

#resultsheader{
font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
border:1px solid;
border-color:#548B54;
padding: 3px 3px 3px 15px;
border-collapse:collapse;
background-color:#EAF2D3;
}
#resultsheader, tr.line{
background-color:#EAF2D3;
}


#bubastisresults, tr{
font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
font-size:1em;
padding:3px 7px 2px 7px;
background-color:#FFFFFF;
border-collapse:collapse;
color:#404040;
}
#bubastisresults tr.header{
background-color:#E0FFFF;
}
#bubastisresults tr.new{
background-color:#CCFFBB;
}
#bubastisresults tr.deleted{
background-color:#E9967A;
}

#deletedClasses, tr{
font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
font-size:1em;
padding:3px 7px 2px 7px;
background-color:#FFFFFF;
border-collapse:collapse;
color:#404040;
}
#deletedClasses tr.header{
background-color:#8B2500;
color:#F5F5F5;
}
#deletedClasses tr.new{
background-color:#C1CDCD;
}
#deletedClasses tr.deleted{
background-color:#C1CDCD;
}
</style>

<script type="text/javascript" src="scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="scripts/jquery.metadata.min.js"></script>
<script type="text/javascript" src="scripts/jquery.swapimage.min.js"></script>

<script type="text/javascript">
    $.swapImage(".swapImage");    
 </script>

</head>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  %>
<%@ page import="java.util.*" %>

<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}">

<div class="headerdiv" id="headerdiv" style="position:absolute; z-index: 1;">
		<iframe src="/inc/head.html" name="head" id="head" frameborder="0" marginwidth="0px" marginheight="0px" scrolling="no"  width="100%" style="position:absolute; z-index: 1; height: 57px;">Your browser does not support inline frames or is currently configured not to display inline frames. Content can be viewed at actual source page: http://www.ebi.ac.uk/inc/head.html</iframe>
	</div>
	<div class="contents" id="contents"><table class="contentspane" id="contentspane" summary="The main content pane of the page"  style="width: 100%"><tr><td class="leftmargin"><img src="http://www.ebi.ac.uk/inc/images/spacer.gif" class="spacer" alt="spacer" /></td><td class="leftmenucell" id="leftmenucell">
	  <div class="leftmenu" id="leftmenu" style="width: 145px; visibility: visible; display: block;"> 
		  <!-- InstanceBeginEditable name="leftnav" -->
		  
		 		  <!-- start left menu here  -->
		  
		  <ul id="sidemenuid" class="sidemenu">   
		<li style="cursor: default;" class="leftmenudivider"></li>

		  <!-- class clickmeopen forces menu to be open by default -->
		  	<li style="cursor: pointer;" class="clickmeopen"><a href="http://www.ebi.ac.uk/efo">EFO Homepage</a>
				<ul class="clickmeopen" style="display: block; margin: 0pt;">
          			<li style="cursor: default;"><a href="http://www.ebi.ac.uk/efo/tools#">Tools</a></li>
          			<li style="cursor: default;"><a href="http://www.ebi.ac.uk/efo/#explore">Explore Ontology</a></li>
          			<li style="cursor: default;"><a href="http://www.ebi.ac.uk/efo/metadata">Ontology Metadata</a></li>
				</ul>
			</li>

			<li class="clickme" style="cursor: pointer;"><a href="http://www.ebi.ac.uk/fg">Functional Genomics Home</a>
				<ul>
          			<li style="cursor: default;"><a href="http://www.ebi.ac.uk/arrayexpress/">ArrayExpress</a></li>
          			<li style="cursor: default;"><a href="http://www.ebi.ac.uk/efo/#explore">Gene Expression Atlas</a></li>
          			<li style="cursor: default;"><a href="http://www.ebi.ac.uk/biosamples/">BioSample Database</a></li>
			<li style="cursor: default;"><a href="http://www.ebi.ac.uk/fg/standards_ontologies.html">Ontologies and Standards</a></li>

				</ul>
			</li>
			<li style="cursor: default;" class="leftmenudivider"></li>
		  </ul>

		  <!-- The following divs add a SLIDING Icon Box to your Menu -->
	<script type="text/javascript" src="template_files/iconboxslider.js"></script>
	<div class="slidecontainer">
		<div class="iconbox2heading">
			<span class="headerToggle" id="news"><img class="headerToggleImage" src="template_files/minus.gif" alt="Collapse"/></span>
				<a href="#">Bubastis Release News</a>
		</div> 
		<div class="iconbox2contents" id="news_content" style="display: block;">

		<p>Bubastis web version Beta 0.15 released 15th December, 2010 </p> <br></br>
		<p>Cite this work/tool.<br></br>
				 <a href="http://bioinformatics.oxfordjournals.org/content/26/8/1112.full">paper here</a></p>
		</div>
	</div>

		<!-- end left menu here -->
		
		<!-- InstanceEndEditable -->
		<!-- InstanceBeginEditable name="lefticons" -->
	  
		
		<!-- InstanceEndEditable -->
	    <script type="text/javascript" src="http://www.ebi.ac.uk/inc/js/sidebars.js"></script><img src="http://www.ebi.ac.uk/inc/images/spacer.gif" class="spacer" alt="spacer" /></div></td><td class="contentsarea" id="contentsarea">
		<!-- InstanceBeginEditable name="contents" -->


		<!-- start contents here -->

<table border="0">
<tr>
<td>
<a name="top"></a>
<h1><img alt="Bubastis logo" src="./images/bubastis_logo.gif" border="0"/>&nbsp; Bubastis Results</h1>
</td>
</tr>
</table>

<table id="resultsheader" width="700">

	<jsp:useBean id="changeBean" type="uk.ac.ebi.efo.bubastis.OntologyChangesBean" scope="request"/>
	<tr class="line"><td>&nbsp;
		Ontology 1: "<jsp:getProperty name="changeBean" property="ontology1Location"/>"
	</td>
	</tr>
	<tr class="line"><td>&nbsp;
		Ontology 2: "<jsp:getProperty name="changeBean" property="ontology2Location"/>"
	</td></tr>
	<tr class="line"><td>&nbsp;
		Number of <a href="#changes">classes that have changed</a>: <jsp:getProperty name="changeBean" property="numChangedClasses"/>
	</td></tr>
	<tr class="line"><td>&nbsp;
		Number of <a href="#additions">classes that have been added</a>: <jsp:getProperty name="changeBean" property="numNewClasses"/>
	</td></tr>
	<tr class="line"><td>&nbsp;
		Number of <a href="#deletions">classes that have been deleted</a>: <jsp:getProperty name="changeBean" property="numDeletedClasses"/>
	</td></tr>
	<tr><td>
	</td></tr>
	<tr><td>
	</td></tr>
	<tr><td>
	<a href="./bubastis.html"><font color="#0099FF"><img class="swapImage {src: './images/bubastis_diff_on_black_button.gif'}" src="./images/bubastis_diff_off_button.gif" alt="Perform another diff" border="0"></img></font></a>&nbsp;
	<a href="#"><font color="#0099FF"><img class="swapImage {src: './images/bubastis_xml_on_button.gif'}" src="./images/bubastis_xml_off_button.gif" alt="Export results as XML" border="0"></img></font></a>&nbsp;
	<a href="#"><font color="#0099FF"><img class="swapImage {src: './images/bubastis_text_on_button.gif'}" src="./images/bubastis_text_off_button.gif" alt="Export results as text" border="0"></img></font></a>&nbsp;
	</td></tr>
</table>



<table width="700">
	<tr>
	<td>
	<a name="changes"></a>
	<h2>Classes modified:</h2>
	</td>
	</tr>
</table>

<table id="bubastisresults" width="700">
<%	//code to populate the table with results
	ArrayList<String> changedClassesList = (ArrayList<String>)request.getAttribute("resultsSet"); 

		if (!changedClassesList.isEmpty()){
			Iterator i = changedClassesList.iterator();
			while (i.hasNext()) {
				
				//Get element
				String element = (String)i.next();
				//if the element is a new axiom display row appropriately
				if(element.startsWith("<newAxiom>")){
					out.println("<tr class=\"new\"><td>+&nbsp&nbsp&nbsp" + element + "</td></tr>");	
				}
				//if the element is a new axiom display row appropriately
				else if(element.startsWith("<deletedAxiom>")){
					out.println("<tr class=\"deleted\"><td>-&nbsp&nbsp&nbsp" + element + "</td></tr>");	
				}
				else if(element.startsWith("<classIRI>")){
					//insert space between last class and this one
					out.println("<tr><td>&nbsp;</td></tr>");
					out.println("<tr class=\"header\"><td>Class: " + element + "</td></tr>");
				}
				else if(element.startsWith("<classLabel>")){
					out.println("<tr class=\"header\"><td>Label: " + element + "</td></tr>");
				}
			}
		}
		else{
			out.println("No changes");
		}
%>
	<tr>
	<td align="left">
	<a href="#top">Back to top</a>
	</td>
	</tr>
</table>

<table width="700">
	<tr>
	<td>
	<a name="additions"></a>
	<h2>New classes:</h2>
	</td>
	</tr>
</table>

<table id="bubastisresults" width="700">
<%	//code to populate the table with results
	ArrayList<String> newClassesList = (ArrayList<String>)request.getAttribute("newClassesSet"); 
		if (!newClassesList.isEmpty()){
			Iterator i = newClassesList.iterator();
			while (i.hasNext()) {
				
				//Get element
				String element = (String)i.next();
				//if the element is a new axiom display row appropriately
				if(element.startsWith("<newAxiom>")){
					out.println("<tr class=\"new\"><td>+&nbsp&nbsp&nbsp" + element + "</td></tr>");	
				}
				//if the element is a new axiom display row appropriately
				else if(element.startsWith("<deletedAxiom>")){
					out.println("<tr class=\"deleted\"><td>-&nbsp&nbsp&nbsp" + element + "</td></tr>");	
				}
				else if(element.startsWith("<classIRI>")){
					//insert space between last class and this one
					out.println("<tr><td>&nbsp;</td></tr>");
					out.println("<tr class=\"header\"><td>Class: " + element + "</td></tr>");
				}
				else if(element.startsWith("<classLabel>")){
					out.println("<tr class=\"header\"><td>Label: " + element + "</td></tr>");
				}
			}
		}
		else{
			out.println("-None-");
		}
%>
	<tr>
	<td align="left">
	<a href="#top">Back to top</a>
	</td>
	</tr>
</table>


<table width="700">
	<tr>
	<td>
	<a name="deletions"></a>
	<h2>Deleted classes:</h2>
	</td>
	</tr>
</table>

<table id="deletedClasses" width="700">
<%	//code to populate the table with results
	ArrayList<String> deletedClassesList = (ArrayList<String>)request.getAttribute("deletedClassesSet"); 

		if (!newClassesList.isEmpty()){
			Iterator i = deletedClassesList.iterator();
			while (i.hasNext()) {
				
				//Get element
				String element = (String)i.next();
				//if the element is a new axiom display row appropriately
				if(element.startsWith("<newAxiom>")){
					out.println("<tr class=\"new\"><td>+&nbsp&nbsp&nbsp" + element + "</td></tr>");	
				}
				//if the element is a new axiom display row appropriately
				else if(element.startsWith("<deletedAxiom>")){
					out.println("<tr class=\"deleted\"><td>-&nbsp&nbsp&nbsp" + element + "</td></tr>");	
				}
				else if(element.startsWith("<classIRI>")){
					//insert space between last class and this one
					out.println("<tr><td>&nbsp;</td></tr>");
					out.println("<tr class=\"header\"><td>Class: " + element + "</td></tr>");
				}
				else if(element.startsWith("<classLabel>")){
					out.println("<tr class=\"header\"><td>Label: " + element + "</td></tr>");
				}
			}
		}
		else{
			out.println("-None-");
		}
%>
	<tr>
	<td align="left">
	<a href="#top">Back to top</a>
	</td>
	</tr>
	
</table>

		<!-- end contents here -->	

					
		<!-- InstanceEndEditable -->
		<img src="http://www.ebi.ac.uk/inc/images/spacer.gif" class="spacer" alt="spacer" /></td><td class="rightmenucell" id="rightmenucell"><div class="rightmenu" id="rightmenu"><img src="http://www.ebi.ac.uk/inc/images/spacer.gif" class="spacer" alt="spacer" /></div></td></tr></table>
		<table class="footerpane" id="footerpane" summary="The main footer pane of the page"><tr><td colspan ="4" class="footerrow">					
		<div class="footerdiv" id="footerdiv"  style="z-index:2;"><iframe src="/inc/foot.html" name="foot" frameborder="0" marginwidth="0px" marginheight="0px" scrolling="no"  height="22px" width="100%"  style="z-index:2;">Your browser does not support inline frames or is currently configured not to display inline frames. Content can be viewed at actual source page: http://www.ebi.ac.uk/inc/foot.html</iframe></div></td></tr>
		</table>
	  <script  src="http://www.ebi.ac.uk/inc/js/footer.js" type="text/javascript"></script>
	</div>

</body>
</html>