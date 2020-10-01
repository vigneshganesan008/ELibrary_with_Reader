													Report															

Table Descriptions:

SQL> desc reader;
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 NAME                                      NOT NULL VARCHAR2(100)
 PHONE                                     NOT NULL NUMBER
 GENDER                                    NOT NULL VARCHAR2(1)
 USERNAME                                  NOT NULL VARCHAR2(50)
 PASSWORD                                  NOT NULL VARCHAR2(100)
 DOB                                       NOT NULL DATE
 AGE                                                NUMBER
 EMAIL                                     NOT NULL VARCHAR2(100)

SQL> desc books;
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 NAME                                      NOT NULL VARCHAR2(100)
 AUTHOR                                    NOT NULL VARCHAR2(100)
 ISBN                                      NOT NULL NUMBER
 EDITION                                            VARCHAR2(10)
 PUBLISHER                                          VARCHAR2(50)
 GENRE                                              VARCHAR2(50)
 AGE                                       NOT NULL NUMBER

SQL> desc elibrary;
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 BOOK_ISBN                                 NOT NULL NUMBER
 BOOK_PATH                                          CLOB
 BOOK_COVER                                         BLOB

SQL> desc log;
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 USERNAME                                  NOT NULL VARCHAR2(50)
 BOOK_ISBN                                 NOT NULL NUMBER
 SESSION_BEGIN                             NOT NULL TIMESTAMP(6)
 SESSION_END                               NOT NULL TIMESTAMP(6)
 DURATION                                           NUMBER


Queries:

1)Display books read by a particular user:-
	Eg.:
		select B.name,L.book_isbn 
		from Books B,Log L 
		where B.isbn = L.book_isbn and username = 'vicky';
	
	Output:	
	NAME                                                                                              BOOK_ISBN
---------------------------------------------------------------------------------------------------- ----------
Aladdin and the Magic Lamp                                                                           1234567890
Alice in Wonderland, Retold in Words of One Syllable                                                  451527747
Gullivers Travels                                                                                    141439491                                               451527747
		
		
2)Display books within a particular time frame:-
	Eg.:
		select B.name,L.book_isbn 
		from Books B,log L 
		where session_begin >= to_timestamp('14-04-2020 17:20:00', 'dd-mm-yyyy hh24:mi:ss')
		  and session_end <= to_timestamp('14-04-2020 17:22:00', 'dd-mm-yyyy hh24:mi:ss')
		  and B.isbn = L.book_isbn;
	
	Output:		
		NAME                                                                                         BOOK_ISBN
---------------------------------------------------------------------------------------------------- ----------
Aladdin and the Magic Lamp                                                                           1234567890
		
3)Display Books that has been read for a particular period of time:-
	Eg.:
		select book_isbn,SUM(duration) 
		from log 
		GROUP BY book_isbn 
		HAVING SUM(DURATION)>2
		and SUM(Duration)<4; 
	
	Output:
	BOOK_ISBN SUM(DURATION)
	---------- -------------
	1234567894          3

4)Display Books that has been read by a particular reader for a particular period of time:-
	Eg.:
		select book_isbn,SUM(duration) 
		from log 
		where username='prak'
		GROUP BY book_isbn 
		HAVING SUM(DURATION)>2;
	
	Output:	
	 BOOK_ISBN SUM(DURATION)
	---------- -------------
	1234567894             3
	
5)Display number of time a book has been read:-
	Eg.:
		select book_isbn,COUNT(book_isbn) 
		from log 
		GROUP BY book_isbn;
	
	Output:	
	 BOOK_ISBN COUNT(BOOK_ISBN)
	---------- ----------------
	451527747                1
	1234567890               1
	1234567891               1
	141439491                1
	1234567894               1

6)Display all books read:-
	Eg.:
		select DISTINCT book_isbn 
		from log;
	
	Output:	
		 BOOK_ISBN
		----------
		451527747
		1234567890
		1234567891
		141439491
		1234567894

7)Display total no. of hours books have been read:-
	Eg.:
		select SUM(duration) 
		from log;
	Output:	
	SUM(DURATION)
	-------------
	10.7833333

8)Display log in descending order of duration :-
	Eg.:
		select * from log 
		order by duration DESC;
	Output:	

USERNAME                                            BOOK_ISBN SESSION_BEGIN                                 SESSION_END
                    DURATION
------------------------------- ---------- ------------------------------------------- -------------------------------
vicky                                               141439491 14-APR-20 05.34.02.948000 PM                14-APR-20 05.38.08.276000 PM
                       4.1
prak                                               1234567894 14-APR-20 05.26.52.121000 PM                14-APR-20 05.29.41.375000 PM
                      3
vicky                                               451527747 14-APR-20 05.22.45.466000 PM                14-APR-20 05.24.59.816000 PM
                      2.23333333
prak                                               1234567891 14-APR-20 05.25.18.096000 PM                14-APR-20 05.26.45.950000 PM
                       1
vicky                                              1234567890 14-APR-20 05.20.57.785000 PM                14-APR-20 05.21.14.869000 PM
                     .2833333
prak                                               1234567894 14-APR-20 05.52.07.549000 PM                14-APR-20 05.52.17.970000 PM
                      .166666667

9)Display no. of times a Reader has read books:-
	Eg.:
		select username,count(username) 
		from log
		group by username;
	
	Output:	
USERNAME                                           COUNT(USERNAME)
-------------------------------------------------- ---------------
vicky                                                            3
prak                                                             3

10)Display file of books:-
	Eg.:
		select B.name,E.book_path from books B,elibrary E where E.book_isbn = B.isbn;
		
	Output:	
	NAME                                                                                                 BOOK_PATH
---------------------------------------------------------------------------------------------------- --------------------------------------------------------------------------------
Aladdin and the Magic Lamp                                                                           C:\Users\Vicky\Documents\Sample PDFs\Aladdin-and-the-Magic-Lamp.pdf
Alice in Wonderland, Retold in Words of One Syllable                                                 C:\Users\Vicky\Documents\Sample PDFs\Alice-in-Wonderland.pdf
All For Love                                                                                         C:\Users\Vicky\Documents\Sample PDFs\All-For-Love.pdf
Around the World in Eighty Days                                                                      C:\Users\Vicky\Documents\Sample PDFs\Around-the-World-in-80-Days.pdf
Gullivers Travels                                                                                    C:\Users\Vicky\Documents\Sample PDFs\Gullivers-Travels.pdf
Pirates: A comedy in one act                                                                         C:\Users\Vicky\Documents\Sample PDFs\Pirates.pdf
The Adventures of Sherlock Holmes                                                                    C:\Users\Vicky\Documents\Sample PDFs\The-Adventures-of-Sherlock-Holmes.pdf
The Art of War                                                                                       C:\Users\Vicky\Documents\Sample PDFs\The-Art-of-War.pdf
The Invisible Man: A Grotesque Romance                                                               C:\Users\Vicky\Documents\Sample PDFs\The-Invisible-Man.pdf
file://H:\Apps\devel\emu8086\documentation\8086_bios_and_dos_in                                      C:\Users\Vicky\Documents\Sample PDFs\8086_and_DOS_interrupts.pdf
8086_bios_and_dos_in                                                                                 C:\Users\Vicky\Documents\Sample PDFs\8086_and_DOS_interrupts.pdf


View:-
	create or replace view RBL
	as
		select R.name as Reader_Name,B.name as Book_Name,B.isbn as ISBN
		from Reader R,Books B,Log L
		where R.username=L.username 
			and B.isbn=L.book_isbn
			order by R.name;

	select * from rbl;
	Output:	
	READER_NAME  			ISBN								   BOOK_NAME
---------------------------------------------------------------------------------------------------- -----------------
Prakash                                                          All For Love
                          1234567891
Prakash                                                          8086_bios_and_dos_in
                          1234567894
Prakash                                                          8086_bios_and_dos_in
                          1234567894
Vicky                                                            Gullivers Travels
                           141439491
Vicky                                                            Alice in Wonderland, Retold in Words of One Syllable
                           451527747
Vicky                                                            Aladdin and the Magic Lamp
                          1234567890