import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Project  {
    static double movement = 0;
    static int wavelength = 30;
    static int distance = 200; //distance between slits

    static int width = 2000;
    static int height = 550;

    static int[] leftSlitCenter = {(width - distance) / 2, height};
    static int[] rightSlitCenter = {(width - distance) / 2 + distance, height};
    static int[] leftSlit = {leftSlitCenter[0] - 5, leftSlitCenter[1] + 20};
    static int[] rightSlit = {rightSlitCenter[0] - 5, rightSlitCenter[1] + 20};
    static int slitWidth = 20;
    static int slitHeight = 50;

    static int lengthToWall = 400;
    static Color[] colors = new Color[256];

    static int pixelSize = 5;



    static int brightness = -25; //negative = darker; positive = brighter


    static int frameWidth = 2000;
    static int graphicsHeight = 500 + height + 70;
    static int frameHeight = graphicsHeight + 280;

    public static void main(String[] args) {
        colors = Utils.createColors(brightness);

        JFrame frame = new JFrame("Double Slit Experiment Simulation");
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);

        FlowLayout frameLayout = new FlowLayout();
        frameLayout.setVgap(0);
        frame.setLayout(frameLayout);

        //graph of intensity vs location
        Graph graph = new Graph();
        frame.add(graph);
        // simulation graphics
        Simulation graphics = new Simulation();
        frame.add(graphics);

        //sliders
        GridLayout slidersLayout = new GridLayout(2, 6);
        slidersLayout.setHgap(25);
        JPanel sliders = new JPanel(slidersLayout);
        sliders.setOpaque(true);
        sliders.setPreferredSize(new Dimension(width, 150));

        //TEXT
        sliders.add(Utils.newFiller());

        JLabel brightnessTxt = Utils.newLabel("<html><body><p style=\"text-align:center;\">" +
                "Brightness: " + (brightness + 25) +
                "<br>(tip: lower the brightness to see interference patterns better)" +
                "</p></body></html>");
        sliders.add(brightnessTxt);

        JLabel distanceTxt = Utils.newLabel("Distance between slits: " + distance);
        sliders.add(distanceTxt);

        JLabel lengthToWallTxt = Utils.newLabel("Distance to the screen: " + lengthToWall);
        sliders.add(lengthToWallTxt);

        JLabel wavelengthTxt = Utils.newLabel("Wavelength: " + wavelength);
        sliders.add(wavelengthTxt);

        sliders.add(Utils.newFiller());


        //SLIDERS
        sliders.add(Utils.newFiller());

        //brightness
        JSlider brightnessSlider = Utils.newSlider(0, -25, 25, 10, 5, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                brightness = -25 + slider.getValue();
                colors = Utils.createColors(brightness);
                brightnessTxt.setText("<html><body><p style=\"text-align:center;\">" +
                        "Brightness: " + (brightness + 25) +
                        "<br>(tip: lower the brightness to see interference patterns better)" +
                        "</p></body></html>");

            }
        });
        sliders.add(brightnessSlider);


        //distance between slits
        JSlider distanceSlider = Utils.newSlider(distance, 100, 300, 50, 10, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                distance = slider.getValue();
                distanceTxt.setText("Distance between slits: " + distance);
                leftSlitCenter = new int[]{(width - distance) / 2, height};
                rightSlitCenter = new int[]{(width - distance) / 2 + distance, height};
                leftSlit = new int[]{leftSlitCenter[0] - 5, leftSlitCenter[1] + 20};
                rightSlit = new int[]{rightSlitCenter[0] - 5, rightSlitCenter[1] + 20};
            }
        });
        sliders.add(distanceSlider);

        //length to wall
        JSlider lengthToWallSlider = Utils.newSlider(lengthToWall, 100, 600, 100, 50, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                lengthToWall = slider.getValue();
                lengthToWallTxt.setText("Distance to the screen: " + lengthToWall);
            }
        });
        sliders.add(lengthToWallSlider);

        //wavelength
        JSlider wavelengthSlider = Utils.newSlider(wavelength, 0, 100, 10, 5, new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                wavelength = slider.getValue();
                wavelengthTxt.setText("Wavelength: " + wavelength);
            }
        });
        sliders.add(wavelengthSlider);

        int sliderGap = 15; //gap between graphic and sliders

        frame.add(Utils.newWhiteSpace(width, sliderGap));
        frame.add(sliders);
        frame.add(Utils.newWhiteSpace(width, frameHeight - graphicsHeight - sliders.getPreferredSize().height - sliderGap));

        frame.pack();
        frame.setVisible(true);
    }


    public static int getWavelength() {
        return wavelength;
    }

    public static int getDistance() {
        return distance;
    }

    public static int getWidth() {
        return width;
    }

    public static int getLengthToWall() {
        return lengthToWall;
    }

    public static int getPixelSize() {
        return pixelSize;
    }

}

//EQUATIONS
//From: getSlitColorLevel
// Wave Equation (reduced): y(x) = A * cos(pi * x/lambda)

//variables:
// y = vertical height; x = horizontal pos; A = amplitude; lambda = wavelength



//From: drawGraph
// Intensity = max intensity * cos^2(beta) * (sin(alpha)/alpha)^2

//variables:
// if screen is placed far enough from the slits:
// alpha = (pi * a * y)/(lambda * L)
// beta = (pi * d * y)/(lambda * L)

// a = size of slit
// d = distance between slits
// lambda = wavelength
// theta = angle along a line perpendicular to screen
// y = distance along the screen (middle = 0)
// L = length to screen