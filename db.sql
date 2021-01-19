CREATE DATABASE stajtakipsistemi;
USE stajtakipsistemi;

create table sirket (
	id  int(3) NOT NULL AUTO_INCREMENT,
	ad varchar(30) NOT NULL,
	adres varchar(100) NOT NULL,
	PRIMARY KEY (id)
);

create table fakulte (
	id  int(3) NOT NULL AUTO_INCREMENT,
	ad varchar(30) NOT NULL,
	adres varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	PRIMARY KEY (id)
);

create table bolum (
	id  int(3) NOT NULL AUTO_INCREMENT,
	ad varchar(30) NOT NULL,
	email varchar(100) NOT NULL,
	fakulte int NOT NULL,
	PRIMARY KEY (id),
    FOREIGN KEY (fakulte) REFERENCES fakulte(id)
);

create table danisman (
	id  int(3) NOT NULL AUTO_INCREMENT,
	ad varchar(30) NOT NULL,
	soyad varchar(30) NOT NULL,
	sifre int NOT NULL,
	email varchar(100) NOT NULL,
	bolum int NOT NULL,
	PRIMARY KEY (id),
    FOREIGN KEY (bolum) REFERENCES bolum(id)
);

create table ogrenci (
	id  int(3) NOT NULL AUTO_INCREMENT,
	ad varchar(30) NOT NULL,
	soyad varchar(30) NOT NULL,
	sifre int NOT NULL,
	email varchar(100) NOT NULL,
	danisman int NOT NULL,
	fakulte int NOT NULL,
	bolum int NOT NULL,
	PRIMARY KEY (id),
    FOREIGN KEY (danisman) REFERENCES danisman(id),
    FOREIGN KEY (fakulte) REFERENCES fakulte(id),
    FOREIGN KEY (bolum) REFERENCES bolum(id)
);

create table stajbasvuru (
	id  int(3) NOT NULL AUTO_INCREMENT,
	danisman int NOT NULL,
	ogrenci int NOT NULL,
	sirket int NOT NULL,
	baslangic_tarihi date NOT NULL,
	bitis_tarihi date NOT NULL,
	kabul_durumu int DEFAULT 0,
    kabul_gun_sayisi int DEFAULT 0,
	PRIMARY KEY (id),
    FOREIGN KEY (danisman) REFERENCES danisman(id),
    FOREIGN KEY (ogrenci) REFERENCES ogrenci(id),
    FOREIGN KEY (sirket) REFERENCES sirket(id)
);

create table stajdefteri (
	id  int(3) NOT NULL AUTO_INCREMENT,
	stajbasvuru int NOT NULL,
	defterPath varchar(255) NOT NULL,
	kabul_durumu int DEFAULT 0,
	kabul_gun_sayisi int DEFAULT 0,
	PRIMARY KEY (id),
    FOREIGN KEY (stajbasvuru) REFERENCES stajbasvuru(id)
);