import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulation extends JComponent implements ActionListener {

    Timer t = new Timer(200, this);

    public Simulation() {
        setPreferredSize(new Dimension(Project.width, Project.height + 100));

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, Project.height, Project.width, Project.slitHeight + 50);
        //slits
        g.setColor(Color.WHITE);
        g.fillRect(Project.leftSlit[0], Project.leftSlit[1], Project.slitWidth, Project.slitHeight);
        g.fillRect(Project.rightSlit[0], Project.rightSlit[1], Project.slitWidth, Project.slitHeight);
        //wall
        g.setColor(Color.GRAY);
        g.fillRect(0, Project.leftSlitCenter[1] + 1, Project.width, 10);


        Project.movement -= 0.5;

        //interference pattern
        drawPattern(g);
        t.start();
    }

    public void drawPattern(java.awt.Graphics g) {
        for (var x = 0; x <= Project.width; x += Project.pixelSize) {
            for (var y = 0; y <= Project.height; y += Project.pixelSize) {
                g.setColor(getColor(x, y));
                g.fillRect(x, y, Project.pixelSize, Project.pixelSize);
            }
        }
    }

    public Color getColor(int x, int y) {
        //0 = darkest, 1 = lightest
        double leftColorLevel = getSlitColorLevel(x, y, Project.leftSlitCenter);
        double rightColorLevel = getSlitColorLevel(x, y, Project.rightSlitCenter);

        double colorLevel = (leftColorLevel + rightColorLevel) / 2;

        Color color = getGradientColor(colorLevel);
        return color;
    }

    public Color getGradientColor(double colorLevel) {
        return Project.colors[Project.colors.length - (int) Math.floor(colorLevel * Project.colors.length) - 1];
    }

    public double getSlitColorLevel(int x, int y, int[] slitCoords) {
        int slitX = slitCoords[0];
        int slitY = slitCoords[1];
        double r = getDistance(x, y, slitX, slitY);
        double theta = getTheta(x, y, slitX, slitY);
        //0 = darkest, 1 = lightest
        //factors wave pattern
        //Wave Equation (reduced): y(x) = A * cos(pi * x/lambda)
        double waveLevel = (Math.cos(Math.PI * r / Project.wavelength + Project.movement) + 1) / 2;
        //greatest intensity in front of slit
        double arcLevel = Math.sin(theta);
        //intensity dies off 1/r^2
        double distanceLevel = Math.sqrt(Math.max(0, Project.lengthToWall - r) / Project.lengthToWall);
        double colorLevel = Math.abs(waveLevel * arcLevel * distanceLevel);
        return colorLevel;
    }

    public double getDistance(int x1, int y1, int x2, int y2) {
        double xDist = x1 - x2;
        double yDist = y1 - y2;
        return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
    }

    public double getTheta(int x, int y, int x2, int y2) {
        double xDist = x - x2;
        double r = getDistance(x, y, x2, y2);
        return Math.abs(Math.acos(xDist / r));
    }

}