package dev.wannaknow.clanusers;

import javax.inject.Inject;
import net.runelite.api.*;
import net.runelite.api.clan.*;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;

@PluginDescriptor(
		name = "Copy FC/CC users to clipboard",
		description = "Copy FriendsChat/ClanChat users to clipboard",
		tags = {"clan", "friends", "clipboard", "names", "log"}
)
public class ClanUsersPlugin extends Plugin
{

	private static final String PLUGIN_NAME = "Copy clan usernames";
	private static final String ICON_FILE = "users.png";

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	private ClanUsersPanel panel;

	private NavigationButton navButton;

	@Override
	protected void startUp() throws Exception {
		panel = new ClanUsersPanel(this);
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), ICON_FILE);

		navButton = NavigationButton.builder()
				.tooltip(PLUGIN_NAME)
				.icon(icon)
				.priority(6)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception {
		clientToolbar.removeNavigation(navButton);
	}

	public void copyRankedFriendsChatUsernames() {
		final FriendsChatManager friendsChatManager = client.getFriendsChatManager();

		if(friendsChatManager != null) {
			String names = "";
			for(FriendsChatMember member : friendsChatManager.getMembers()) {
				if(member.getRank() != FriendsChatRank.UNRANKED) {
					//log.info(member.getName());
					names += member.getName()+"\n";
				}
			}
			copyStringToClipboard(names);
		} else {
			panel.getCopiedNames().setText("Not in a FriendsChat.");
		}
	}

	public void copyFriendsChatUsernames() {
		final FriendsChatManager friendsChatManager = client.getFriendsChatManager();

		if(friendsChatManager != null) {
			String names = "";
			for(FriendsChatMember member : friendsChatManager.getMembers()) {
				names += member.getName()+"\n";
			}
			copyStringToClipboard(names);
		}
	}

	public void copyOnlineClanChatUsernames() {
		final ClanChannel clanChannel = client.getClanChannel();

		if(clanChannel != null) {
			String names = "";
			for(ClanChannelMember member : clanChannel.getMembers()) {
				names += member.getName() + "\n";
			}
			copyStringToClipboard(names);
		}else {
			panel.getCopiedNames().setText("Not in a ClanChat.");
		}
	}

	public void copyOnlineRankedClanChatUsernames() {
		final ClanChannel clanChannel = client.getClanChannel();

		if(clanChannel != null) {
			String names = "";
			for(ClanChannelMember member : clanChannel.getMembers()) {

				if(member.getRank() != ClanRank.GUEST) {
					names += member.getName() + "\n";
				}
			}
			copyStringToClipboard(names);
		}else {
			panel.getCopiedNames().setText("Not in a ClanChat.");
		}
	}

	public void copyClanChatUsernames() {
		final ClanSettings clanChannel = client.getClanSettings();

		if(clanChannel != null) {
			String names = "";
			for(ClanMember member : clanChannel.getMembers()) {
				names += member.getName()+"\n";
			}
			copyStringToClipboard(names);
		}else {
			panel.getCopiedNames().setText("Not in a ClanChat.");
		}
	}

	public void copyGuestChatUsernames() {
		final ClanSettings clanChannel = client.getGuestClanSettings();

		if(clanChannel != null) {
			String names = "";
			for(ClanMember member : clanChannel.getMembers()) {
				names += member.getName()+"\n";
			}
			copyStringToClipboard(names);
		}else {
			panel.getCopiedNames().setText("Not in a ClanChat.");
		}
	}

	private void copyStringToClipboard(String copy) {
		if(StringUtils.isBlank((copy))) {
			panel.getCopiedNames().setText("No names found.");
		} else {
			panel.getCopiedNames().setText(copy);
			StringSelection stringSelection = new StringSelection(copy);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}
	}
}
