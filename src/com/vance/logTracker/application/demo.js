(function() {
    YUI().use('charts', function (Y) 
    { 	
    	
//    	var string = YAHOO.util.History.getQueryStringParameter("query");
//    	alert("string value is: "+string);
//    	var aaa=Y.one("#mychart").get("id");
//    	alert(aaa);
//    	alert(document.getElementById("avgResponseDataSize").getAttribute("innerHTML"));
        var myDataValues = [ 
            {category:"5/2/2010", miscellaneous:2000, expenses:3700, revenue:2200}, 
            {category:"5/2/2010", miscellaneous:50, expenses:9100, revenue:100}, 
            {category:"5/3/2010", miscellaneous:400, expenses:1100, revenue:1500}, 
            {category:"5/4/2010", miscellaneous:200, expenses:1900, revenue:2800}, 
            {category:"5/5/2010", miscellaneous:5000, expenses:5000, revenue:2650}
        ];
//    	var myDataValues=myvalues;
//    	var myDataValues =[ {category:'5/2/2010', miscellaneous:2000, expenses:3700, revenue:2200}]; 
        
        var mychart = new Y.Chart({dataProvider:myDataValues, render:"#mychart"});
    });
})();

