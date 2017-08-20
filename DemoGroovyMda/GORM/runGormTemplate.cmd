@echo off 
color a
title Generateur MDA Grails
date /t
set /p xmiFile=entrez le nom de votre fichier xmi: 
set /p projet=entrez le nom de projet a creer: 
time /t
echo ################ Generation Du Class Domain et des Test  ###################
echo Veuillez patientez ..........................................................
java -jar groovymda-core-1.1-SNAPSHOT-jar-with-dependencies.jar file:./%xmiFile% %projet% file:./GormProcessor.groovy > log.txt
echo ############## Fin Generation Du Class Domain et des Test  #################
time /t
echo ################ Generation de l application %projet% GRAILS ###################
echo Veuillez patientez ..........................................................
call grails create-app %projet% >> log.txt
move runControllerViewGenerator.cmd %projet%
move run.cmd %projet%
cd %projet%
call grails clean
call grails install-plugin i18n-templates
call grails generate-i18n-messages "*"
echo ############## Fin Generation de l application %projet% GRAILS #################
time /t
set /p choix1=Voulez vous generez les controleurs et les vus GSP? (o, n):
if not "%choix1%" == "o" goto Step2
echo ############### Generation Des controleurs et des vues GRAILS ##################
call runControllerViewGenerator.cmd
echo ############# Fin Generation Des controleurs et des vues GRAILS ################
time /t
:Step2
set /p choix=Voulez vous lancer l'applicatioin? (o, n) : 
if not "%choix%" == "o" goto Fin
echo ############# Debut Lancement de l'application ################
echo !!!!!!!!!!!!!!!!! l application sera accessible via le lien: http://localhost:8080/%projet% !!!!!!!!!!!!!!!
echo !!!!!!!!!!!!!!!! ENJOY (RABBAH Mahmoud Almostafa) !!!!!!!!!!!!!!!!
echo Veuillez patientez ..........................................................
call run.cmd
:Fin 
echo !!!!!!!!!!!!!!!! ENJOY (RABBAH Mahmoud Almostafa) !!!!!!!!!!!!!!!!
pause