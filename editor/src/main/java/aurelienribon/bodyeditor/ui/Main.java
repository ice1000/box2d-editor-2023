package aurelienribon.bodyeditor.ui;

import aurelienribon.bodyeditor.Ctx;
import aurelienribon.bodyeditor.canvas.Canvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Aurelien Ribon | <a href="http://www.aurelienribon.com/">...</a>
 */
public class Main {
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException ex) {
        }

        SwingUtilities.invokeLater(() -> {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            LwjglCanvas glCanvas = new LwjglCanvas(new Canvas(), config);
            MainWindow mw = Ctx.window;

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            mw.setSize(
                    Math.min(1150, screenSize.width - 100),
                    Math.min(800, screenSize.height - 100)
            );

            mw.setCanvas(glCanvas.getCanvas());
            mw.setLocationRelativeTo(null);
            mw.setVisible(true);
            mw.setResizable(false);
            SwingUtilities.invokeLater(() -> {
                mw.setSize(mw.getWidth() - 1, mw.getHeight() - 1);
                mw.setResizable(true);
            });
            parseArgs(args);
        });
    }

    private static void parseArgs(String[] args) {
        for (int i = 1; i < args.length; i++) {
            if (args[i - 1].equals("-f")) {
                try {
                    File file = new File(args[i]).getCanonicalFile();
                    Ctx.io.setProjectFile(file);
                    Ctx.io.importFromFile();
                } catch (IOException | JSONException ex) {
                }
            }
        }
    }
}
