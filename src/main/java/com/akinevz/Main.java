package com.akinevz;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.install.DependencyResolver;
import com.akinevz.install.PlatformUnupportedException;

public class Main implements Runnable, AutoCloseable {
    static final Logger logger = Logger.getLogger(Main.class.getName());
    static final ExecutorService es = Executors.newFixedThreadPool(2);

    public static void main(String[] args)
            throws PlatformUnupportedException, IOException, DependenciesUnsatisfiedException, InterruptedException {
        try (var main = new Main()) {
            main.run();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unhandled exception (shutting down): ", ex);
        }
    }

    @Override
    public void close() throws Exception {
        es.shutdown();
    }

    @Override
    public void run() {
        try {
            var dr = new DependencyResolver();

            var dependencies = new String[] { "texlive-latex-extra", "pandoc" };
            if (!dr.ensureHas(dependencies)) {
                throw new DependenciesUnsatisfiedException(dependencies);
            }
            logger.log(Level.INFO, "All packages installed");
        } catch (DependenciesUnsatisfiedException | PlatformUnupportedException ex) {
            logger.log(Level.WARNING, "Unable to continue due to: ");
            ex.printStackTrace();
        } catch (IOException | InterruptedException ex) {
            logger.log(Level.SEVERE, "Unhandled exception: ", ex);
        }
    }
}