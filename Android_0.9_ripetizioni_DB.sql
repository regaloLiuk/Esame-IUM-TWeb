-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Creato il: Gen 03, 2020 alle 21:18
-- Versione del server: 8.0.18
-- Versione PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ripetizioni`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `corso`
--

DROP TABLE IF EXISTS `corso`;
CREATE TABLE IF NOT EXISTS `corso` (
  `materia` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`materia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `corso`
--

INSERT INTO `corso` (`materia`) VALUES
('Fisica'),
('Italiano'),
('Matematica'),
('Programmazione'),
('Storia');

-- --------------------------------------------------------

--
-- Struttura della tabella `insegnamento`
--

DROP TABLE IF EXISTS `insegnamento`;
CREATE TABLE IF NOT EXISTS `insegnamento` (
  `idprof` int(5) NOT NULL,
  `professore` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `materia` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`idprof`,`materia`),
  KEY `professore-corso_ibfk_2` (`materia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `insegnamento`
--

INSERT INTO `insegnamento` (`idprof`, `professore`, `materia`) VALUES
(1, 'Mario Rossi', 'Italiano'),
(1, 'Mario Rossi', 'Storia'),
(2, 'Anna Bianchi', 'Italiano'),
(3, 'Marco Neri', 'Programmazione'),
(4, 'Paolino Paperino', 'Fisica'),
(4, 'Paolino Paperino', 'Matematica');

-- --------------------------------------------------------

--
-- Struttura della tabella `professore`
--

DROP TABLE IF EXISTS `professore`;
CREATE TABLE IF NOT EXISTS `professore` (
  `nome` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `cognome` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id` int(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `professore`
--

INSERT INTO `professore` (`nome`, `cognome`, `id`) VALUES
('Mario', 'Rossi', 1),
('Anna', 'Bianchi', 2),
('Marco', 'Neri ', 3),
('Paolino', 'Paperino', 4);

-- --------------------------------------------------------

--
-- Struttura della tabella `ripetizione`
--

DROP TABLE IF EXISTS `ripetizione`;
CREATE TABLE IF NOT EXISTS `ripetizione` (
  `idprof` int(4) NOT NULL,
  `professore` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `materia` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `i` int(1) NOT NULL,
  `giorno` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ora` int(2) NOT NULL,
  `stato` int(1) NOT NULL,
  PRIMARY KEY (`idprof`,`materia`,`giorno`,`ora`),
  KEY `materia` (`materia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `ripetizione`
--

INSERT INTO `ripetizione` (`idprof`, `professore`, `materia`, `i`, `giorno`, `ora`, `stato`) VALUES
(1, 'Mario Rossi', 'Italiano', 3, 'giovedi', 15, 1),
(1, 'Mario Rossi', 'Italiano', 3, 'giovedi', 16, 1),
(1, 'Mario Rossi', 'Italiano', 3, 'giovedi', 17, 0),
(1, 'Mario Rossi', 'Italiano', 3, 'giovedi', 18, 1),
(1, 'Mario Rossi', 'Italiano', 3, 'giovedi', 19, 0),
(1, 'Mario Rossi', 'Italiano', 0, 'lunedi', 15, 1),
(1, 'Mario Rossi', 'Italiano', 0, 'lunedi', 16, 0),
(1, 'Mario Rossi', 'Italiano', 0, 'lunedi', 17, 0),
(1, 'Mario Rossi', 'Italiano', 0, 'lunedi', 18, 0),
(1, 'Mario Rossi', 'Italiano', 0, 'lunedi', 19, 0),
(1, 'Mario Rossi', 'Italiano', 1, 'martedi', 15, 0),
(1, 'Mario Rossi', 'Italiano', 1, 'martedi', 16, 0),
(1, 'Mario Rossi', 'Italiano', 1, 'martedi', 17, 0),
(1, 'Mario Rossi', 'Italiano', 1, 'martedi', 18, 0),
(1, 'Mario Rossi', 'Italiano', 1, 'martedi', 19, 0),
(1, 'Mario Rossi', 'Italiano', 2, 'mercoledi', 15, 0),
(1, 'Mario Rossi', 'Italiano', 2, 'mercoledi', 16, 0),
(1, 'Mario Rossi', 'Italiano', 2, 'mercoledi', 17, 0),
(1, 'Mario Rossi', 'Italiano', 2, 'mercoledi', 18, 0),
(1, 'Mario Rossi', 'Italiano', 2, 'mercoledi', 19, 0),
(1, 'Mario Rossi', 'Italiano', 4, 'venerdi', 15, 0),
(1, 'Mario Rossi', 'Italiano', 4, 'venerdi', 16, 0),
(1, 'Mario Rossi', 'Italiano', 4, 'venerdi', 17, 0),
(1, 'Mario Rossi', 'Italiano', 4, 'venerdi', 18, 0),
(1, 'Mario Rossi', 'Italiano', 4, 'venerdi', 19, 0),
(1, 'Mario Rossi', 'Storia', 3, 'giovedi', 15, 1),
(1, 'Mario Rossi', 'Storia', 3, 'giovedi', 16, 1),
(1, 'Mario Rossi', 'Storia', 3, 'giovedi', 17, 0),
(1, 'Mario Rossi', 'Storia', 3, 'giovedi', 18, 1),
(1, 'Mario Rossi', 'Storia', 3, 'giovedi', 19, 0),
(1, 'Mario Rossi', 'Storia', 0, 'lunedi', 15, 1),
(1, 'Mario Rossi', 'Storia', 0, 'lunedi', 16, 0),
(1, 'Mario Rossi', 'Storia', 0, 'lunedi', 17, 0),
(1, 'Mario Rossi', 'Storia', 0, 'lunedi', 18, 0),
(1, 'Mario Rossi', 'Storia', 0, 'lunedi', 19, 0),
(1, 'Mario Rossi', 'Storia', 1, 'martedi', 15, 0),
(1, 'Mario Rossi', 'Storia', 1, 'martedi', 16, 0),
(1, 'Mario Rossi', 'Storia', 1, 'martedi', 17, 0),
(1, 'Mario Rossi', 'Storia', 1, 'martedi', 18, 0),
(1, 'Mario Rossi', 'Storia', 1, 'martedi', 19, 0),
(1, 'Mario Rossi', 'Storia', 2, 'mercoledi', 15, 0),
(1, 'Mario Rossi', 'Storia', 2, 'mercoledi', 16, 0),
(1, 'Mario Rossi', 'Storia', 2, 'mercoledi', 17, 0),
(1, 'Mario Rossi', 'Storia', 2, 'mercoledi', 18, 0),
(1, 'Mario Rossi', 'Storia', 2, 'mercoledi', 19, 0),
(1, 'Mario Rossi', 'Storia', 4, 'venerdi', 15, 0),
(1, 'Mario Rossi', 'Storia', 4, 'venerdi', 16, 0),
(1, 'Mario Rossi', 'Storia', 4, 'venerdi', 17, 0),
(1, 'Mario Rossi', 'Storia', 4, 'venerdi', 18, 0),
(1, 'Mario Rossi', 'Storia', 4, 'venerdi', 19, 0),
(2, 'Anna Bianchi', 'Italiano', 3, 'giovedi', 15, 0),
(2, 'Anna Bianchi', 'Italiano', 3, 'giovedi', 16, 0),
(2, 'Anna Bianchi', 'Italiano', 3, 'giovedi', 17, 0),
(2, 'Anna Bianchi', 'Italiano', 3, 'giovedi', 18, 0),
(2, 'Anna Bianchi', 'Italiano', 3, 'giovedi', 19, 0),
(2, 'Anna Bianchi', 'Italiano', 0, 'lunedi', 15, 1),
(2, 'Anna Bianchi', 'Italiano', 0, 'lunedi', 16, 1),
(2, 'Anna Bianchi', 'Italiano', 0, 'lunedi', 17, 0),
(2, 'Anna Bianchi', 'Italiano', 0, 'lunedi', 18, 0),
(2, 'Anna Bianchi', 'Italiano', 0, 'lunedi', 19, 0),
(2, 'Anna Bianchi', 'Italiano', 1, 'martedi', 15, 0),
(2, 'Anna Bianchi', 'Italiano', 1, 'martedi', 16, 0),
(2, 'Anna Bianchi', 'Italiano', 1, 'martedi', 17, 0),
(2, 'Anna Bianchi', 'Italiano', 1, 'martedi', 18, 0),
(2, 'Anna Bianchi', 'Italiano', 1, 'martedi', 19, 0),
(2, 'Anna Bianchi', 'Italiano', 2, 'mercoledi', 15, 0),
(2, 'Anna Bianchi', 'Italiano', 2, 'mercoledi', 16, 0),
(2, 'Anna Bianchi', 'Italiano', 2, 'mercoledi', 17, 0),
(2, 'Anna Bianchi', 'Italiano', 2, 'mercoledi', 18, 0),
(2, 'Anna Bianchi', 'Italiano', 2, 'mercoledi', 19, 0),
(2, 'Anna Bianchi', 'Italiano', 4, 'venerdi', 15, 0),
(2, 'Anna Bianchi', 'Italiano', 4, 'venerdi', 16, 0),
(2, 'Anna Bianchi', 'Italiano', 4, 'venerdi', 17, 0),
(2, 'Anna Bianchi', 'Italiano', 4, 'venerdi', 18, 0),
(2, 'Anna Bianchi', 'Italiano', 4, 'venerdi', 19, 0),
(3, 'Marco Neri', 'Programmazione', 3, 'giovedi', 15, 0),
(3, 'Marco Neri', 'Programmazione', 3, 'giovedi', 16, 0),
(3, 'Marco Neri', 'Programmazione', 3, 'giovedi', 17, 0),
(3, 'Marco Neri', 'Programmazione', 3, 'giovedi', 18, 1),
(3, 'Marco Neri', 'Programmazione', 3, 'giovedi', 19, 0),
(3, 'Marco Neri', 'Programmazione', 0, 'lunedi', 15, 1),
(3, 'Marco Neri', 'Programmazione', 0, 'lunedi', 16, 0),
(3, 'Marco Neri', 'Programmazione', 0, 'lunedi', 17, 0),
(3, 'Marco Neri', 'Programmazione', 0, 'lunedi', 18, 0),
(3, 'Marco Neri', 'Programmazione', 0, 'lunedi', 19, 0),
(3, 'Marco Neri', 'Programmazione', 1, 'martedi', 15, 0),
(3, 'Marco Neri', 'Programmazione', 1, 'martedi', 16, 0),
(3, 'Marco Neri', 'Programmazione', 1, 'martedi', 17, 1),
(3, 'Marco Neri', 'Programmazione', 1, 'martedi', 18, 0),
(3, 'Marco Neri', 'Programmazione', 1, 'martedi', 19, 0),
(3, 'Marco Neri', 'Programmazione', 2, 'mercoledi', 15, 0),
(3, 'Marco Neri', 'Programmazione', 2, 'mercoledi', 16, 0),
(3, 'Marco Neri', 'Programmazione', 2, 'mercoledi', 17, 0),
(3, 'Marco Neri', 'Programmazione', 2, 'mercoledi', 18, 0),
(3, 'Marco Neri', 'Programmazione', 2, 'mercoledi', 19, 0),
(3, 'Marco Neri', 'Programmazione', 4, 'venerdi', 15, 0),
(3, 'Marco Neri', 'Programmazione', 4, 'venerdi', 16, 0),
(3, 'Marco Neri', 'Programmazione', 4, 'venerdi', 17, 0),
(3, 'Marco Neri', 'Programmazione', 4, 'venerdi', 18, 0),
(3, 'Marco Neri', 'Programmazione', 4, 'venerdi', 19, 0),
(4, 'Paolino Paperino', 'Fisica', 3, 'giovedi', 15, 1),
(4, 'Paolino Paperino', 'Fisica', 3, 'giovedi', 16, 1),
(4, 'Paolino Paperino', 'Fisica', 3, 'giovedi', 17, 1),
(4, 'Paolino Paperino', 'Fisica', 3, 'giovedi', 18, 1),
(4, 'Paolino Paperino', 'Fisica', 3, 'giovedi', 19, 1),
(4, 'Paolino Paperino', 'Fisica', 0, 'lunedi', 15, 1),
(4, 'Paolino Paperino', 'Fisica', 0, 'lunedi', 16, 0),
(4, 'Paolino Paperino', 'Fisica', 0, 'lunedi', 17, 0),
(4, 'Paolino Paperino', 'Fisica', 0, 'lunedi', 18, 0),
(4, 'Paolino Paperino', 'Fisica', 0, 'lunedi', 19, 0),
(4, 'Paolino Paperino', 'Fisica', 1, 'martedi', 15, 1),
(4, 'Paolino Paperino', 'Fisica', 1, 'martedi', 16, 0),
(4, 'Paolino Paperino', 'Fisica', 1, 'martedi', 17, 0),
(4, 'Paolino Paperino', 'Fisica', 1, 'martedi', 18, 0),
(4, 'Paolino Paperino', 'Fisica', 1, 'martedi', 19, 0),
(4, 'Paolino Paperino', 'Fisica', 2, 'mercoledi', 15, 0),
(4, 'Paolino Paperino', 'Fisica', 2, 'mercoledi', 16, 0),
(4, 'Paolino Paperino', 'Fisica', 2, 'mercoledi', 17, 0),
(4, 'Paolino Paperino', 'Fisica', 2, 'mercoledi', 18, 0),
(4, 'Paolino Paperino', 'Fisica', 2, 'mercoledi', 19, 0),
(4, 'Paolino Paperino', 'Fisica', 4, 'venerdi', 15, 0),
(4, 'Paolino Paperino', 'Fisica', 4, 'venerdi', 16, 0),
(4, 'Paolino Paperino', 'Fisica', 4, 'venerdi', 17, 0),
(4, 'Paolino Paperino', 'Fisica', 4, 'venerdi', 18, 0),
(4, 'Paolino Paperino', 'Fisica', 4, 'venerdi', 19, 0),
(4, 'Paolino Paperino', 'Matematica', 3, 'giovedi', 15, 1),
(4, 'Paolino Paperino', 'Matematica', 3, 'giovedi', 16, 1),
(4, 'Paolino Paperino', 'Matematica', 3, 'giovedi', 17, 1),
(4, 'Paolino Paperino', 'Matematica', 3, 'giovedi', 18, 1),
(4, 'Paolino Paperino', 'Matematica', 3, 'giovedi', 19, 1),
(4, 'Paolino Paperino', 'Matematica', 0, 'lunedi', 15, 0),
(4, 'Paolino Paperino', 'Matematica', 0, 'lunedi', 16, 0),
(4, 'Paolino Paperino', 'Matematica', 0, 'lunedi', 17, 0),
(4, 'Paolino Paperino', 'Matematica', 0, 'lunedi', 18, 0),
(4, 'Paolino Paperino', 'Matematica', 0, 'lunedi', 19, 0),
(4, 'Paolino Paperino', 'Matematica', 1, 'martedi', 15, 1),
(4, 'Paolino Paperino', 'Matematica', 1, 'martedi', 16, 0),
(4, 'Paolino Paperino', 'Matematica', 1, 'martedi', 17, 0),
(4, 'Paolino Paperino', 'Matematica', 1, 'martedi', 18, 0),
(4, 'Paolino Paperino', 'Matematica', 1, 'martedi', 19, 0),
(4, 'Paolino Paperino', 'Matematica', 2, 'mercoledi', 15, 0),
(4, 'Paolino Paperino', 'Matematica', 2, 'mercoledi', 16, 0),
(4, 'Paolino Paperino', 'Matematica', 2, 'mercoledi', 17, 0),
(4, 'Paolino Paperino', 'Matematica', 2, 'mercoledi', 18, 0),
(4, 'Paolino Paperino', 'Matematica', 2, 'mercoledi', 19, 0),
(4, 'Paolino Paperino', 'Matematica', 4, 'venerdi', 15, 0),
(4, 'Paolino Paperino', 'Matematica', 4, 'venerdi', 16, 0),
(4, 'Paolino Paperino', 'Matematica', 4, 'venerdi', 17, 0),
(4, 'Paolino Paperino', 'Matematica', 4, 'venerdi', 18, 0),
(4, 'Paolino Paperino', 'Matematica', 4, 'venerdi', 19, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `storico`
--

DROP TABLE IF EXISTS `storico`;
CREATE TABLE IF NOT EXISTS `storico` (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `idprof` int(4) NOT NULL,
  `professore` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `materia` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `giorno` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ora` int(2) NOT NULL,
  `stato` int(1) NOT NULL,
  PRIMARY KEY (`username`,`idprof`,`materia`,`giorno`,`ora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `storico`
--

INSERT INTO `storico` (`username`, `idprof`, `professore`, `materia`, `giorno`, `ora`, `stato`) VALUES
('GinoPasticcino56', 1, 'Mario Rossi', 'Storia', 'lunedi', 15, 1),
('GinoPasticcino56', 3, 'Marco Neri ', 'Programmazione', 'martedi', 17, 1),
('PinoAlbero34', 1, 'Mario Rossi', 'Italiano', 'giovedi', 15, 0),
('PinoAlbero34', 1, 'Mario Rossi', 'Italiano', 'giovedi', 16, 0),
('PinoAlbero34', 1, 'Mario Rossi', 'Italiano', 'giovedi', 18, 1),
('PinoAlbero34', 3, 'Marco Neri ', 'Programmazione', 'giovedi', 18, 1),
('PinoAlbero34', 4, 'Paolino Paperino', 'Fisica', 'giovedi', 19, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

DROP TABLE IF EXISTS `utente`;
CREATE TABLE IF NOT EXISTS `utente` (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ruolo` int(2) NOT NULL,
  `nome` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `cognome` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`username`, `password`, `ruolo`, `nome`, `cognome`, `email`) VALUES
('Amministratore', 'password', 1, '', '', ''),
('GinoPasticcino56', 'dolci', 0, 'Gino', 'Pasticcino', 'cremashantily@email.it'),
('PinoAlbero34', 'abete', 0, 'Pino', 'Albero', 'natale2008@email.com');

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `insegnamento`
--
ALTER TABLE `insegnamento`
  ADD CONSTRAINT `insegnamento_ibfk_1` FOREIGN KEY (`idprof`) REFERENCES `professore` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `insegnamento_ibfk_2` FOREIGN KEY (`materia`) REFERENCES `corso` (`materia`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `ripetizione`
--
ALTER TABLE `ripetizione`
  ADD CONSTRAINT `ripetizione_ibfk_1` FOREIGN KEY (`idprof`) REFERENCES `professore` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ripetizione_ibfk_2` FOREIGN KEY (`materia`) REFERENCES `corso` (`materia`) ON DELETE CASCADE;

--
-- Limiti per la tabella `storico`
--
ALTER TABLE `storico`
  ADD CONSTRAINT `storico_ibfk_1` FOREIGN KEY (`username`) REFERENCES `utente` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
