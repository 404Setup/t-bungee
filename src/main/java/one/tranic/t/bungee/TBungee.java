package one.tranic.t.bungee;

import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import one.tranic.t.base.TInterface;
import one.tranic.t.base.command.source.SystemCommandSource;
import one.tranic.t.base.player.Player;
import one.tranic.t.bungee.command.source.BungeeConsoleSource;
import one.tranic.t.bungee.player.BungeePlayer;
import one.tranic.t.utils.Collections;
import one.tranic.t.utils.minecraft.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Deprecated
@SuppressWarnings("unused")
public class TBungee implements TInterface<CommandSender, ProxiedPlayer> {
    private static boolean initialized = false;
    private static BungeeAudiences adventure;
    private final Plugin plugin;
    private final Platform[] supportedPlatforms = new Platform[]{Platform.BungeeCord};

    public TBungee(Plugin plugin) {
        this.plugin = plugin;
        enable();
    }

    public static @NotNull BungeeAudiences adventure() {
        if (adventure == null)
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        return adventure;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static @NotNull ProxyServer getServer() {
        return ProxyServer.getInstance();
    }

    @Override
    public void enable() {
        if (initialized) return;
        adventure = BungeeAudiences.create(plugin);
        initialized = true;
    }

    @Override
    public void disable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        initialized = false;
    }

    @Override
    public Platform[] getSupportedPlatforms() {
        return supportedPlatforms;
    }

    @Override
    public SystemCommandSource<CommandSender, ProxiedPlayer> getConsoleSource() {
        return new BungeeConsoleSource();
    }

    @Override
    public @Nullable Player<ProxiedPlayer> getPlayer(@NotNull String name) {
        return BungeePlayer.createPlayer(name);
    }

    @Override
    public @Nullable Player<ProxiedPlayer> getPlayer(@NotNull UUID uuid) {
        return BungeePlayer.createPlayer(uuid);
    }

    @Override
    public @NotNull List<Player<ProxiedPlayer>> getOnlinePlayers() {
        final List<Player<ProxiedPlayer>> players = Collections.newArrayList();

        for (var p : TBungee.getServer().getPlayers())
            players.add(BungeePlayer.createPlayer(p));
        return players;
    }

    @Override
    public @NotNull List<ProxiedPlayer> getPlatformOnlinePlayers() {
        return Collections.newArrayList(TBungee.getServer().getPlayers());
    }

    @Override
    public @NotNull List<String> getOnlinePlayersName() {
        final List<String> players = Collections.newArrayList();
        for (var p : TBungee.getServer().getPlayers()) players.add(p.getName());
        return players;
    }
}
