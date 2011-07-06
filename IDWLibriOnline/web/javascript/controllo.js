///riferimenti agli oggetti controllati
var login,user,pass;
var ricerca,titolo;
var regist, userreg, passreg, email, tel, nome, cognome, codicefiscale, indirizzo, citta, provincia, cap;
var forminslibro, inslibro_isbn, inslibro_titolo, inslibro_editore, inslibro_annopubblicazione, inslibro_autori, inslibro_tag, inslibro_numerocopie, inslibro_stato, inslibro_duratamax;
var formricercauser, ricerca_user;

function check(elemento,stringa){
    if (elemento.value == "") {
		//attiviamo il campo
		elemento.focus();
		//selezioniamo il testo errato
		elemento.select();
		//e bordiamo di rosso il campo da correggere
		elemento.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo '" + stringa + "' vuoto!");
		//ritorniamo false per indicare che l'azione è fallita
		return false;		
	} else {
		elemento.style.border="";
		//ritorniamo true per indicare che l'azione è andata a buon fine
		return true;
	}
}

//funzione per il controllo dell'intera form durante la submission
function checkFormRicerca() {
	//eseguiamo il controllo del campo
	return (check(titolo,"Titolo"));
	//in questo modo, se questo ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkFormLogin() {
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (check(user,"Username") && check(pass,"Password"));
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkFormReg() {
	//eseguiamo i controlli dei campi, e ritorniamo l'AND dei loro risultati
	return (check(user_reg,"Username") && check(pass_reg,"Password") && check(email,"Email") && check(tel,"Telefono") && check(nome,"Nome") && check(cognome,"Cognome") && check(codicefiscale,"Codice fiscale") && check(indirizzo,"Indirizzo") && check(citta,"Città") && check(provincia,"Provincia") && check(cap,"Cap"));
	//in questo modo, se uno ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkFormInserimentoLibro(){
        //eseguiamo i controlli dei campi, e ritorniamo l'AND dei loro risultati
	return (check(inslibro_isbn,"Isbn") && check(inslibro_titolo,"Titolo") && check(inslibro_editore,"Editore") && check(inslibro_annopubblicazione,"Anno pubblicazione") && check(inslibro_autori,"Autori") && check(inslibro_tag,"Tag") && check(inslibro_numerocopie,"Numero volumi") && check(inslibro_stato,"Stato") && check(inslibro_duratamax,"Durata massima"));
	//in questo modo, se uno ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkRicercaUser(){
        //eseguiamo i controlli dei campi, e ritorniamo l'AND dei loro risultati
        return (check(ricerca_user,"Username"));
        //in questo modo, se uno ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkInserisciTag(){
        //eseguiamo i controlli dei campi, e ritorniamo l'AND dei loro risultati
        return (check(instag,"Tag"));
        //in questo modo, se uno ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkInserisciStato(){
        //eseguiamo i controlli dei campi, e ritorniamo l'AND dei loro risultati
        return (check(insstato,"Stato"));
        //in questo modo, se uno ritorna false, la form non verrà sottomessa
}

window.onload = function() {

        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        ricerca = document.getElementById('form_ricerca_base');
	titolo = document.getElementById('titolo');
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(ricerca != null && titolo.value != null){
            titolo.onchange = function() {return check(titolo,"Titolo");}
            ricerca.onsubmit = function() {return checkFormRicerca();}
        }
        
        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
	login = document.getElementById('form_login');
	user = document.getElementById('username');
	pass = document.getElementById('password');	
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(login != null && user.value != null && pass.value != null){
            user.onchange = function() {return check(user,"Username");}
            pass.onchange = function() {return check(pass,"Password");}
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
            userreg.onchange = function() {return check(userreg,"Username");}
            passreg.onchange = function() {return check(passreg,"Password");}
            email.onchange = function() {return check(email,"Email");}
            tel.onchange = function() {return checktel(tel,"Telefono");}
            nome.onchange = function() {return check(nome,"Nome");}
            cognome.onchange = function() {return check(cognome,"Cognome");}
            codicefiscale.onchange = function(){return check(codicefiscale,"Codice fiscale");}
            indirizzo.onchange = function() {return check(indirizzo,"Indirizzo");}
            citta.onchange = function() {return check(citta,"Città");}
            provincia.onchange = function() {return check(provincia,"Provincia");}
            cap.onchange = function() {return check(cap,"Cap");}
            regist.onsubmit = function() {return checkFormReg();}
        }
        
        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        forminslibro = document.getElementById("insertbook");
        inslibro_isbn = document.getElementById("insertbook_isbn");
        inslibro_titolo = document.getElementById("insertbook_titolo");
        inslibro_editore = document.getElementById("insertbook_editore");
        inslibro_annopubblicazione = document.getElementById("insertbook_annopubblicazione");
        inslibro_autori = document.getElementById("insertbook_autore");
        inslibro_tag = document.getElementById("insertbook_tag");
        inslibro_numerocopie = document.getElementById("insertbook_numerocopie");
        inslibro_stato = document.getElementById("insertbook_stato");
        inslibro_duratamax = document.getElementById("insertbook_duratamax");
        //impostiamo i listener corretti sulla form e sui suoi campi
        if(forminslibro != null && inslibro_isbn.value != null && inslibro_titolo.value != null && inslibro_editore.value != null && inslibro_annopubblicazione.value != null && inslibro_autori.value != null && inslibro_tag.value != null && inslibro_numerocopie.value != null && inslibro_stato.value != null && inslibro_duratamax.value != null){
            inslibro_isbn.onchange = function() {return check(inslibro_isbn,"Isbn");}
            inslibro_titolo.onchange = function() {return check(inslibro_titolo,"Titolo");}
            inslibro_editore.onchange = function() {return check(inslibro_editore,"Editore");}
            inslibro_annopubblicazione.onchange = function() {return check(inslibro_annopubblicazione,"Anno pubblicazione");}
            inslibro_autori.onchange = function() {return check(inslibro_autori,"Autori");}
            inslibro_tag.onchange = function() {return check(inslibro_tag,"Tag");}
            inslibro_numerocopie.onchange = function() {return check(inslibro_numerocopie,"Numero copie");}
            inslibro_stato.onchange = function() {return check(inslibro_stato,"Stato");}
            inslibro_duratamax.onchange = function() {return check(inslibro_duratamax,"Durata massima");}
            forminslibro.onsubmit = function() {return checkFormInserimentoLibro();}
        }
        
        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        formricercauser = document.getElementById("form_ricerca_utente");
        ricerca_user = document.getElementById("ricerca_utente_username");
        //impostiamo i listener corretti sulla form e sui suoi campi
        if(formricercauser != null && ricerca_user.value != null){
            ricerca_user.onchange = function() {return check(ricerca_user,"Username");}
            formricercauser.onsubmit = function() {return checkRicercaUser();}
        }
        
        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        formrinstag = document.getElementById("inserttag");
        instag = document.getElementById("inserttag_tag");
        //impostiamo i listener corretti sulla form e sui suoi campi
        if(formrinstag != null && instag.value != null){
            instag.onchange = function() {return check(instag,"Tag");}
            formrinstag.onsubmit = function() {return checkInserisciTag();}
        }
        
        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        formrinsstato = document.getElementById("insertstato");
        insstato = document.getElementById("insertstato_stato");
        //impostiamo i listener corretti sulla form e sui suoi campi
        if(formrinsstato != null && insstato.value != null){
            insstato.onchange = function() {return check(insstato,"Stato");}
            formrinsstato.onsubmit = function() {return checkInserisciStato();}
        }
}