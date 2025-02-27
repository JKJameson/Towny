package com.palmergames.bukkit.towny;

import com.palmergames.bukkit.towny.tasks.UpdateCheckerTask;
import com.palmergames.bukkit.util.Version;
import org.bukkit.Bukkit;

public class TownyUpdateChecker {
	private static boolean update = false;
	private static boolean checkedSuccessfully = false;
	private static Version newVersion;
	
	public static void checkForUpdates(Towny towny) {
		Bukkit.getScheduler().runTaskAsynchronously(towny, new UpdateCheckerTask(towny));
	}

	public static void setUpdate(boolean update) {
		TownyUpdateChecker.update = update;
	}

	public static boolean hasUpdate() {
		return update;
	}

	public static void setNewVersion(Version newVersion) {
		TownyUpdateChecker.newVersion = newVersion;
	}

	public static Version getNewVersion() {
		return newVersion;
	}
	
	public static String getUpdateURL() {
		if (newVersion == null)
			return "";
		
		return "https://github.com/TownyAdvanced/Towny/releases/tag/" + newVersion;
	}

	public static void setCheckedSuccessfully(boolean checkedSuccessfully) {
		TownyUpdateChecker.checkedSuccessfully = checkedSuccessfully;
	}

	public static boolean hasCheckedSuccessfully() {
		return checkedSuccessfully;
	}
	
	public static boolean shouldShowNotification() {
		if (!TownySettings.isShowingUpdateNotifications() || !TownyUpdateChecker.hasUpdate())
			return false;
		
		Version currentVersion = Version.fromString(Towny.getPlugin().getVersion());
		if (currentVersion.isPreRelease())
			return true;

		if (TownySettings.isUpdateNotificationsMajorOnly()) {
			return !newVersion.isPreRelease();
		}
		
		return true;
	}
}
