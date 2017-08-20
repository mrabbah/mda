@echo off
color a
title Generateur des Controleurs
call grails generate-controller demo.Auteur >> log.txt
echo generation du controleur et vue du domain demo.Auteur termine avec success!
call grails generate-controller demo.Livre >> log.txt
echo generation du controleur et vue du domain demo.Livre termine avec success!
call grails generate-controller demo.Etudiant >> log.txt
echo generation du controleur et vue du domain demo.Etudiant termine avec success!
