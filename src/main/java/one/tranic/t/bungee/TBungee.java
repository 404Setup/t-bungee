package one.tranic.t.bungee;

import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import one.tranic.t.base.TBase;
import one.tranic.t.base.command.source.CommandSource;
import one.tranic.t.base.command.source.SystemCommandSource;
import one.tranic.t.base.player.Player;
import one.tranic.t.base.player.Players;
import one.tranic.t.bungee.command.source.BungeeConsoleSource;
import one.tranic.t.bungee.player.BungeePlayer;
import one.tranic.t.bungee.player.BungeePlayers;
import one.tranic.t.utils.Reflect;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Deprecated
@SuppressWarnings("unused")
public class TBungee {
    private static boolean initialized = false;
    private static BungeeAudiences adventure;

    public static @NotNull BungeeAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }

    public static void init(Plugin plugin) {
        if (initialized) return;
        adventure = BungeeAudiences.create(plugin);
        try {
            Reflect.assignToStaticFieldIfUninitialized(TBase.class, "getConsoleSourceSupplier", (Supplier<CommandSource<?, ?>>) TBungee::getBungeeConsoleSource);
            Reflect.assignToStaticFieldIfUninitialized(Players.class, "getPlayerWithStringMethod", (Function<String, Player<?>>) BungeePlayer::createPlayer);
            Reflect.assignToStaticFieldIfUninitialized(Players.class, "getPlayerWithUUIDMethod", (Function<UUID, Player<?>>) BungeePlayer::createPlayer);
            Reflect.assignToStaticFieldIfUninitialized(Players.class, "getOnlinePlayersMethod", (Supplier<List<Player<?>>>) BungeePlayers::getOnlinePlayers);
            Reflect.assignToStaticFieldIfUninitialized(Players.class, "getPlatformOnlinePlayersMethod", (Supplier<List<?>>) BungeePlayers::getPlatformOnlinePlayers);
            Reflect.assignToStaticFieldIfUninitialized(Players.class, "getOnlinePlayersNameMethod", (Supplier<List<String>>) BungeePlayers::getOnlinePlayersName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        initialized = true;
    }

    public static void disable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        initialized = false;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static @NotNull ProxyServer getServer() {
        return ProxyServer.getInstance();
    }

    public static SystemCommandSource<?, ?> getBungeeConsoleSource() {
        return new BungeeConsoleSource();
    }
}
