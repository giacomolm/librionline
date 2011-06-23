-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 23 giu, 2011 at 02:11 PM
-- Versione MySQL: 5.5.8
-- Versione PHP: 5.3.5

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
-- Struttura della tabella `autore`
--

CREATE TABLE IF NOT EXISTS `autore` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cognome` varchar(50) NOT NULL,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dump dei dati per la tabella `autore`
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
(11, 'Sommerville', 'Ian');

-- --------------------------------------------------------

--
-- Struttura della tabella `copiaelettronica`
--

CREATE TABLE IF NOT EXISTS `copiaelettronica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mimetype` varchar(25) NOT NULL,
  `url` varchar(50) NOT NULL,
  `libro` varchar(13) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `libro` (`libro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `copiaelettronica`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `gruppo`
--

CREATE TABLE IF NOT EXISTS `gruppo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gruppo` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dump dei dati per la tabella `gruppo`
--

INSERT INTO `gruppo` (`id`, `gruppo`) VALUES
(1, 'bibliotecario'),
(2, 'registrato');

-- --------------------------------------------------------

--
-- Struttura della tabella `gruppo_servizio`
--

CREATE TABLE IF NOT EXISTS `gruppo_servizio` (
  `gruppo` int(11) NOT NULL,
  `servizio` int(11) NOT NULL,
  PRIMARY KEY (`gruppo`,`servizio`),
  KEY `servizio` (`servizio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `gruppo_servizio`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `libro`
--

CREATE TABLE IF NOT EXISTS `libro` (
  `isbn` varchar(13) NOT NULL,
  `titolo` varchar(50) NOT NULL,
  `editore` varchar(25) DEFAULT NULL,
  `annoPubblicazione` year(4) DEFAULT NULL,
  `recensione` text,
  `lingua` int(11) DEFAULT NULL,
  `data_ins` date NOT NULL,
  PRIMARY KEY (`isbn`),
  KEY `lingua` (`lingua`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `libro`
--

INSERT INTO `libro` (`isbn`, `titolo`, `editore`, `annoPubblicazione`, `recensione`, `lingua`, `data_ins`) VALUES
('9788804589655', 'La solitudine dei numeri primi', 'Mondadori', 2010, 'Alice è una bambina obbligata dal padre a frequentare la scuola di sci. È una mattina di nebbia fitta, lei non ha voglia, il latte della colazione le pesa sullo stomaco. Persa nella nebbia, staccata dai compagni, se la fa addosso. Umiliata, cerca di scendere, ma finisce fuori pista spezzandosi una gamba. Resta sola, incapace di muoversi, al fondo di un canale innevato, a domandarsi se i lupi ci sono anche in inverno. Mattia è un bambino molto intelligente, ma ha una gemella, Michela, ritardata. La presenza di Michela umilia Mattia di fronte ai suoi coetanei e per questo, la prima volta che un compagno di classe li invita entrambi alla sua festa, Mattia abbandona Michela nel parco, con la promessa che tornerà presto da lei. Questi due episodi iniziali, con le loro conseguenze irreversibili, saranno il marchio impresso a fuoco nelle vite di Alice e Mattia, adolescenti, giovani e infine adulti. Le loro esistenze si incroceranno, e si scopriranno strettamente uniti, eppure invincibilmente divisi. Come quei numeri speciali, che i matematici chiamano "primi gemelli": due numeri primi vicini ma mai abbastanza per toccarsi davvero. Un romanzo d''esordio che alterna momenti di durezza e spietata tensione a scene rarefatte e di trattenuta emozione, di sconsolata tenerezza e di tenace speranza.', 1, '2011-06-21'),
('9788850321001', 'Sistemi operativi. Con esempi per l''uso in Java', 'Apogeo', 2005, 'Questo libro introduce in modo chiaro ed esauriente allo studio dei sistemi operativi. L''enfasi è posta sugli aspetti concettuali, per fornire una solida conoscenza dei principi teorici e delle tecniche che sono i fondamenti dei sistemi operativi moderni. A completamento e concretizzazione pratica di tali aspetti teorici, vengono presentate anche le caratteristiche principali di alcuni sistemi operativi reali oggi molto diffusi (Linux, Windows 2000IXP, Solaris e MacOS) come esempi e casi di studio. Gli autori esaminano in dettaglio le componenti essenziali dei sistemi operativi: la gestione dei processi, la gestione della memoria, i sistemi di input/output e la gestione del file system.', 1, '2011-06-21'),
('9788850328697', 'Programmazione in C ', 'Apogeo', 2009, 'Il linguaggio C può essere definito la lingua franca della programmazione e, nonostante altri linguaggi (molti basati su C) abbiano guadagnato popolarità negli ultimi anni, resta il più utilizzato per la realizzazione di prodotti software e tra i più diffusi nei corsi base di programmazione. Il volume di King è uno dei testi di maggior successo in campo accademico e professionale poiché tratta in modo completo ed esaustivo le caratteristiche e le librerie del C, facendo riferimento agli standard C89 e C99. Gli argomenti sono presentati in maniera graduale e accompagnati da numerosi esempi, i concetti difficili sono introdotti dapprima in modo sintetico e ripresi successivamente in sempre maggiore dettaglio, rendendo il testo accessibile ai principianti e utile per i programmatori esperti. Completano il testo numerosi esercizi e progetti di programmazione.', 1, '2011-06-21'),
('9788850329649', 'Sviluppare siti con gli standard web', 'Apogeo', 2010, 'Questo libro si rivolge a progettisti web, sviluppatori software, imprenditori e manager che vogliono disporre di siti web a costi ridotti, che funzionino al meglio e che siano in grado di raggiungere il maggior numero di persone, e tutto questo non solo tenendo conto di browser, dispositivi mobili e altri sistemi di visualizzazione presenti al momento, ma anche di apparecchiature che l''evoluzione tecnologica saprà proporre in futuro. Chi lavora sul Web conosce bene l''obsolescenza che caratterizza lo sviluppo frenetico della tecnologia. Si realizzano siti solo per ricostruirli continuamente. Accade troppo spesso che la ricostruzione di un sito debba essere effettuata non tanto per aumentare o perfezionare le funzionalità di accesso e navigazione, ma semplicemente per tenere il passo di browser e dispositivi che sembrano sempre più avanzati dei cicli di design e sviluppo di un sito web.Questa situazione è così consolidata da apparire più che normale, al punto da costituire il prezzo da pagare per il corretto mantenimento di un sito professionale. È altrettanto vero però che si tratta di un prezzo sempre più difficile da affrontare. Gli standard web permettono di porre fine a tutto questo.', 1, '2011-06-17'),
('9788861142879', 'HTML 5', 'Mondadori Informatica', 2011, 'Tutti i siti web si basano sull''HTML, il linguaggio che descrive il formato, l''aspetto, l''impaginazione, la visualizzazione del contenuto delle pagine su Internet. HTML 5, l''ultima versione di questo linguaggio universale, è pienamente descritta - nelle specifiche e nell''uso - in questo testo che spiega cosa si può fare di nuovo, dalle semplici pagine web ai blog più creativi. Gli autori, avendo lavorato sin dall''inizio nel gruppo che ha definito le nuove specifiche, ne mettono in luce sia gli aspetti positivi sia quelli problematici, con particolare riguardo a quelli che sono ancora i limiti da superare in futuro. Un testo indispensabile sia per i web designer sia per gli sviluppatori, che intendono implementare l''HTML 5 nei loro progetti di siti o che ne vogliono considerare l''utilizzo futuro.', 1, '2011-06-21'),
('9788871923031', 'Thinking in Java / Fondamenti', 'Pearson', 2006, 'Questo manuale costituisce una guida affidabile, che il lettore può seguire con facilità per essere condotto gradualmente attraverso le caratteristiche di Java, le sue funzionalità fino ad arrivare ad avere un quadro completo e rigoroso del linguaggio e delle possibilità che offre. Questa nuova edizione è aggiornata a Java SE5, ma il codice fornito dall''autore è stato testato anche con una versione beta di Java SE6, le cui novità riguardano soprattutto miglioramenti nella velocità di esecuzione e nelle librerie di Java.', 1, '2011-06-21'),
('9788871923543', 'Ingegneria del software', 'Pearson', 2007, 'Negli ultimi anni, l''ingegneria del software ha caratterizzato il progresso della nostra società. Vasti, complessi e articolati sistemi software sono alla base di Internet, della telefonia mondiale, della produzione industriale, dei sistemi di trasporto, delle nuove tecnologie in campo medicale, dei media e degli strumenti di entertainment. Il libro spazia su tutti gli aspetti coinvolti nella produzione del software, dall''iniziale analisi dei requisiti, alla progettazione, allo sviluppo, alla verifica e validazione, e alla gestione di un progetto. Ottava edizione aggiornata e ampliata.', 1, '2011-06-21'),
('9788871925691', 'Sistemi operativi. Concetti ed esempi', 'Pearson', 2009, 'Uno strumento didattico che conferma, edizione dopo edizione, il suo straordinario valore. Macchine virtuali e multi-processo come il PalmOS, sistemi operativi in tempo reale e sistemi operativi open-source sono gli argomenti di punta dei più recenti sviluppi nel campo dei sistemi operativi e delle reti, e vengono qui presentati con ricchezza di dettagli e approfondimenti tecnici. Questa ottava edizione è inoltre arricchita da nuovi problemi ed esercizi di programmazione che enfatizzano i processi, le memorie condivise, la sincronizzazione dei processi e la rete, consentendo di memorizzare e rinforzare l''apprendimento dei concetti più importanti. Il testo rappresenta quindi uno strumento consolidato per fornire una solida base teorica alla comprensione dei sistemi operativi, presentando i concetti fondamentali senza concentrarsi su di un particolare sistema operativo o hardware, e discutendone poi le loro implementazioni attuali.', 1, '2011-06-21');

-- --------------------------------------------------------

--
-- Struttura della tabella `libro_autore`
--

CREATE TABLE IF NOT EXISTS `libro_autore` (
  `libro` varchar(13) NOT NULL,
  `autore` int(11) NOT NULL,
  PRIMARY KEY (`libro`,`autore`),
  KEY `autore` (`autore`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `libro_autore`
--

INSERT INTO `libro_autore` (`libro`, `autore`) VALUES
('9788850329649', 1),
('9788850329649', 2),
('9788861142879', 3),
('9788861142879', 4),
('9788850328697', 5),
('9788871923031', 6),
('9788850321001', 7),
('9788871925691', 7),
('9788850321001', 8),
('9788871925691', 8),
('9788850321001', 9),
('9788871925691', 9),
('9788804589655', 10),
('9788871923543', 11);

-- --------------------------------------------------------

--
-- Struttura della tabella `libro_tag`
--

CREATE TABLE IF NOT EXISTS `libro_tag` (
  `libro` varchar(13) NOT NULL DEFAULT '',
  `tag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`libro`,`tag`),
  KEY `tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `libro_tag`
--

INSERT INTO `libro_tag` (`libro`, `tag`) VALUES
('9788850321001', 1),
('9788850328697', 1),
('9788850329649', 1),
('9788861142879', 1),
('9788871923031', 1),
('9788871923543', 1),
('9788871925691', 1),
('9788850328697', 2),
('9788850329649', 2),
('9788861142879', 2),
('9788871923031', 2),
('9788871923543', 2),
('9788850329649', 3),
('9788861142879', 3),
('9788804589655', 4),
('9788871923543', 5);

-- --------------------------------------------------------

--
-- Struttura della tabella `lingua`
--

CREATE TABLE IF NOT EXISTS `lingua` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lingua` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dump dei dati per la tabella `lingua`
--

INSERT INTO `lingua` (`id`, `lingua`) VALUES
(1, 'Italiano'),
(2, 'Inglese');

-- --------------------------------------------------------

--
-- Struttura della tabella `prestito`
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dump dei dati per la tabella `prestito`
--

INSERT INTO `prestito` (`id`, `dataPrestito`, `dataRestituzione`, `restituito`, `volume`, `user`) VALUES
(1, '2011-06-22', NULL, 0, 1, 3),
(2, '2011-06-22', NULL, 0, 9, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `servizio`
--

CREATE TABLE IF NOT EXISTS `servizio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `servizio` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `servizio`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `stato`
--

CREATE TABLE IF NOT EXISTS `stato` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stato` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dump dei dati per la tabella `stato`
--

INSERT INTO `stato` (`id`, `stato`) VALUES
(1, 'integro'),
(2, 'leggermente danneggiato'),
(3, 'danneggiato');

-- --------------------------------------------------------

--
-- Struttura della tabella `tag`
--

CREATE TABLE IF NOT EXISTS `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dump dei dati per la tabella `tag`
--

INSERT INTO `tag` (`id`, `tag`) VALUES
(1, 'Informatica'),
(2, 'Programmazione'),
(3, 'Web'),
(4, 'Narrativa'),
(5, 'Ingegneria');

-- --------------------------------------------------------

--
-- Struttura della tabella `traduzione`
--

CREATE TABLE IF NOT EXISTS `traduzione` (
  `libro1` varchar(13) NOT NULL,
  `libro2` varchar(13) NOT NULL,
  PRIMARY KEY (`libro1`,`libro2`),
  KEY `libro2` (`libro2`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `traduzione`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(25) DEFAULT NULL,
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `email`, `telefono`, `nome`, `cognome`, `codiceFiscale`, `indirizzo`, `citta`, `provincia`, `cap`, `gruppo`) VALUES
(1, 'bibliotecario', '18042a2d9336bf77016b1e21d915bed6', 'bibliotecario@biblio.it', '99999999999', 'Bibliotecario', 'Bibliotecario', 'BBBBBBBBBBBBBBBB', 'Via Bo, 30', 'Bibliotecario', 'BB', 99999, 2),
(2, 'giacomolm', 'e6ab178d6d3a042ff8d779dbc9c60460', 'giacomolm@hotmail.it', '3201534917', 'Giacomo', 'Lamonaco', 'LMNGCM89L22G141E', 'via san migliazzo 21', 'Miglianico', 'CH', 66100, 2),
(3, 'zilfio', '8759ded2b55ddaf88bc34a4625bc1ae7', 'zilfio88@gmail.com', '3487145593', 'Silvio', 'D''Orazio', 'DRZSLV88L24A515D', 'via Fiume, 99', 'Roccavivi', 'AQ', 67050, 2),
(4, 'prova', '189bbbb00c5f1fb7fba9ad9285f193d1', 'prova@prova.it', '9999999999999999999999999', 'Prova', 'Prova', 'PPPPPPPPPPPPPPPP', 'Via Fiume, 99', 'Prova', 'PP', 0, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `volume`
--

CREATE TABLE IF NOT EXISTS `volume` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stato` int(11) DEFAULT NULL,
  `durataMax` int(11) NOT NULL DEFAULT '30',
  `libro` varchar(13) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stato` (`stato`),
  KEY `libro` (`libro`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dump dei dati per la tabella `volume`
--

INSERT INTO `volume` (`id`, `stato`, `durataMax`, `libro`) VALUES
(1, 1, 30, '9788871923031'),
(2, 2, 30, '9788871923031'),
(3, 1, 30, '9788871923543'),
(4, 2, 30, '9788871925691'),
(5, 1, 30, '9788861142879'),
(6, 2, 30, '9788861142879'),
(7, 1, 30, '9788850329649'),
(8, 1, 30, '9788850321001'),
(9, 1, 30, '9788850328697'),
(10, 3, 30, '9788850328697'),
(11, 2, 30, '9788804589655'),
(12, 3, 30, '9788804589655');

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `copiaelettronica`
--
ALTER TABLE `copiaelettronica`
  ADD CONSTRAINT `copiaelettronica_ibfk_1` FOREIGN KEY (`libro`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `gruppo_servizio`
--
ALTER TABLE `gruppo_servizio`
  ADD CONSTRAINT `gruppo_servizio_ibfk_1` FOREIGN KEY (`gruppo`) REFERENCES `gruppo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `gruppo_servizio_ibfk_2` FOREIGN KEY (`servizio`) REFERENCES `servizio` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `libro`
--
ALTER TABLE `libro`
  ADD CONSTRAINT `libro_ibfk_1` FOREIGN KEY (`lingua`) REFERENCES `lingua` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limiti per la tabella `libro_autore`
--
ALTER TABLE `libro_autore`
  ADD CONSTRAINT `libro_autore_ibfk_1` FOREIGN KEY (`libro`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `libro_autore_ibfk_2` FOREIGN KEY (`autore`) REFERENCES `autore` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `libro_tag`
--
ALTER TABLE `libro_tag`
  ADD CONSTRAINT `libro_tag_ibfk_1` FOREIGN KEY (`libro`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `libro_tag_ibfk_2` FOREIGN KEY (`tag`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `prestito`
--
ALTER TABLE `prestito`
  ADD CONSTRAINT `prestito_ibfk_1` FOREIGN KEY (`volume`) REFERENCES `volume` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `prestito_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Limiti per la tabella `traduzione`
--
ALTER TABLE `traduzione`
  ADD CONSTRAINT `traduzione_ibfk_1` FOREIGN KEY (`libro1`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `traduzione_ibfk_2` FOREIGN KEY (`libro2`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`gruppo`) REFERENCES `gruppo` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limiti per la tabella `volume`
--
ALTER TABLE `volume`
  ADD CONSTRAINT `volume_ibfk_1` FOREIGN KEY (`stato`) REFERENCES `stato` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `volume_ibfk_2` FOREIGN KEY (`libro`) REFERENCES `libro` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE;
