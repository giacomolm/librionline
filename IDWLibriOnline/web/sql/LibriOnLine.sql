-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 09, 2011 at 09:06 AM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `librionline`
--

-- --------------------------------------------------------

--
-- Table structure for table `autore`
--

CREATE TABLE IF NOT EXISTS `autore` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cognome` varchar(50) NOT NULL,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `autore`
--

INSERT INTO `autore` (`id`, `cognome`, `nome`) VALUES
(1, 'Zeldman', 'Effrey'),
(2, 'Marcotte', 'Ethan'),
(3, 'Lawson', 'Bruce'),
(4, 'Sharp', 'Remy'),
(5, 'King', 'Kim N.'),
(6, 'Eckel', 'Bruce'),
(7, 'Silberschatz', 'Abraham'),
(8, 'Galvin', 'P. Baer'),
(9, 'Gagne', 'Greg'),
(10, 'Giordano', 'Paolo'),
(11, 'Sommerville', 'Ian'),
(12, 'Lamonaco', 'Giacomo'),
(13, 'D''Orazio', 'Silvio'),
(14, 'Sedgewick', 'Robert'),
(15, 'Davis', 'Michele E.'),
(16, 'Phillips', 'Jon A.'),
(17, 'Malik', 'D. S.'),
(18, 'Amedeo', 'Enrico'),
(19, 'Camilleri', 'Andrea'),
(20, 'Kofler', 'Michael'),
(21, 'Marcellini', 'Paolo'),
(22, 'Sbordone', 'Carlo'),
(23, 'Volo', 'Fabio');

-- --------------------------------------------------------

--
-- Table structure for table `copiaelettronica`
--

CREATE TABLE IF NOT EXISTS `copiaelettronica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mimetype` varchar(25) NOT NULL,
  `url` varchar(50) NOT NULL,
  `libro` varchar(13) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `libro` (`libro`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `copiaelettronica`
--


-- --------------------------------------------------------

--
-- Table structure for table `gruppo`
--

CREATE TABLE IF NOT EXISTS `gruppo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gruppo` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `gruppo`
--

INSERT INTO `gruppo` (`id`, `gruppo`) VALUES
(1, 'bibliotecario'),
(2, 'registrato');

-- --------------------------------------------------------

--
-- Table structure for table `gruppo_servizio`
--

CREATE TABLE IF NOT EXISTS `gruppo_servizio` (
  `gruppo` int(11) NOT NULL,
  `servizio` int(11) NOT NULL,
  PRIMARY KEY (`gruppo`,`servizio`),
  KEY `servizio` (`servizio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gruppo_servizio`
--


-- --------------------------------------------------------

--
-- Table structure for table `libro`
--

CREATE TABLE IF NOT EXISTS `libro` (
  `isbn` varchar(13) NOT NULL,
  `titolo` varchar(50) NOT NULL,
  `editore` varchar(25) NOT NULL,
  `annoPubblicazione` varchar(4) NOT NULL,
  `recensione` text,
  `lingua` int(11) DEFAULT NULL,
  `data_ins` date NOT NULL,
  PRIMARY KEY (`isbn`),
  KEY `lingua` (`lingua`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `libro`
--

INSERT INTO `libro` (`isbn`, `titolo`, `editore`, `annoPubblicazione`, `recensione`, `lingua`, `data_ins`) VALUES
('9788804589655', 'La solitudine dei numeri primi', 'Mondadori', '2011', 'Alice è una bambina obbligata dal padre a frequentare la scuola di sci. È una mattina di nebbia fitta, lei non ha voglia, il latte della colazione le pesa sullo stomaco. Persa nella nebbia, staccata dai compagni, se la fa addosso. Umiliata, cerca di scendere, ma finisce fuori pista spezzandosi una gamba. Resta sola, incapace di muoversi, al fondo di un canale innevato, a domandarsi se i lupi ci sono anche in inverno. Mattia è un bambino molto intelligente, ma ha una gemella, Michela, ritardata. La presenza di Michela umilia Mattia di fronte ai suoi coetanei e per questo, la prima volta che un compagno di classe li invita entrambi alla sua festa, Mattia abbandona Michela nel parco, con la promessa che tornerà presto da lei. Questi due episodi iniziali, con le loro conseguenze irreversibili, saranno il marchio impresso a fuoco nelle vite di Alice e Mattia, adolescenti, giovani e infine adulti. Le loro esistenze si incroceranno, e si scopriranno strettamente uniti, eppure invincibilmente divisi. Come quei numeri speciali, che i matematici chiamano "primi gemelli": due numeri primi vicini ma mai abbastanza per toccarsi davvero. Un romanzo d''esordio che alterna momenti di durezza e spietata tensione a scene rarefatte e di trattenuta emozione, di sconsolata tenerezza e di tenace speranza.', 1, '2011-06-21'),
('9788804592389', 'Il tempo che vorrei', 'Mondadori', '2009', 'Il tempo che vorrei è il nuovo romanzo di Fabio Volo, che, con due milioni e mezzo di copie e un passaparola entusiasta e incessante è uno degli autori più letti in assoluto. Ci sono un padre e un figlio su un treno, non si sa per dove nè perchè. Il figlio ha l''età dell''autore e questa non è l''unica somiglianza. In treno, si sa, è facile perdersi nei pensieri. E allora il figlio pensa e ricorda. Un''infanzia fatta di fantasie assurde, le stesse che passano per la testa a quasi tutti i bambini. Un amore finito male. L''amicizia, la malattia, il lavoro, il riscatto sociale. Ma il figlio pensa soprattutto al padre. In "Il tempo che vorrei" Fabio Volo tratta temi cari a tutti con una sensibilità e un''efficacia sorprendenti.', 1, '2011-07-05'),
('9788820733834', 'Elementi di analisi matematica 1', 'Liguori', '2002', 'Si affrontano i metodi e gli strumenti di Analisi Matematica uno in un contesto semplificato, in accordo con le nuove esigenze didattiche determinate dai nuovi Corsi di Laurea di tre anni. Con linguaggio semplice si trattano tutti gli usuali argomenti di Analisi I, con il dovuto rigore, con motivazioni, esempi e dimostrazioni, anche se quest''ultime talvolta posposte, o proposte in un contesto semplificato, per mettere maggiormente in risalto gli aspetti più significativi. La lettura del testo, arricchito da molte figure, risulta agevolata da una veste grafica, che con opportuni riquadri e sfumature di colore, mette in risalto gli enunciati, gli esempi e le dimostrazioni.', 1, '2011-07-05'),
('9788838925634', 'Il gioco degli specchi ', 'Sellerio Editore Palermo', '2011', 'Qualcuno vuole confondere il Commissario Montalbano. E lo fa con indizi sbagliati, donne avvenenti e un proiettile conficcato nella sua auto. ?Il gioco degli specchi?, new entry nei nuovi intriganti capitoli del celebre investigatore siculo firmato Andrea Camilleri, è un vero e proprio labirinto di piste false e sbagliate in cui sarà facile perdersi persino per il protagonista. Con l?usuale ironia a cui l?autore ci ha ormai abituati, Montalbano si trova a dover risolvere una storia di mafia e ?ammazzatine?. Qualcuno mette una bomba in un deposito ormai vuoto da molto tempo. Chi può essere il responsabile? Le tracce fanno pensare ai vicini di casa, dove vivono anche alcuni ex malviventi con la fedina penale decisamente sporca. Sempre lì vicino abita però una simpatica coppietta borghese: i Lombardo. Lui per lavoro è costretto a viaggiare per tutta l?isola, mentre lei lavora vicino a casa e casualmente finirà tra le braccia del nostro Commissario. Perché? E? casuale che le se rompa la macchina e chieda aiuto a Montalbano? O qualcuno l?ha manomessa? Confuso dalla bellezza prorompente della ragazza, il commissario farà fatica a mantenere la sua lucidità e a far luce sul caso. Le due storie, quello della bomba e della ragazza, sembrano non c?entrare nulla, ma come nel più classico dei casi scritti di Montalbano, nulla accade per caso e i fili della storia si legano strettamente. Sullo sfondo la Sicilia magica di Vigata, quasi inabitata, distante e colorata in cui le battute e i pensieri che il lettore intercetta sono in siciliano, una lingua che Camilleri mischia con quella reale, rendendola ancora più divertente e più impregnata di realtà.Tra un arancino, una scollatura avvenente e un bagno nel suo mare, il Commissario riuscirà però a far fronte a questo intricato gioco di specchi in cui tutto si riflette e rimanda ad un?altra storia e poi ancora ad un?altra in un crescendo di tensione, di omicidi, lettere anonime e pathos. Poco c?entrano la mafia e il traffico di droga perché nei libri di Andrea Camilleri e in particolare in questo ultimo noir ?Il gioco degli specchi? sono il bene e il male, il lato oscuro e più impenetrabile di ognuno di noi, persino dei personaggi positivi, a far venire a galla il vero colpevole.', 1, '2011-07-05'),
('9788848120883', 'Programmare in PHP e MySQL', 'Tecniche Nuove', '2008', 'PHP e MySQL sono velocemente diventati lo standard per lo sviluppo di siti web che coinvolgono l''utilizzo di database, come per esempio i cataloghi online, i sistemi di e-commerce, i blog e siti di vario contenuto dinamico. Questo testo è un ausilio per tutti coloro che si devono cimentare nella progettazione di un sito web con alto contento informativo e intendono utilizzare gli strumenti più facili, più economici ma al tempo stesso di maggiore efficacia.', 1, '2011-07-01'),
('9788850321001', 'Sistemi operativi. Con esempi per l''uso in Java', 'Apogeo', '2011', 'Questo libro introduce in modo chiaro ed esauriente allo studio dei sistemi operativi. L''enfasi è posta sugli aspetti concettuali, per fornire una solida conoscenza dei principi teorici e delle tecniche che sono i fondamenti dei sistemi operativi moderni. A completamento e concretizzazione pratica di tali aspetti teorici, vengono presentate anche le caratteristiche principali di alcuni sistemi operativi reali oggi molto diffusi (Linux, Windows 2000IXP, Solaris e MacOS) come esempi e casi di studio. Gli autori esaminano in dettaglio le componenti essenziali dei sistemi operativi: la gestione dei processi, la gestione della memoria, i sistemi di input/output e la gestione del file system.', 1, '2011-06-21'),
('9788850324682', 'My SQL 5', 'Apogeo', '2006', 'Il volume introduce i lettori a questo potente sistema per DB, il cui successo, già prima del rilascio della versione 5, era sotto gli occhi di tutta la comunità web. Tra i punti di forza di questo RDBMS, vi sono le prestazioni elevate, la stabilità, la facilità di utilizzo e la grande varietà di risorse disponibili online. Inoltre MySQL lavora sui principali sistemi operativi: Windows, Linux, Mac OS X e numerosi sistemi su base Unix. Unito a PHP, MySQL è un potente strumento per la realizzazione di siti web dinamici di ogni dimensione. Dopo aver presentato nozioni comuni di SQL e di teoria dei database relazionali, vengono affrontati i problemi inerenti l''installazione e l''utilizzo avanzato con PHP e altri linguaggi di programmazione.', 1, '2011-07-05'),
('9788850326273', 'UML Pocket', 'Apogeo', '2007', 'UML è l''acronimo di Unified Modeling Language e definisce un insieme di convenzioni per la descrizione di sistemi software, principalmente orientati agli oggetti. Serve a chi sviluppa software sia in fase progettuale sia in fase di debug. Il risultato del processo che impiega UML è un complesso di illustrazioni grafiche che evidenziano il funzionamento di un sistema e la sua struttura. Lo scopo di questa breve guida è illustrare gli elementi fondamentali di UML.', 1, '2011-07-05'),
('9788850328697', 'Programmazione in C ', 'Apogeo', '2011', 'Il linguaggio C può essere definito la lingua franca della programmazione e, nonostante altri linguaggi (molti basati su C) abbiano guadagnato popolarità negli ultimi anni, resta il più utilizzato per la realizzazione di prodotti software e tra i più diffusi nei corsi base di programmazione. Il volume di King è uno dei testi di maggior successo in campo accademico e professionale poiché tratta in modo completo ed esaustivo le caratteristiche e le librerie del C, facendo riferimento agli standard C89 e C99. Gli argomenti sono presentati in maniera graduale e accompagnati da numerosi esempi, i concetti difficili sono introdotti dapprima in modo sintetico e ripresi successivamente in sempre maggiore dettaglio, rendendo il testo accessibile ai principianti e utile per i programmatori esperti. Completano il testo numerosi esercizi e progetti di programmazione.', 1, '2011-06-21'),
('9788850328970', 'Ajax', 'Apogeo', '2009', 'AJAX, Asynchronous JavaScript and XML, oggi rappresenta il paradigma di un nuovo modo di affrontare lo sviluppo web. Permette di realizzare applicazioni dove l''interattività è esaltata, favorendo una maggiore fruibilità dei servizi. AJAX non è una tecnologia in senso stretto, ma la sinergia di tecnologie già note come Javascript, CSS, XML ed XMLHttpRequest, che insieme sono le fondamenta di molte delle nuove applicazioni distribuite nel Web. Questo testo ne spiega le basi in maniera semplice ma allo stesso tempo pratica, guidando l''utente nello sviluppo di applicazioni web veramente interattive.', 1, '2011-07-05'),
('9788850329427', 'Visual Basic 10', 'Apogeo', '2010', 'Visual Basic 10, o VB.NET, è un linguaggio di programmazione orientato agli oggetti con cui è possibile realizzare applicazioni per Windows, per il Web e per i dispositivi mobili. Partendo dalle basi, questo libro permette anche all''utente meno esperto di avvicinarsi alla programmazione in Visual Basic, lo guida attraverso un percorso con un graduale aumento di complessità, dall''analisi degli aspetti essenziali della piattaforma di sviluppo fino a concetti più avanzati, egli consente di creare applicazioni in modo semplice e preciso. Esempi chiari, immagini e porzioni di codice aiutano il lettore ad apprendere velocemente i principi del linguaggio.', 1, '2011-07-05'),
('9788850329649', 'Sviluppare siti con gli standard web', 'Apogeo', '2011', 'Questo libro si rivolge a progettisti web, sviluppatori software, imprenditori e manager che vogliono disporre di siti web a costi ridotti, che funzionino al meglio e che siano in grado di raggiungere il maggior numero di persone, e tutto questo non solo tenendo conto di browser, dispositivi mobili e altri sistemi di visualizzazione presenti al momento, ma anche di apparecchiature che l''evoluzione tecnologica saprà proporre in futuro. Chi lavora sul Web conosce bene l''obsolescenza che caratterizza lo sviluppo frenetico della tecnologia. Si realizzano siti solo per ricostruirli continuamente. Accade troppo spesso che la ricostruzione di un sito debba essere effettuata non tanto per aumentare o perfezionare le funzionalità di accesso e navigazione, ma semplicemente per tenere il passo di browser e dispositivi che sembrano sempre più avanzati dei cicli di design e sviluppo di un sito web.Questa situazione è così consolidata da apparire più che normale, al punto da costituire il prezzo da pagare per il corretto mantenimento di un sito professionale. È altrettanto vero però che si tratta di un prezzo sempre più difficile da affrontare. Gli standard web permettono di porre fine a tutto questo.', 1, '2011-06-17'),
('9788850329670', 'Programmazione in C++', 'Apogeo', '2011', 'Il linguaggio di programmazione C++, nato dal C, è assai diffuso sia in ambito professionale sia in ambito didattico e accademico, per merito della sua principale peculiarità: fondere in modo efficace i paradigmi di programmazione strutturata e di programmazione orientata agli oggetti. Grazie alla grande esperienza didattica dell''Autore, il testo introduce in maniera chiara e graduale alla programmazione in C++, focalizzando l''attenzione sugli argomenti che solitamente creano difficoltà durante l''apprendimento di un linguaggio, come l''uso delle variabili, delle espressioni e delle strutture di controllo del flusso d''esecuzione, per poi procedere verso temi più avanzati, tra i quali troviamo array, puntatori e programmazione ricorsiva. Senza trascurare riferimenti alle tecniche di debugging, l''Autore enfatizza la necessità di apprendere una corretta prassi di programmazione, verso la quale guida lo studente attraverso la presentazione di numerosi e dettagliati esempi, documentati e commentati. Completa e arricchisce il testo una vasta raccolta di esercizi e di "esercizi di programmazione", progetti di complessità adeguata e graduata.', 1, '2011-07-02'),
('9788861142879', 'HTML 5', 'Mondadori Informatica', '1', 'Tutti i siti web si basano sull''HTML, il linguaggio che descrive il formato, l''aspetto, l''impaginazione, la visualizzazione del contenuto delle pagine su Internet. HTML 5, l''ultima versione di questo linguaggio universale, è pienamente descritta - nelle specifiche e nell''uso - in questo testo che spiega cosa si può fare di nuovo, dalle semplici pagine web ai blog più creativi. Gli autori, avendo lavorato sin dall''inizio nel gruppo che ha definito le nuove specifiche, ne mettono in luce sia gli aspetti positivi sia quelli problematici, con particolare riguardo a quelli che sono ancora i limiti da superare in futuro. Un testo indispensabile sia per i web designer sia per gli sviluppatori, che intendono implementare l''HTML 5 nei loro progetti di siti o che ne vogliono considerare l''utilizzo futuro.', 1, '2011-06-21'),
('9788871921518', 'Algoritmi in C', 'Pearson', '2002', '', 1, '2011-06-30'),
('9788871923031', 'Thinking in Java / Fondamenti', 'Pearson', '2011', 'Questo manuale costituisce una guida affidabile, che il lettore può seguire con facilità per essere condotto gradualmente attraverso le caratteristiche di Java, le sue funzionalità fino ad arrivare ad avere un quadro completo e rigoroso del linguaggio e delle possibilità che offre. Questa nuova edizione è aggiornata a Java SE5, ma il codice fornito dall''autore è stato testato anche con una versione beta di Java SE6, le cui novità riguardano soprattutto miglioramenti nella velocità di esecuzione e nelle librerie di Java.', 1, '2011-06-21'),
('9788871923543', 'Ingegneria del software', 'Pearson', '2011', 'Negli ultimi anni, l''ingegneria del software ha caratterizzato il progresso della nostra società. Vasti, complessi e articolati sistemi software sono alla base di Internet, della telefonia mondiale, della produzione industriale, dei sistemi di trasporto, delle nuove tecnologie in campo medicale, dei media e degli strumenti di entertainment. Il libro spazia su tutti gli aspetti coinvolti nella produzione del software, dall''iniziale analisi dei requisiti, alla progettazione, allo sviluppo, alla verifica e validazione, e alla gestione di un progetto. Ottava edizione aggiornata e ampliata.', 1, '2011-06-21'),
('9788871925691', 'Sistemi operativi. Concetti ed esempi', 'Pearson', '2011', 'Uno strumento didattico che conferma, edizione dopo edizione, il suo straordinario valore. Macchine virtuali e multi-processo come il PalmOS, sistemi operativi in tempo reale e sistemi operativi open-source sono gli argomenti di punta dei più recenti sviluppi nel campo dei sistemi operativi e delle reti, e vengono qui presentati con ricchezza di dettagli e approfondimenti tecnici. Questa ottava edizione è inoltre arricchita da nuovi problemi ed esercizi di programmazione che enfatizzano i processi, le memorie condivise, la sincronizzazione dei processi e la rete, consentendo di memorizzare e rinforzare l''apprendimento dei concetti più importanti. Il testo rappresenta quindi uno strumento consolidato per fornire una solida base teorica alla comprensione dei sistemi operativi, presentando i concetti fondamentali senza concentrarsi su di un particolare sistema operativo o hardware, e discutendone poi le loro implementazioni attuali.', 1, '2011-06-21');

-- --------------------------------------------------------

--
-- Table structure for table `libro_autore`
--

CREATE TABLE IF NOT EXISTS `libro_autore` (
  `libro` varchar(13) NOT NULL,
  `autore` int(11) NOT NULL,
  PRIMARY KEY (`libro`,`autore`),
  KEY `autore` (`autore`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `libro_autore`
--

INSERT INTO `libro_autore` (`libro`, `autore`) VALUES
('9788850329649', 1),
('9788861142879', 1),
('9788871923543', 1),
('9788850329649', 2),
('9788861142879', 2),
('9788861142879', 3),
('9788861142879', 4),
('9788850328697', 5),
('9788861142879', 5),
('9788871923031', 6),
('9788850321001', 7),
('9788871925691', 7),
('9788850321001', 8),
('9788871925691', 8),
('9788850321001', 9),
('9788871925691', 9),
('9788804589655', 10),
('9788871923543', 11),
('9788871921518', 14),
('9788848120883', 15),
('9788848120883', 16),
('9788850329670', 17),
('9788850326273', 18),
('9788850328970', 18),
('9788850329427', 18),
('9788838925634', 19),
('9788850324682', 20),
('9788820733834', 21),
('9788820733834', 22),
('9788804592389', 23);

-- --------------------------------------------------------

--
-- Table structure for table `libro_tag`
--

CREATE TABLE IF NOT EXISTS `libro_tag` (
  `libro` varchar(13) NOT NULL DEFAULT '',
  `tag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`libro`,`tag`),
  KEY `tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `libro_tag`
--

INSERT INTO `libro_tag` (`libro`, `tag`) VALUES
('9788848120883', 1),
('9788850321001', 1),
('9788850324682', 1),
('9788850326273', 1),
('9788850328697', 1),
('9788850328970', 1),
('9788850329427', 1),
('9788850329649', 1),
('9788850329670', 1),
('9788861142879', 1),
('9788871921518', 1),
('9788871923031', 1),
('9788871923543', 1),
('9788871925691', 1),
('9788848120883', 2),
('9788850324682', 2),
('9788850326273', 2),
('9788850328697', 2),
('9788850328970', 2),
('9788850329427', 2),
('9788850329649', 2),
('9788850329670', 2),
('9788861142879', 2),
('9788871923031', 2),
('9788871923543', 2),
('9788848120883', 3),
('9788850328970', 3),
('9788850329649', 3),
('9788804589655', 4),
('9788804592389', 4),
('9788838925634', 4),
('9788850326273', 5),
('9788871921518', 5),
('9788871923543', 5),
('9788820733834', 9);

-- --------------------------------------------------------

--
-- Table structure for table `lingua`
--

CREATE TABLE IF NOT EXISTS `lingua` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lingua` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `lingua`
--

INSERT INTO `lingua` (`id`, `lingua`) VALUES
(1, 'Italiano'),
(2, 'Inglese'),
(3, 'Francese'),
(4, 'Spagnolo'),
(5, 'Tedesco');

-- --------------------------------------------------------

--
-- Table structure for table `prestito`
--

CREATE TABLE IF NOT EXISTS `prestito` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataPrestito` date NOT NULL,
  `dataRestituzione` date DEFAULT NULL,
  `restituito` tinyint(1) NOT NULL DEFAULT '0',
  `volume` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `volume` (`volume`,`user`),
  KEY `user` (`user`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Dumping data for table `prestito`
--

INSERT INTO `prestito` (`id`, `dataPrestito`, `dataRestituzione`, `restituito`, `volume`, `user`) VALUES
(1, '2011-06-22', '2011-06-29', 1, 1, 3),
(2, '2011-06-22', '2011-06-27', 1, 9, 2),
(3, '2011-06-23', '2011-06-25', 1, 2, 4),
(4, '2011-05-03', '2011-06-29', 1, 12, 5),
(5, '2011-06-28', '2011-06-29', 1, 11, 2),
(6, '2011-06-28', '2011-06-29', 1, 5, 3),
(7, '2011-06-28', '2011-06-29', 1, 6, 2),
(8, '2011-06-28', '2011-06-29', 1, 2, 2),
(9, '2011-06-29', '2011-06-29', 1, 4, 5),
(10, '2011-06-29', '2011-06-29', 1, 5, 2),
(11, '2011-06-29', '2011-06-29', 1, 9, 2),
(12, '2011-06-29', '2011-06-29', 1, 5, 2),
(13, '2011-06-29', '2011-06-29', 1, 5, 2),
(14, '2011-06-29', '2011-06-29', 1, 9, 2),
(15, '2011-06-29', '2011-06-29', 1, 11, 2),
(16, '2011-06-29', '2011-06-29', 1, 12, 2),
(17, '2011-06-29', '2011-06-29', 1, 9, 2),
(18, '2011-05-26', NULL, 0, 11, 5),
(19, '2011-06-29', '2011-06-29', 1, 5, 2),
(20, '2011-06-29', NULL, 0, 12, 2),
(21, '2011-06-29', '2011-06-30', 1, 9, 3),
(22, '2011-06-29', NULL, 0, 7, 4),
(23, '2011-06-29', '2011-06-29', 1, 4, 3),
(24, '2011-06-29', '2011-06-29', 1, 8, 3),
(25, '2011-06-30', NULL, 0, 5, 3),
(26, '2011-07-01', NULL, 0, 13, 3),
(27, '2011-07-05', NULL, 0, 58, 3),
(28, '2011-07-05', NULL, 0, 53, 2),
(29, '2011-07-05', NULL, 0, 54, 5),
(30, '2011-07-05', NULL, 0, 35, 3),
(31, '2011-07-05', NULL, 0, 27, 2),
(32, '2011-07-07', NULL, 0, 43, 3);

-- --------------------------------------------------------

--
-- Table structure for table `servizio`
--

CREATE TABLE IF NOT EXISTS `servizio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `servizio` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `servizio`
--


-- --------------------------------------------------------

--
-- Table structure for table `stato`
--

CREATE TABLE IF NOT EXISTS `stato` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stato` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `stato`
--

INSERT INTO `stato` (`id`, `stato`) VALUES
(1, 'integro'),
(2, 'leggermente danneggiato'),
(3, 'danneggiato');

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE IF NOT EXISTS `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `tag`
--

INSERT INTO `tag` (`id`, `tag`) VALUES
(1, 'Informatica'),
(2, 'Programmazione'),
(3, 'Web'),
(4, 'Narrativa'),
(5, 'Ingegneria'),
(6, 'Medicina'),
(8, 'Novita'),
(9, 'Matematica');

-- --------------------------------------------------------

--
-- Table structure for table `traduzione`
--

CREATE TABLE IF NOT EXISTS `traduzione` (
  `libro1` varchar(13) NOT NULL,
  `libro2` varchar(13) NOT NULL,
  PRIMARY KEY (`libro1`,`libro2`),
  KEY `libro2` (`libro2`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `traduzione`
--


-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telefono` varchar(25) DEFAULT NULL,
  `nome` varchar(25) NOT NULL,
  `cognome` varchar(25) NOT NULL,
  `codiceFiscale` varchar(16) NOT NULL,
  `indirizzo` varchar(50) NOT NULL,
  `citta` varchar(50) NOT NULL,
  `provincia` varchar(2) NOT NULL,
  `cap` int(5) NOT NULL,
  `gruppo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gruppo` (`gruppo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `email`, `telefono`, `nome`, `cognome`, `codiceFiscale`, `indirizzo`, `citta`, `provincia`, `cap`, `gruppo`) VALUES
(1, 'bibliotecario', '18042a2d9336bf77016b1e21d915bed6', 'bibliotecario@biblio.it', '99999999999', 'Bibliotecario', 'Bibliotecario', 'BBBBBBBBBBBBBBBB', 'Via Bo, 30', 'Bibliotecario', 'BB', 99999, 1),
(2, 'giacomolm', 'e6ab178d6d3a042ff8d779dbc9c60460', 'giacomolm@hotmail.it', '3201534917', 'Giacomo', 'Lamonaco', 'LMNGCM89L22G141E', 'via san migliazzo 21', 'Miglianico', 'CH', 66100, 2),
(3, 'zilfio', '8759ded2b55ddaf88bc34a4625bc1ae7', 'zilfio88@gmail.com', '3487145593', 'Silvio', 'D''Orazio', 'DRZSLV88L24A515D', 'via Fiume, 99', 'Roccavivi', 'AQ', 67050, 2),
(4, 'prova', '189bbbb00c5f1fb7fba9ad9285f193d1', 'prova@prova.it', '9999999999999999999999999', 'Prova', 'Prova', 'PPPPPPPPPPPPPPPP', 'Via Fiume, 99', 'Prova', 'PP', 0, 2),
(5, 'acarous', '4f25a6a80d00e4af1a805e7d5d3cbc79', 'alessio.felicioni.89@gmail.com', '3899340535', 'Alessio', 'Felicioni', 'FLCLSS89H01G482N', 'via Verrotti, 238', 'Montesilvano', 'PE', 65015, 2);

-- --------------------------------------------------------

--
-- Table structure for table `volume`
--

CREATE TABLE IF NOT EXISTS `volume` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stato` int(11) DEFAULT NULL,
  `durataMax` int(11) NOT NULL DEFAULT '30',
  `libro` varchar(13) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stato` (`stato`),
  KEY `libro` (`libro`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=61 ;

--
-- Dumping data for table `volume`
--

INSERT INTO `volume` (`id`, `stato`, `durataMax`, `libro`) VALUES
(1, 1, 30, '9788871923031'),
(2, 2, 30, '9788871923031'),
(3, 1, 30, '9788871923543'),
(4, 2, 15, '9788871925691'),
(5, 1, 30, '9788861142879'),
(6, 2, 30, '9788861142879'),
(7, 1, 30, '9788850329649'),
(8, 1, 30, '9788850321001'),
(9, 1, 30, '9788850328697'),
(10, 3, 30, '9788850328697'),
(11, 2, 30, '9788804589655'),
(12, 3, 30, '9788804589655'),
(13, 1, 7, '9788848120883'),
(14, 2, 30, '9788848120883'),
(15, 1, 15, '9788871921518'),
(16, 2, 30, '9788871921518'),
(17, 3, 60, '9788871921518'),
(21, 1, 7, '9788804589655'),
(22, 1, 7, '9788804589655'),
(24, 3, 99, '9788848120883'),
(25, 1, 30, '9788871925691'),
(26, 1, 7, '9788850329649'),
(27, 1, 30, '9788850329670'),
(28, 2, 7, '9788850329670'),
(29, 1, 7, '9788850329670'),
(30, 1, 7, '9788850329427'),
(31, 1, 7, '9788850329427'),
(32, 1, 7, '9788850329427'),
(33, 1, 7, '9788850329427'),
(34, 1, 7, '9788850329427'),
(35, 1, 30, '9788850328970'),
(36, 1, 14, '9788850328970'),
(37, 1, 14, '9788850328970'),
(38, 1, 14, '9788850328970'),
(39, 1, 14, '9788850326273'),
(40, 1, 14, '9788850326273'),
(41, 1, 14, '9788850326273'),
(42, 1, 7, '9788838925634'),
(43, 1, 30, '9788850324682'),
(44, 1, 30, '9788850324682'),
(45, 1, 30, '9788850324682'),
(46, 1, 30, '9788850324682'),
(47, 1, 30, '9788850324682'),
(48, 1, 30, '9788850324682'),
(49, 1, 30, '9788850324682'),
(50, 1, 30, '9788850324682'),
(51, 1, 30, '9788850324682'),
(53, 1, 30, '9788820733834'),
(54, 1, 30, '9788820733834'),
(55, 1, 14, '9788820733834'),
(56, 1, 14, '9788820733834'),
(57, 1, 14, '9788820733834'),
(58, 2, 15, '9788804592389'),
(59, 1, 30, '9788804592389');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `copiaelettronica`
--
ALTER TABLE `copiaelettronica`
  ADD CONSTRAINT `copiaelettronica_ibfk_1` FOREIGN KEY (`libro`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `gruppo_servizio`
--
ALTER TABLE `gruppo_servizio`
  ADD CONSTRAINT `gruppo_servizio_ibfk_1` FOREIGN KEY (`gruppo`) REFERENCES `gruppo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `gruppo_servizio_ibfk_2` FOREIGN KEY (`servizio`) REFERENCES `servizio` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `libro`
--
ALTER TABLE `libro`
  ADD CONSTRAINT `libro_ibfk_1` FOREIGN KEY (`lingua`) REFERENCES `lingua` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `libro_autore`
--
ALTER TABLE `libro_autore`
  ADD CONSTRAINT `libro_autore_ibfk_1` FOREIGN KEY (`libro`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `libro_autore_ibfk_2` FOREIGN KEY (`autore`) REFERENCES `autore` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `libro_tag`
--
ALTER TABLE `libro_tag`
  ADD CONSTRAINT `libro_tag_ibfk_1` FOREIGN KEY (`libro`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `libro_tag_ibfk_2` FOREIGN KEY (`tag`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `prestito`
--
ALTER TABLE `prestito`
  ADD CONSTRAINT `prestito_ibfk_1` FOREIGN KEY (`volume`) REFERENCES `volume` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `prestito_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `traduzione`
--
ALTER TABLE `traduzione`
  ADD CONSTRAINT `traduzione_ibfk_1` FOREIGN KEY (`libro1`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `traduzione_ibfk_2` FOREIGN KEY (`libro2`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`gruppo`) REFERENCES `gruppo` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `volume`
--
ALTER TABLE `volume`
  ADD CONSTRAINT `volume_ibfk_1` FOREIGN KEY (`stato`) REFERENCES `stato` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `volume_ibfk_2` FOREIGN KEY (`libro`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE;
