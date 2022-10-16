import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graph extends JComponent implements ActionListener {


    Timer t = new Timer(200, this);

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    Graph() {

        setPreferredSize(new Dimension(Project.getWidth(), 500));
        setLocation(0, 50);
        setLayout(null);


        Font labelFont = new Font("Tahoma", Font.PLAIN, 50);

        JLabel yAxis = new JLabel("Intensity");
        yAxis.setFont(labelFont);
        yAxis.setForeground(Color.WHITE);
        Dimension yAxisSize = yAxis.getPreferredSize();
        yAxis.setBounds(graphX - gap - yAxisSize.width, graphHeight/2+50 - (yAxisSize.height)/2, yAxisSize.width, yAxisSize.height);
        add(yAxis);

        JLabel xAxis = new JLabel("Location");
        xAxis.setFont(labelFont);
        xAxis.setForeground(Color.WHITE);
        Dimension xAxisSize = xAxis.getPreferredSize();
        xAxis.setBounds(graphX + 15 + graphHeight - (xAxisSize.width/2), graphHeight + 50 + gap, xAxisSize.width, xAxisSize.height);
        add(xAxis);
    }

    static int graphHeight = 300;
    //graph width = 2*graphHeight
    int graphX = Project.getWidth()/2-graphHeight-25;
    static int relativeGraphX = 77;
    static int gap = 20;

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        //x axis
        g.fillRect(graphX, 50+graphHeight, 2*graphHeight + 15, 10);
        //y axis
        g.fillRect(graphX, 50, 10, graphHeight);

        //graph
        drawGraph(g);
        t.start();
    }

    public void drawGraph(java.awt.Graphics g) {
        double maxIntensity = graphHeight;

        g.setColor(Color.RED);

        for (double y = -graphHeight-0.1; y <= graphHeight-0.1; y+= 0.2) { //to avoid y=0
            // Intensity = max intensity * cos^2(beta) * (sin(alpha)/alpha)^2
            //alpha = (pi * a * y)/(lambda * L)
            //beta = (pi * d * y)/(lambda * L)

            double alpha = (Math.PI * 0.2 * y*500.0)/(Project.getWavelength() * Project.getLengthToWall());
            double beta = (Math.PI * Project.getDistance() * y)/(Project.getWavelength() * Project.getLengthToWall());

            //                                  interference pattern            diffraction
            double intensity = maxIntensity * Math.pow(Math.cos(beta), 2) * Math.pow((Math.sin(alpha)/alpha), 2);

            g.fillRect((int) (y+0.1+graphX+10+graphHeight),50+graphHeight - (int) intensity - Project.pixelSize, Project.getPixelSize(), Project.getPixelSize());
        }

    }
}