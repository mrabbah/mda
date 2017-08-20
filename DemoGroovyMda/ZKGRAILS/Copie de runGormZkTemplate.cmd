@echo off 
color a
title Generateur MDA ZK Grails
date /t
set /p xmiFile=entrez le nom de votre fichier xmi: 
set /p projet=entrez le nom de projet a creer: 
time /t
echo ################ Creation du projet: %projet%  ###################
echo\
call grails create-app %projet% >> log.txt
mkdir %projet%\grails-app\windows
echo ############## Fin creation du projet: %projet% #################
echo\
echo\
echo\
time /t
echo ################ Generation Des Class Domain et des Test et des vues  ###################
echo\
echo Veuillez patientez ..........................................................
echo\
java -jar groovymda-core-1.1-SNAPSHOT-jar-with-dependencies.jar file:./%xmiFile% %projet% file:./GormZkProcessor.groovy > log.txt
move runControllerGenerator.cmd %projet%
move run.cmd %projet%
move /Y resources.groovy %projet%\grails-app\conf\spring\
COPY /Y cancel.png %projet%\web-app\images\skin\
mkdir %projet%\src\java\util
COPY /Y SelectedItemsConverterV3.java %projet%\src\java\util\
echo ############## Fin Generation Des Class Domain et des Test et des vues  #################
echo\
echo\
echo\
time /t
echo ########## Installation du plugin ZK ##########
cd %projet%
call grails clean
call grails install-plugin ../grails-zk-0.7.1.zip
echo ########## Fin installation du plugin ###########
echo\
echo\
echo\
time /t
echo ############### Generation Des controleurs GRAILS ##################
call runControllerGenerator.cmd
echo ############# Fin Generation Des controleurs et des vues GRAILS ################
echo\
echo\
echo\
time /t
set /p choix=Voulez vous lancer l'applicatioin? (o, n) : 
if not "%choix%" == "o" goto Fin
echo ############# Debut Lancement de l'application ################
echo\
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo !!!!!!!!!!!!!!!! ENJOY (RABBAH Mahmoud Almostafa) !!!!!!!!!!!!!!!!
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo\
call start http://localhost:9595/%projet%
call run.cmd
:Fin 
echo\
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo !!!!!!!!!!!!!!!! ENJOY (RABBAH Mahmoud Almostafa) !!!!!!!!!!!!!!!!
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo\
pause