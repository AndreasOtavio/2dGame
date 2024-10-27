package main;

import Inputs.KeyBoardInputs;
import Inputs.MouseInputs;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage[] animations;
    private int animationIndex = 0;
    private int animationSpeed = 200;
    private int runningAnimationSpeed = 50;    // Velocidade de animação quando o personagem está correndo

    private Timer timer;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        loadAnimations();
        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        timer = new Timer(animationSpeed, e -> updateAnimation());
        timer.start();
    }

    private void loadAnimations() {
        String folderPath = "/" + playerAction;
        try {
            // Obtém o URL do recurso da pasta de imagens
            var url = getClass().getResource(folderPath);
            if (url == null) {
                System.err.println("Pasta não encontrada: " + folderPath);
                return;
            }

            // Converte o URL em um Path se possível
            File folder = new File(url.toURI());
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".png"));

            if (files == null || files.length == 0) {
                System.err.println("Nenhuma imagem encontrada na pasta: " + folderPath);
                return;
            }

            // Redimensiona o array `animations` com base na quantidade de arquivos PNG na pasta
            animations = new BufferedImage[files.length];

            for (int i = 0; i < files.length; i++) {
                try (InputStream is = getClass().getResourceAsStream(folderPath + "/CharacterAction0" + i + ".png")) {
                    if (is == null) {
                        System.err.println("Imagem não encontrada: " + folderPath + "/CharacterAction0" + i + ".png");
                        continue;
                    }
                    animations[i] = ImageIO.read(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void setAnimation() {
        int newAction = moving ? RUNNING : IDLE;
        if (playerAction != newAction) {
            playerAction = newAction;
            loadAnimations();
            animationIndex = 0;

            // Define a velocidade do timer de acordo com a nova ação
            if (playerAction == RUNNING) {
                timer.setDelay(runningAnimationSpeed);
            } else {
                timer.setDelay(animationSpeed);
            }
        }
    }

    private void updatePosition() {
        if (moving){
            switch (playerDirection){
                case LEFT:
                    xDelta -= 3.5F;
                    animationSpeed = 600;
                    break;
                case UP:
                    yDelta -= 3.5F;
                    break;
                case RIGHT:
                    xDelta += 3.5F;
                    animationSpeed = 600;
                    break;
                case DOWN:
                    yDelta += 3.5F;
                    break;
            }
        }
    }

    private void updateAnimation() {
        animationIndex = (animationIndex + 1) % animations.length;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setAnimation(); // Verifica se precisa mudar a animação
        updatePosition();

        BufferedImage currentImage = animations[animationIndex];
        if (currentImage != null) {
            g.drawImage(currentImage, (int) xDelta, (int) yDelta, 256, 160, null);
        }
    }



}
