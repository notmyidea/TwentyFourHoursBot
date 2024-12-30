# TwentyFourHourBot

TwentyFourHourBot is a Discord bot built using JDA (Java Discord API) that allows you to register a channel for automatic message deletion every 24 hours. When a channel is registered, an embed message is sent and pinned to indicate that the channel is now a 24-hour deletion candidate. Every day at 12 PM UTC, all messages in the registered channel, except the pinned embed, will be deleted.

## Features

- Register a channel for 24-hour message deletion.
- Send and pin an embed message in the registered channel.
- Automatically delete all messages except the pinned embed every 24 hours at 12 PM UTC.

## Prerequisites

- Java 11 or higher
- Maven
- A Discord bot token

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/notmyidea/twentyfourhourbot.git
cd twentyfourhourbot
```

### Configure the Bot

* Create a .env file in the root directory of the project.
```BOT_TOKEN=your_disorcd_bot_token```

### Run the Bot using java -jar


## Usage

### Register a Channel

To register a channel for 24-hour message deletion, use the /register command in the desired channel. An embed message will be sent and pinned to indicate that the channel is now a 24-hour deletion candidate.

### Unregister a Channel

To unregister a channel from 24-hour message deletion, use the /unregister command in the desired channel.

## License
I put barely any work into this project and you will notice, wouldn't run this without modifying it yourself, saving the channel id just isn't useful for me so you'll have to do it yourself. No license, do whatever you want with it.
