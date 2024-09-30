package main;

import Inputs.KeyBoardInputs;
import Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage[] idleAnimation;
    private int animationIndex = 0; // Controla o quadro atual da animação
    private int animationSpeed = 200; // Tempo em milissegundos para mudar de quadro
    private Timer timer;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importMainCharacterIdle();
        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        // Inicializa o Timer para alternar a imagem em intervals
        timer = new Timer(animationSpeed, e -> updateAnimation());
        timer.start(); // Inicia o Timer para atualizar a animação
    }

    private void importMainCharacterIdle() {
        idleAnimation = new BufferedImage[4];
        for (int i = 0; i < idleAnimation.length; i++) {

            InputStream is = getClass().getResourceAsStream("/Main_Character0" + i + ".png");
            if (is == null) {
                System.err.println("Imagem não encontrada: /Main_Character0" + i + ".png");
                continue;
            }
            try {
                idleAnimation[i] = ImageIO.read(is); // Salva a imagem no array
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    public void setRectPosition(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    // Atualiza o índice da animação para alternar as imagens
    private void updateAnimation() {
        animationIndex = (animationIndex + 1) % idleAnimation.length; // Incrementa o índice e volta para 0 quando chegar ao fim
        repaint(); // Redesenha o painel para mostrar a nova imagem
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha a imagem atual da animação no painel
        BufferedImage currentImage = idleAnimation[animationIndex];
        if (currentImage != null) {
            g.drawImage(currentImage, (int) xDelta, (int) yDelta, 128, 80, null); // Desenha a imagem atual
        }
    }
}
