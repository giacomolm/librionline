function confirm_modifica(){
    var n = form.length;
    for(var i = 0; i < n; i++){
        if(form.elements[i].value == ''){
          alert('Attenzione: il campo '+form.elements[i].name.split("_", 2)[1]+ ' deve essere riempito');
            //attiviamo il campo
            form.elements[i].focus();
            //selezioniamo il testo errato
            form.elements[i].select();
            //e bordiamo di rosso il campo da correggere
            form.elements[i].style.border="1px solid red";
          return false;
        }
    }
    return true;
}

window.onload = function() {

        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        form = document.getElementsByName('update').item(0);
	elimina = document.getElementById('elimina');
        
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(elimina != null && form != null){
            form.onsubmit = function() {return confirm_modifica();}
            elimina.onclick = function() {return confirm_eliminazione();}
        }

}


