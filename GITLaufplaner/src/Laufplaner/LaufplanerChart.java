package Laufplaner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LaufplanerChart extends JFrame {
    private static final String DB_URL = "jdbc:ucanaccess://C:\\Users\\Ann Manegold\\git\\laufplanerGFS\\GFS\\LaufplanerDatabase.accdb";
    
    public LaufplanerChart() {
        super("Laufplaner - Letzte Läufe");

        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT TOP 10 IdLauf, Kilometer, Zeit, Datum FROM Eintraege ORDER BY IdLauf DESC";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("IdLauf");
                double km = result.getDouble("Kilometer");
                double zeit = result.getDouble("Zeit");
                Date datum = result.getDate("Datum");
                double pace = zeit / km;

                dataset.addValue(pace, "Pace (min/km)", datum.toLocalDate().toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Letzte Läufe", "Datum", "Pace (min/km)", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        chart.setBackgroundPaint(Color.WHITE);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);

        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			    LaufplanerChart chart = new LaufplanerChart();
			    chart.setVisible(true);
			}
		});
    }
}