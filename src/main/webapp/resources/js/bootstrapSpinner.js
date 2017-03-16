(function($) {
	
    //Add  
    $(".quantity-add").click(function(e){  
        var count = 1;  
        var newcount = 0;  
          
        var countField = $(this).parent().find("input");  
        var count = countField.val();  
        var newcount = parseInt(count) + 1;  
 
        countField.val(newcount);  
        countField.trigger("change");
    });  
  
    //Remove  
    $(".quantity-remove").click(function(e){  
        var count = 1;  
        var newcount = 0;  
 
        var countField = $(this).parent().find("input");  
        var count = countField.val();  
        var newcount = parseInt(count) - 1;  
 
        countField.val(newcount < 0 ? 0 : newcount);    
        countField.trigger("change");          
    });   
})(jQuery);