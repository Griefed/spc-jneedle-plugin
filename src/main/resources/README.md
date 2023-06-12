# NekoDetector Plugin for ServerPackCreator

This is a plugin for [ServerPackCreator](https://github.com/Griefed/ServerPackCreator)

**NOTE:** This plugin requires at least [ServerPackCreator version 4.1.5](https://github.com/Griefed/ServerPackCreator/releases/tag/4.1.5) 

This plugin, once installed, automatically scans your modpack and server pack for infections when you start the generation
of a new server pack and informs you about any found infections, or if no infections were found.

This plugin currently performs three scans:
- your modpack before generating the server pack
- the server pack after it was generated, before any ZIP-archive is created
- final scan after every other task has completed

![example](img/example-scan.png)

## What is fractureiser?

The fractureiser malware, once you run it, infects any jar it is able to find. This plugin incorporates [nekodetector](https://github.com/MCRcortex/nekodetector)
to automatically scan your modpack and server pack for infections.

For more information about the malware, please refer to the [information document](https://github.com/fractureiser-investigation/fractureiser/blob/main/README.md).

## It found something, what now?

If the scans returned positive, refer to the [users document](https://github.com/fractureiser-investigation/fractureiser/blob/main/docs/users.md)
for more information and help on what to do next.

## How do I install this plugin?

Your ServerPackCreator installation has a folder called `plugins`.
Download the spc-nekodetector-plugin JAR-file from the latest release and put it into said `plugin`-folder.
If you've had ServerPackCreator running whilst installing the plugin, you need to restart ServerPackCreator.

Afterwards, every generation of a server pack will run the aforementiond scans.