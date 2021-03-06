package com.algorithmjunkie.mc.proxychannels

import co.aikar.commands.BungeeCommandManager
import com.algorithmjunkie.mc.proxychannels.channel.ChannelManager
import com.algorithmjunkie.mc.proxychannels.command.ProxyChannelsCommand
import com.algorithmjunkie.mc.proxychannels.listener.ChatListener
import com.algorithmjunkie.mc.proxychannels.listener.PlayerListener
import com.algorithmjunkie.mc.proxychannels.listener.RedisBungeeListener
import com.imaginarycode.minecraft.redisbungee.RedisBungee
import net.md_5.bungee.api.plugin.Plugin

class ProxyChannelsPlugin : Plugin() {
    lateinit var commandManager: BungeeCommandManager
    lateinit var channelManager: ChannelManager
    val redisBungeeChannelName = "proxyChannelMessage"

    override fun onEnable() {
        instance = this

        commandManager = BungeeCommandManager(this)
        channelManager = ChannelManager(this)
        channelManager.channelsConfig.reload()

        commandManager.registerCommand(ProxyChannelsCommand(this))

        val pluginManager = proxy.pluginManager
        pluginManager.registerListener(this, ChatListener(this))
        pluginManager.registerListener(this, PlayerListener(this))

        if (isRedisBungeePresent()) {
            RedisBungee.getApi().registerPubSubChannels(redisBungeeChannelName)
            pluginManager.registerListener(this, RedisBungeeListener(this))
        }
    }

    internal fun isRedisBungeePresent(): Boolean {
        return proxy.pluginManager.getPlugin("RedisBungee") != null
    }

    companion object {
        lateinit var instance: ProxyChannelsPlugin
    }
}