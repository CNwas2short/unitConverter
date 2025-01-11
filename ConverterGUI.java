import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConverterGUI extends JFrame implements ActionListener {

    private JLabel valueLabel, fromValue, toValue, resultLabel;
    private JTextField text;
    private JComboBox<String> fromBox, toBox;
    private JButton button;

    private static final String[] options = { "Inches", "Feet", "Yard", "Centimeters", "Meters", "Miles",
            "Kilometers" };

    private static final double[] measurements = {
            1, // Inches
            12, // Feet
            36, // Yards
            2.54, // Centimeters
            39.37, // Meters
            63360, // Miles (1 mile = 63,360 inches)
            39370.1 // Kilometers (1 kilometer = 39,370.1 inches)
    };

    ConverterGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setTitle("Unit Converter");

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        // first row, label and textbox, gets value
        valueLabel = new JLabel("Value:  ");
        c.gridx = 0;
        c.gridy = 0;
        this.add(valueLabel, c);
        text = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        this.add(text, c);

        // second row, label and combobox, "from"
        fromValue = new JLabel("From:    ");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        this.add(fromValue, c);
        fromBox = new JComboBox<>(options);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        this.add(fromBox, c);

        // third row, label and combobox, "to"
        toValue = new JLabel("To:    ");
        c.gridx = 0;
        c.gridy = 2;
        this.add(toValue, c);
        toBox = new JComboBox<>(options);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        this.add(toBox, c);

        // fourth row, button, converts value
        button = new JButton("Convert");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        this.add(button, c);

        // result
        resultLabel = new JLabel("");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        this.add(resultLabel, c);

        button.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double value = Double.parseDouble(text.getText());
            String from = (String) fromBox.getSelectedItem();
            String to = (String) toBox.getSelectedItem();

            solver(value, from, to);

        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Try again");
        }
    }

    public void solver(double value, String from, String to) {
        int fromIndex = findUnitIndex(from);
        int toIndex = findUnitIndex(to);

        if (fromIndex == -1 || toIndex == -1) {
            resultLabel.setText("Invalid unit. Try again");
        } else if (from.equals(to)) {
            resultLabel.setText("Units are the same. No conversion needed.");
        } else {
            double valueInInches = value * measurements[fromIndex];
            double answer = valueInInches / measurements[toIndex];
            resultLabel.setText(answer + " " + to);
        }
    }

    public int findUnitIndex(String unit) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(unit)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        new ConverterGUI();
    }

}
