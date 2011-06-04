-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 11 mag, 2011 at 01:06 PM
-- Versione MySQL: 5.1.36
-- Versione PHP: 5.3.0

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
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `autore`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `copiaelettronica`
--

CREATE TABLE IF NOT EXISTS `copiaelettronica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mimetype` varchar(25) NOT NULL,
  `url` varchar(50) NOT NULL,
  `libro` varchar(10) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `gruppo`
--


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
  `isbn` varchar(10) NOT NULL,
  `titolo` varchar(50) NOT NULL,
  `editore` varchar(25) DEFAULT NULL,
  `annoPubblicazione` int(4) DEFAULT NULL,
  `recensione` text,
  `lingua` int(11) DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  KEY `lingua` (`lingua`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `libro`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `libro_autore`
--

CREATE TABLE IF NOT EXISTS `libro_autore` (
  `libro` varchar(10) NOT NULL,
  `autore` int(11) NOT NULL,
  PRIMARY KEY (`libro`,`autore`),
  KEY `autore` (`autore`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `libro_autore`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `libro_tag`
--

CREATE TABLE IF NOT EXISTS `libro_tag` (
  `libro` varchar(10) NOT NULL,
  `tag` int(11) NOT NULL,
  PRIMARY KEY (`libro`,`tag`),
  KEY `tag` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `libro_tag`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `lingua`
--

CREATE TABLE IF NOT EXISTS `lingua` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lingua` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `lingua`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `prestito`
--

CREATE TABLE IF NOT EXISTS `prestito` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataPrestito` date NOT NULL,
  `dataRestituzione` date NOT NULL,
  `restituito` tinyint(1) NOT NULL DEFAULT '0',
  `volume` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `volume` (`volume`,`user`),
  KEY `user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `prestito`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `stato`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `tag`
--

CREATE TABLE IF NOT EXISTS `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `tag`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `traduzione`
--

CREATE TABLE IF NOT EXISTS `traduzione` (
  `libro1` varchar(10) NOT NULL,
  `libro2` varchar(10) NOT NULL,
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
  `password` varchar(25) NOT NULL,
  `email` varchar(25) DEFAULT NULL,
  `telefono` varchar(25) DEFAULT NULL,
  `nome` varchar(25) NOT NULL,
  `cognome` varchar(25) NOT NULL,
  `codiceFiscale` varchar(16) NOT NULL,
  `indirizzo` varchar(50) NOT NULL,
  `citta` varchar(50) NOT NULL,
  `provincia` varchar(25) NOT NULL,
  `cap` int(5) NOT NULL,
  `gruppo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gruppo` (`gruppo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `user`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `volume`
--

CREATE TABLE IF NOT EXISTS `volume` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stato` int(11) DEFAULT NULL,
  `libro` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stato` (`stato`),
  KEY `libro` (`libro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `volume`
--


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
