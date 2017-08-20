/*
Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.commandrunner;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * ProcessConsumer permet de traiter tous les flux d'un process.<br>
 * Cette classe permet de d�finir simplement les flux d'E/S associ� � un process
 * via les m�thodes input()/output()/error(). <br>
 * <br>
 * Par exemple, pour utiliser des fichiers comme flux d'E/S :<br>
 * 
 * <pre><code>
 * ProcessConsumer pc = ...;
 * int result = pc.input( new FileInputStream(&quot;file_stdin.txt&quot;) )
 *   .output( new FileInputStream(&quot;file_stdout.txt&quot;) )
 *   .error( new FileInputStream(&quot;file_stderr.txt&quot;) )
 *   .consume();
 * </code></pre>
 * 
 * Par d�faut, aucun flux d'entr�e n'est associ�, alors que les flux de sorties
 * standard et d'erreur du process sont associ� � ceux de l'application Java.
 * 
 * @author rabbah
 * @version 1.0-SNAPSHOT
 */
public class ProcessConsumer {

    /**
     * L'instance statique de l'executor qui sera charg� de lancer les t�ches de
     * fond.
     */
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool(new ThreadFactory() {

        private final ThreadGroup threadGroup = new ThreadGroup(
                "ProcessConsumerThreadGroup");
        private int count = 0;

        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(this.threadGroup, runnable,
                    "ProcessConsumerThread-" + (++count));
            t.setPriority(Thread.NORM_PRIORITY);
            t.setDaemon(true);
            return t;
        }
    });
    /** Taille du buffer de lecture lors de la copie des flux. */
    private static final int BUF_SIZE = 8192;
    /** Charset utilis� pour la convertion des flux. */
    private final Charset charset;
    /** Flux d'entr�e � rediriger vers le process */
    private Readable stdin = null;
    /** Flux de sortie standard � rediriger depuis le process */
    private Appendable stdout = System.out;
    /** Flux de sortie d'erreur � rediriger depuis le process */
    private Appendable stderr = System.err;
    /** La copie des flux a-t-elle d�j� eu lieu. */
    private boolean started = false;
    /** Instance du process (selon le type de constructeur qui est utilis�) */
    private final Process userProcess;
    /**
     * Instance du processbuilder (selon le type de constructeur qui est
     * utilis�)
     */
    private final ProcessBuilder builder;

    /**
     * Construit un ProcessConsumer pour ce Process, en utilisant le charset par
     * d�faut.
     *
     * @param process
     *            Le processus qui sera trait�.
     */
    public ProcessConsumer(Process process) {
        this(null, process, null);
    }

    /**
     * Construit un ProcessConsumer pour ce Process.
     *
     * @param process
     *            Le processus qui sera trait�.
     * @param cs
     *            Le charset � utiliser pour la conversion.
     */
    public ProcessConsumer(Process process, Charset cs) {
        this(null, process, cs);
    }

    /**
     * Construit un ProcessConsumer pour ce ProcessBuilder, en utilisant le
     * charset par d�faut.
     *
     * @param pb
     *            Le ProcessBuilder qui sera utilis� pour cr�er le process.
     */
    public ProcessConsumer(ProcessBuilder pb) {
        this(pb, null, null);
    }

    /**
     * Construit un ProcessConsumer pour ce ProcessBuilder.
     *
     * @param pb
     *            Le ProcessBuilder qui sera utilis� pour cr�er le process.
     * @param cs
     *            Le charset � utiliser pour la conversion.
     */
    public ProcessConsumer(ProcessBuilder pb, Charset cs) {
        this(pb, null, cs);
    }

    /**
     * Construit priv� contenant tout le code d'initialisation du
     * ProcessConsumer.
     *
     * @param builder
     *            Le ProcessBuilder qui sera utilis� pour cr�er le process.
     * @param process
     *            Le processus qui sera trait�.
     * @param cs
     *            Le charset � utiliser pour la conversion.
     */
    private ProcessConsumer(ProcessBuilder builder, Process process,
            Charset charset) {
        this.builder = builder;
        this.userProcess = process;
        this.charset = charset != null ? charset : Charset.defaultCharset();
        if (this.builder == null && this.userProcess == null) {
            throw new NullPointerException("null ProcessBuilder && Process");
        }
    }

    /**
     * Transforme un InputStream en un objet Readable, en utilisant le charset.
     *
     * @param in
     *            InputStream (peut �tre null)
     * @return un InputStreamReader, ou <b>null</b> si <code>in</code> est
     *         <b>null</b>.
     */
    private Readable readable(InputStream in) {
        if (in == null) {
            return null;
        }
        return new InputStreamReader(in, this.charset);
    }

    /**
     * Transforme un CharSequence en un objet Readable.
     *
     * @param in
     *            CharSequence (peut �tre null)
     * @return un ReadableCharSequence, ou <b>null</b> si <code>in</code> est
     *         <b>null</b>.
     */
    private Readable readable(CharSequence in) {
        if (in == null) {
            return null;
        }
        return new ReadableCharSequence(in);
    }

    /**
     * D�finit un objet Readable comme flux d'entr�e pour le process. Une seule
     * des m�thodes <code>input()</code> peut �tre utilis�.<br>
     * Par d�faut, aucun flux d'entr�e n'est utilis�.
     *
     * @param in
     *            Le flux d'entr�e du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux d'entr�e a d�j� �t� d�fini pr�c�demment.
     */
    public ProcessConsumer input(Readable in) throws IllegalStateException {
        if (this.stdin != null) {
            throw new IllegalStateException("INPUT already set.");
        }
        this.stdin = in;
        return this;
    }

    /**
     * D�finit un InputStream comme flux d'entr�e pour le process.
     *
     * @param in
     *            Le flux d'entr�e du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux d'entr�e a d�j� �t� d�fini pr�c�demment.
     * @see ProcessConsumer#input(Readable)
     */
    public ProcessConsumer input(InputStream in) throws IllegalStateException {
        if (in == System.in) {
            throw new IllegalStateException(
                    "System.in cannot be used as 'input'");
        }
        return input(readable(in));
    }

    /**
     * D�finit un CharSequence comme flux d'entr�e pour le process.
     *
     * @param in
     *            Le flux d'entr�e du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux d'entr�e a d�j� �t� d�fini pr�c�demment.
     * @see ProcessConsumer#input(Readable)
     */
    public ProcessConsumer input(CharSequence in) throws IllegalStateException {
        return input(readable(in));
    }

    /**
     * Supprime le flux d'entr�e pour le process (pas de flux d'entr�e).
     *
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux d'entr�e a d�j� �t� d�fini pr�c�demment.
     * @see ProcessConsumer#input(Readable)
     */
    public ProcessConsumer input() throws IllegalStateException {
        return input((Readable) null);
    }

    /**
     * Transforme un OutputStream en un objet Appendable, en utilisant le
     * charset courant.
     *
     * @param out
     *            OutputStream (peut �tre null)
     * @return un OutputStreamWriter, ou <b>null</b> si <code>out</code> est
     *         <b>null</b>.
     */
    private Appendable appendable(OutputStream out) {
        if (out == null) {
            return null;
        }
        return new OutputStreamWriter(out, this.charset);
    }

    /**
     * D�finit un objet Appendable comme flux de sortie standard pour le
     * process. Une seule des m�thodes <code>output()</code> peut �tre
     * utilis�.<br>
     * Par d�faut, le flux de sortie standard de l'application est utilis� (<code>{@linkplain System#out}</code>).
     *
     * @param out
     *            Le flux de sortie standard du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie standard a d�j� �t� d�fini
     *             pr�c�demment.
     */
    public ProcessConsumer output(Appendable out) throws IllegalStateException {
        if (this.stdout != System.out) {
            throw new IllegalStateException("OUTPUT already set.");
        }
        this.stdout = out;
        return this;
    }

    /**
     * D�finit un objet OutputStream comme flux de sortie standard pour le
     * process.
     *
     * @param out
     *            Le flux de sortie standard du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie standard a d�j� �t� d�fini
     *             pr�c�demment.
     * @see ProcessConsumer#output(Appendable)
     */
    public ProcessConsumer output(OutputStream out)
            throws IllegalStateException {
        return output(appendable(out));
    }

    /**
     * D�finit un objet PrintStream comme flux de sortie standard pour le
     * process.
     *
     * @param out
     *            Le flux de sortie standard du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie standard a d�j� �t� d�fini
     *             pr�c�demment.
     * @see ProcessConsumer#output(Appendable)
     */
    public ProcessConsumer output(PrintStream out) throws IllegalStateException {
        return output((Appendable) out);
    }

    /**
     * Supprime le flux de sortie standard pour le process (pas de flux de
     * sortie).
     *
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie standard a d�j� �t� d�fini
     *             pr�c�demment.
     * @see ProcessConsumer#output(Appendable)
     */
    public ProcessConsumer output() throws IllegalStateException {
        return output((Appendable) null);
    }

    /**
     * D�finit un objet Appendable comme flux de sortie d'erreur pour le
     * process. Une seule des m�thodes <code>error()</code> peut �tre utilis�.<br>
     * Par d�faut, le flux de sortie d'erreur de l'application est utilis� (<code>{@linkplain System#err}</code>).
     *
     * @param err
     *            Le flux de sortie d'erreur du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie d'erreur a d�j� �t� d�fini
     *             pr�c�demment.
     */
    public ProcessConsumer error(Appendable err) throws IllegalStateException {
        if (this.stderr != System.err) {
            throw new IllegalStateException("ERROR already set.");
        }
        this.stderr = err;
        return this;
    }

    /**
     * D�finit un objet OutputStream comme flux de sortie d'erreur pour le
     * process.
     *
     * @param err
     *            Le flux de sortie d'erreur du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie d'erreur a d�j� �t� d�fini
     *             pr�c�demment.
     * @see ProcessConsumer#error(Appendable)
     */
    public ProcessConsumer error(OutputStream err) throws IllegalStateException {
        return error(appendable(err));
    }

    /**
     * D�finit un objet PrintStream comme flux de sortie d'erreur pour le
     * process.
     *
     * @param err
     *            Le flux de sortie d'erreur du process.
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie d'erreur a d�j� �t� d�fini
     *             pr�c�demment.
     * @see ProcessConsumer#error(Appendable)
     */
    public ProcessConsumer error(PrintStream err) throws IllegalStateException {
        return error((Appendable) err);
    }

    /**
     * Supprime le flux de sortie d'erreur pour le process (pas de flux de
     * sortie).
     *
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie d'erreur a d�j� �t� d�fini
     *             pr�c�demment.
     * @see ProcessConsumer#error(Appendable)
     */
    public ProcessConsumer error() throws IllegalStateException {
        return error((Appendable) null);
    }

    /**
     * D�finit un objet PrintStream comme flux de sortie d'erreur pour le
     * process. Cette m�thode ne peut �tre utilis� qu'avec une instance de
     * ProcessBuilder.
     *
     * @return <b>this</b>
     * @throws IllegalStateException
     *             Lorsque le flux de sortie d'erreur a d�j� �t� d�fini
     *             pr�c�demment, ou que cet instance de ProcessConsumer ne
     *             poss�de pas de ProcessBuilder.
     * @see ProcessConsumer#error(Appendable)
     * @see ProcessConsumer#ProcessConsumer(ProcessBuilder)
     * @see ProcessConsumer#ProcessConsumer(ProcessBuilder, Charset)
     */
    public ProcessConsumer errorRedirect() throws IllegalStateException {
        if (this.builder == null) {
            throw new IllegalStateException("No ProcessBuilder");
        }
        error();
        this.builder.redirectErrorStream(true);
        return this;
    }

    /**
     * Retourne le process � utiliser selon le constructeur utilis�. Soit cette
     * m�thode retourne le process pass� au constructeur, soit elle d�marre un
     * nouveau process via le ProcessBuilder
     *
     * @return Le process
     * @throws IOException
     *             Si le process a d�j� �t� "d�marr�"
     */
    private Process getProcess() throws IOException {
        if (this.started) {
            throw new IOException("Process already started");
        }
        if (this.builder == null) {
            return this.userProcess;
        }
        return this.builder.start();
    }

    /**
     * Consume tous les flux du process en associant les diff�rents flux. Cette
     * m�thode est bloquante tant que le process n'est pas termin�.
     *
     * @return Le code de retour du process.
     * @see Process#exitValue()
     * @throws IOException
     *             Erreur d'E/S
     */
    public int consume() throws IOException {
        Future<?> inputTask = null;
        Future<?> errorTask = null;
        Process process = getProcess();
        try {
            OutputStream pIn = process.getOutputStream();
            if (this.stdin == null) {
                pIn.close();
            } else {
                inputTask = dumpInBackground(this.stdin, appendable(pIn));
            }

            InputStream pErr = process.getErrorStream();
            if (this.stderr == null) {
                pErr.close();
            } else {
                errorTask = dumpInBackground(readable(pErr), this.stderr);
            }

            InputStream pOut = process.getInputStream();
            if (this.stdout == null) {
                pOut.close();
            } else {
                dump(readable(pOut), this.stdout);
            }

            try {
                return process.waitFor();
            } catch (InterruptedException e) {
                IOException ioe = new InterruptedIOException();
                ioe.initCause(e);
                throw ioe;
            }
        } finally {
            process.destroy();
            if (inputTask != null) {
                inputTask.cancel(true);
            }
            if (errorTask != null) {
                errorTask.cancel(true);
            }
        }
    }

    /**
     * Consume tous les flux du process en associant les diff�rents flux, et
     * redirige la sortie standard vers une chaine de caract�re. Cette m�thode
     * est bloquante tant que le process n'est pas termin�.
     *
     * @return Une chaine de caract�re contenant la sortie standard du process.
     * @throws IOException
     *             Erreur d'E/S
     * @throws IllegalStateException
     *             Lorsque le flux de sortie standard a d�j� �t� d�fini
     *             pr�c�demment.
     */
    public String consumeAsString() throws IOException, IllegalStateException {
        StringBuilder builder = new StringBuilder();
        output(builder).consume();
        return builder.toString();
    }

    /**
     * Consume tous les flux du process en associant les diff�rents flux. Cette
     * m�thode se contente d'ex�cuter la m�thode
     * {@link ProcessConsumer#consume()} en t�che de fond.
     *
     * @return L'objet Future permettant de manipuler la t�che de fond.
     * @see Future
     */
    public Future<Integer> consumeInBackground() {
        return ProcessConsumer.inBackground(new Callable<Integer>() {

            public Integer call() throws Exception {
                return ProcessConsumer.this.consume();
            }
        });
    }

    /**
     * Consume tous les flux du process en associant les diff�rents flux, et
     * redirige la sortie standard vers une chaine de caract�re. Cette m�thode
     * se contente d'ex�cuter la m�thode
     * {@link ProcessConsumer#consumeAsString()} en t�che de fond.
     *
     * @return L'objet Future permettant de manipuler la t�che de fond.
     * @see Future
     */
    public Future<String> consumeAsStringInBackground() {
        return ProcessConsumer.inBackground(new Callable<String>() {

            public String call() throws Exception {
                return ProcessConsumer.this.consumeAsString();
            }
        });
    }

    /**
     * Tente de fermer l'objet en param�tre.
     *
     * @param c
     *            L'objet � fermer.
     */
    private void tryToClose(Object c) {
        if (c instanceof Closeable && c != System.in && c != System.out
                && c != System.err) {
            try {
                ((Closeable) c).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (c == this.stdin) {
                this.stdin = null;
            } else if (c == this.stdout) {
                this.stdout = null;
            } else if (c == this.stderr) {
                this.stderr = null;
            }
        }
    }

    /**
     * Garde fou qui force la fermeture des flux au cas o�...
     */
    @Override
    protected void finalize() {
        tryToClose(this.stdin);
        tryToClose(this.stdout);
        tryToClose(this.stderr);
    }

    /**
     * Copie des flux de <code>in</code> vers <code>out</code>.
     *
     * @param in
     *            Flux depuis lequel les donn�es seront lues
     * @param out
     *            Flux vers lequel les donn�es seront �crites
     * @throws IOException
     *             Erreur E/S
     */
    private final void dump(Readable in, Appendable out) throws IOException {
        try {
            try {
                Flushable flushable = null;
                if (out instanceof Flushable) {
                    flushable = ((Flushable) out);
                }
                Thread current = Thread.currentThread();
                CharBuffer cb = CharBuffer.allocate(BUF_SIZE);
                int len;

                cb.clear();
                while (!current.isInterrupted() && (len = in.read(cb)) > 0
                        && !current.isInterrupted()) {
                    cb.position(0).limit(len);
                    out.append(cb);
                    cb.clear();
                    if (flushable != null) {
                        flushable.flush();
                    }
                }
            } finally {
                tryToClose(in);
            }
        } finally {
            tryToClose(out);
        }
    }

    /**
     * Copie des flux de <code>in</code> vers <code>out</code>, en t�che de
     * fond.
     *
     * @param in
     *            Flux depuis lequel les donn�es seront lues
     * @param out
     *            Flux vers lequel les donn�es seront �crites
     */
    public final Future<Void> dumpInBackground(final Readable in,
            final Appendable out) {
        return ProcessConsumer.inBackground(new Callable<Void>() {

            public Void call() throws Exception {
                dump(in, out);
                return null;
            }
        });
    }

    /**
     * Ex�cute une t�che dans un thread s�par�e, en utilisant un pool de thread.
     *
     * @param <T>
     *            Le type du r�sultat de la t�che.
     * @param task
     *            La t�che a ex�cuter.
     * @return L'objet Future permettant de manipuler la t�che.
     * @see Future
     */
    protected static <T> Future<T> inBackground(Callable<T> task) {
        return ProcessConsumer.EXECUTOR.submit(task);
    }
}
