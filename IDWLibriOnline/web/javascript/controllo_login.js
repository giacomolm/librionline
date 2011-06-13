///riferimenti agli oggetti controllati
var login,user,pass;

//funzione di controllo per il campo di input testuale user
function checkuser() {
	if (user.value == "") {
		//attiviamo il campo
		user.focus();
		//selezioniamo il testo errato
		user.select();
		//e bordiamo di rosso il campo da correggere
		user.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Username\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		user.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "password" pass
function checkpass() {
	if (pass.value == "") {
		//attiviamo il campo
		pass.focus();
		//selezioniamo il testo errato
		pass.select();
		//e bordiamo di rosso il campo da correggere
		pass.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Password\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		pass.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione per il controllo dell'intera form durante la submission
function checkForm() {
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (checkuser() && checkpass());
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

window.onload = function() {
	//acquisiamo i riferimenti alla form e ai camp che volgiamo controllare
	login = document.getElementById('form_login');
	user = document.getElementById('user');
	pass = document.getElementById('pass');	
	//impostiamo i listener corretti sulla form e sui suoi campi
	user.onchange = function() {return checkuser();}
	pass.onchange = function() {return checkpass();}
	login.onsubmit = function() {return checkForm();}
}