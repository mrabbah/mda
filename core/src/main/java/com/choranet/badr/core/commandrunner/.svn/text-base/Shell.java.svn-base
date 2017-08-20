/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.commandrunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe repr�sente le shell syst�me.<br>
 * Elle permet de simplifier l'ex�cution de programme externe.<br>
 * <br>
 * Cette classe utilise la classe {@linkplain ProcessConsumer} afin de g�rer
 * simplement les flux d'entr�es/sorties du process.
 * 
 * @author rabbah
 * @version 1.0-SNAPSHOT
 */
public class Shell {
    private static final Logger log = Logger.getLogger(Shell.class.toString());

    /** Commande permettant de lancer le shell sous les syst�mes Windows 9x. */
    private static final String[] DEFAULT_WIN9X_SHELL = {"command.com", "/C"};
    /** Commande permettant de lancer le shell sous les syst�mes Windows NT/XP/Vista. */
    private static final String[] DEFAULT_WINNT_SHELL = {"cmd.exe", "/C"};
    /** Commande permettant de lancer le shell sous les syst�mes Unix/Linux/MacOS/BSD. */
    private static final String[] DEFAULT_UNIX_SHELL = {"/bin/sh", "-c"};
    /** Shell du syst�me courant, d�termin� lors du chargement de la classe. */
    private static final String[] SYSTEM_SHELL = getSystemShell();

    /**
     * Retourne le shell courant sous forme d'un tableau de String
     * repr�sentant les diff�rents param�tres a ex�cuter.<br/>
     * Le shell � utiliser d�pend du syst�me d'exploitation et
     * de certaine variable d'environnement (<b>%ComSpec%</b> sous Windows,
     * <b>$SHELL</b> sous les autres syst�mes).
     * @return Le tableau de param�tre utile � l'ex�cution du shell.
     */
    private static String[] getSystemShell() {
        // On d�termine le shell selon deux cas : Windows ou autres :
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            // On tente de d�terminer le shell selon la variable d'environnement ComSpec :
            String comspec = System.getenv("ComSpec");
            if (comspec != null) {
                return new String[]{comspec, "/C"};
            }
            // Sinon on d�termine le shell selon le nom du syst�me :
            if (osName.startsWith("Windows 3") || osName.startsWith("Windows 95")
                    || osName.startsWith("Windows 98") || osName.startsWith("Windows ME")) {
                return DEFAULT_WIN9X_SHELL;
            }
            return DEFAULT_WINNT_SHELL;
        }
        // On tente de d�terminer le shell selon la variable d'environnement SHELL :
        String shell = System.getenv("SHELL");
        if (shell != null) {
            return new String[]{shell, "-c"};
        }
        // Sinon on utilise le shell par d�faut (/bin/sh)
        return DEFAULT_UNIX_SHELL;
    }
    /** Le tableau repr�sentant les param�tres du shell. */
    private final String[] shell;
    /** Le charset associ� � cette instance du shell. */
    private Charset charset = null;
    /** Le r�pertoire associ� � cette instance du shell. */
    private File directory = null;
    /** Les variables d'environnement utilisateurs associ� � ce shell. */
    private Map<String, String> userEnv = null;
    /** Indique si les variables d'environnements globales doivent �tre h�rit�. */
    private boolean systemEnvInherited = true;

    /**
     * Construit un nouveau Shell en utilisant le shell systeme.
     */
    public Shell() {
        this.shell = Shell.SYSTEM_SHELL;
    }

    /**
     * Construit un nouveau shell en utilisant la commande repr�sent� en param�tre.
     * Par exemple pour forcer l'utilisation du bash :
     * <pre><code>Shell sh = new Shell("/bin/bash", "-c");</code></pre>
     * @param cmds Les param�tres permettant de lancer le shell.
     */
    public Shell(String... cmds) {
        this.shell = new String[cmds.length];
        System.arraycopy(cmds, 0, this.shell, 0, this.shell.length);
    }

    /**
     * Retourne le charset associ� avec cette instance de shell.
     * @return Charset.
     */
    public Charset getCharset() {
        if (this.charset == null) {
            this.charset = Charset.defaultCharset();
        }
        return this.charset;
    }

    /**
     * Modifie le charset associ� avec cette instance de shell.
     * @param charset Le nouveau charset a utiliser.
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Modifie le charset associ� avec cette instance de shell.
     * @param charsetName Le nom du nouveau charset
     * @throws IllegalCharsetNameException
     * @throws UnsupportedCharsetException
     */
    public void setCharset(String charsetName)
            throws IllegalCharsetNameException, UnsupportedCharsetException {
        this.charset = Charset.forName(charsetName);
    }

    /**
     * Retourne une map contenant les variables d'environnements utilisateurs.
     * Cette Map est librement modifiables afin d'ajouter/supprimer des �l�ments.
     * @return Map des variables d'environnements utilisateurs.
     */
    public Map<String, String> getUserEnv() {
        if (this.userEnv == null) {
            this.userEnv = new HashMap<String, String>();
        }
        return this.userEnv;
    }

    /**
     * Retourne le r�pertoire � partir duquel les commandes du shell seront lanc�s.
     * @return Le r�pertoire courant.
     */
    public File getDirectory() {
        if (this.directory == null) {
            this.directory = new File("").getAbsoluteFile();
        }
        return this.directory;
    }

    /**
     * Modifie le r�pertoire � partir duquel les commandes du shell seront lanc�s.
     * @param directory Le nouveau r�pertoire
     * @throws IllegalArgumentException Si <code>directory</code> ne repr�sente pas un r�pertoire.
     */
    public void setDirectory(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Not a directory");
        }
        this.directory = directory;
    }

    /**
     * Indique si les variables d'environnements de l'application Java
     * courante doivent �tre pass� aux commandes lanc�es par ce shell.
     * @return <b>true</b> si les nouveaux process h�ritent des variables d'environnements,
     * <b>false</b> sinon.
     */
    public boolean isSystemEnvInherited() {
        return this.systemEnvInherited;
    }

    /**
     * Modifie la valeur de l'attribut 'inheritSystemEnv'.
     * @param inheritSystemEnv La nouvelle valeur de l'attribut.
     * @see Shell#isSystemEnvInherited()
     */
    public void setSystemEnvInherited(boolean inheritSystemEnv) {
        this.systemEnvInherited = inheritSystemEnv;
    }

    /**
     * Creer un ProcessBuilder selon la configuration de ce shell
     * @param args Les param�tres principaux de la commande
     * @return Un ProcessBuilder correctement initialis�.
     */
    private ProcessBuilder create(String... args) {
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.directory(directory);
        if (!systemEnvInherited) {
            pb.environment().clear();
        }
        if (userEnv != null) {
            pb.environment().putAll(userEnv);
        }
        return pb;
    }

    /**
     * Cr�e un processus repr�sentant le shell et l'associe � une instance
     * de ProcessConsumer.<br/>
     * Le processus ne sera r�ellement d�marr� que lors de l'appel d'une
     * des m�thodes <code>consume()</code> de la classe ProcessConsumer.<br>
     * <br>
     * Cela permet de lancer plusieurs commandes dans le m�me shell
     * via le flux d'entr�e du processus.
     * @return Une instance de ProcessConsumer associ� au processus du shell.
     * @see ProcessConsumer#consume()
     * @see ProcessConsumer#input(Readable)
     */
    public ProcessConsumer shell() {
        return new ProcessConsumer(create(this.shell[0]), this.charset);
    }

    /**
     * Cr�e un processus repr�sentant la commande du shell et l'associe
     * � une instance de ProcessConsumer.<br/>
     * Cette m�thode instancie un nouveau shell en tant que processus afin
     * d'ex�cuter la ligne de commande en param�tre. Le processus ne sera
     * r�ellement d�marr� que lors de l'appel d'une des m�thodes
     * <code>consume()</code> de la classe ProcessConsumer.<br>
     * <br>
     * La commande pass� en param�tre accepte toutes les sp�cificit�es du
     * shell syst�me (redirections, pipes, structures conditionnelles, etc.).
     * @param commandLine La ligne de commande � ex�cuter par le shell .
     * @return Une instance de ProcessConsumer associ� � la commande.
     * @see ProcessConsumer#consume()
     */
    public ProcessConsumer command(String commandLine) {
        log.log(Level.INFO, "\n+++++++++++++++++ lancement de la commande"
                        + " +++++++++++++++++\n{0}\n+++++++++++++++++++++++++++"
                        + "+++++++++++++++++++++++++++++++++", commandLine);
        ProcessBuilder pb = create(this.shell);
        pb.command().add(commandLine);
        return new ProcessConsumer(pb, this.charset);
    }

    /**
     * Identique � {@link Shell#command(String)}, si ce n'est que la ligne
     * de commande est d'abord formatt� en utilisant les param�tres via
     * la classe MessageFormat.<br><br>
     * Cete m�thode est �quivalent � la ligne suivante :<br>
     * <pre><code>command(MessageFormat.format(commandLine, arguments))</code></pre>
     * @param commandLine La ligne de commande � formater.
     * @param arguments Les param�tres du formattage.
     * @return Une instance de ProcessConsumer associ� � la commande.
     * @see MessageFormat#format(String, Object...)
     * @see Shell#command(String)
     */
    public ProcessConsumer command(String commandLine, Object... arguments) {
        return command(MessageFormat.format(commandLine, arguments));
    }

    /**
     * Cr�e un processus standard et l'associe � une instance de ProcessConsumer.
     * Le premier param�tre doit obligatoirement correspondre � un nom de programme
     * existant, de la m�me mani�re qu'avec l'utilisation de {@linkplain Runtime#exec(String[])}.
     * <br><br>
     * Contrairement � {@link Shell#command(String)}, cette m�thode n'instancie
     * pas le processus du shell mais directement le programme pass� en
     * premier param�tre.  Le processus ne sera r�ellement d�marr� que
     * lors de l'appel d'une des m�thodes <code>consume()</code> de la
     * classe ProcessConsumer.<br>
     * @param args Les param�tres de la commandes a ex�cuter.
     * @return Une instance de ProcessConsumer associ� au process.
     */
    public ProcessConsumer exec(String... args) {
        return new ProcessConsumer(create(args), this.charset);
    }

    /**
     * Retourne le nom du shell syst�me.
     * C'est � dire le nom du fichier en local qui
     * return Le nom du shell.
     */
    @Override
    public String toString() {
        return this.shell[0];
    }

    /**
     * Ex�cute la ligne de commande dans le shell syst�me,
     * en affichant les donn�es dans les flux des sorties de l'application.
     * @param commandLine La ligne de commande � ex�cuter.
     * @return Le code de retour de la ligne de commande.
     * @throws IOException Erreur d'ex�cution de la commande.
     * @see System#out
     * @see System#err
     */
    public static int system(String commandLine) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(Shell.SYSTEM_SHELL);
        pb.command().add(commandLine);
        return new ProcessConsumer(pb, null).consume();
    }

    /**
     * Identique � {@link Shell#system(String)}, si ce n'est que la ligne
     * de commande est d'abord formatt� en utilisant les param�tres via
     * la classe MessageFormat.<br><br>
     * @param commandLine La ligne de commande � ex�cuter.
     * @return Le code de retour de la ligne de commande.
     * @throws IOException Erreur d'ex�cution de la commande.
     * @see Shell#system(String)
     */
    public static int system(String commandLine, Object... arguments) throws IOException {
        return system(MessageFormat.format(commandLine, arguments));
    }

    /**
     * Ex�cute le programme sp�cifi� en affichant les donn�es dans
     * les flux des sorties de l'application.
     * @param args Les diff�rents param�tres du programme.
     * @return Le code de retour du programme.
     * @throws IOException Erreur d'ex�cution du programme.
     * @see System#out
     * @see System#err
     */
    public static int execute(String... args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(args);
        return new ProcessConsumer(pb, null).consume();
    }
}
