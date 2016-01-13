use szkola004;

--1

insert into jezyk value 
(1, 'angielski', 'EN'),
(2, 'niemiecki', 'DE'),
(3, 'włoski', 'IT'),
(4, 'hiszpański', 'ES');

--2

insert into program value 
(1, 'brak', 'audiolingwalna'),
(2, 'brak', 'komunikatywna'),
(3, 'brak', 'gramatyczno-tłumaczeniowa');

--3

insert into lektor value 
(1, 'Mateusz Wlazły', 'Warszawa', '999222333', 'wlazly@poczta.com', 'brak', 'brak'),
(2, 'Gertruda Wolfenstein', 'Warszawa', '222333444', 'gw@post.de', 'brak', 'brak'),
(3, 'Joe Matoe', 'Warszawa', '123456789', 'matoe@pexample.com', 'brak', 'brak'),
(4, 'Benuat Beniowski', 'Warszawa', '999888777', 'benny@poczta.com', 'brak', 'brak'),
(5, 'Katarzyna Wielka', 'Warszawa', '111222333', 'kathegreat@poczta.com', 'brak', 'brak'),
(6, 'Ryszard Czamberlejn', 'Warszawa', '222333444', 'czember@pexample.com', 'brak', 'brak'),
(7, 'Aneta Kucharska', 'Poznań', '555333444', 'kuchnia@poczta.com', 'brak', 'brak'),
(8, 'Mścisław Niedźwiedź', 'Poznań', '666333444', 'bear@example.com', 'brak', 'brak'),
(9, 'Krzysztof Toporski', 'Poznań', '333444888', 'toporek@mail.com', 'brak', 'brak'),
(10, 'Grzegorz Papieski', 'Lublin', '111999888', 'greg@example.com', 'brak', 'brak'),
(11, 'Marta Gronkowska', 'Lublin', '999888555', 'groniec@example.com', 'brak', 'brak'),
(12, 'Ryszard Martyński', 'Lublin', '222333000', 'rysiek@poczta.com', 'brak', 'brak'),
(13, 'Kazimierz Wielki', 'Kraków', '444999222', 'kazek@example.com', 'brak', 'brak'),
(14, 'Jadwiga Węgierska', 'Kraków', '111444999', 'jadwiga@example.com', 'brak', 'brak'),
(15, 'Sandra von Gotha', 'Lubawa', '444999000', 'sandravongotha@example.de', 'brak', 'brak'),
(16, 'Jakub Popławski', 'Lubawa', '999000111', 'polawski@example.com', 'brak', 'brak');

--4

insert into firma value 
(1, '111-222-33-44', 'ABC Spedycja', 'ABC', 'Warszawa', 'Marta Krystyniak', '999000333', '999000443', 'mkrystyniak@abcspe.pl', 'Warszawa, ul. Dostarczycieli 2'),
(2, '111-333-44-55', 'CDA Informatyka', 'CDA', 'Warszawa', 'Krzysztof Cyfrowy', '999222333', '999012333', 'biuro@cbainf.pl', '00-567 Warszawa, ul. Informatyków 12'),
(3, '111-444-55-66', 'MirBud', 'MBU', 'Warszawa', 'Mirosław Budowlaniec', '967822333', '992342333', 'biuro@mirbud.pl', '00-222 Warszawa, ul. Budowlana 15'),
(4, '111-555-77-88', 'Alk-Ko', 'ALK', 'Kraków', 'Zygmunt Pijany', '949225673', '94322333', 'pijany@alko.pl', '55-959 Kraków, ul. Pijarów 5'),
(5, '111-666-22-33', 'Spożywcza Dolina', 'SPD', 'Lublin', 'Teresa Owocowa', '149222333', '349222333', 'towocowa@spozdol.pl', '11-789 Lublin, ul. Spożywcza 44'),
(6, '111-777-33-44', 'Szwedzkie Drewno', 'SWE', 'Lubawa', 'Marian Drwal', '993234333', '994334333', 'biuro@swdrewno.se', '22-959 Lubawa, ul. Drewniana 3');

--5

insert into jezyk_lektora value 
(1, 1, 0),
(1, 3, 0),
(2, 2, 1),
(2, 1, 0),
(3, 1, 1),
(4, 1, 0),
(5, 1, 0),
(5, 4, 0),
(6, 1, 0),
(7, 1, 0),
(7, 4, 0),
(8, 1, 0),
(8, 4, 0),
(9, 1, 0),
(9, 2, 0),
(10, 1, 0),
(11, 2, 0),
(12, 1, 0),
(13, 2, 0),
(14, 1, 0),
(15, 2, 1),
(16, 1, 0);

--6

insert into podrecznik value 
(1, 'New Plus', 'A0', 1),
(2, 'Alles klar Neu', 'A0', 2),
(3, 'Outcomes', 'A1', 1),
(4, 'Nuovo Progetto italiano', 'A1', 3),
(5, 'World Around', 'A2', 1),
(6, 'Nuevo Espanol en marcha', 'A2', 4),
(7, 'The Study of Language', 'B1', 1),
(8, 'Themen aktuell', 'B1', 2),
(9, 'English for Cabin Crew', 'B1', 1),
(10, 'VA BENE!', 'B2', 3),
(11, 'Tree or Three?', 'B2', 1),
(12, '¡Vamos a actuar!', 'C1', 4),
(13, 'Zoom in', 'C1', 1),
(14, 'Alles klar Neu', 'C2', 2),
(15, 'Talking Culture', 'C2', 1),
(16, 'Perfettamente!', 'C3', 3);

--7

insert into wypozyczenie value 
(1, 1, now()),
(2, 4, now()),
(3, 2, now()),
(4, 3, now()),
(5, 5, now()),
(6, 9, now()),
(7, 7, now()),
(8, 15, now()),
(9, 1, now()),
(10, 5, now());

--8

insert into kurs value 
(1, 'ABC-EN-01', 'Kurs dla początkującej grupy', '2014/2015', 'letni', 'sala konferencyjna', 1, 1, 1, 1),
(2, 'CDA-EN-01', 'Kurs dla grupy zaawansowanej', '2014/2015', 'letni', 'sala nr 2', 2, 3, 1, 1),
(3, 'MBU-EN-01', 'Trudni słuchacze', '2015/2016', 'zimowy', 'sala nr 5', 3, 6, 1, 1),
(4, 'MBU-ES-01', 'Kurs dla początkującej grupy', '2015/2016', 'zimowy', 'sala nr 12', 3, 5, 4, 1),
(5, 'ABC-EN-01', 'Kurs indywidualny', '2015/2016', 'zimowy', 'sala nr 14', 1, 6, 1, 1),
(6, 'ABC-EN-02', 'Kurs indywidualny', '2015/2016', 'zimowy', 'sala nr 5', 1, 8 , 1, 1),
(7, 'ABC-EN-03', 'Kurs indywidualny', '2015/2016', 'zimowy', 'sala nr 8', 1, 6, 1, 1),
(8, 'ALK-EN-01', 'Kurs dla początkującej grupy', '2015/2016', 'zimowy', 'salka przy stołówce', 4, 14, 1, 1),
(9, 'ALK-EN-02', 'Potrzebny specjalistyczny język', '2015/2016', 'zimowy', 'sala konferencyjna', 4, 14, 1, 1),
(10, 'ALK-EN-03', 'Kurs dla początkującej grupy', '2015/2016', 'zimowy', 'sala nr 12, I piętro', 4, 14, 1, 1),
(11, 'SPD-EN-01', 'Kurs dla początkującej grupy', '2015/2016', 'zimowy', 'sala nr 2', 5, 10, 1, 1),
(12, 'SPD-EN-02', 'Kurs dla początkującej grupy', '2015/2016', 'zimowy', 'sala przy recepcji', 5, 12, 1, 1),
(13, 'SPD-EN-03', 'Wymagany język branżowy', '2015/2016', 'zimowy', 'sala konferencyjna', 5, 12, 1, 1),
(14, 'SWE-EN-01', 'Kurs dla początkującej grupy', '2015/2016', 'zimowy', 'sala nr 8, na lewo od recepcji', 6, 16, 1, 1),
(15, 'SWE-EN-02', 'Kurs dla początkującej grupy', '2015/2016', 'zimowy', 'sala nr 15', 6, 16, 1, 1),
(16, 'SWE-DE-01', 'Trudni słuchacze', '2015/2016', 'zimowy', 'sala nr 8', 6, 15, 2, 1),
(17, 'SWE-DE-02', 'Kursanci bardzo wymagający', '2015/2016', 'zimowy', 'sala konferencyjna', 6, 15, 2, 1),
(18, 'CDA-EN-02', 'Kurs dla grupy zaawansowanej', '2015/2016', 'zimowy', 'sala II piętro', 1, 1, 1, 1);

--9

insert into wyplata value 
(1, now(), 1200.0, "zajęcia październik", 1),
(2, now(), 400.0, "zajęcia październik", 2),
(3, now(), 1750.0, "zajęcia październik", 3),
(4, now(), 1120.5, "zajęcia październik", 4),
(5, now(), 955.55, "zajęcia październik", 5),
(6, now(), 400.0, "zajęcia październik", 6);

--10

insert into rachunek value 
(1, 'ttp/34', '2015-11-06', 1200.0, "zajęcia październik", 1),
(2, '10', '2015-11-05', 600.0, "zajęcia październik", 2),
(3, 'rew15', '2015-11-11', 1750.0, "zajęcia październik", 3),
(4, '554/87', '2015-11-02', 1120.5, "zajęcia październik", 4),
(5, '12-mat-11', '2015-11-08', 955.55, "zajęcia październik", 5),
(6, '003', '2015-11-11', 400.0, "zajęcia październik", 6),
(7, 'list-01', '2015-12-01', 1200.0, "zajęcia listopad", 1),
(8, '15-11-2', '2015-12-01', 600.0, "zajęcia listopad", 2),
(9, '43-rr-1', '2015-12-01', 1750.0, "zajęcia listopad", 3),
(10, 'dew100-1', '2015-12-01', 1120.5, "zajęcia listopad", 4),
(11, 'kr/12', '2015-12-01', 955.55, "zajęcia listopad", 5),
(12, '10', '2015-12-01', 740.0, "zajęcia listopad", 6),
(13, 'corr/1', now(), 200.0, "zajęcia zaległe(wrzesień)", 1),
(14, 'wpl-cor-2', now(), 400.0, "zajęcia zaległe(wrzesień)", 6);

--11

insert into stawka_lektora value 
(1, 1, 30.0),
(2, 1, 30.0),
(3, 6, 30.0),
(4, 5, 30.0),
(5, 6, 30.0),
(6, 8, 30.0),
(7, 6, 30.0),
(8, 14, 30.0),
(9, 14, 35.0),
(10, 14, 35.0),
(11, 10, 30.0),
(12, 12, 30.0),
(13, 12, 40.0),
(14, 16, 30.0),
(15, 16, 30.0),
(16, 15, 60.0),
(17, 15, 60.0),
(18, 1, 35.0);

--12

insert into faktura value 
(1, 'TiP-2015-10-1', '2015-11-01', 2200.0, "zajęcia październik", 1),
(2, 'TiP-2015-10-2', '2015-11-01', 900.0, "zajęcia październik", 2),
(3, 'TiP-2015-10-3', '2015-11-01', 2750.0, "zajęcia październik", 3),
(4, 'TiP-2015-10-4', '2015-11-01', 2120.5, "zajęcia październik", 4),
(5, 'TiP-2015-10-5', '2015-11-01', 1955.55, "zajęcia październik", 5),
(6, 'TiP-2015-10-6', '2015-11-01', 1400.0, "zajęcia październik", 6),
(7, 'TiP-2015-10-7', '2015-12-01', 2200.0, "zajęcia listopad", 1),
(8, 'TiP-2015-10-8', '2015-12-01', 1600.0, "zajęcia listopad", 2),
(9, 'TiP-2015-10-9', '2015-12-01', 2750.0, "zajęcia listopad", 3),
(10, 'TiP-2015-10-10', '2015-12-01', 2120.5, "zajęcia listopad", 4),
(11, 'TiP-2015-10-11', '2015-12-01', 1955.55, "zajęcia listopad", 5),
(12, 'TiP-2015-10-12', '2015-12-01', 1740.0, "zajęcia listopad", 6);

--13

insert into wplata value 
(1, now(), 2200.0, "zajęcia październik", 1),
(2, now(), 900.0, "zajęcia październik", 2),
(3, now(), 2750.0, "zajęcia październik", 3),
(4, now(), 2120.5, "zajęcia październik", 4),
(5, now(), 1955.55, "zajęcia październik", 5),
(6, now(), 1400.0, "zajęcia październik", 6);

--14

insert into stawka_firmy value 
(1, 60, 0),
(1, 80, 1),
(2, 60, 0),
(2, 80, 1),
(3, 50, 0),
(3, 80, 1),
(4, 60, 0),
(4, 80, 1),
(5, 60, 0),
(5, 80, 1),
(6, 55, 0),
(6, 80, 1);

--15

insert into kursant value 
(1, 'Walentyna Motriaszkowa', '145678333', 'wmotr@example.com', 1),
(2, 'Amelia Paryska', '234522333', 'amelipari@przyklad.com', 1),
(3, 'Klaudia Armenska', '654322333', 'klaudiaarme@example.com', 1),
(4, 'Wincenty Wangogski', '212222343', 'wango@example.com', 2),
(5, 'Klaudiusz Niemy', '2312872333', 'niemiec@example.com', 2),
(6, 'Wiktor Wspaniały', '111222333', 'wspanialek@przyklad.com', 2),
(7, 'Mateusz Wajchowy', '841222333', 'wajcho@example.com', 3),
(8, 'Dorota Makota', '111222323', 'dmakota@example.com', 3),
(9, 'Agnieszka Tumieszka', '921222333', 'atumieszka@example.com', 3),
(10, 'Magdalena Belcen', '891222333', 'mbelcen@example.com', 4),
(11, 'Tomasz Niechciecki', '111222333', 'tniech@example.com', 4),
(12, 'Zdzisław Tubylski', '111227773', 'ztubylski@example.com', 4),
(13, 'Antoni Tupecki', '671222333', 'atupecki@przyklad.com', 5),
(14, 'Mariusz Abecki', '111222333', 'm.abecki@example.com', 5),
(15, 'Krzystof Baran', '211222333', 'ka_baran@internet.com', 5),
(16, 'Mateusz Ameryski', '111255533', 'ma_amer@example.com', 6),
(17, 'Krystyna Matejska', '321222333', 'kris@matejska.com', 6),
(18, 'Paulina Tustecka', '111224323', 'ptustecka@example.com', 6),
(19, 'Magdalena Potocka', '921222333', 'mpotocka@example.com', 3),
(20, 'Karol Radziwiłł', '891222333', 'kradziwill@example.com', 4),
(21, 'Tomasz Sapieha', '111222333', 'tsapieha@example.com', 4),
(22, 'Zdzisława Wiśniewska', '111227773', 'zwisniewska@example.com', 4),
(23, 'Henryka Jabłońska', '671222333', 'hjablonska@przyklad.com', 5),
(24, 'Mariusz Rokicki', '111222333', 'rokitnik@example.com', 5),
(25, 'Władysław Kozłowski', '211222333', 'wlakoz@internet.com', 5),
(26, 'Tadeusz Wyspiański', '111255533', 'tawyspa@example.com', 6),
(27, 'Joanna Mierzwińska', '321222333', 'jo-mierz@matejska.com', 6),
(28, 'Paulina de Volaille', '111224323', 'paulinedevolaille@example.com', 6);

--16

insert into ankieta value 
(1, now(), 	1,1,1,1,1, 1,1,1,1,1,1,
		1,0,1,1,1, 1,1,1,1,1,1, 'brak uwag', 1, 1),
(2, now(), 	1,1,0,1,1, 1,1,1,1,1,1,
		1,1,1,0,1, 1,1,1,1,1,1, 'brak uwag', 1, 2),
(3, now(), 	1,1,1,1,0, 1,1,1,1,1,1,
		1,1,1,1,1, 1,1,1,1,1,1, 'brak uwag', 1, 3),		
(4, now(), 	1,1,1,1,1, 1,0,1,1,1,1,
		1,1,1,1,1, 1,1,0,1,1,1, 'brak uwag', 3, 4),		
(5, now(), 	1,1,1,1,1, 1,1,1,0,1,1,
		1,1,1,1,1, 1,1,1,1,0,1, 'brak uwag', 3, 5),		
(6, now(), 	1,1,1,1,1, 1,1,1,1,1,0,
		1,1,1,1,1, 1,1,1,1,1,1, 'brak uwag', 3, 6);

--17

insert into kurs_kursanta value 

(1, 1, 'brak'),
(1, 2, 'brak'),
(1, 3, 'brak'),
(2, 4, 'brak'),
(2, 5, 'brak'),
(2, 6, 'brak'),
(3, 7, 'brak'),
(3, 9, 'brak'),
(4, 8, 'brak'),
(4, 19, 'brak'),
(5, 1, 'brak'),
(6, 2, 'brak'),
(7, 3, 'brak'),
(8, 10, 'brak'),
(9, 11, 'brak'),
(10, 12, 'brak'),
(11, 13, 'brak'),
(12, 14, 'brak'),
(13, 15, 'brak'),
(14, 16, 'brak'),
(15, 17, 'brak'),
(16, 18, 'brak'),
(17, 26, 'brak'),
(18, 4, 'brak'),
(18, 5, 'brak'),
(18, 6, 'brak');

--18

insert into test value 

(1, 'semestralny', 70, 1, 1),
(2, 'semestralny', 70, 1, 2),
(3, 'semestralny', 80, 1, 3),
(4, 'semestralny', 50, 2, 4),
(5, 'semestralny', 90, 2, 5),
(6, 'semestralny', 90, 2, 6);

--19

insert into termin value 
(1, 'wtorek', '14:00', '15:00', 3),
(2, 'czwartek', '14:00', '15:00', 3),
(3, 'poniedziałek', '17:00', '18:00', 4),
(4, 'środa', '17:00', '18:00', 4),
(5, 'środa', '14:00', '15:00', 5),
(6, 'piątek', '14:00', '15:00', 5),
(7, 'wtorek', '14:00', '15:00', 6),
(8, 'czwartek', '14:00', '15:00', 6),
(9, 'wtorek', '18:00', '19:00', 7),
(10, 'czwartek', '18:00', '19:00', 7),
(11, 'wtorek', '06:00', '07:00', 8),
(12, 'czwartek', '06:00', '07:00', 8);

--20

insert into lekcja value 
(1, '2015-10-01', 0, 3),
(2, '2015-10-06', 0, 3),
(3, '2015-10-08', 0, 3),
(4, '2015-10-13', 0, 3),
(5, '2015-10-15', 1, 3),
(6, '2015-10-20', 1, 3),
(7, '2015-10-22', 0, 3),
(8, '2015-10-27', 0, 3),
(9, '2015-10-29', 0, 3),

(10, '2015-10-05', 0, 4),
(11, '2015-10-07', 0, 4),
(12, '2015-10-12', 0, 4),
(13, '2015-10-14', 0, 4),
(14, '2015-10-19', 1, 4),
(15, '2015-10-21', 0, 4),
(16, '2015-10-26', 0, 4),
(17, '2015-10-28', 0, 4),
(18, '2015-10-30', 0, 4),

(19, '2015-10-02', 0, 5),
(20, '2015-10-07', 0, 5),
(21, '2015-10-09', 0, 5),
(22, '2015-10-14', 0, 5),
(23, '2015-10-16', 1, 5),
(24, '2015-10-21', 1, 5),
(25, '2015-10-23', 0, 5),
(26, '2015-10-28', 0, 5),
(27, '2015-10-30', 0, 5),

(28, '2015-11-03', 0, 5),
(29, '2015-11-05', 0, 5),
(30, '2015-11-10', 1, 5),
(31, '2015-11-12', 0, 5),
(32, '2015-11-15', 0, 5),
(33, '2015-11-17', 0, 5),
(34, '2015-11-19', 0, 5),
(35, '2015-11-24', 0, 5),
(36, '2015-11-26', 0, 5),

(37, '2015-10-01', 0, 6),
(38, '2015-10-06', 0, 6),
(39, '2015-10-08', 0, 6),
(40, '2015-10-13', 0, 6),
(41, '2015-10-15', 1, 6),
(42, '2015-10-20', 1, 6),
(43, '2015-10-22', 0, 6),
(44, '2015-10-27', 0, 6),
(45, '2015-10-29', 0, 6),

(46, '2015-10-01', 0, 7),
(47, '2015-10-06', 0, 7),
(48, '2015-10-08', 0, 7),
(49, '2015-10-13', 0, 7),
(50, '2015-10-15', 0, 7),
(51, '2015-10-20', 1, 7),
(52, '2015-10-22', 0, 7),
(53, '2015-10-27', 0, 7),
(54, '2015-10-29', 0, 7),

(55, '2015-10-01', 0, 8),
(56, '2015-10-06', 0, 8),
(57, '2015-10-08', 0, 8),
(58, '2015-10-13', 0, 8),
(59, '2015-10-15', 0, 8),
(60, '2015-10-20', 1, 8),
(61, '2015-10-22', 0, 8),
(62, '2015-10-27', 0, 8),
(63, '2015-10-29', 0, 8),

(64, '2015-10-01', 0, 9),
(65, '2015-10-06', 0, 9),
(66, '2015-10-08', 0, 9),
(67, '2015-10-13', 0, 9),
(68, '2015-10-15', 0, 9),
(69, '2015-10-20', 1, 9),
(70, '2015-10-22', 0, 9),
(71, '2015-10-27', 0, 9),
(72, '2015-10-29', 0, 9),

(73, '2015-10-02', 0, 10),
(74, '2015-10-06', 0, 10),
(75, '2015-10-09', 0, 10),
(76, '2015-10-13', 0, 10),
(77, '2015-10-16', 0, 10),
(78, '2015-10-20', 1, 10),
(79, '2015-10-23', 0, 10),
(80, '2015-10-27', 0, 10),
(81, '2015-10-30', 0, 10),

(82, '2015-10-01', 0, 11),
(83, '2015-10-06', 0, 11),
(84, '2015-10-08', 0, 11),
(85, '2015-10-13', 0, 11),
(86, '2015-10-15', 0, 11),
(87, '2015-10-20', 0, 11),
(88, '2015-10-22', 0, 11),
(89, '2015-10-27', 0, 11),
(90, '2015-10-29', 0, 11),

(91, '2015-10-01', 0, 12),
(92, '2015-10-06', 0, 12),
(93, '2015-10-08', 0, 12),
(94, '2015-10-13', 0, 12),
(95, '2015-10-15', 0, 12),
(96, '2015-10-20', 1, 12),
(97, '2015-10-22', 0, 12),
(98, '2015-10-27', 0, 12),
(99, '2015-10-29', 0, 12),

(100, '2015-10-01', 0, 13),
(101, '2015-10-06', 0, 13),
(102, '2015-10-08', 0, 13),
(103, '2015-10-13', 0, 13),
(104, '2015-10-15', 0, 13),
(105, '2015-10-20', 1, 13),
(106, '2015-10-22', 0, 13),
(107, '2015-10-27', 0, 13),
(108, '2015-10-29', 0, 13),

(109, '2015-10-01', 0, 14),
(110, '2015-10-06', 0, 14),
(111, '2015-10-08', 0, 14),
(112, '2015-10-13', 0, 14),
(113, '2015-10-15', 0, 14),
(114, '2015-10-20', 0, 14),
(115, '2015-10-22', 0, 14),
(116, '2015-10-27', 0, 14),
(117, '2015-10-29', 0, 14),

(118, '2015-10-01', 0, 15),
(119, '2015-10-06', 0, 15),
(120, '2015-10-08', 0, 15),
(121, '2015-10-13', 0, 15),
(122, '2015-10-15', 0, 15),
(123, '2015-10-20', 0, 15),
(124, '2015-10-22', 0, 15),
(125, '2015-10-27', 0, 15),
(126, '2015-10-29', 0, 15),

(127, '2015-10-01', 0, 16),
(128, '2015-10-06', 0, 16),
(129, '2015-10-08', 0, 16),
(130, '2015-10-13', 0, 16),
(131, '2015-10-15', 0, 16),
(132, '2015-10-20', 0, 16),
(133, '2015-10-22', 0, 16),
(134, '2015-10-27', 0, 16),
(135, '2015-10-29', 0, 16),

(136, '2015-10-01', 0, 17),
(137, '2015-10-06', 1, 17),
(138, '2015-10-08', 0, 17),
(139, '2015-10-13', 0, 17),
(140, '2015-10-15', 0, 17),
(141, '2015-10-20', 1, 17),
(142, '2015-10-22', 0, 17),
(143, '2015-10-27', 0, 17),
(144, '2015-10-29', 0, 17),

(145, '2015-10-01', 0, 18),
(146, '2015-10-06', 0, 18),
(147, '2015-10-08', 0, 18),
(148, '2015-10-13', 0, 18),
(149, '2015-10-15', 0, 18),
(150, '2015-10-20', 0, 18),
(151, '2015-10-22', 0, 18),
(152, '2015-10-27', 0, 18),
(153, '2015-10-29', 0, 18);

--21

insert into obecnosc value 
(1, 7, 0),
(2, 7, 1),
(3, 7, 1),
(4, 7, 1),
(5, 7, 1),
(6, 7, 1),
(7, 7, 1),
(8, 7, 1),
(9, 7, 1),

(1, 9, 1),
(2, 9, 1),
(3, 9, 1),
(4, 9, 1),
(5, 9, 1),
(6, 9, 1),
(7, 9, 1),
(8, 9, 1),
(9, 9, 0),

(10, 8, 1),
(11, 8, 1),
(12, 8, 1),
(13, 8, 1),
(14, 8, 1),
(15, 8, 1),
(16, 8, 1),
(17, 8, 1),
(18, 8, 1),

(10, 19, 1),
(11, 19, 1),
(12, 19, 1),
(13, 19, 1),
(14, 19, 1),
(15, 19, 1),
(16, 19, 1),
(17, 19, 1),
(18, 19, 1),

(19, 1, 1),
(20, 1, 1),
(21, 1, 1),
(22, 1, 1),
(23, 1, 1),
(24, 1, 1),
(25, 1, 1),
(26, 1, 1),
(27, 1, 1),

(28, 1, 0),
(29, 1, 1),
(30, 1, 1),
(31, 1, 1),
(32, 1, 1),
(33, 1, 1),
(34, 1, 1),
(35, 1, 1),
(36, 1, 1),

(37, 2, 1),
(38, 2, 1),
(39, 2, 1),
(40, 2, 1),
(41, 2, 1),
(42, 2, 1),
(43, 2, 1),
(44, 2, 1),
(45, 2, 0);

--22

insert into jezyk_kursanta value 

(1, 1, "B1"),
(2, 1, "B1"),
(3, 1, "A2"),
(4, 1, "C1"),
(5, 1, "C1"),
(6, 1, "C1"),
(7, 1, "B1"),
(8, 1, "C1"),
(9, 1, "B1"),
(9, 4, "B1"),
(10, 1, "B1"),
(10, 4, "B1"),
(11, 1, "B1"),
(12, 1, "B1"),
(13, 1, "A1"),
(14, 1, "B1"),
(15, 1, "B1"),
(16, 1, "A1"),
(17, 1, "A2"),
(18, 1, "B1"),
(18, 2, "B2"),
(19, 1, "A2"),
(20, 1, "B1"),
(21, 1, "B1"),
(22, 1, "B1"),
(23, 1, "B1"),
(24, 1, "B1"),
(25, 1, "B1"),
(26, 1, "B1"),
(26, 2, "B2"),
(27, 1, "B1"),
(28, 1, "B1");


