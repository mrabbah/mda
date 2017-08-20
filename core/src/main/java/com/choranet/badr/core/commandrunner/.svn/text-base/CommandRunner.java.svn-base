/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.commandrunner;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description CammandRunner sert a executer des commandes dos ou shell
 * independament de l environnement d execution
 * @version 1.0-SNAPSHOT
 * @date 13 févr. 2011
 * @author rabbah
 */
public class CommandRunner {

    private static final Logger log = Logger.getLogger(CommandRunner.class.toString());
    private static InputStream sortieErreur;
    private static InputStream sortieStandard;

    /**
     * Executer une commande systeme
     * @param command commande a executer
     * @return status d execution
     */
    public static RunCommandStatusEnum run(String command) {
        try {
            Shell shell = new Shell();
            ProcessConsumer pc = shell.command(command);
            int codeRetour = pc.consume();
            if (codeRetour == 0) {
                return RunCommandStatusEnum.SUCCEE;
            } else {
                return RunCommandStatusEnum.ECHEC;
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return RunCommandStatusEnum.ECHEC;
        }
    }

    /**
     * Executer une liste de commandes systeme
     * @param commands commandes a executer
     * @return status d execution
     */
    public static RunCommandStatusEnum run(String[] commands) {
        try {
            Shell shell = new Shell();
            for (int i = 0; i < commands.length; i++) {
                String command = commands[i];
                ProcessConsumer pc = shell.command(command);
                int codeRetour = pc.consume();
                if (codeRetour != 0) {
                    return RunCommandStatusEnum.ECHEC;
                } 
            }
            return RunCommandStatusEnum.SUCCEE;

        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return RunCommandStatusEnum.ECHEC;
        }
    }

    /**
     * Executer une commande systeme a partir d un repertoire
     * @param command commande a executer
     * @param directory repertoire a partir du quel aura l execution
     * @return status d execution
     */
    public static RunCommandStatusEnum run(String command, File directory) {
        try {
            Shell shell = new Shell();
            shell.setDirectory(directory);
            ProcessConsumer pc = shell.command(command);
            int codeRetour = pc.consume();

            if (codeRetour == 0) {
                return RunCommandStatusEnum.SUCCEE;
            } else {
                return RunCommandStatusEnum.ECHEC;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return RunCommandStatusEnum.ECHEC;
        }
    }

    /**
     * Executer une suite de commandes systeme a partir d un repertoire
     * @param commands commandes a executer
     * @param directory repertoire a partir du quel aura l execution
     * @return status d execution
     */
    public static RunCommandStatusEnum run(String[] commands, File directory) {
        try {
            Shell shell = new Shell();
            shell.setDirectory(directory);
            for (int i = 0; i < commands.length; i++) {
                String command = commands[i];
                ProcessConsumer pc = shell.command(command);
                int codeRetour = pc.consume();
                if (codeRetour != 0) {
                    return RunCommandStatusEnum.ECHEC;
                }
            }
            return RunCommandStatusEnum.SUCCEE;

        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return RunCommandStatusEnum.ECHEC;
        }
    }

    /**
     * getter .
     * @return sortie d erreur
     */
    public static InputStream getSortieErreur() {
        return sortieErreur;
    }

    /**
     * getter .
     * @return sortie standard
     */
    public static InputStream getSortieStandard() {
        return sortieStandard;
    }
}
