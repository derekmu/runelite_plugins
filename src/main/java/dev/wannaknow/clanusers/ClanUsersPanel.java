package dev.wannaknow.clanusers;

import lombok.Getter;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;

public class ClanUsersPanel extends PluginPanel {

    private ClanUsersPlugin plugin;

    @Getter
    private JTextArea copiedNames;

    ClanUsersPanel(ClanUsersPlugin plugin) {

        getParent().setLayout(new BorderLayout());
        getParent().add(this, BorderLayout.NORTH);

        copiedNames = new JTextArea();
        copiedNames.setEditable(false);

        JButton rankedFCbutton = new JButton("Copy ranked FC usernames");
        rankedFCbutton.addActionListener(l -> {
            plugin.copyRankedFriendsChatUsernames();
        });

        JButton allFCbutton = new JButton("Copy all FC usernames");
        allFCbutton.addActionListener(l -> {
            plugin.copyFriendsChatUsernames();
        });

        JButton onlineCCbutton = new JButton("Copy online CC usernames");
        onlineCCbutton.addActionListener(l -> {
            plugin.copyOnlineClanChatUsernames();
        });

        JButton onlineRankedCCbutton = new JButton("Copy online ranked CC usernames");
        onlineRankedCCbutton.addActionListener(l -> {
            plugin.copyOnlineRankedClanChatUsernames();
        });

        JButton allCCbutton = new JButton("Copy CC usernames");
        allCCbutton.addActionListener(l -> {
            plugin.copyClanChatUsernames();
        });

        JButton guestCCbutton = new JButton("Copy Guest usernames");
        guestCCbutton.addActionListener(l -> {
            plugin.copyGuestChatUsernames();
        });

        add(rankedFCbutton);
        add(allFCbutton);
        add(onlineCCbutton);
        add(onlineRankedCCbutton);
        add(allCCbutton);
        add(guestCCbutton);
        add(copiedNames);
    }
}
