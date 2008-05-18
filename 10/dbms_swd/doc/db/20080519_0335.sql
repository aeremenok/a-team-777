-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.13-rc-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema etu
--

CREATE DATABASE IF NOT EXISTS etu;
USE etu;

--
-- Definition of table `db_artist`
--

DROP TABLE IF EXISTS `db_artist`;
CREATE TABLE `db_artist` (
  `artistID` int(11) NOT NULL auto_increment,
  `birthdate` datetime default NULL,
  `country` varchar(50) default NULL,
  `description` varchar(1000) default NULL,
  `imageUrl` varchar(255) default NULL,
  `name` varchar(100) default NULL,
  `picture` longblob,
  PRIMARY KEY  (`artistID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `db_artist`
--

/*!40000 ALTER TABLE `db_artist` DISABLE KEYS */;
INSERT INTO `db_artist` (`artistID`,`birthdate`,`country`,`description`,`imageUrl`,`name`,`picture`) VALUES 
 (1,'2008-05-19 01:04:27','','(1817–1900)','images/artist.jpg','Айвазовский (Гайвазовский) Иван Константинович',NULL),
 (2,'2008-05-19 02:03:57','','Родился 29 ноября (10 декабря) 1798 в семье художника П.И.Брюлло, брат живописца К.П.Брюллова. Получил начальное образование у отца, мастера декоративной резьбы, затем учился в Академии художеств (1810–1821). Летом 1822 его вместе с братом послали за границу за счет Общества поощрения художеств. Посетив Германию, Францию, Италию, Англию и Швейцарию, в 1830 он возвратился в Петербург. С 1831 – профессор Академии художеств.','images/Brullov.jpg','Брюллов Александр Павлович',NULL),
 (3,'2008-05-19 02:21:16','','Репин родился 24 июля (5 августа) 1844 года в семье военного поселенца в городе Чугуев Харьковской губернии. В 1863 году поступил в Академию Художеств в Петербурге. Учился у Р.К.Жуковского и И.Н.Крамского. С начала 1890-х годов Репин подолгу работал в приобретённом им имении Здравневе Витебской губернии. В 1894—1907 годах он стал профессором-руководителем мастерской в Академии художеств. С 1873 года Репин путешествует за границей, по возвращении в Петербург становится деятельным членом Товарищества передвижных художественных выставок, к которому он примкнул с 1874 году. В 1893 году Репин стал действительным членом Петербургской Академии художеств. Художник скончался 29 сентября 1930 года в Куоккале, где и был похоронен.','images/autor_img.jpg','Репин Илья Ефимович',NULL),
 (4,'2008-05-19 03:17:52','','Кипренский Орест Адамович. (1782–1836)','images/00170_b.jpg','Кипренский Орест Адамович',NULL);
/*!40000 ALTER TABLE `db_artist` ENABLE KEYS */;


--
-- Definition of table `db_genre`
--

DROP TABLE IF EXISTS `db_genre`;
CREATE TABLE `db_genre` (
  `Name` varchar(255) default NULL,
  `descrption` varchar(1000) default NULL,
  `genreID` int(11) NOT NULL auto_increment,
  `imageURL` varchar(255) default NULL,
  `picture` longblob,
  PRIMARY KEY  (`genreID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `db_genre`
--

/*!40000 ALTER TABLE `db_genre` DISABLE KEYS */;
INSERT INTO `db_genre` (`Name`,`descrption`,`genreID`,`imageURL`,`picture`) VALUES 
 ('Автопортрет','',1,'images/portraitoftretyakov.jpg',NULL),
 ('Пейзаж','Это вид изобразительного искусства, в котором возможно использование различных материалов для написания картин. Это может быть живопись, графика или просто рисунок углём на бумаге, так же для написания природы можно работать акварелью.',2,'images/05214i.jpg',NULL),
 ('Натюрморт','Слово натюрморт переводится дословно, как мёртвая жизнь или застывшая жизнь, задача художника в этом жанре писать картины, которые дадут возможность зрителю углубиться в атмосферу, царившую в мастерской художника в момент написания картины. Натюрморт это мгновение жизни застывшее на картине, которое можно изобразить, используя различные материалы, такие как масло, холст, бумага акварель, сангина или уголь.',3,'images/i.jpeg',NULL),
 ('Портрет','В этом разделе находятся, картинная галерея, в которой представлены картины, написанные в жанре портрета. Здесь вы можете выбрать и купить картину, заказать портрет с натуры или по фото.',7,'images/1123.jpeg',NULL),
 ('Графика','Графика – вид изобразительного искусства, где художник передаёт красоту божьего мира и человека как венца творения создателя в чёрно белых тонах, то есть без использования цвета. Для графических картин используется большёе количество материалов исполнения. Сангина, уголь, итальянский карандаш, белила, мел и даже акварель и масляные краски при написании гризальных картин.',8,'images/1234.jpeg',NULL);
/*!40000 ALTER TABLE `db_genre` ENABLE KEYS */;


--
-- Definition of table `db_masterpiece`
--

DROP TABLE IF EXISTS `db_masterpiece`;
CREATE TABLE `db_masterpiece` (
  `ID_artist` int(11) NOT NULL,
  `ID_genre` int(11) default NULL,
  `ID_museum` int(11) default NULL,
  `ID_type` int(11) NOT NULL,
  `creationYear` int(11) default NULL,
  `description` varchar(1000) default NULL,
  `height` int(11) default NULL,
  `imageURL` varchar(255) default NULL,
  `mass` int(11) default NULL,
  `masterpieceID` int(11) NOT NULL auto_increment,
  `material` varchar(100) default NULL,
  `photo` varchar(255) default NULL,
  `picture` longblob,
  `title` varchar(255) default NULL,
  `width` int(11) default NULL,
  PRIMARY KEY  (`masterpieceID`),
  KEY `ID_artist` (`ID_artist`),
  KEY `ID_genre` (`ID_genre`),
  KEY `ID_museum` (`ID_museum`),
  CONSTRAINT `db_masterpiece_ibfk_1` FOREIGN KEY (`ID_artist`) REFERENCES `db_artist` (`artistID`),
  CONSTRAINT `db_masterpiece_ibfk_2` FOREIGN KEY (`ID_genre`) REFERENCES `db_genre` (`genreID`),
  CONSTRAINT `db_masterpiece_ibfk_3` FOREIGN KEY (`ID_museum`) REFERENCES `db_museum` (`museumID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `db_masterpiece`
--

/*!40000 ALTER TABLE `db_masterpiece` DISABLE KEYS */;
INSERT INTO `db_masterpiece` (`ID_artist`,`ID_genre`,`ID_museum`,`ID_type`,`creationYear`,`description`,`height`,`imageURL`,`mass`,`masterpieceID`,`material`,`photo`,`picture`,`title`,`width`) VALUES 
 (1,1,4,1,NULL,'',NULL,'images/J74i.jpg',NULL,3,NULL,NULL,NULL,'Лунная ночь на Босфоре',NULL),
 (2,2,4,1,2009,'Холст, масло \r\n1845–1846',52,'images/06068_b.jpg',NULL,6,NULL,NULL,NULL,'Прогулка в коляске',42),
 (3,7,4,1,1881,'69 × 57\r\nХолст, масло\r\n1881',57,'images/00730_b.jpg',NULL,9,NULL,NULL,NULL,'Портрет композитора Модеста Петровича Мусоргского',69),
 (3,7,4,1,1885,'Репин И. Е.\r\nИван Грозный и сын его Иван 16 ноября 1581 года\r\nХолст, масло\r\n1885\r\nlavr',254,'images/00743_b.jpg',NULL,12,NULL,NULL,NULL,'Иван Грозный и сын его Иван 16 ноября 1581 года',199),
 (3,7,4,1,1888,'Репин И. Е.\r\nНе ждали \r\n \r\nХолст, масло\r\n1884–1888\r\nlavr',167,'images/00740_b.jpg',NULL,15,NULL,NULL,NULL,'Не ждали',160),
 (3,1,4,1,1882,'Репин И. Е. \r\n61 × 50  \r\nХолст, масло\r\n1882',50,'images/00734_b.jpg',NULL,18,NULL,NULL,NULL,'Актриса Пелагея Антиповна Стрепетова',61),
 (3,7,4,1,1883,'Репин И. Е.\r\nКрестный ход в Курской губернии\r\n175 × 280\r\nХолст, масло\r\n1880-1883  ',280,'images/00738_b.jpg',NULL,21,NULL,NULL,NULL,'Крестный ход в Курской губернии',175),
 (4,2,4,1,1828,'Кипренский О. А.\r\nАвтопортрет\r\n48,5 х 42,3\r\nХолст, масло\r\n1828',42,'images/00170_b.jpg',NULL,32,NULL,NULL,NULL,'Кипренский Орест Адамович',48);
/*!40000 ALTER TABLE `db_masterpiece` ENABLE KEYS */;


--
-- Definition of table `db_museum`
--

DROP TABLE IF EXISTS `db_museum`;
CREATE TABLE `db_museum` (
  `description` varchar(1000) default NULL,
  `imageURL` varchar(255) default NULL,
  `museumID` int(11) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  `picture` longblob,
  `www` varchar(255) default NULL,
  PRIMARY KEY  (`museumID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `db_museum`
--

/*!40000 ALTER TABLE `db_museum` DISABLE KEYS */;
INSERT INTO `db_museum` (`description`,`imageURL`,`museumID`,`name`,`picture`,`www`) VALUES 
 ('Государственный Эрмитаж занимает шесть величественных зданий, расположенных вдоль набережной Невы в самом центре Санкт-Петербурга. Ведущее место в этом неповторимом архитектурном ансамбле, сложившемся в XVIII - XIX веках, занимает Зимний дворец - резиденция русских царей, построенная в 1754 -1762 годах по проекту Ф.Б. Растрелли. В музейный комплекс входят также восточное крыло здания Главного штаба, Меншиковский дворец и недавно построенное Фондохранилище.','images/hermitage.jpg',1,'Эрмитаж',NULL,NULL),
 ('Русский музей - первый в стране государственный музей русского изобразительного искусства, основан в 1895 году в Санкт-Петербурге по Указу императора Николая II. Торжественно открылся для посетителей - 19 марта (7 марта по старому стилю) 1898 года','images/russmus.jpg',2,'Русский музей',NULL,NULL),
 ('Павел Михайлович Третьяков происходил из древнего, но небогатого купеческого рода, который жил в Малоярославце. Можно было бы составить подробную родословную знаменитого собирателя, если бы при отступлении Наполеоновской армии в 1812 году не сгорели архивы города.','images/f1.jpg',4,'Государственная Третьяковская Галерея',NULL,NULL);
/*!40000 ALTER TABLE `db_museum` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
