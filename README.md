TCSS360A-PRJ#01-W01Sun-rev0.docx (^) Page 1 of 3

# TCSS 360 Software Development and Quality Assurance Techniques

```
(Spring 2020 - Section A)
```
```
Project Assignment # 01
(PRJ#01-W0 3 Sun)
```
Revision History
Revision 0 – posted on Sun, March 29th , and to be presented in the class on Mon, March 30 th, 2020.

```
Submit PRJ#01C by 11: 59 pm on Sun April 19 th, 20 20
You will have three ( 3 ) days of grace period for this assignment.
```
General Information

- You can go with the randomly assigned group leader (or moderator) or choose somebody else who will
    volunteer for assuming the responsibilities of this position.
       o The group leader will be responsible for coordinating the group meetings, task assignments, and
          collecting and submitting the project deliverables at the end.
       o The group leader will be awarded +5 bonus points for her/his services for completing this assignment.
- You are going to develop a single software as a group in the specified period of the assignment.
    o You can make up to three (3) submissions for each assignment before it is closed, and only the last
       submission will be graded.
    o If you want to resubmit, please do so. You do not need to notify me.

PRJ#01A (W01-Sun) due to 11:59 pm on Sun April 5th, 2020 and
PRJ#01B (W02-Sun) due to 11:59 pm on Sun April 1 2 th, 2020

- These are interim checkpoints where you will individually answer questions about the progress of your project
    in the previous week.
- These questions will include personal questions such as:
    o What did you plan to do last week?
    o How did you do last week?
    o What went wrong/right the previous week?
    o What are you planning to do next week?
    o How was the performance of your group last week?
    o What are the risks that may cause the group to miss the deadline?
- Your answers will help me to monitor your progress closely and to share some advice with your group in some
    cases that may (or may not) help your group do perform better.


```
TCSS 360A (Spring 2020) – Group Project Assignment #0 1
```
```
Page 2 of 3
```
PRJ#01C (W03-Sun): due to 11: 59 pm on Sun April 19 th, 20 20

- You will individually answer questions about your project experience and report the lessons you learned
    during this group assignment. You will also do a peer review of your group members.
    - Only one person from your group should submit your group project on behalf of all group members.
    - Your group should submit the following artifacts as a group:
- All submitted files will be named prefixed with specially “PRJ#01-GroupNN”, where NN is the group
    number when you upload your file onto Canvas.
    o PRJ#01-GroupNN-src.zip: Your zipped Eclipse project folder that includes all your source codes, JUnit
    test codes, other required files (such as image and data files), and a README that explains how to run your
    software.
    o PRJ#01-GroupNN-testcases.pdf: The list of all test cases with a short definition of the test procedure and
    the expected results.
    o PRJ#01-GroupNN-testvideo.mp4: (or a link to some other location): You should shoot a 5 min video
    where you introduce your source and test codes, you compile and execute your program to prove that it
    compiles and runs without error, and run your test suites to show the test coverages of your units.
    o PRJ#01-GroupNN-demovideo.mp4: (or a link to some other location): You should shoot a 3 - 5 min video
    where you present all the functions and capabilities provided by your software.


```
TCSS 360A (Spring 2020) – Group Project Assignment #0 1
```
```
Page 3 of 3
```
I. Problem Definition:

You are going to develop the core software for the Wireless Vantage Pro2 Integrated Sensor Suite (ISS)
(Product number: 6322) manufactured by Davis Instruments.

The ISS software shall

- collect data measurements from all attached
    sensors of the device according to the device
    specifications (provided on the website),
- process the sensor data properly to serve (or send)
    it to the data monitoring devices. These include
    Wireless Vantage Pro2 Console Receivers
    (Product number: 6316) and Wireless Weather
    Envoy (Product number: 6316) that transfers the
    incoming data to a stand-alone weather
    monitoring application running on your computer.
- transfer the data between the weather station and receiver or envoy devices on a (wireless) IP network
    by serializing the data.

You should develop the essential ISS software in Java and its unit tests using JUnit 5 to test your ISS
software with a test coverage of 90% or above.

Since you do not have the actual sensors that can provide data to the ISS, you will also need to implement
proxy sensors by following their specifications. For the sake of this assignment, you should implement all
sensors collectively as “a single stand-alone application” or “a single driver class running on its own
thread” separate from your ISS software and interchanging data with the ISS by serialized sensor and
control data.

Hint: The textbook includes a case study on a wilderness weather station where you can benefit while you work
on this project. Please see Chapters 5, 6, and 7 for relevant information.

II. Programming Guidelines:

1. You should include Javadoc comments at the beginning of each file, class with some necessary
    descriptive information, as well as some comments to the complicated parts of your code.
2. Please try to eliminate as many of the warnings displayed by Eclipse, and optionally other Eclipse
    plug-ins (like CheckStyle, FindBugs, and PMD) to improve the quality of your codes before submitting
    them.
3. You should code defensively. You should test setters and constructors for invalid values, as well as
    null values and thrown exceptions, or handle the error cases properly so that your program will not
    crash during its execution. //
