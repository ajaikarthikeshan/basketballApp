package ui;

import model.Player;
import model.Team;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Creates a window for viewing the list of players in a team. Provides options to view individual player stats
 * and navigate back to the home window.
 */
public class ViewPlayerListWindow extends JFrame {
    private Team team;
    private JTextArea playerListArea;
    private JButton viewStatsButton;
    private JButton backButton;
    private JScrollPane scrollPane;

    public ViewPlayerListWindow(Team team) {
        this.team = team;

        setTitle("View Player List");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addEventListeners();

        setVisible(true);
    }

    // Modifies: playerListArea, scrollPane
    // Effects: Initializes the GUI components for displaying player list and buttons
    private void initializeComponents() {
        playerListArea = new JTextArea();
        scrollPane = new JScrollPane(playerListArea);
        JPanel buttonPanel = new JPanel();
        viewStatsButton = new JButton("View Player Stats");
        backButton = new JButton("Back to Home");
        buttonPanel.add(viewStatsButton);
        buttonPanel.add(backButton);
    }

    // Effects: Sets up the layout of the player list table and buttons
    private void layoutComponents() {
        setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Jersey No.");
        ArrayList<Player> players = team.getPlayers();
        for (Player player : players) {
            model.addRow(new Object[]{player.getName(), player.getPosition(), player.getJerseyNumber()});
        }

        JTable playerTable = new JTable(model);
        playerTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(playerTable);

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewStatsButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addEventListeners() {
        viewStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPlayerStats();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeWindow(team);
            }
        });
    }

    // Effects: Responds to the viewStatsButton click event by displaying player stats dialog
    private void viewPlayerStats() {
        String input = JOptionPane.showInputDialog(this, "Enter player jersey number to view stats:");
        if (input != null && !input.isEmpty()) {
            int jerseyNumber = Integer.parseInt(input);
            Player player = team.findPlayerByJerseyNumber(jerseyNumber);
            if (player != null) {
                displayPlayerStats(player);
            } else {
                JOptionPane.showMessageDialog(this, "Player not found.");
            }
        }
    }

    // EFFECTS: Helps display the results
    private void displayPlayerStats(Player player) {
        StringBuilder statsMessage = new StringBuilder();
        statsMessage.append("Player Stats:\n");
        statsMessage.append("Name: ").append(player.getName()).append("\n");
        statsMessage.append("Points: ").append(player.getStats().getPoints()).append("\n");
        statsMessage.append("Rebounds: ").append(player.getStats().getRebounds()).append("\n");
        statsMessage.append("Assists: ").append(player.getStats().getAssists()).append("\n");
        statsMessage.append("Blocks: ").append(player.getStats().getBlocks()).append("\n");

        JOptionPane.showMessageDialog(this, statsMessage.toString());
    }
}
