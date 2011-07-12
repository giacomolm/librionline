///riferimenti agli oggetti controllati
var login,user,pass;
var ricerca,titolo;
var regist, userreg, passreg, email, tel, nome, cognome, codicefiscale, indirizzo, citta, provincia, cap;

function confirm_inserimento(form){
    elements = form.getElementsByTagName("input");
    for(var i = 0; i <elements.length; i++){
        if(elements[i].value == '' && elements[i].className != 'notrequired'){
            alert('Attenzione: il campo '+elements[i].name.split("_", 2)[1]+ ' deve essere riempito');
            //attiviamo il campo
            elements[i].focus();
            //selezioniamo il testo errato
            elements[i].select();
            //e bordiamo di rosso il campo da correggere
            elements[i].style.border="1px solid red";
            return false;
        }
    }
    elements = form.getElementsByTagName("select");
    for(i = 0; i<elements.length; i++){
        if(elements[i].value == '' && elements[i].className != 'notrequired'){
            alert('Attenzione: deve essere selezione almeno un '+elements[i].name.split("_", 2)[1]+ '');
            return false;
        }
    }
    return true;
}

function confirm_modifica(form){
    elements = form.getElementsByTagName("input");
    for(var i = 0; i<elements.length; i++){
        if(elements[i].value == '' && elements[i].className != 'notrequired'){
            alert('Attenzione: il campo '+elements[i].name.split("_", 2)[1]+ ' deve essere riempito');
            //attiviamo il campo
            elements[i].focus();
            //selezioniamo il testo errato
            elements[i].select();
            //e bordiamo di rosso il campo da correggere
            elements[i].style.border="1px solid red";
            return false;
        }
    }
    
    elements = form.getElementsByTagName("select");
    for(i = 0; i<elements.length; i++){
        if(elements[i].value == '' && elements[i].className != 'notrequired'){
            alert('Attenzione: deve essere selezione almeno un '+elements[i].name.split("_", 2)[1]+ '');
            return false;
        }
    }
    return true;
}

function confirm_eliminazione(){
    var richiesta = window.confirm("Procedere con l'eliminazione?");       
    return richiesta;
}

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

window.onload = function() {

        //acquisiamo i riferimenti alla form e ai campi che vogliamo controllare
        /*ricerca = document.getElementById('form_ricerca_base');
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
        }*/
        
        //acquisiamo i riferimenti alla form di inserimento
        form_insert_book = document.getElementById('insertbook');
        form_insert_autori = document.getElementById('insertauthor');
        form_insert_ce = document.getElementById('insertcopiaelettronica');
        form_insert_lingua = document.getElementById('insertlanguage');
        form_insert_volumi = document.getElementById('insertvol');
        form_insert_tag = document.getElementById('inserttag');
        form_insert_stato = document.getElementById('insertstato');
        form_insert_utente= document.getElementById('registrazione');
        
        if(form_insert_book != null){           
            form_insert_book.onsubmit = function() {return confirm_inserimento(form_insert_book);}
        }
        if(form_insert_autori != null){           
            form_insert_autori.onsubmit = function() {return confirm_inserimento(form_insert_autori);}
        }
        if(form_insert_ce != null){           
            form_insert_ce.onsubmit = function() {return confirm_inserimento(form_insert_ce);}
        }
        if(form_insert_lingua != null){           
            form_insert_lingua.onsubmit = function() {return confirm_inserimento(form_insert_lingua);}
        }
        if(form_insert_volumi != null){           
            form_insert_volumi.onsubmit = function() {return confirm_inserimento(form_insert_volumi);}
        }
        if(form_insert_tag != null){           
            form_insert_tag.onsubmit = function() {return confirm_inserimento(form_insert_tag);}
        }
        if(form_insert_stato != null){           
            form_insert_stato.onsubmit = function() {return confirm_inserimento(form_insert_stato);}
        }
        if(form_insert_utente != null){           
            form_insert_utente.onsubmit = function() {return confirm_inserimento(form_insert_utente);}
        }
        //acquisiamo i riferimenti alle form edi modifica
        form_book = document.getElementById('updatebook');
        form_autori = document.getElementById('updateautore');
        form_lingua = document.getElementById('updatelingua');
        form_volumi = document.getElementById('updatevol');
        form_tag = document.getElementById('updatetag');
        form_stato = document.getElementById('updatestato');
        form_utente= document.getElementById('backoffice_updateutente');
	elimina = document.getElementById('elimina');
        
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(form_book != null){           
            form_book.onsubmit = function() {return confirm_modifica(form_book);}
        }
        if(form_autori != null){           
            form_autori.onsubmit = function() {return confirm_modifica(form_autori);}
        }
        if(form_lingua != null){           
            form_lingua.onsubmit = function() {return confirm_modifica(form_lingua);}
        }
        if(form_volumi != null){           
            form_volumi.onsubmit = function() {return confirm_modifica(form_volumi);}
        }
        if(form_tag != null){           
            form_tag.onsubmit = function() {return confirm_modifica(form_tag);}
        }        
        if(form_stato != null){           
            form_stato.onsubmit = function() {return confirm_modifica(form_stato);}
        }
        if(form_utente != null){           
            form_utente.onsubmit = function() {return confirm_modifica(form_utente);}
        }
        if(elimina != null){            
            elimina.onclick = function() {return confirm_eliminazione();}
        }
}