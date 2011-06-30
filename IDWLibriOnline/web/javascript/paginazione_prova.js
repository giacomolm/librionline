// When document is ready, initialize pagination
$(document).ready(function(){ 
initPagination();
});

function initPagination() {
pageselectCallback(0,0); //patch per risultato iniziale

var num_entries = jQuery('#hiddenresult div.result').length;
jQuery("#Pagination").pagination(num_entries, {
callback: pageselectCallback,
items_per_page:1 // Show only one item per page
});
}