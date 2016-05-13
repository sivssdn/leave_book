-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 15, 2016 at 01:08 PM
-- Server version: 5.7.12-log
-- PHP Version: 5.5.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `leave_book`
--

-- --------------------------------------------------------

--
-- Table structure for table `gate`
--

CREATE TABLE IF NOT EXISTS `gate` (
  `serial` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(10) NOT NULL,
  `date_out` date NOT NULL,
  `time_out` time NOT NULL,
  `date_in` date NOT NULL,
  `time_in` time NOT NULL,
  `late` text NOT NULL,
  PRIMARY KEY (`serial`),
  UNIQUE KEY `serial` (`serial`),
  KEY `student_id` (`student_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `gate`
--

INSERT INTO `gate` (`serial`, `student_id`, `date_out`, `time_out`, `date_in`, `time_in`, `late`) VALUES
(1, '121', '2016-04-04', '02:02:02', '2016-04-07', '01:01:01', 'no');

-- --------------------------------------------------------

--
-- Table structure for table `master`
--

CREATE TABLE IF NOT EXISTS `master` (
  `student_id` varchar(20) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `primary_contact` int(15) NOT NULL,
  `secondary_contact` int(15) NOT NULL,
  `batch` int(4) NOT NULL,
  `email` varchar(60) NOT NULL,
  `hostel` varchar(10) NOT NULL,
  `room_number` int(3) NOT NULL,
  `hid` varchar(16) NOT NULL,
  `image` varchar(50) NOT NULL,
  `permission` varchar(10) NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`student_id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `master`
--

INSERT INTO `master` (`student_id`, `name`, `primary_contact`, `secondary_contact`, `batch`, `email`, `hostel`, `room_number`, `hid`, `image`, `permission`, `status`) VALUES
('3308', 'paras', 10, 10, 2017, 'paras.bhattrai@ashoka.edu.in', 'men', 426, '11', '11', 'one time', 1);

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `serial` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(12) NOT NULL,
  `date_out` date NOT NULL,
  `time_out` time NOT NULL,
  `date_in` date NOT NULL,
  `time_in` time NOT NULL,
  PRIMARY KEY (`serial`),
  UNIQUE KEY `serial` (`serial`),
  KEY `student_id` (`student_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`serial`, `student_id`, `date_out`, `time_out`, `date_in`, `time_in`) VALUES
(1, '101', '2016-04-05', '01:01:01', '2016-04-06', '01:01:01');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
