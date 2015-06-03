/*!
 * Bubastis web app javascript
 * Adapted from Limpopo.  This handles AJAX queries and graphical illustration of results
 *
 * @author Tony Burdett
 * @author James Malone
 * @date 24-Aug-2010
 */


function selectionMadeDIV(value) {
	

	// hide all other ontology1 divs
    $("div[id^='bubastis.ontologyinput.input.']").each(function(index) {  
        // hide
        $(this).hide();
    });
    
    //get values using .val() method from jquery of the two select divs
    var loadType1 = $("#bubastis.ontologyinput.selector1").val(); 
    var loadType2 = $("#bubastis.ontologyinput.selector2").val(); 
  
    if (loadType1 == "ontology1URL" && loadType2 == "ontology2URL"){
    	//show the URI - URI div
    	$("div[id^='bubastis.ontologyinput.input.URLandURL']").show();
    	
    	//set the post type to application/x-www-form-urlencoded
    	$( "form#bubastis_form" )
    	.attr( "enctype", "application/x-www-form-urlencoded" )
    	.attr( "encoding", "application/x-www-form-urlencoded");
    	
    }
    else if(loadType1 == "ontology1file" && loadType2 == "ontology2URL"){
    	// show the file - URI div
        $("div[id^='bubastis.ontologyinput.input.FileandURL']").show();
        
    	//set the post type to multipart/form-data
    	$( "form#bubastis_form" )
    	.attr( "enctype", "multipart/form-data" )
    	.attr( "encoding", "multipart/form-data");
        
    }
    else if(loadType1 == "ontology1URL" && loadType2 == "ontology2file"){
    	// show the file - URI div
        $("div[id^='bubastis.ontologyinput.input.URLandFile']").show();
        
    	//set the post type to multipart/form-data
    	$( "form#bubastis_form" )
    	.attr( "enctype", "multipart/form-data" )
    	.attr( "encoding", "multipart/form-data");
    }
    else if(loadType1 == "ontology1file" && loadType2 == "ontology2file"){
    	// show the file - URI div
        $("div[id^='bubastis.ontologyinput.input.FileandFile']").show();
        
    	//set the post type to multipart/form-data
    	$( "form#bubastis_form" )
    	.attr( "enctype", "multipart/form-data" )
    	.attr( "encoding", "multipart/form-data");
    }
   
    // show the selected div only
    $("#bubastis.ontology1.input." + value).show();
  
}



function setEncodingType(){
	//check the selectors and if both are URL then set encoding type to urlencoded
	var inputType1 = $("select[id='bubastis.ontologyinput.selector1']").val(); 
	var inputType2 = $("select[id='bubastis.ontologyinput.selector2']").val(); 
	if (inputType1 == "ontology1URL" && inputType2 == "ontology2URL"){
		//set the post type to application/x-www-form-urlencoded
		$( "form#bubastis_form" )
		.attr( "enctype", "application/x-www-form-urlencoded" )
		.attr( "encoding", "application/x-www-form-urlencoded");
	}
	//otherwise set type to multipart
	else {
    	//set the post type to multipart/form-data
    	$( "form#bubastis_form" )
    	.attr( "enctype", "multipart/form-data" )
    	.attr( "encoding", "multipart/form-data");
	}	
	
}


function setInitialSelections(){
	setEncodingType();

}


function changeSelection(value){
    
    
    if (value == "ontology1URL"){
    	
    	
    	//replace contents of ontology 1 div with text box
    	$("#bubastis\\.ontologyinput\\.input1").html("<label>"+
        				"<font color='#0099FF'>Ontology 1: </font>"+
            			"<input name='ontology1'"+
                   		"	type='text'"+
                   		"	size='38'"+
                   		"	style='color: #484848; height=20px; vertical-align:middle;'/>"+
        			"</label>");	
    }
    else if (value == "ontology1file"){
    	//replace contents of ontology 1 div with file input
    	$("#bubastis\\.ontologyinput\\.input1").html("<label>"+

    					"<font color='#0099FF'>Ontology 1: </font>"+
    					"<input name='ontology1file'"+
    					"type='file'"+
    					"size='30'"+
                   		"	style='color: #484848; height=20px; vertical-align:middle;'/>"+
						"</label>");
    	
    }
    else if (value == "ontology2URL"){
    	//replace contents of ontology 2 div with text box
    	$("#bubastis\\.ontologyinput\\.input2").html("<label>"+    	
    					"<font color='#0099FF'>Ontology 2: </font>"+
    					"<input name='ontology2url'"+
    					"	type='text'"+
    					"	size='38'"+
    					"	style='color: #484848; height=20px; vertical-align:middle;'/>"+
    					"</label>");
    }
    else if (value == "ontology2file"){
    	//replace contents of ontology 2 div with file input
    	$("#bubastis\\.ontologyinput\\.input2").html("<label>"+

    					"<font color='#0099FF'>Ontology 2: </font>"+
    					"<input name='ontology2file'"+
    					"type='file'"+
    					"size='30'"+
                   		"	style='color: #484848; height=20px; vertical-align:middle;'/>"+
						"</label>");
    }
    	
    setEncodingType();
}





function selectionMade(value) {
	// hide all other ontology1 divs
    $("tr[id^='bubastis.ontologyinput.input.']").each(function(index) {  
        // hide
        $(this).hide();
    });
    
    //get values using .val() method from jquery of the two select divs
    var loadType1 = $("select[id='bubastis.ontologyinput.selector1']").val(); 
    var loadType2 = $("select[id='bubastis.ontologyinput.selector2']").val(); 
  
    if (loadType1 == "ontology1URL" && loadType2 == "ontology2URL"){
    	//show the URI - URI div
    	$("tr[id^='bubastis.ontologyinput.input.URLandURL']").show();
    	//and the options boxes below 
    	$("tr[id^='bubastis.ontologyinput.input.optionsURLandURL']").show();
    	
    	//set the post type to application/x-www-form-urlencoded
    	$( "form#bubastis_form" )
    	.attr( "enctype", "application/x-www-form-urlencoded" )
    	.attr( "encoding", "application/x-www-form-urlencoded");
    	
    }
    else if(loadType1 == "ontology1file" && loadType2 == "ontology2URL"){
    	// show the file - URI div
        $("tr[id^='bubastis.ontologyinput.input.FileandURL']").show();
    	//and the options boxes below 
    	$("tr[id^='bubastis.ontologyinput.input.optionsFileandURL']").show();
                
    	//set the post type to multipart/form-data
    	$( "form#bubastis_form" )
    	.attr( "enctype", "multipart/form-data" )
    	.attr( "encoding", "multipart/form-data");
        
    }
    else if(loadType1 == "ontology1URL" && loadType2 == "ontology2file"){
    	// show the file - URI div
        $("tr[id^='bubastis.ontologyinput.input.URLandFile']").show();
    	//and the options boxes below 
    	$("tr[id^='bubastis.ontologyinput.input.optionsURLandFile']").show();
    	
    	//set the post type to multipart/form-data
    	$( "form#bubastis_form" )
    	.attr( "enctype", "multipart/form-data" )
    	.attr( "encoding", "multipart/form-data");
    }
    else if(loadType1 == "ontology1file" && loadType2 == "ontology2file"){
    	// show the file - URI div
        $("tr[id^='bubastis.ontologyinput.input.FileandFile']").show();
    	//and the options boxes below 
    	$("tr[id^='bubastis.ontologyinput.input.optionsFileandFile']").show();
        
    	//set the post type to multipart/form-data
    	$( "form#bubastis_form" )
    	.attr( "enctype", "multipart/form-data" )
    	.attr( "encoding", "multipart/form-data");
    }
   
    // show the selected div only
    $("#bubastis.ontology1.input." + value).show();
  
}


