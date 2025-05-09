package one.tranic.t.bungee.command.source;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import one.tranic.t.base.command.source.SystemCommandSource;
import one.tranic.t.bungee.TBungee;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class BungeeConsoleSource extends SystemCommandSource<CommandSender, ProxiedPlayer> {
    @Override
    public void broadcastMessage(@NotNull Component message) {
        TBungee.getServer().broadcast(net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().serialize(message));
    }

    @Override
    public void broadcastMessage(@NotNull String message) {
        TBungee.getServer().broadcast(message);
    }

    @Override
    public CommandSender getSource() {
        return TBungee.getServer().getConsole();
    }

    @Override
    public void sendMessage(String message) {
        getSource().sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        getSource().sendMessage(net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().serialize(message));
    }
}
