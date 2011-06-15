///riferimenti agli oggetti controllati
var login,user,pass;
var ricerca,titolo;
var regist, userreg, passreg, email, tel, nome, cognome, codicefiscale, indirizzo, citta, provincia, cap;

function check(elemento){
    if (elemento.value == "") {
		//attiviamo il campo
		elemento.focus();
		//selezioniamo il testo errato
		elemento.select();
		//e bordiamo di rosso il campo da correggere
		elemento.style.border="1px solid red";
                //finestra che indica l'errore
                alert("Lunghezza del campo selezionato vuoto!");
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
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (check(titolo));
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkFormLogin() {
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (check(user) && check(pass));
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

//funzione per il controllo dell'intera form durante la submission
function checkFormReg() {
	//eseguiamo i controlli dui due campi, e ritorniamo l'AND dei loro risultati
	return (check(user_reg) && check(pass_reg) && check(email) && check(tel) && check(nome) && check(cognome) && check(codicefiscale) && check(indirizzo) && check(citta) && check(provincia) && check(cap));
	//in questo modo, se uno dei due ritorna false, la form non verrà sottomessa
}

window.onload = function() {

        //acquisiamo i riferimenti alla form e ai camp che volgiamo controllare
        ricerca = document.getElementById('form_ricerca_base');
	titolo = document.getElementById('titolo');
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(ricerca != null && titolo.value != null){
            titolo.onchange = function() {return check(titolo);}
            ricerca.onsubmit = function() {return checkFormRicerca();}
        }
        
        //acquisiamo i riferimenti alla form e ai camp che volgiamo controllare
	login = document.getElementById('form_login');
	user = document.getElementById('user');
	pass = document.getElementById('pass');	
	//impostiamo i listener corretti sulla form e sui suoi campi
        if(login != null && user.value != null && pass.value != null){
            user.onchange = function() {return check(user);}
            pass.onchange = function() {return check(pass);}
            login.onsubmit = function() {return checkFormLogin();}
        }
        
        //acquisiamo i riferimenti alla form e ai camp che volgiamo controllare
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
            userreg.onchange = function() {return check(userreg);}
            passreg.onchange = function() {return check(passreg);}
            email.onchange = function() {return check(email);}
            tel.onchange = function() {return checktel(tel);}
            nome.onchange = function() {return check(nome);}
            cognome.onchange = function() {return check(cognome);}
            codicefiscale.onchange = function(){return check(codicefiscale);}
            indirizzo.onchange = function() {return check(indirizzo);}
            citta.onchange = function() {return check(citta);}
            provincia.onchange = function() {return check(provincia);}
            cap.onchange = function() {return check(cap);}
            regist.onsubmit = function() {return checkFormReg();}
        }
}