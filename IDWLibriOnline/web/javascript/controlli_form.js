///riferimenti agli oggetti controllati
var login,user,pass;
var ricerca,titolo;
var regist, username, password;

//funzione di controllo per il campo di input testuale user
function checkuser_reg() {
	if (username.value == "") {
		//attiviamo il campo
		username.focus();
		//selezioniamo il testo errato
		username.select();
		//e bordiamo di rosso il campo da correggere
		username.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Username\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		username.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "password" pass
function checkpass_reg() {
	if (password.value == "") {
		//attiviamo il campo
		password.focus();
		//selezioniamo il testo errato
		password.select();
		//e bordiamo di rosso il campo da correggere
		password.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Password\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		password.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

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
function checkFormLogin() {
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (checkuser() && checkpass());
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

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

//funzione per il controllo dell'intera form durante la submission
function checkFormReg() {
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (checkuser_reg() && checkpass_reg());
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

window.onload = function() {

        
        //acquisiamo i riferimenti alla form e ai camp che volgiamo controllare
        ricerca = document.getElementById('form_ricerca_base');
	titolo = document.getElementById('titolo');
	//impostiamo i listener corretti sulla form e sui suoi campi
	titolo.onchange = function() {return checktitolo();}
	ricerca.onsubmit = function() {return checkFormRicerca();}
        
        //acquisiamo i riferimenti alla form e ai camp che volgiamo controllare
	login = document.getElementById('form_login');
	user = document.getElementById('user');
	pass = document.getElementById('pass');	
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(user && pass){
            user.onchange = function() {return checkuser();}
            pass.onchange = function() {return checkpass();}
            login.onsubmit = function() {return checkFormLogin();}
        }
        
        //acquisiamo i riferimenti alla form e ai camp che volgiamo controllare
        regist = document.getElementById("registrazione");
        username = document.getElementById("user_reg");
        password = document.getElementById("pass_reg");
        //impostiamo i listener corretti sulla form e sui suoi campi
        if(username && password){
            username.onchange = function() {return checkuser_reg();}
            password.onchange = function() {return checkpass_reg();}
            regist.onsubmit = function() {return checkFormReg();}
        }
}