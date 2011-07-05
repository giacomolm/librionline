///riferimenti agli oggetti controllati
var login,user,pass;
var ricerca,titolo;
var regist, userreg, passreg, email, tel, nome, cognome, codicefiscale, indirizzo, citta, provincia, cap;
var inseriscilibro, inseriscilibro_isbn, inseriscilibro_titolo, inseriscilibro_editore, inseriscilibro_annopubblicazione, inseriscilibro_autori, inseriscilibro_tag, inseriscilibro_numerovolumi, inseriscilibro_stato, inseriscilibro_duratamax;

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

//funzione di controllo per il campo di input "testuale" username
function checkuser_reg() {
	if (userreg.value == "") {
		//attiviamo il campo
		userreg.focus();
		//selezioniamo il testo errato
		userreg.select();
		//e bordiamo di rosso il campo da correggere
		userreg.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Username\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		userreg.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "password" password
function checkpass_reg() {
	if (passreg.value == "") {
		//attiviamo il campo
		passreg.focus();
		//selezioniamo il testo errato
		passreg.select();
		//e bordiamo di rosso il campo da correggere
		passreg.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Password\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		passreg.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" email
function checkemail() {
	if (email.value == "") {
		//attiviamo il campo
		email.focus();
		//selezioniamo il testo errato
		email.select();
		//e bordiamo di rosso il campo da correggere
		email.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Email\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		email.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" telefono
function checktel() {
	if (tel.value == "") {
		//attiviamo il campo
		tel.focus();
		//selezioniamo il testo errato
		tel.select();
		//e bordiamo di rosso il campo da correggere
		tel.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Telefono\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		tel.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" nome
function checknome() {
	if (nome.value == "") {
		//attiviamo il campo
		nome.focus();
		//selezioniamo il testo errato
		nome.select();
		//e bordiamo di rosso il campo da correggere
		nome.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Nome\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		nome.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" cognome
function checkcognome() {
	if (cognome.value == "") {
		//attiviamo il campo
		cognome.focus();
		//selezioniamo il testo errato
		cognome.select();
		//e bordiamo di rosso il campo da correggere
		cognome.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Cognome\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		cognome.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" codicefiscale
function checkcodicefiscale() {
	if (codicefiscale.value == "") {
		//attiviamo il campo
		codicefiscale.focus();
		//selezioniamo il testo errato
		codicefiscale.select();
		//e bordiamo di rosso il campo da correggere
		codicefiscale.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Codice Fiscale\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		codicefiscale.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" indirizzo
function checkindirizzo() {
	if (indirizzo.value == "") {
		//attiviamo il campo
		indirizzo.focus();
		//selezioniamo il testo errato
		indirizzo.select();
		//e bordiamo di rosso il campo da correggere
		indirizzo.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Indirizzo\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		indirizzo.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" citta
function checkcitta() {
	if (citta.value == "") {
		//attiviamo il campo
		citta.focus();
		//selezioniamo il testo errato
		citta.select();
		//e bordiamo di rosso il campo da correggere
		citta.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Citta\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		citta.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" provincia
function checkprovincia() {
	if (provincia.value == "") {
		//attiviamo il campo
		provincia.focus();
		//selezioniamo il testo errato
		provincia.select();
		//e bordiamo di rosso il campo da correggere
		provincia.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Provincia\' vuoto! \n N.B. La provincia è formata da 2 lettere!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		provincia.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione di controllo per il campo di input "testuale" cap
function checkcap() {
	if (cap.value == "") {
		//attiviamo il campo
		cap.focus();
		//selezioniamo il testo errato
		cap.select();
		//e bordiamo di rosso il campo da correggere
		cap.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'CAP\' vuoto! \n N.B. Il cap è formato da 5 cifre!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		cap.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione per il controllo dell'intera form durante la submission
function checkFormReg() {
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (checkuser_reg() && checkpass_reg() && checkemail() && checktel() && checknome() && checkcognome() && checkcodicefiscale() && checkindirizzo() && checkcitta() && checkprovincia() && checkcap());
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkInsLibro() {
	//eseguiamo i controlli sui campi, e ritorniamo l'AND dei loro risultati
	return (checkinslibro_isbn() && checkinslibro_titolo() && checkinslibro_editore() && checkinslibro_annopubblicazione() && checkinslibro_autori() && checkinslibro_tag() && checkinslibro_numerovolumi() && checkinslibro_stato() && checkinslibro_duratamax());
	//in questo modo, se uno dei campi ritorna false, la form non verrà sottomessa
}

function checkinslibro_isbn() {
	if (inseriscilibro_isbn.value == "") {
		//attiviamo il campo
		inseriscilibro_isbn.focus();
		//selezioniamo il testo errato
		inseriscilibro_isbn.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_isbn.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Isbn\' vuoto! \n N.B. L'isbn è formato da 13 cifre!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_isbn.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

function checkinslibro_titolo() {
	if (inseriscilibro_titolo.value == "") {
		//attiviamo il campo
		inseriscilibro_titolo.focus();
		//selezioniamo il testo errato
		inseriscilibro_titolo.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_titolo.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Titolo\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_titolo.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

function checkinslibro_editore() {
	if (inseriscilibro_editore.value == "") {
		//attiviamo il campo
		inseriscilibro_editore.focus();
		//selezioniamo il testo errato
		inseriscilibro_editore.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_editore.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Editore\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_editore.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

function checkinslibro_annopubblicazione() {
	if (inseriscilibro_annopubblicazione.value == "") {
		//attiviamo il campo
		inseriscilibro_annopubblicazione.focus();
		//selezioniamo il testo errato
		inseriscilibro_annopubblicazione.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_annopubblicazione.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Anno pubblicazione\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_annopubblicazione.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

function checkinslibro_autori() {
	if (inseriscilibro_autori.value == "") {
		//attiviamo il campo
		inseriscilibro_autori.focus();
		//selezioniamo il testo errato
		inseriscilibro_autori.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_autori.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Autori\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_autori.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

function checkinslibro_tag() {
	if (inseriscilibro_tag.value == "") {
		//attiviamo il campo
		inseriscilibro_tag.focus();
		//selezioniamo il testo errato
		inseriscilibro_tag.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_tag.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Tag\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_tag.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

function checkinslibro_numerovolumi() {
	if (inseriscilibro_numerovolumi.value == "") {
		//attiviamo il campo
		inseriscilibro_numerovolumi.focus();
		//selezioniamo il testo errato
		inseriscilibro_numerovolumi.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_numerovolumi.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Numero volumi\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_numerovolumi.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

function checkinslibro_stato() {
	if (inseriscilibro_stato.value == "") {
		//attiviamo il campo
		inseriscilibro_stato.focus();
		//selezioniamo il testo errato
		inseriscilibro_stato.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_stato.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Stato\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_stato.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

function checkinslibro_duratamax() {
	if (inseriscilibro_duratamax.value == "") {
		//attiviamo il campo
		inseriscilibro_duratamax.focus();
		//selezioniamo il testo errato
		inseriscilibro_duratamax.select();
		//e bordiamo di rosso il campo da correggere
		inseriscilibro_duratamax.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo \'Durata\' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		inseriscilibro_duratamax.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

window.onload = function() {

        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        ricerca = document.getElementById('form_ricerca_base');
	titolo = document.getElementById('titolo');
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(ricerca != null && titolo.value != null){
            titolo.onchange = function() {return checktitolo();}
            ricerca.onsubmit = function() {return checkFormRicerca();}
        }
        
        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
	login = document.getElementById('form_login');
	user = document.getElementById('user');
	pass = document.getElementById('pass');	
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(login != null && user.value != null && pass.value != null){
            user.onchange = function() {return checkuser();}
            pass.onchange = function() {return checkpass();}
            login.onsubmit = function() {return checkFormLogin();}
        }
        
        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        regist = document.getElementById("registrazione");
        userreg = document.getElementById("user_reg");
        passreg = document.getElementById("pass_reg");
        email = document.getElementById('email');
        tel = document.getElementById('tel');
        nome = document.getElementById('nome');
        cognome = document.getElementById('cognome');
        codicefiscale = document.getElementById('codicefiscale');
        indirizzo = document.getElementById('indirizzo');
        citta = document.getElementById('citta');
        provincia = document.getElementById('provincia');
        cap = document.getElementById('cap');
        //impostiamo i listener corretti sulla form e sui suoi campi
        if(regist != null && userreg.value != null && passreg.value != null && email.value != null && tel.value != null && nome.value != null && cognome.value != null && codicefiscale.value != null && indirizzo.value != null && citta.value != null && provincia.value != null && cap.value != null){
            userreg.onchange = function() {return checkuser_reg();}
            passreg.onchange = function() {return checkpass_reg();}
            email.onchange = function() {return checkemail();}
            tel.onchange = function() {return checktel();}
            nome.onchange = function() {return checknome();}
            cognome.onchange = function() {return checkcognome();}
            codicefiscale.onchange = function(){return checkcodicefiscale();}
            indirizzo.onchange = function() {return checkindirizzo();}
            citta.onchange = function() {return checkcitta();}
            provincia.onchange = function() {return checkprovincia();}
            cap.onchange = function() {return checkcap();}
            regist.onsubmit = function() {return checkFormReg();}
        }
        
        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        inseriscilibro = document.getElementById("insertbook");
        inseriscilibro_isbn = document.getElementById("insertbook_isbn");
        inseriscilibro_titolo = document.getElementById("insertbook_titolo");
        inseriscilibro_editore = document.getElementById("insertbook_editore");
        inseriscilibro_annopubblicazione = document.getElementById("insertbook_annopubblicazione");
        inseriscilibro_autori = document.getElementById("insertbook_autore");
        inseriscilibro_tag = document.getElementById("insertbook_tag");
        inseriscilibro_numerovolumi = document.getElementById("insertbook_numerocopie");
        inseriscilibro_stato = document.getElementById("insertbook_stato");
        inseriscilibro_duratamax = document.getElementById("insertbook_duratamax");
        //impostiamo i listener corretti sulla form e sui suoi campi
        if(inseriscilibro != null && inseriscilibro_isbn.value != null && inseriscilibro_titolo.value != null && inseriscilibro_editore.value != null && inseriscilibro_annopubblicazione.value != null && inseriscilibro_autori.value != null && inseriscilibro_tag.value != null && inseriscilibro_numerovolumi.value != null && inseriscilibro_stato.value != null && inseriscilibro_duratamax.value != null){
            inseriscilibro_isbn.onchange = function() {return checkinslibro_isbn();}
            inseriscilibro_titolo.onchange = function() {return checkinslibro_titolo();}
            inseriscilibro_editore.onchange = function() {return checkinslibro_editore();}
            inseriscilibro_annopubblicazione.onchange = function() {return checkinslibro_annopubblicazione();}
            inseriscilibro_autori.onchange = function() {return checkinslibro_autori();}
            inseriscilibro_tag.onchange = function() {return checkinslibro_tag();}
            inseriscilibro_numerovolumi.onchange = function() {return checkinslibro_numerovolumi();}
            inseriscilibro_stato.onchange = function() {return checkinslibro_stato();}
            inseriscilibro_duratamax.onchange = function() {return checkinslibro_duratamax();}
            inseriscilibro.onsubmit = function() {return checkInsLibro();}
        }
}