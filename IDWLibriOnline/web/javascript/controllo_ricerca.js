///riferimenti agli oggetti controllati
var ricerca,titolo;

//funzione di controllo per il campo di input testuale titolo
function checktitolo() {
	if (titolo.value == "") {
		//attiviamo il campo
		titolo.focus();
		//selezioniamo il testo errato
		titolo.select();
		//e bordiamo di rosso il campo da correggere
		titolo.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Titolo\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		titolo.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione per il controllo dell'intera form durante la submission
function checkFormRicerca() {
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (checktitolo());
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

window.onload = function() {
	//acquisiamo i riferimenti alla form e ai camp che volgiamo controllare
	ricerca = document.getElementById('ricerca_base');
	titolo = document.getElementById('titolo');
	//impostiamo i listener corretti sulla form e sui suoi campi
	titolo.onchange = function() {return checktitolo();}
	ricerca.onsubmit = function() {return checkFormRicerca();}
}