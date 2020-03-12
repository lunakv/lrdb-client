# La Radio di Biagi

## Goal
The goal of this project was to create a web server transmitting messages and an Android app replaying those messages on subscribed devices in morse code. This would server as a modern equivalent of the wireless telegraphs of old. It was comissioned for a summer camp for young in 2019.

This repository contains the client portion of the project as well as general documentation. Go to the [lrdb-server](https://github.com/lunakv/lrdb-server) repository to learn specifically about the project's server side.

## Warnings
### Reproducibility
Do not try to build the app directly from this source. It doesn't contain all the necessary source files, for example the Google API key needed for proper function of Firebase, and some configuration strings were intentionally obfuscated. You can download a packaged binary from the [Releases](https://github.com/lunakv/lrdb-client/releases) tab.

### Language 🇮🇹
While the source code itself is in English, for flavor reasons, all the resource strings are in Italian. Their function should be identifiable from the resource names.

## Usage
Once you download the app, open the home screen and subscribe to the message channel (Inizia ad ascoltare). When subscribed, make sure you have your ringtone volume turned up so you can hear the messages.

When the client receives a new message from the server, it vibrates, alerting the user a new message is incoming. Then it plays the received message twice, with a short jingle indicating the beginning and end of each message.

If you wish to stop receiving messages, you can unsubscribe on the home screen (Smetti di ascoltare).

The application also comes with a morse code cheat sheet and a handy morse code notepad, both accessible through the sidebar.

## Developers
You can find the developer documentation for the project in the [Wiki](https://github.com/lunakv/lrdb-client/wiki)
