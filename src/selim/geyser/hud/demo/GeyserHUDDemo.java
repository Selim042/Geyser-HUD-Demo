package selim.geyser.hud.demo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import selim.geyser.core.shared.EnumComponent;
import selim.geyser.core.shared.IGeyserPlugin;
import selim.geyser.hud.bukkit.GeyserHUDSpigot;
import selim.geyser.hud.bukkit.ItemStackHUDPartSpigot;
import selim.geyser.hud.bukkit.StringHUDPartSpigot;
import selim.geyser.hud.shared.IGeyserHUD;
import selim.geyser.hud.shared.IHUDPart;
import selim.geyser.hud.shared.RectangleHUDPart;
import selim.geyser.hud.shared.StringHUDPart;

public class GeyserHUDDemo extends JavaPlugin implements Listener, IGeyserPlugin {

	@Override
	public EnumComponent[] requiredComponents() {
		return new EnumComponent[] { EnumComponent.HUD };
	}

	@Override
	public boolean requiredOnClient(EnumComponent component) {
		return component == EnumComponent.HUD;
	}

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	private static int color = 0xFFFFFF;

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Plugin t = this;
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				IGeyserHUD hud = GeyserHUDSpigot.getHud(event.getPlayer());
				if (hud != null) {
					StringHUDPart stringPart = hud
							.addPart(new StringHUDPartSpigot("Balance: $10", 0, 0, color));
					IHUDPart rectangle = hud.addPart(new RectangleHUDPart(0, 0, 50, 50, 0xFFFFFF));
					hud.addPart(new ItemStackHUDPartSpigot(new ItemStack(Material.COMPASS), 0, 16));
					Bukkit.getScheduler().scheduleSyncDelayedTask(t, new Runnable() {

						@Override
						public void run() {
							hud.removePart(rectangle.getHUDId());
						}
					}, 1000);
					Bukkit.getScheduler().scheduleSyncRepeatingTask(t, new Runnable() {

						@Override
						public void run() {
							if (color <= 0x000000)
								color = 0xFFFFFF;
							color -= 0xFF8;
							stringPart.setColor(color);
							hud.update();
						}
					}, 0, 10);
				}
			}
		}, 50);
	}

}
