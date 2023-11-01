<img align="right" src="./assets/ketting.png?raw=true" height="150" width="150">

# Ketting

Ketting is a fork of [MinecraftForge](https://github.com/MinecraftForge/MinecraftForge/)
that enables the use of Bukkit plugins on Forge servers.

## Notice

Ketting is still in development and is not ready for production use.

### PATCHES DONE
Bukkit: 0 / 528

## How does it work?

Ketting combines CraftBukkit and Spigot patches with Forge's patches
to create a server that can run both Forge mods and Bukkit plugins together
(hopefully) without any issues. Ketting will also include some self-made patches
to make sure that the server runs as smoothly as possible.

## How can I use it?

<b>Make sure that you have Java 17 or higher installed on your system.</b>

- Step 1: Download the latest version of Ketting from the [releases page](https://github.com/kettingpowered/Ketting-1-20-x/releases).

- Step 2: Place the downloaded jar in an empty (or already existing) server folder.

- Step 3: Run the jar file using the following command:
  ```bash
  java -jar ketting-<version>-server.jar
  ```

- Step 4: Enjoy!

## How can I contribute?

Please report any issues that you find on the [issues page](https://github.com/kettingpowered/Ketting-1-20-x/issues).
Make sure that you are using the latest version of Ketting before reporting an issue.
Contributions are welcome, but please make sure that your code is clean and readable.

## How can I build it myself?

Before building Ketting, I would recommend to first build the latest
Forge MDK from [their website](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.2.html) to prevent some issues with the build.
<br>

<b>If you encounter the 'client srg not found' issue, please follow the step above.</b>
<br>
<b>Also, please make sure that you are using JDK 17 or higher.</b>

Ketting uses the Gradle build system, so you can build it by running the following command:

```bash
./gradlew build
```
To create a jar, run

```bash
./gradlew kettingJar
```
(this might take a while, but when it's done, you can find the jar in `build/libs` under `projects/forge`)