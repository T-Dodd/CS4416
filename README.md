# CS4416
Project for CS4416 Cybersecurity

# Contributors
* Tyler Dodd
* Andrija Sevaljevic
* Russell Phillips

# Instructions
The program is run through the Main.java class in the src folder "src/Main.Java". To use, select a file with the select file button. Then, click scan to scan the file for unsafe functions. The edit functions button opens a window that allows disabling and enabling which functions are scanned for. Currently, the program only supports C source code for default language recognition. The program may run when given a text file that is not C source code, but the results are undefined and unknown. The program will read from the full length file path specified by the user. The program allows for some customization via a checkbox selection window. Any functions that are unchecked will be removed for the duration of the program lifetime, and any functions added will be available as checkboxes the next time the function dialog box is opened. All changes made through the running application are temporary for the duration of the program lifetime. If you would like to modify the default functions permanently, they are located in the deprecatedFuncs.txt file. 

# SOURCES FOR VULNERABLE FUNCTIONS
* https://wiki.sei.cmu.edu/confluence/display/c/MSC24-C.+Do+not+use+deprecated+or+obsolescent+functions
* https://github.com/git/git/blob/master/banned.h
* https://stackoverflow.com/a/2565736
* https://github.com/x509cert/banned/blob/master/banned.h
