package res;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {
    public static BufferedImage[] letters;

    static {
        letters = new BufferedImage[2];
        letters[0] = loadImage("/home/dzierzen/MEGA/Nauka/Studia/3SEM/SKJ/TrzecieZadanie/TicTacToe/src/files/x.png");
        letters[1] = loadImage("/home/dzierzen/MEGA/Nauka/Studia/3SEM/SKJ/TrzecieZadanie/TicTacToe/src/files/o.png");
    }

    private static BufferedImage loadImage (String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
