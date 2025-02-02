import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class AWT {
    private TextField tf;
    //private TextArea ta;

    AWT() {
            Frame f = new Frame("Weather App");
        Label t = new Label("WEATHER APP");
        Label l=new Label("City:");
        TextField tf=new TextField();
        Button b1 = new Button("check");
        JTextArea ta = new JTextArea();



        // Set fonts
        Font labelFont = new Font("Arial", Font.BOLD, 30);
        Font labelF = new Font("Arial", Font.BOLD, 24);
        Font textFieldFont = new Font("Abadi", Font.BOLD, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Font textAreaFont = new Font("Abadi", Font.BOLD, 17);

        t.setFont(labelFont);
        l.setFont(labelF);
        tf.setFont(textFieldFont);
        b1.setFont(buttonFont);
        ta.setFont(textAreaFont);

        // Highlight button
        b1.setBackground(Color.ORANGE);
        b1.setForeground(Color.BLACK);

        // Customize text area output
        Color lightBlue = new Color(173, 216, 200);
        ta.setBackground(lightBlue);
        ta.setForeground(Color.BLACK);
        ta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        f.setBackground(new Color(240, 248, 255)); // AliceBlue

        // Customize label colors
        t.setForeground(new Color(0, 128, 128)); // Teal
        l.setForeground(new Color(0, 128, 128)); // Teal

        // Customize text field background
        tf.setBackground(new Color(255, 255, 224)); // LightYellow
        tf.setForeground(Color.BLACK);

        f.setLayout(null);
        f.add(t);
        f.add(l);
        f.add(tf);
        f.add(b1);
        f.add(ta);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(250, 280, 385, 300);
        panel.add(ta);
        ta.setBounds(0, 0, 385, 300);
        f.add(panel);

        t.setBounds(325, 80, 250, 50); // Centered title
        l.setBounds(250, 170, 100, 30); // Label for city name
        tf.setBounds(360, 170, 300, 30); // Text field for city name
        b1.setBounds(350, 220, 150, 40); // Button to check weather
       // ta.setBounds(250, 280, 385, 300);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String city = tf.getText();
                String s = weather(city);
                ta.setText(s);
            }
        });

        f.setSize(900, 900);
        f.setVisible(true);
    }

    public String weather(String city) {
        String ans = "";
        try {
            URL url = new URL("http://api.weatherapi.com/v1/current.json?key=7645bfae968843de9d074314252801&q=" + city + "&aqi=yes");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader inp = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String in;
            StringBuilder response = new StringBuilder();
            while ((in = inp.readLine()) != null) {
                response.append(in);
            }
            inp.close();
            String json = jsonf(response.toString());
            ans = data(json);
        } catch (Exception e) {
            ans = "Incorrect city name";
        }
        return ans;
    }

    public static String jsonf(String j) {
        StringBuilder sb = new StringBuilder();
        int sp = 0;
        for (int i = 0; i < j.length(); i++) {
            char c = j.charAt(i);
            if (c == '{') {
                sb.append("\n").append(" ".repeat(sp)).append(c).append("\n");
                sp += 3;
                sb.append(" ".repeat(sp));
            } else if (c == '}') {
                sp -= 3;
                sb.append("\n").append(" ".repeat(sp)).append(c);
            } else if (c == ',') {
                sb.append(c).append("\n").append(" ".repeat(sp));
            } else sb.append(c);
        }
        return sb.toString();
    }

    public static String data(String js) {
        int h = 0, s = 0, l = 0;
        StringBuilder bd = new StringBuilder("\n-----------------Basic Data---------------------------\n\n");
        h = js.indexOf("\"name\"");
        s = js.indexOf(":", h) + 1;
        l = (js.indexOf(",", h) < js.indexOf("\n", h)) ? js.indexOf(",", h) : js.indexOf("\n", h);
        bd.append(" City: ").append(js.substring(s, l)).append("\n");

        h = js.indexOf("\"region\"");
        s = js.indexOf(":", h) + 1;
        l = (js.indexOf(",", h) < js.indexOf("\n", h)) ? js.indexOf(",", h) : js.indexOf("\n", h);
        bd.append(" Region: ").append(js.substring(s, l)).append("\n");

        h = js.indexOf("\"localtime\"");
        s = js.indexOf(":", h) + 1;
        l = (js.indexOf(",", h) < js.indexOf("\n", h)) ? js.indexOf(",", h) : js.indexOf("\n", h);
        bd.append(" Local Time: ").append(js.substring(s, l)).append("\n");

        bd.append("\n---------------Weather Details------------------------\n\n");
        h = js.indexOf("\"temp_c\"");
        s = js.indexOf(":", h) + 1;
        l = (js.indexOf(",", h) < js.indexOf("\n", h)) ? js.indexOf(",", h) : js.indexOf("\n", h);
        bd.append(" Temperature: ").append(js.substring(s, l)).append(" 'C\n");

        h = js.indexOf("\"humidity\"");
        s = js.indexOf(":", h) + 1;
        l = (js.indexOf(",", h) < js.indexOf("\n", h)) ? js.indexOf(",", h) : js.indexOf("\n", h);
        bd.append(" Humidity: ").append(js.substring(s, l)).append("\n");

        h = js.indexOf("\"heatindex_c\"");
        s = js.indexOf(":", h) + 1;
        l = (js.indexOf(",", h) < js.indexOf("\n", h)) ? js.indexOf(",", h) : js.indexOf("\n", h);
        bd.append(" Heat Index: ").append(js.substring(s, l)).append(" 'C\n");

        h = js.indexOf("\"wind_kph\"");
        s = js.indexOf(":", h) + 1;
        l = (js.indexOf(",", h) < js.indexOf("\n", h)) ? js.indexOf(",", h) : js.indexOf("\n", h);
        bd.append(" Wind Speed: ").append(js.substring(s, l)).append(" kph\n");

        return bd.toString();
    }

    public static void main(String[] args) {
        new AWT();
    }
}