Name: Abhijeet Kulkarni
-----------------------------------------------------------------------
Assuming you are in the directory containing this README:

## To clean:
ant -buildfile src/build.xml clean

-----------------------------------------------------------------------
## To compile:
ant -buildfile src/build.xml all

-----------------------------------------------------------------------
## To run by specifying arguments from command line
## We will use this to run your code
ant -buildfile src/build.xml run -Darg0=[preference file] -Darg1=[add drop file] -Darg2=[output file] -Darg3=[number of thread] -Darg4=[debug level]

-----------------------------------------------------------------------

## To create tarball for submission
ant -buildfile src/build.xml tarzip

-----------------------------------------------------------------------

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating I will have to sign an
official form that I have cheated and that this form will be stored in
my official university record. I also understand that I will receive a
grade of 0 for the involved assignment for my first offense and that I
will receive a grade of F for the course for any additional
offense.”

Abhijeet Kulkarni
[Date: 03/07/2017]

-----------------------------------------------------------------------

Provide justification for Data Structures used in this assignment in
term of Big O complexity (time and/or space)
1. 	HashMap is used to store the schedules after initial 
	allotment. As to add/drop courses we often need to
	access the schedule, hashmap is used.

2. 	ArrayList is used to represent CoursePool. ArrayList 
	grows 1.5 times. Number of students generally grow as
	fast as 2 times, thus arraylist is used.

-----------------------------------------------------------------------

Provide list of citations (urls, etc.) from where you have taken code
(if any).