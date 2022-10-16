import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Utils {

    
    public static JLabel newFiller() {
        return new JLabel("");
    }

    public static JPanel newWhiteSpace(int width, int height) {
        JPanel whiteSpace = new JPanel(new FlowLayout());
        whiteSpace.setOpaque(true);
        whiteSpace.setPreferredSize(new Dimension(width, height));

        return whiteSpace;
    }

    public static JLabel newLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 15));

        return label;
    }

    public static JSlider newSlider(int value, int min, int max, int majorSpacing, int minorSpacing, ChangeListener changeListener) {
        JSlider slider = new JSlider();
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMinimum(min);
        slider.setMaximum(max);
        slider.setValue(value);
        slider.setMajorTickSpacing(majorSpacing);
        slider.setMinorTickSpacing(minorSpacing);

        slider.addChangeListener(changeListener);

        return slider;
    }

    // generate gray gradient hex values
    public static Color[] createColors(int offset) {
        Color[] colors = new Color[256];
        //lightest to darkest
        char[] hexValues = {'f', 'e', 'd', 'c', 'b', 'a', '9', '8', '7', '6', '5', '4', '3', '2', '1', '0'};

        //second half from offset is black
        for (int i = colors.length - 1; i >= colors.length - 1 + offset; i--) {
            colors[i] = Color.decode("#000000");
        }

        int index = colors.length - 1 + offset;
        for (int i = hexValues.length - 1; i >= 0; i--) {
            char hexValue1 = hexValues[i];
            for (int j = hexValues.length - 1; j >= 0; j--) {
                char hexValue2 = hexValues[j];
                String color = "#" + hexValue1 + hexValue2 + hexValue1 + hexValue2 + hexValue1 + hexValue2;
                if (index < 0) {
                    return colors;
                }
                colors[index] = Color.decode(color);
                index--;
            }
        }
        return colors;

    }

}
