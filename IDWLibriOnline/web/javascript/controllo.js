///riferimenti agli oggetti controllati
var login,user,pass;
var ricerca,titolo;
var regist, userreg, passreg, email, tel, nome, cognome, codicefiscale, indirizzo, citta, provincia, cap;
var inseriscilibro, inseriscilibro_isbn, inseriscilibro_titolo, inseriscilibro_editore, inseriscilibro_annopubblicazione, inseriscilibro_autori, inseriscilibro_tag, inseriscilibro_numerovolumi, inseriscilibro_stato, inseriscilibro_duratamax;

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
function checkInsLibro(){
        //eseguiamo i controlli dei campi, e ritorniamo l'AND dei loro risultati
	return (check(inseriscilibro_isbn,"Isbn") && check(inseriscilibro_titolo,"Titolo") && check(inseriscilibro_editore,"Editore") && check(inseriscilibro_annopubblicazione,"Anno pubblicazione") && check(inseriscilibro_autori,"Autori") && check(inseriscilibro_tag,"Tag") && check(inseriscilibro_numerovolumi,"Numero volumi") && check(inseriscilibro_stato,"Stato") && check(inseriscilibro_numerovolumi,"Durata massima"));
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
	user = document.getElementById('user');
	pass = document.getElementById('pass');	
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
            inseriscilibro_isbn.onchange = function() {return check(inseriscilibro_isbn,"Isbn");}
            inseriscilibro_titolo.onchange = function() {return check(inseriscilibro_titolo,"Titolo");}
            inseriscilibro_editore.onchange = function() {return check(inseriscilibro_editore,"Editore");}
            inseriscilibro_annopubblicazione.onchange = function() {return check(inseriscilibro_annopubblicazione,"Anno pubblicazione");}
            inseriscilibro_autori.onchange = function() {return check(inseriscilibro_autori,"Autori");}
            inseriscilibro_tag.onchange = function() {return check(inseriscilibro_tag,"Tag");}
            inseriscilibro_numerovolumi.onchange = function() {return check(inseriscilibro_numerovolumi,"Numero copie");}
            inseriscilibro_stato.onchange = function() {return check(inseriscilibro_stato,"Stato");}
            inseriscilibro_duratamax.onchange = function() {return check(inseriscilibro_duratamax,"Durata massima");}
            inseriscilibro.onsubmit = function() {return checkInsLibro();}
        }
}