# Easy Player

Easy Player is a simple and efficient audio player designed specifically for gamers. It not only includes basic audio play and stop functions, but also supports quick searches for local audio files. The most notable feature is its ability to float above full-screen game interfaces, allowing users to access audio functionality without frequently switching windows.
[点击这里查看中文版本](README_zh.md)

## Key Features
- **Basic Audio Playback**: Supports various common audio file formats, enabling users to easily play and stop audio.
- **Quick Search**: Users can quickly find locally stored audio files by entering their names, saving valuable time.
- **Always on Top**: The player can stay on top of other windows, ensuring it remains accessible during gameplay.

## Project Dependencies
Easy Player relies on the following library versions, please ensure you have the respective dependencies installed:
- **Java**: `JDK 23`
- **Maven**: `3.9.7`
- **JavaFX**: `23.0.1`

## Compatibility
Currently, Easy Player **only supports Windows operating systems**.

## Quick Start
To quickly get started with Easy Player, please follow these steps:

```bash
# Clone the repository
git clone https://github.com/wzace123/ezplayer.git

# Navigate to the project directory
cd ezplayer

# Run the player
mvn clean javafx:run
```

If you prefer using an installation package, please download the [EZPlayer installer](https://github.com/wzace123/ezplayer/releases/tag/ezplayer-v1.0.0).

## Usage Instructions
1. After starting the player, you will see a simple user interface.
2. Before using the search box to input audio files, please configure the directory for local audio files in the settings.
3. Click on an audio file in the playlist to start playing, and click the stop button to stop playback.
4. In the settings, you can adjust the transparency of the player to minimize interference with other operations.

This design not only enhances the gaming experience but also reduces distractions caused by window switching, allowing users to focus more on gameplay and interactions with teammates.

We hope you enjoy using Easy Player and that it makes your gaming experience more enjoyable!