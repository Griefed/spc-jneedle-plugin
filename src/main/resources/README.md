# jNeedle Plugin for ServerPackCreator

This is a plugin for [ServerPackCreator](https://github.com/Griefed/ServerPackCreator)

**NOTE:** This plugin requires at least [ServerPackCreator version 4.1.5](https://github.com/Griefed/ServerPackCreator/releases/tag/4.1.5) 

This plugin, once installed, automatically scans your modpack and server pack, using [KosmX's jNeedle malware detection tool](https://github.com/KosmX/jneedle),
for infections when you start the generation of a new server pack and informs you about any found infections,
or if no infections were found.

This plugin currently performs two scans:
- your modpack before generating the server pack
- final scan after every other task has completed

## It found something, what now?

If the scans returned positive, refer to the [users document](https://github.com/fractureiser-investigation/fractureiser/blob/main/docs/users.md)
for more information and help on what to do next. The linked document should at least get you started on how to clean your system.
Though it is strongly recommended to use more sophisticated methods of cleaning your system, for example with a professional
malware scanner which can move things into quarantine, or outright clean your system of infections.

## How do I install this plugin?

Your ServerPackCreator installation has a folder called `plugins`.
Download the spc-jneedle-plugin JAR-file from the latest release and put it into said `plugin`-folder.
If you've had ServerPackCreator running whilst installing the plugin, you need to restart ServerPackCreator.

Afterward, every generation of a server pack will run the aforementioned scans.