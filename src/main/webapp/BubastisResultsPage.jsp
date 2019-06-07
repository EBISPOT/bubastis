<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<title>Bubastis - Ontology Diff Tool</title>
	<meta name="description" content="Bubastis is an ontology diff tool which can be used to extract logical differences between 2 ontologies" /><!-- Describe what this page is about -->
	<meta name="keywords" content="Bubastis,
Ontology,
Diff,
Experimental,
Factor,
Array Express,
arrayexpress,
EFO,
Experimental factor ontology" /><!-- 3 to 10 keywords about the content of this page (not the whole project) -->
	<meta name="author" content="James Malone" /><!-- Your [project-name] here -->
	<meta name="HandheldFriendly" content="true" />
	<meta name="MobileOptimized" content="width" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta name="theme-color" content="#70BDBD" /> <!-- Android Chrome mobile browser tab color -->
	<!-- Get suggested SEO and social metatags at:
         https://www.ebi.ac.uk/style-lab/websites/patterns/meta-copy.html -->

	<!-- If you link to any other sites frequently, consider optimising performance with a DNS prefetch -->
	<link rel="dns-prefetch" href="//www.ebi.ac.uk" />

	<!-- If you have custom icon, replace these as appropriate.
         You can generate them at realfavicongenerator.net -->
	<link rel="icon" type="image/x-icon" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/favicon.ico" />
	<link rel="icon" type="image/png" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/favicon-32x32.png" />
	<link rel="icon" type="image/png" sizes="192x192" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/android-chrome-192x192.png" /> <!-- Android (192px) -->
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/apple-icon-114x114.png" /> <!-- For iPhone 4 Retina display (114px) -->
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/apple-icon-72x72.png" /> <!-- For iPad (72px) -->
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/apple-icon-144x144.png" /> <!-- For iPad retinat (144px) -->
	<link rel="apple-touch-icon-precomposed" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/apple-icon-57x57.png" /> <!-- For iPhone (57px) -->
	<link rel="mask-icon" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/safari-pinned-tab.svg" color="#ffffff" /> <!-- Safari icon for pinned tab -->
	<meta name="msapplication-TileColor" content="#2b5797" /> <!-- MS Icons -->
	<meta name="msapplication-TileImage" content="//ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/images/logos/EMBL-EBI/favicons/mstile-144x144.png" />

	<!-- CSS: implied media=all -->
	<!-- CSS concatenated and minified via ant build script-->

	<link rel="stylesheet" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/css/ebi-global.css" type="text/css" media="all" />
	<link rel="stylesheet" href="https://dev.ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/fonts.css" type="text/css" media="all" />

	<!-- Use this CSS file for any custom styling -->
	<!--
      <link rel="stylesheet" href="css/custom.css" type="text/css" media="all" />
    -->

	<!-- If you have a custom header image or colour -->
	<!--
    <meta name="ebi:masthead-color" content="#000" />
    <meta name="ebi:masthead-image" content="//www.ebi.ac.uk/web_guidelines/EBI-Framework/images/backgrounds/embl-ebi-background.jpg" />
    -->

	<!-- you can replace this with theme-[projectname].css. See http://www.ebi.ac.uk/web/style/colour for details of how to do this -->
	<!-- also inform ES so we can host your colour palette file -->
	<link rel="stylesheet" href="https://ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/css/theme-embl-petrol.css" type="text/css" media="all" />

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

	<!-- end CSS-->

	<script type="text/javascript" src="scripts/bubastisjs.js"></script>
	<script type="text/javascript" src="scripts/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="scripts/overlib/overlib.js"></script>



	<!--  end meta tags, css, javascript here  -->
	<!-- InstanceEndEditable -->

	<!-- google analytics -->
	<script type="text/javascript">

        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-34447281-1']);
        _gaq.push(['_trackPageview']);

        (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
        })();

	</script>
</head>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"  %>
<%@ page import="java.util.*" %>

<body class="level2"><!-- add any of your classes or IDs -->
<div id="skip-to">
	<a href="#content">Skip to main content</a>
</div>

<header id="masthead-black-bar" class="clearfix masthead-black-bar">
</header>
<div id="content">
	<div data-sticky-container>
		<header id="masthead" class="masthead" data-sticky data-sticky-on="large" data-top-anchor="content:top" data-btm-anchor="content:bottom">
			<div class="masthead-inner row">
				<!-- local-title -->
				<div class="columns medium-12" id="local-title">
					<h1><a href="../../" title="Back to Bubastis homepage"><img alt="Bubastis logo"
																				src="./images/bubastis_logo.gif"
																				border="0"/></a></h1>
				</div>
				<!-- /local-title -->
				<!-- local-nav -->
				<nav >
					<ul id="local-nav" class="dropdown menu float-left" data-description="navigational">
						<li><a href="../../">Home</a></li>
						<li><a href="https://github.com/EBISPOT/bubastis">Download</a></li>
						<li><a href="mailto:efo-users@ebi.ac.uk?subject=Bubastis Question from web">Support</a></li>
					</ul>
				</nav>
				<!-- /local-nav -->
			</div>
		</header>
	</div>
	<!-- Suggested layout containers -->
	<section id="main-content-area" class="row" role="main">
		<!-- Your menu structure should make a breadcrumb redundant, but if a breadcrump is needed uncomment the below -->
		<nav aria-label="You are here:" role="navigation">
			<ul class="breadcrumbs columns">
				<li><a href="../../">EFO homepage</a></li>
				<li><a href="../">Bubastis</a></li>
				<li>
					<span class="show-for-sr">Current: </span> Bubastis results
				</li>
			</ul>
		</nav>

		<div class="columns">
			<section>
				<h2>Diff results</h2>
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
						<a href="./bubastis"><font color="#0099FF"><img class="swapImage {src: './images/bubastis_diff_on_black_button.gif'}" src="./images/bubastis_diff_off_button.gif" alt="Perform another diff" border="0"></img></font></a>&nbsp;
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
			</section>
		</div>

	</section>
	<!-- Optional local footer (insert citation / project-specific copyright / etc here -->
	<!--
    <footer id="local-footer" class="local-footer" role="local-footer">
      <div class="row">
        <span class="reference">How to reference this page: ...</span>
      </div>
    </footer>
    -->
	<!-- End optional local footer -->
</div>
<!-- End suggested layout containers / #content -->

<footer>
	<div id="global-footer" class="global-footer">
		<nav id="global-nav-expanded" class="global-nav-expanded row">
			<!-- Footer will be automatically inserted by footer.js -->
		</nav>
		<section id="ebi-footer-meta" class="ebi-footer-meta row">
			<!-- Footer meta will be automatically inserted by footer.js -->
		</section>
	</div>
</footer>

<!-- JavaScript -->
<!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<!--
<script>window.jQuery || document.write('<script src="../js/libs/jquery-1.10.2.min.js"><\/script>')</script>
-->

<script defer="defer" src="//ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/script.js"></script>

<!-- The Foundation theme JavaScript -->
<script src="//ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/libraries/foundation-6/js/foundation.js"></script>
<script src="//ebi.emblstatic.net/web_guidelines/EBI-Framework/v1.3/js/foundationExtendEBI.js"></script>
<script type="text/JavaScript">$(document).foundation();</script>
<script type="text/JavaScript">$(document).foundationExtendEBI();</script>

<!-- Google Analytics details... -->
<!-- Change UA-XXXXX-X to be your site's ID -->
<script type="text/javascript">!function(e,a,n,t,i,o,c){e.GoogleAnalyticsObject=i,e[i]=e[i]||function(){(e[i].q=e[i].q||[]).push(arguments)},e[i].l=1*new Date,o=a.createElement(n),c=a.getElementsByTagName(n)[0],o.async=1,o.src="//www.google-analytics.com/analytics.js",c.parentNode.insertBefore(o,c)}(window,document,"script",0,"ga"),ga("create","UA-629242-1",{cookieDomain:"auto"}),ga("require","linkid","linkid.js"),ga("set","anonymizeIp",!0),ga("send","pageview");</script>
</body>
</html>
