<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">



<xsl:template match="/">
  <html>
	<head>


		<title>Diff results</title>
		<style type="text/css">
		body
		{
		margin:10px;
		background-color:#FFFFFF;
		}
		
		p
		{
		font-size:0.9em;
		color:#404040;
		font-family:Arial, Helvetica, sans-serif;
		text-align:left;
		}

		h2
		{
		font-family:Arial, Helvetica, sans-serif;
		font-size:1.2em;
		color:#404040;
		}

		#resultsheader{
		font-family:Arial, Helvetica, sans-serif;
		border:1px solid;
		border-color:#548B54;
		border-collapse: collapse;
 		background-color:#EAF2D3;
		font-size:0.95em;
		width:800;
		
		}

		#bubastisresults, tr{
		font-family:Arial, Helvetica, sans-serif;
		border-collapse: collapse;
 		border-spacing: 0;
		font-size:0.95em;
		width:800;
		}

		.classheader
		{
		background-color: #E0FFFF;
		}
		
		.axiomdeleted
		{
		background-color: #E9967A;
		}

		.axiomadded
		{
		background-color: #CCFFBB;
		}


		#deletedclasses, tr{
		font-family:Arial, Helvetica, sans-serif;
		border-collapse: collapse;
 		border-spacing: 0;
		font-size:0.95em;
		width:800;
		color:#404040;
		}

		#deletedclasses tr.classheader{
		background-color: #8B2500;
		color:#F5F5F5;
		}

		#deletedclasses tr.axiom{
		background-color: #C1CDCD;
		}


		</style>
	</head>

  <body>


<!--summary header-->
<table id="resultsheader">
	<tr><td>
	<a name="top"><h2>Diff Summary</h2></a>
	</td></tr>
	<tr><td>Number of <a href="#changes">classes that have changed</a>: <xsl:value-of select="diffReport/diffSummary/numberChangedClasses"/>
	</td></tr>
	<tr><td>Number of <a href="#additions">classes that have been added</a>: <xsl:value-of select="diffReport/diffSummary/numberNewClasses"/>
	</td></tr>

<tr><td>Number of <a href="#deletions">classes that have been deleted</a>: <xsl:value-of select="diffReport/diffSummary/numberDeletedClasses"/>
	</td></tr>

</table>


<!-- details of diff -->

  <a name="changes"><h2>Classes modified:</h2></a>
	<table id="bubastisresults">

    <xsl:for-each select="diffReport/changedClasses/changedClass">
   
      <tr class="classheader"><td>Class: <xsl:value-of select="classIRI"/></td></tr>
      <tr class="classheader"><td>Label: <xsl:value-of select="classLabel"/></td></tr>

	<xsl:for-each select="current()/deletedAxiom">

      <tr class="axiomdeleted"><td>-&#160;&#160;<xsl:value-of select="current()"/></td></tr>

	</xsl:for-each>


 	<xsl:for-each select="current()/newAxiom">

	<tr class="axiomadded"><td>+&#160;&#160;<xsl:value-of select="current()" /></td></tr>

	</xsl:for-each>
	<tr><td>&#160;</td></tr>


</xsl:for-each>

  </table>

<a href="#top"><p>Back to top</p></a>
  <a name="additions"><h2>Classes added:</h2></a>


	<table id="bubastisresults">

    <xsl:for-each select="diffReport/newClasses/newClass">

    
      <tr class="classheader"><td>Class: <xsl:value-of select="classIRI"/></td></tr>
      <tr class="classheader"><td>Label: <xsl:value-of select="classLabel"/></td></tr>
	

	<xsl:for-each select="current()/newAxiom">

	<tr class="axiomadded"><td>+&#160;&#160;<xsl:value-of select="current()" /></td></tr>


	</xsl:for-each>

	<tr><td>&#160;</td></tr>
	

</xsl:for-each>

  </table>

<a href="#top"><p>Back to top</p></a>
  <a name="deletions"><h2>Classes deleted:</h2></a>

	<table id="deletedclasses">

    <xsl:for-each select="diffReport/deletedClasses/deletedClass">

    
      <tr class="classheader"><td>Class: <xsl:value-of select="classIRI"/></td></tr>
      <tr class="classheader"><td>Label: <xsl:value-of select="classLabel"/></td></tr>
	

	<xsl:for-each select="current()/newAxiom">

	<tr class="axiom"><td>-&#160;&#160;<xsl:value-of select="current()" /></td></tr>


	</xsl:for-each>

	<tr><td>&#160;</td></tr>
	

</xsl:for-each>

  </table>
<a href="#top"><p>Back to top</p></a>
<p>Created by Bubastis <a href="http://www.ebi.ac.uk/efo/bubastis">http://www.ebi.ac.uk/efo/bubastis</a></p>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>

