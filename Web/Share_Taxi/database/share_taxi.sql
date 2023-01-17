/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9 : Database - share_taxi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`share_taxi` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `share_taxi`;

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rateid` int(10) NOT NULL AUTO_INCREMENT,
  `req_id` int(10) DEFAULT NULL,
  `rating` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`rateid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

/*Table structure for table `req_aprvl` */

DROP TABLE IF EXISTS `req_aprvl`;

CREATE TABLE `req_aprvl` (
  `apr_id` int(10) NOT NULL AUTO_INCREMENT,
  `req_id` int(10) DEFAULT NULL,
  `driver_id` int(10) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`apr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `req_aprvl` */

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `req_id` int(10) NOT NULL AUTO_INCREMENT,
  `rider_id` int(10) DEFAULT NULL,
  `flatitude` varchar(50) DEFAULT NULL,
  `flongitude` varchar(50) DEFAULT NULL,
  `tlatitude` varchar(50) DEFAULT NULL,
  `tlongitude` varchar(50) DEFAULT NULL,
  `noofpassenger` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`req_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `request` */

/*Table structure for table `tbl_advancepay` */

DROP TABLE IF EXISTS `tbl_advancepay`;

CREATE TABLE `tbl_advancepay` (
  `advpay_id` int(10) NOT NULL AUTO_INCREMENT,
  `apr_id` int(10) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `balance` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`advpay_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `tbl_advancepay` */

/*Table structure for table `tbl_booking` */

DROP TABLE IF EXISTS `tbl_booking`;

CREATE TABLE `tbl_booking` (
  `book_id` int(10) NOT NULL AUTO_INCREMENT,
  `rider_id` int(10) DEFAULT NULL,
  `vehicle_id` int(10) DEFAULT NULL,
  `booking_from` varchar(50) DEFAULT NULL,
  `booking_to` varchar(50) DEFAULT NULL,
  `booking_date` varchar(50) DEFAULT NULL,
  `booking_time` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `booking_status` varchar(50) DEFAULT NULL,
  `req_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `tbl_booking` */

/*Table structure for table `tbl_complaint` */

DROP TABLE IF EXISTS `tbl_complaint`;

CREATE TABLE `tbl_complaint` (
  `complaint_id` int(10) NOT NULL AUTO_INCREMENT,
  `rider_id` int(10) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_complaint` */

insert  into `tbl_complaint`(`complaint_id`,`rider_id`,`complaint`,`reply`,`date`,`status`) values (1,1,'too late','ok','12-21-2019','replied');

/*Table structure for table `tbl_driverregistration` */

DROP TABLE IF EXISTS `tbl_driverregistration`;

CREATE TABLE `tbl_driverregistration` (
  `driver_id` int(10) NOT NULL AUTO_INCREMENT,
  `driver_fname` varchar(50) DEFAULT NULL,
  `driver_lname` varchar(50) DEFAULT NULL,
  `driver_hname` varchar(50) DEFAULT NULL,
  `driver_city` varchar(50) DEFAULT NULL,
  `driver_pincode` varchar(50) DEFAULT NULL,
  `driver_gender` varchar(50) DEFAULT NULL,
  `driver_email` varchar(50) DEFAULT NULL,
  `driver_dob` varchar(50) DEFAULT NULL,
  `driver_phn` varchar(50) DEFAULT NULL,
  `driver_lisence` varchar(50) DEFAULT NULL,
  `driver_photo` varchar(150) DEFAULT NULL,
  `driver_exp` varchar(50) DEFAULT NULL,
  `driver_doj` varchar(50) DEFAULT NULL,
  `driver_status` varchar(50) DEFAULT NULL,
  `loginid` int(10) DEFAULT NULL,
  PRIMARY KEY (`driver_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_driverregistration` */

insert  into `tbl_driverregistration`(`driver_id`,`driver_fname`,`driver_lname`,`driver_hname`,`driver_city`,`driver_pincode`,`driver_gender`,`driver_email`,`driver_dob`,`driver_phn`,`driver_lisence`,`driver_photo`,`driver_exp`,`driver_doj`,`driver_status`,`loginid`) values (1,'Arjun','S.K','Rosevilla','Kochi','687676','Male','arjun@gmail.com','12-04-1985','9054564636','FCC76567',NULL,'3','23-04-2010','Active',2);

/*Table structure for table `tbl_feedback` */

DROP TABLE IF EXISTS `tbl_feedback`;

CREATE TABLE `tbl_feedback` (
  `feedback_id` int(10) NOT NULL AUTO_INCREMENT,
  `rider_id` int(10) DEFAULT NULL,
  `feedback` varchar(100) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_feedback` */

insert  into `tbl_feedback`(`feedback_id`,`rider_id`,`feedback`,`date`,`time`,`status`) values (1,1,'good','12-21-2019','3:09 PM','ok');

/*Table structure for table `tbl_location` */

DROP TABLE IF EXISTS `tbl_location`;

CREATE TABLE `tbl_location` (
  `loc_id` int(10) NOT NULL AUTO_INCREMENT,
  `driver_id` int(10) DEFAULT NULL,
  `lattitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `availability` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `tbl_location` */

/*Table structure for table `tbl_login` */

DROP TABLE IF EXISTS `tbl_login`;

CREATE TABLE `tbl_login` (
  `loginid` int(10) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `login_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`loginid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_login` */

insert  into `tbl_login`(`loginid`,`uname`,`password`,`login_type`) values (1,'admin','admin','admin'),(2,'ar','ar','driver'),(3,'ll','ll','rider');

/*Table structure for table `tbl_riderregistration` */

DROP TABLE IF EXISTS `tbl_riderregistration`;

CREATE TABLE `tbl_riderregistration` (
  `rider_id` int(10) NOT NULL AUTO_INCREMENT,
  `ride_fname` varchar(50) DEFAULT NULL,
  `rider_lname` varchar(50) DEFAULT NULL,
  `rider_gender` varchar(50) DEFAULT NULL,
  `rider_phnumber` varchar(50) DEFAULT NULL,
  `rider_email` varchar(50) DEFAULT NULL,
  `loginid` int(10) DEFAULT NULL,
  PRIMARY KEY (`rider_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_riderregistration` */

insert  into `tbl_riderregistration`(`rider_id`,`ride_fname`,`rider_lname`,`rider_gender`,`rider_phnumber`,`rider_email`,`loginid`) values (1,'Linu','S','Male','8689767676','li@gmail.com',3);

/*Table structure for table `tbl_vehicle` */

DROP TABLE IF EXISTS `tbl_vehicle`;

CREATE TABLE `tbl_vehicle` (
  `vehicle_id` int(10) NOT NULL AUTO_INCREMENT,
  `driver_id` int(10) DEFAULT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  `no_of_seats` varchar(50) DEFAULT NULL,
  `vehicle_no` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `availability` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tbl_vehicle` */

insert  into `tbl_vehicle`(`vehicle_id`,`driver_id`,`vehicle_type`,`no_of_seats`,`vehicle_no`,`amount`,`availability`) values (1,1,'Taxi','5','KL 17 3323','20','0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
